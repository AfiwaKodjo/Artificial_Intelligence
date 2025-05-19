package blocksworld;

import cp.*;
import modelling.*;
import javax.swing.*;
import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWIntegerGUI;
import java.util.*;

public class BlocksworldAdvancedConstraintsDemo {
    public static void main(String[] args) {
        int nbBlocks = 5;
        int nbPiles = 3;

        // Creation des variables du monde des blocs
        BlocksworldVariables vars = new BlocksworldVariables(nbBlocks, nbPiles);

        // Ensemble des contraintes de base du monde des blocs
        Set<Constraint> baseConstraints = createBaseConstraints(vars);

        // Configuration des solveurs avec heuristiques
        VariableHeuristic varHeuristic = new DomainSizeVariableHeuristic(false);
        ValueHeuristic valHeuristic = new RandomValueHeuristic(new Random());

        // Ensemble des variables
        Set<Variable> allVariables = getAllVariables(vars);

        // Test 1: Configuration croissante uniquement
        System.out.println("\n=== TEST AVEC CONTRAINTES CROISSANTES ===");
        Set<Constraint> increasingConstraints = new HashSet<>(baseConstraints);
        addIncreasingConstraints(increasingConstraints, vars);
        testWithAllSolvers(allVariables, increasingConstraints, varHeuristic, valHeuristic);

        // Test 2: Configuration regulière uniquement
        System.out.println("\n=== TEST AVEC CONTRAINTES REGULIERES ===");
        Set<Constraint> regularConstraints = new HashSet<>(baseConstraints);
        addRegularConstraints(regularConstraints, vars);
        testWithAllSolvers(allVariables, regularConstraints, varHeuristic, valHeuristic);

        // Test 3: Configuration à la fois regulière et croissante
        System.out.println("\n=== TEST AVEC CONTRAINTES REGULIERES ET CROISSANTES ===");
        Set<Constraint> combinedConstraints = new HashSet<>(baseConstraints);
        addRegularConstraints(combinedConstraints, vars);
        addIncreasingConstraints(combinedConstraints, vars);
        testWithAllSolvers(allVariables, combinedConstraints, varHeuristic, valHeuristic);
    }

    private static Set<Constraint> createBaseConstraints(BlocksworldVariables vars) {
        Set<Constraint> constraints = new HashSet<>();

        // Contraintes de difference entre blocs
        for (Variable onB1 : vars.getOnB().values()) {
            for (Variable onB2 : vars.getOnB().values()) {
                if (!onB1.equals(onB2)) {
                    constraints.add(new DifferenceConstraint(onB1, onB2));
                }
            }
        }

        // Contraintes d'implication pour les blocs fixes
        for (Variable onB : vars.getOnB().values()) {
            for (int b : vars.getOnB().keySet()) {
                constraints.add(new Implication(
                        onB,
                        new HashSet<>(Collections.singleton(b)),
                        vars.getFixedB().get(b),
                        new HashSet<>(Collections.singleton(true))));
            }
        }

        // Contraintes d'implication pour les piles libres
        for (Variable onB : vars.getOnB().values()) {
            for (int p : vars.getFreeP().keySet()) {
                constraints.add(new Implication(
                        onB,
                        new HashSet<>(Collections.singleton(p)),
                        vars.getFreeP().get(p),
                        new HashSet<>(Collections.singleton(false))));
            }
        }

        return constraints;
    }

    private static void addIncreasingConstraints(Set<Constraint> constraints, BlocksworldVariables vars) {
        // Ajout des contraintes croissantes
        for (Map.Entry<Integer, Variable> entry : vars.getOnB().entrySet()) {
            int currentBlock = entry.getKey();
            Variable onB = entry.getValue();

            // Pour chaque valeur possible du domaine qui represente un bloc
            for (Object value : onB.getDomain()) {
                if (value instanceof Integer && (Integer) value >= 0) {
                    if (currentBlock < (Integer) value) {
                        // Si le bloc courant est plus petit que le bloc de destination,
                        // on ajoute une contrainte pour l'interdire
                        Set<Object> forbidden = new HashSet<>();
                        forbidden.add(value);
                        constraints.add(new UnaryConstraint(onB, forbidden));
                    }
                }
            }
        }
    }

