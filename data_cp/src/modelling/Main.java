package modelling;

import java.util.*;

public class Main{
    public static void main(String[] args) {

        // Création des instances de Variable et BooleanVariable
        Variable x = new Variable("X", Set.of(1, 2, 3));
        BooleanVariable y = new BooleanVariable("Y");

        // Création des contraintes
        UnaryConstraint unaryConstraint = new UnaryConstraint(x, Set.of(1, 2));
        Implication implication = new Implication(x, Set.of(1), y, Set.of(true));
        DifferenceConstraint differenceConstraint = new DifferenceConstraint(x, y);

        // Création des instanciations
        Map<Variable, Object> instanciation1 = new HashMap<>();
        instanciation1.put(x, 1);
        instanciation1.put(y, true);

        Map<Variable, Object> instanciation2 = new HashMap<>();
        instanciation2.put(x, 3);
        instanciation2.put(y, false);

        // Vérification de si ou non les contraintes sont satisfaites
        System.out.println("Exemple 1:");

        System.out.println("UnaryConstraint satisfaite: " + unaryConstraint.isSatisfiedBy(instanciation1));
        System.out.println("Implication satisfaite: " + implication.isSatisfiedBy(instanciation1));
        System.out.println("DifferenceConstraint satisfaite: " + differenceConstraint.isSatisfiedBy(instanciation1));

        System.out.println("\nExemple 2:");
        System.out.println("UnaryConstraint satisfaite: " + unaryConstraint.isSatisfiedBy(instanciation2));
        System.out.println("Implication satisfaite: " + implication.isSatisfiedBy(instanciation2));
        System.out.println("DifferenceConstraint satisfaite: " + differenceConstraint.isSatisfiedBy(instanciation2));
       

    }
}