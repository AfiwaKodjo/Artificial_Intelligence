package cp;

import java.util.*;

import modelling.*;

public class Main {
    public static void main(String[] args){

       //Création des instances de Variable et BooleanVariable
        Variable variable1 = new Variable("X", Set.of(1, 2, 3));
        Variable variable2 = new Variable("Y", Set.of(4, 5, 6));

        Set<Object> domains = new HashSet<>();
        domains.add(7);
        domains.add(8);
        domains.add(9);


        BooleanVariable y = new BooleanVariable("Y");
        Set<Variable> variables = new HashSet<>();
        variables.add(variable1);
        variables.add(variable2);
        Map<Variable, Set<Object>> var = new HashMap<>();
        var.put(variable1, domains);
        var.put(variable2,domains);


        // Création des contraintes
        UnaryConstraint unaryConstraint = new UnaryConstraint(variable1, Set.of(1,2));
        Implication implication = new Implication(variable1, Set.of(1), y, Set.of(true));
        DifferenceConstraint differenceConstraint = new DifferenceConstraint(variable1, y);
        Set<Constraint> constraints = new HashSet<>();
        //Ensemble des contraintes
        constraints.add(unaryConstraint);
        constraints.add(implication);
        constraints.add(differenceConstraint);
     

       BacktrackSolver backtrack = new BacktrackSolver(variables, constraints);
       System.out.println(backtrack.solve());

        //Test de la classe ArcConsistency
        ArcConsistency arcConsistency = new ArcConsistency(constraints);
        System.out.println(arcConsistency.enforceNodeConsistency(var));
        System.out.println("--------------------------------");
        System.out.println(arcConsistency.revise(variable1, domains, variable2, domains));
        System.out.println(arcConsistency.ac1(var));
   
       //Test de MACSolver
       System.out.println("MACSolver");
       MACSolver macSolver = new MACSolver(variables, constraints);
       System.out.println(""+macSolver.solve());

       //Test de NbConstraintsVariableHeuristic
       System.out.println("--Heuristique--");
       NbConstraintsVariableHeuristic heuristic = new NbConstraintsVariableHeuristic(constraints, false);
       System.out.println(heuristic.best(variables, var));

       //Test de DomainSizeVariableHeuristic
       DomainSizeVariableHeuristic variableHeuristic = new DomainSizeVariableHeuristic(false);
       System.out.println(variableHeuristic.best(variables, var));

       //Test de RandomValueHeuristic
       Random random = new Random();
       RandomValueHeuristic randomValueHeuristic = new RandomValueHeuristic(random);
       System.out.println(randomValueHeuristic.ordering(variable1, domains));
    }
    
}
