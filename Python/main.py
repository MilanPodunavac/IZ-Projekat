from flask import Flask, render_template, flash, redirect, url_for, request, session, logging
from owlready2 import *


app = Flask(__name__)
onto_path.append("C:/Users/User/Desktop/Milan/IZ/IZ-Projekat")
classes = get_ontology("https://raw.githubusercontent.com/MilanPodunavac/IZ-Projekat/main/ontology_classes_xml.owl")
classes.load()
instances = get_ontology("https://raw.githubusercontent.com/MilanPodunavac/IZ-Projekat/main/ontology_instances_xml.owl")
instances.load()


@app.route('/')
def index():
    processor = instances.Processor("AMD_Processor_A6-9500E")
    return processor.has_a_name


if __name__ == '__main__':
    app.run(debug=True)

