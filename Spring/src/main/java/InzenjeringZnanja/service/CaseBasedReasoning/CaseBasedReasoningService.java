package InzenjeringZnanja.service.CaseBasedReasoning;

import InzenjeringZnanja.model.*;
import org.springframework.stereotype.Service;
import ucm.gaia.jcolibri.casebase.LinealCaseBase;
import ucm.gaia.jcolibri.cbraplications.StandardCBRApplication;
import ucm.gaia.jcolibri.cbrcore.*;
import ucm.gaia.jcolibri.exception.ExecutionException;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.EqualsStringIgnoreCase;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import ucm.gaia.jcolibri.method.retrieve.selection.SelectCases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CaseBasedReasoningService implements StandardCBRApplication {

    private Connector _connector;  /** Connector object */
    private CBRCaseBase _caseBase;  /** CaseBase object */
    private NNConfig _simConfig;  /** KNN configuration */
    private List<PersonalComputer> result;

    @Override
    public void configure() throws ExecutionException {
        _connector =  new SparqlConnector();

        _caseBase = new LinealCaseBase();  // Create a Lineal case base for in-memory organization

        _simConfig = new NNConfig(); // KNN configuration
        _simConfig.setDescriptionSimFunction(new Average());  // global similarity function = average

        _simConfig.addMapping(new Attribute("cpu", PersonalComputer.class), new Average());
        _simConfig.addMapping(new Attribute("motherboard", PersonalComputer.class), new Average());
        _simConfig.addMapping(new Attribute("gpu", PersonalComputer.class), new Average());
        _simConfig.addMapping(new Attribute("ram", PersonalComputer.class), new Average());
        _simConfig.addMapping(new Attribute("disk", PersonalComputer.class), new Average());

        _simConfig.addMapping(new Attribute("chipset", Motherboard.class), new EqualsStringIgnoreCase());
        _simConfig.addMapping(new Attribute("ramSlots", Motherboard.class), new Interval(2));
        _simConfig.addMapping(new Attribute("memoryType", Motherboard.class), new EqualsStringIgnoreCase());
        _simConfig.addMapping(new Attribute("maximumMemorySize", Motherboard.class), new Interval(32));
        _simConfig.addMapping(new Attribute("maximumMemoryFrequency", Motherboard.class), new Interval(100));

        _simConfig.addMapping(new Attribute("cores", CentralProcessingUnit.class), new Interval(2));
        _simConfig.addMapping(new Attribute("manufacturer", CentralProcessingUnit.class), new EqualsStringIgnoreCase());
        _simConfig.addMapping(new Attribute("frequency", CentralProcessingUnit.class), new Interval(0.1));
        _simConfig.addMapping(new Attribute("thermalDesignPower", CentralProcessingUnit.class), new Interval(10));

        _simConfig.addMapping(new Attribute("capacity", RandomAccessMemory.class), new Interval(2));
        _simConfig.addMapping(new Attribute("numberOfModules", RandomAccessMemory.class), new Interval(2));
        _simConfig.addMapping(new Attribute("frequency", RandomAccessMemory.class), new Interval(333));

        _simConfig.addMapping(new Attribute("manufacturer", GraphicsCard.class), new EqualsStringIgnoreCase());
        _simConfig.addMapping(new Attribute("memoryCapacity", GraphicsCard.class), new Interval(2));
        _simConfig.addMapping(new Attribute("tdp", GraphicsCard.class), new Interval(10));

        _simConfig.addMapping(new Attribute("type", MemoryDisk.class), new EqualsStringIgnoreCase());
        _simConfig.addMapping(new Attribute("capacity", MemoryDisk.class), new Interval(50));
        _simConfig.addMapping(new Attribute("speed", MemoryDisk.class), new Interval(100));


        // Equal - returns 1 if both individuals are equal, otherwise returns 0
        // Interval - returns the similarity of two number inside an interval: sim(x,y) = 1-(|x-y|/interval)
        // Threshold - returns 1 if the difference between two numbers is less than a threshold, 0 in the other case
        // EqualsStringIgnoreCase - returns 1 if both String are the same despite case letters, 0 in the other case
        // MaxString - returns a similarity value depending of the biggest substring that belong to both strings
        // EnumDistance - returns the similarity of two enum values as the their distance: sim(x,y) = |ord(x) - ord(y)|
        // EnumCyclicDistance - computes the similarity between two enum values as their cyclic distance
        // Table - uses a table to obtain the similarity between two values. Allowed values are Strings or Enums. The table is read from a text file.
        // TableSimilarity(List<String> values).setSimilarity(value1,value2,similarity)
    }

    @Override
    public CBRCaseBase preCycle() throws ExecutionException {
        _caseBase.init(_connector);
        java.util.Collection<CBRCase> cases = _caseBase.getCases();
        for (CBRCase c: cases)
            System.out.println(c.getDescription());
        return _caseBase;
    }

    @Override
    public void cycle(CBRQuery cbrQuery) throws ExecutionException {
        result = new ArrayList<>();
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), cbrQuery, _simConfig);
        eval = SelectCases.selectTopKRR(eval, 5); // STAVITI NA 5 KASNIJE
        System.out.println("Retrieved cases:");
        for (RetrievalResult nse : eval){
            System.out.println(nse.get_case().getDescription() + " -> " + nse.getEval());
            result.add((PersonalComputer) nse.get_case().getDescription());
        }
    }

    @Override
    public void postCycle() throws ExecutionException {

    }

    public List<PersonalComputer> start(PersonalComputer pc){
        try{
            configure();
            preCycle();
            CBRQuery query = new CBRQuery();
            query.setDescription(pc);
            cycle(query);
            postCycle();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
