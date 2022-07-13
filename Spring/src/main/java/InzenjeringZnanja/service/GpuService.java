package InzenjeringZnanja.service;

import InzenjeringZnanja.dto.GpuDto;
import InzenjeringZnanja.global.SparqlConstants;
import org.apache.jena.query.*;
import org.springframework.stereotype.Service;

@Service
public class GpuService {

    public GpuDto Get(String name){
        String selectString = SparqlConstants.Prefix +
                "SELECT  ?name ?cost ?GPU_6pin_connectors ?GPU_8pin_connectors ?GPU_core_boost_clock ?GPU_core_clock ?GPU_DirectX_Version ?GPU_DP_connectors ?GPU_HDMI_connectors ?GPU_memory_bus ?GPU_memory_type ?GPU_recommended_system_power ?GPU_VGA_connectors ?GPU_width ?length ?thermal_design_power ?is_manufactured_by \n" +
                "WHERE {\n" +
                "?gpu rdf:type iz:Graphics_card .\n" +
                "?gpu iz:has_a_name \"" + name + "\".\n" +
                "  ?gpu rdf:type iz:Graphics_card .\n" +
                "  OPTIONAL {?gpu iz:has_a_name ?name ;}\n" +
                "  OPTIONAL {?gpu iz:costs ?cost .}\n" +
                "  OPTIONAL {?gpu iz:GPU_6pin_connectors ?GPU_6pin_connectors .}\n" +
                "  OPTIONAL {?gpu iz:GPU_8pin_connectors ?GPU_8pin_connectors .}\n" +
                "  OPTIONAL {?gpu iz:GPU_core_boost_clock ?GPU_core_boost_clock .}\n" +
                "  OPTIONAL {?gpu iz:GPU_core_clock ?GPU_core_clock .}\n" +
                "  OPTIONAL {?gpu iz:GPU_DirectX_Version ?GPU_DirectX_Version .}\n" +
                "  OPTIONAL {?gpu iz:GPU_DP_connectors ?GPU_DP_connectors .}\n" +
                "  OPTIONAL {?gpu iz:GPU_HDMI_connectors ?GPU_HDMI_connectors .}\n" +
                "  OPTIONAL {?gpu iz:GPU_memory_bus ?GPU_memory_bus .}\n" +
                "  OPTIONAL {?gpu iz:GPU_memory_type ?GPU_memory_type .}\n" +
                "  OPTIONAL {?gpu iz:GPU_recommended_system_power ?GPU_recommended_system_power .}\n" +
                "  OPTIONAL {?gpu iz:GPU_VGA_connectors ?GPU_VGA_connectors .}\n" +
                "  OPTIONAL {?gpu iz:GPU_width ?GPU_width .}\n" +
                "  OPTIONAL {?gpu iz:length ?length .}\n" +
                "  OPTIONAL {?gpu iz:thermal_design_power ?thermal_design_power .}\n" +
                "  OPTIONAL {?gpu iz:is_manufactured_by ?is_manufactured_by .}\n" +
                "}";

        Query query = QueryFactory.create(selectString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        GpuDto gpu = new GpuDto();
        if (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            gpu.setName((solution.getLiteral("name") != null) ? solution.getLiteral("name").getString() : null);
            gpu.setPrice((solution.getLiteral("cost") != null) ? solution.getLiteral("cost").getFloat() : -1);
            gpu.setSixPinConnectors((solution.getLiteral("GPU_6pin_connectors") != null) ? solution.getLiteral("GPU_6pin_connectors").getInt() : -1);
            gpu.setEightPinConnectors((solution.getLiteral("GPU_8pin_connectors") != null) ? solution.getLiteral("GPU_8pin_connectors").getInt() : -1);
            gpu.setCoreBoostClock((solution.getLiteral("GPU_core_boost_clock") != null) ? solution.getLiteral("GPU_core_boost_clock").getInt() : -1);
            gpu.setCoreClock((solution.getLiteral("GPU_core_clock") != null) ? solution.getLiteral("GPU_core_clock").getInt() : -1);
            gpu.setDirectXVersion((solution.getLiteral("GPU_DirectX_Version") != null) ? solution.getLiteral("GPU_DirectX_Version").getString() : null);
            gpu.setDpConnectors((solution.getLiteral("GPU_DP_connectors") != null) ? solution.getLiteral("GPU_DP_connectors").getInt() : -1);
            gpu.setHdmiConnectors((solution.getLiteral("GPU_HDMI_connectors") != null) ? solution.getLiteral("GPU_HDMI_connectors").getInt() : -1);
            gpu.setMemoryBus((solution.getLiteral("GPU_memory_bus") != null) ? solution.getLiteral("GPU_memory_bus").getInt() : -1);
            gpu.setMemoryType((solution.getLiteral("GPU_memory_type") != null) ? solution.getLiteral("GPU_memory_type").getInt() : -1);
            gpu.setRecommendedSystemPower((solution.getLiteral("GPU_recommended_system_power") != null) ? solution.getLiteral("GPU_recommended_system_power").getInt() : -1);
            gpu.setVgaConnectors((solution.getLiteral("GPU_VGA_connectors") != null) ? solution.getLiteral("GPU_VGA_connectors").getInt() : -1);
            gpu.setWidth((solution.getLiteral("GPU_width") != null) ? solution.getLiteral("GPU_width").getFloat() : -1);
            gpu.setLength((solution.getLiteral("length") != null) ? solution.getLiteral("length").getFloat() : -1);
            gpu.setTdp((solution.getLiteral("thermal_design_power") != null) ? solution.getLiteral("thermal_design_power").getInt() : -1);
            gpu.setManufacturer((solution.getLiteral("is_manufactured_by") != null) ? solution.getLiteral("is_manufactured_by").getString() : null);
        }
        return gpu;
    }

}
