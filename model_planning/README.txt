# Membres du Groupe :

- KODJO Afiwa Aimée   22312155
- DOSSEH Georges      22012628  


 
 ## Mode d'emploi : 
  - Rassurez vous d'etre à la racine du projet et tapez les commandes suivantes.
  - Compilation: 
  ```bash
  javac -d build -classpath 'lib/*' $(find src -name "*.java")
  ```
  - Exécutions:

  ##   Tests:
     
  ```bash
  planningTests: java -cp build:lib/planningtests.jar planning.Test
  modellingTests: java -cp build:lib/modellingtests.jar modelling.Test
  ```
  
  ## Pour voir la démo des classes, cette dernière est faite dans le Main de chaque package(modelling, planning)

  ```bash
  Main de modelling: java -cp build:lib/modellingtests.jar modelling.Main
  Main de planning: java -cp build:lib/planningtests.jar planning.Main
  ```


