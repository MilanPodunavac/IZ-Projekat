# IZ-Projekat
# PCStoreSystemForSupport

## Built with
- Frontend: React, Bootstrap
- Backend: Java

## Installation

1. Clone the repo
   ```sh
   git clone https://github.com/MilanPodunavac/IZ-Projekat
   ```
2. Frontend - open terminal in project directory and run:
   ```sh
   npm install
   ```
   ```sh
   npm run start
   ```

3. Backend - open Java project in Spring folder, refresh maven dependencies and run
- developed in IntelliJ
- if there are errors with dependencies, they might need to be manually imported through Project Structure -> Modules -> Dependencies
- if there is a problem with duplicated imported log4j .jar files, manual removal of one of them should fix it

4. Needs fuseki server running in the background, with dataset labeled "IZ-Projekat" and imported data from files named "ontology_instancecs_xml.owl" and "ontology_classes_xml.owl" (emphasis on _xml part)

- fuzzy logic described in "pc_usage.fcl"
- bayes breadowns described in "kvarovi.net"