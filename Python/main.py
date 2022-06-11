import string

from flask import Flask, render_template, flash, redirect, url_for, request, session, logging
from owlready2 import *


app = Flask(__name__)
onto_path.append("D:/ftn/GODINA4/LETNJI/IZ/Projekat/IZ-Projekat")
classes = get_ontology("https://raw.githubusercontent.com/MilanPodunavac/IZ-Projekat/feat/python-setup/ontology_classes_xml.owl")
classes.load()
print(list(classes.classes()))
instances = get_ontology("https://raw.githubusercontent.com/MilanPodunavac/IZ-Projekat/feat/python-setup/ontology_instances_xml.owl")
instances.load()
print(list(instances.individuals()))
ecomponent = list(classes.classes())[0]
with instances:
    class Motherboard(Thing):
        pass
    mb = classes.Motherboard
    motherboard = list(classes.search(iri = "https://github.com/MilanPodunavac/IZ-Projekat#Motherboard"))
    mb_instances = list(instances.search(type = motherboard))
    print(motherboard)
    print(mb_instances)


@app.route('/')
def index():
    processors = len(list(instances.individuals()))
    #motherboard = instances.search_one(is_a = classes.Motherboard, has_a_name = "motherboard ASUS Matična ploča PRIME A320M-K")
    return str(processors)


if __name__ == '__main__':
    app.run(debug=True)

