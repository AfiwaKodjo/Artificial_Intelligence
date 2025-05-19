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
  cpTests: java -cp build:lib/cptests.jar cp.Test
  dataminingTests: java -cp build:lib/dataminingtests.jar datamining.Test
  ```
  
  ## Pour voir la démo des classes, cette dernière est faite dans le Main de chaque package(cp, datamining)

  ```bash
  Main de cp: java -cp build:lib/cptests.jar cp.Main
  Main de datamining: java -cp build:lib/dataminingtests.jar datamining.Main
  ```


