from owlready2 import get_ontology

classes = get_ontology("https://raw.githubusercontent.com/MilanPodunavac/IZ-Projekat/feat/python-setup/ontology_classes_xml.owl")
classes.load()
instances = get_ontology("https://raw.githubusercontent.com/MilanPodunavac/IZ-Projekat/feat/python-setup/ontology_instances_xml.owl")
instances.load()
base_iri = "https://github.com/MilanPodunavac/IZ-Projekat#"