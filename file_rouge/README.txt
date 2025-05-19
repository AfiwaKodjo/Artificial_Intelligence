# Membres du Groupe :

- DOSSEH Georges    
- KODJO Afiwa Aimée 

== Classes exécutables et démo ==
[Exercice 8] BlocksworldPlannerDemo.java

Compilation: javac -d build -cp ".:lib/*:src" src/blocksworld/*.java src/cp/*.java src/modelling/*.java src/planning/*.java src/datamining/*.java
Exécution: java -cp "build:lib/*" blocksworld.BlocksworldPlannerDemo

[Exercice 9] BlocksworldConstraintSolverDemo.java
Compilation: javac -d build -cp ".:lib/*:src" src/blocksworld/*.java src/cp/*.java src/modelling/*.java src/planning/*.java src/datamining/*.java
Exécution: java -cp "build:lib/*" blocksworld.BlocksworldConstraintSolverDemo


[Exercice 10] BlocksworldAdvancedConstraintsDemo.java
Compilation: javac -d build -cp ".:lib/*:src" src/blocksworld/*.java src/cp/*.java src/modelling/*.java src/planning/*.java src/datamining/*.java
Exécution: java -cp "build:lib/*" blocksworld.BlocksworldAdvancedConstraintsDemo


[Exercice 12] BlocksworldAssociationRulesDemo.java
Compilation: javac -d build -cp ".:lib/*:src" src/blocksworld/*.java src/cp/*.java src/modelling/*.java src/planning/*.java src/datamining/*.java
Exécution: java -cp "build:lib/*" blocksworld.BlocksworldAssociationRulesDemo

== Structure du projet et exercices ==

/blocksworld/
[Exercices 1-2]
  - BlocksworldVariables.java : Gestion des variables 
  - Blocksworld.java : Classe principale 
  - BlockswordConstraint.java : Contraintes de base

[Exercices 3-4]
  - RegularImplication.java : Contraintes régulières
  - IncreaseImplication.java : Contraintes croissantes

[Exercices 6-7]
  - BasicActionWorld.java : Génération des actions
  - BlocksworldHeuristic.java : Première heuristique
  - BlocksworldHeuristic2.java : Seconde heuristique

