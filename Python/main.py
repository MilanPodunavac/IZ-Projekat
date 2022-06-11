from flask import Flask, render_template, flash, redirect, url_for, request, session, logging
from owlready2 import *

import local_directory
from ontology import *


app = Flask(__name__)


@app.route('/Motherboard/<motherboard_name>', methods=['GET'])
def get_motherboards(motherboard_name):
    motherboards = instances.search_one(type=classes.search_one(iri=base_iri+"Motherboard"), has_a_name=motherboard_name)
    return str(motherboards)


@app.route('/Motherboard', methods=['POST'])
def post_motherboard():
    motherboard_name = request.json["name"]
    with instances:
        motherboard_class = classes.search_one(iri=base_iri+"Motherboard")
        motherboard = motherboard_class(motherboard_name, has_a_name=motherboard_name)
    return str(motherboard)


if __name__ == '__main__':
    app.run(debug=True)

