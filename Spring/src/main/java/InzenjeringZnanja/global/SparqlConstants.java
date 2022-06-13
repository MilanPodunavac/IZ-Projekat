package InzenjeringZnanja.global;

public class SparqlConstants {
    public static final String Prefix = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX ontology_instances: <https://raw.githubusercontent.com/MilanPodunavac/IZ-Projekat/main/ontology_instances_xml.owl#>\n" +
            "PREFIX ontology_classes: <https://raw.githubusercontent.com/MilanPodunavac/IZ-Projekat/main/ontology_classes_xml.owl#>\n" +
            "PREFIX iz: <https://github.com/MilanPodunavac/IZ-Projekat#>\n" +
            "BASE <https://github.com/MilanPodunavac/IZ-Projekat#>\n";

    public static final String SELECT_URL = "http://localhost:3030/IZ-Projekat/sparql";

    public static final String UPDATE_URL = "http://localhost:3030/IZ-Projekat/update";
}
