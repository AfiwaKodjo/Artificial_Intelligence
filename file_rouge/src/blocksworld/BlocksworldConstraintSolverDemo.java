package blocksworld;

import cp.*;
import modelling.*;
import java.util.*;
import javax.swing.JFrame;
import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWIntegerGUI;

public class BlocksworldConstraintSolverDemo {
    private static void displaySolution(Map<Variable, Object> solution, BlocksworldVariables vars, String solverName) {
        if (solution != null) {
            System.out.println("\n" + solverName + " a trouve une solution :");

            // Affichage textuel
            for (Map.Entry<Variable, Object> entry : solution.entrySet()) {
                if (entry.getKey().getName().startsWith("On")) {
                    System.out.println(entry.getKey().getName() + " = " + entry.getValue());
                }
            }

            // Affichage graphique
            BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(vars.getNbBlock());

            // Construction de l'etat à partir de la solution
            for (int b = 0; b < vars.getNbBlock(); b++) {
                Variable onB = vars.getOnB().get(b);
                Object value = solution.get(onB);
                if (value instanceof Integer) {
                    int underBlock = (Integer) value;
                    if (underBlock >= 0) {
                        builder.setOn(b, underBlock);
                    }
                }
            }

            BWState<Integer> state = builder.getState();

            // Creation et affichage de la fenêtre
            BWIntegerGUI gui = new BWIntegerGUI(vars.getNbBlock());
            JFrame frame = new JFrame(solverName);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(gui.getComponent(state));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else {
            System.out.println("\n" + solverName + " n'a pas trouve de solution.");
        }
    }

    public static void main(String[] args) {
        int nbBlocks = 5;
        int nbPiles = 3;

        // Creation des variables
        BlocksworldVariables vars = new BlocksworldVariables(nbBlocks, nbPiles);

        // Creation des contraintes de base
        Set<Constraint> constraints = new HashSet<>();

        //Contraintes de difference entre blocs
        for (Variable onB1 : vars.getOnB().values()) {
            for (Variable onB2 : vars.getOnB().values()) {
                if (!onB1.equals(onB2)) {
                    constraints.add(new DifferenceConstraint(onB1, onB2));
                }
            }
        }

        //Contraintes d'implication pour les blocs fixes
        for (Variable onB : vars.getOnB().values()) {
            for (int b : vars.getOnB().keySet()) {
                Set<Object> s1 = new HashSet<>();
                s1.add(b);
                Set<Object> s2 = new HashSet<>();
                s2.add(true);
                constraints.add(new Implication(onB, s1, vars.getFixedB().get(b), s2));
            }
        }

        //Contraintes d'implication pour les piles libres
        for (Variable onB : vars.getOnB().values()) {
            for (int p : vars.getFreeP().keySet()) {
                Set<Object> s1 = new HashSet<>();
                s1.add(p);
                Set<Object> s2 = new HashSet<>();
                s2.add(false);
                constraints.add(new Implication(onB, s1, vars.getFreeP().get(p), s2));
            }
        }

        //Ajout des contraintes pour une configuration regulière
        for (Map.Entry<Integer, Variable> entry : vars.getOnB().entrySet()) {
            int currentBlock = entry.getKey();
            Variable currentOnB = entry.getValue();

            for (Map.Entry<Integer, Variable> otherEntry : vars.getOnB().entrySet()) {
                int otherBlock = otherEntry.getKey();
                if (currentBlock != otherBlock) {
                    Variable otherOnB = otherEntry.getValue();
                    constraints.add(new Implication(
                            currentOnB,
                            new HashSet<>(Collections.singleton(otherBlock)),
                            otherOnB,
                            new HashSet<>(Collections.singleton(currentBlock - (otherBlock - currentBlock)))));
                }
            }
        }

        // Creation de l'ensemble des variables
        Set<Variable> allVariables = new HashSet<>();
        allVariables.addAll(vars.getOnB().values());
        allVariables.addAll(vars.getFixedB().values());
        allVariables.addAll(vars.getFreeP().values());

        System.out.println("Test des solveurs sur une instance de monde des blocs regulier");
        System.out.println("Nombre de blocs: " + nbBlocks);
        System.out.println("Nombre de piles: " + nbPiles);
        System.out.println("Nombre de variables: " + allVariables.size());
        System.out.println("Nombre de contraintes: " + constraints.size());

        // Test avec BacktrackSolver
        System.out.println("\nTest avec BacktrackSolver:");
        long startTime = System.currentTimeMillis();
        BacktrackSolver backtrackSolver = new BacktrackSolver(allVariables, constraints);
        Map<Variable, Object> backtrackSolution = backtrackSolver.solve();
        System.out.println("Temps d'execution: " + (System.currentTimeMillis() - startTime) + " ms");
        displaySolution(backtrackSolution, vars, "BacktrackSolver");

        // Test avec MACSolver
        System.out.println("\nTest avec MACSolver:");
        startTime = System.currentTimeMillis();
        MACSolver macSolver = new MACSolver(allVariables, constraints);
        Map<Variable, Object> macSolution = macSolver.solve();
        System.out.println("Temps d'execution: " + (System.currentTimeMillis() - startTime) + " ms");
        displaySolution(macSolution, vars, "MACSolver");

        // Test avec HeuristicMACSolver
        System.out.println("\nTest avec HeuristicMACSolver:");
        VariableHeuristic varHeuristic = new DomainSizeVariableHeuristic(false);
        ValueHeuristic valHeuristic = new RandomValueHeuristic(new Random());
        startTime = System.currentTimeMillis();
        HeuristicMACSolver heuristicSolver = new HeuristicMACSolver(allVariables, constraints, varHeuristic,valHeuristic);
        Map<Variable, Object> heuristicSolution = heuristicSolver.solve();
        System.out.println("Temps d'execution: " + (System.currentTimeMillis() - startTime) + " ms");
        displaySolution(heuristicSolution, vars, "HeuristicMACSolver");
    }
}