    private static void addRegularConstraints(Set<Constraint> constraints, BlocksworldVariables vars) {
        // Ajout des contraintes pour une configuration regulière
        for (Map.Entry<Integer, Variable> entry : vars.getOnB().entrySet()) {
            int currentBlock = entry.getKey();
            Variable currentOnB = entry.getValue();

            for (Map.Entry<Integer, Variable> otherEntry : vars.getOnB().entrySet()) {
                int otherBlock = otherEntry.getKey();
                if (currentBlock != otherBlock) {
                    Variable otherOnB = otherEntry.getValue();
                    // Ajoute des implications pour maintenir des ecarts reguliers dans les piles
                    constraints.add(new Implication(
                            currentOnB,
                            new HashSet<>(Collections.singleton(otherBlock)),
                            otherOnB,
                            new HashSet<>(Collections.singleton(currentBlock - (otherBlock - currentBlock)))));
                }
            }
        }
    }

    private static Set<Variable> getAllVariables(BlocksworldVariables vars) {
        Set<Variable> allVariables = new HashSet<>();
        allVariables.addAll(vars.getOnB().values());
        allVariables.addAll(vars.getFixedB().values());
        allVariables.addAll(vars.getFreeP().values());
        return allVariables;
    }

    private static void testWithAllSolvers(Set<Variable> variables, Set<Constraint> constraints,
            VariableHeuristic varHeuristic, ValueHeuristic valHeuristic) {
        // Test avec BacktrackSolver
        System.out.println("\nBacktrackSolver:");
        long startTime = System.currentTimeMillis();
        BacktrackSolver backtrackSolver = new BacktrackSolver(variables, constraints);
        Map<Variable, Object> backtrackSolution = backtrackSolver.solve();
        System.out.println("Temps: " + (System.currentTimeMillis() - startTime) + " ms");
        printSolution(backtrackSolution);

        // Test avec MACSolver
        System.out.println("\nMACSolver:");
        startTime = System.currentTimeMillis();
        MACSolver macSolver = new MACSolver(variables, constraints);
        Map<Variable, Object> macSolution = macSolver.solve();
        System.out.println("Temps: " + (System.currentTimeMillis() - startTime) + " ms");
        printSolution(macSolution);

        // Test avec HeuristicMACSolver
        System.out.println("\nHeuristicMACSolver:");
        startTime = System.currentTimeMillis();
        HeuristicMACSolver heuristicSolver = new HeuristicMACSolver(variables, constraints, varHeuristic, valHeuristic);
        Map<Variable, Object> heuristicSolution = heuristicSolver.solve();
        System.out.println("Temps: " + (System.currentTimeMillis() - startTime) + " ms");
        printSolution(heuristicSolution);
    }

    private static void printSolution(Map<Variable, Object> solution) {
        if (solution != null) {
            System.out.println("Solution trouvee :");

            // Affichage textuel simplifie des variables OnB
            for (Map.Entry<Variable, Object> entry : solution.entrySet()) {
                if (entry.getKey().getName().startsWith("On")) {
                    System.out.println(entry.getKey().getName() + " = " + entry.getValue());
                }
            }

            // Creation de l'etat pour l'affichage graphique
            BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(5); // 5 blocs

            // On parcourt la solution pour configurer l'etat
            for (Map.Entry<Variable, Object> entry : solution.entrySet()) {
                if (entry.getKey().getName().startsWith("On")) {
                    int blockNum = Integer.parseInt(entry.getKey().getName().substring(2));
                    Object value = entry.getValue();
                    if (value instanceof Integer && (Integer) value >= 0) {
                        builder.setOn(blockNum, (Integer) value);
                    }
                }
            }

            BWState<Integer> state = builder.getState();

            // Creation et affichage de la fenêtre
            SwingUtilities.invokeLater(() -> {
                BWIntegerGUI gui = new BWIntegerGUI(5);
                JFrame frame = new JFrame("Solution Monde des Blocs");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(gui.getComponent(state));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        } else {
            System.out.println("Pas de solution trouvee");
        }
    }
}