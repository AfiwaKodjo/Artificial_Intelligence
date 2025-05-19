package blocksworld;

import java.util.*;
import modelling.*;
import planning.*;

public class BasicActionWorld extends Blocksworld {
    private Set<Action> actions;

    public BasicActionWorld(int nbBlock, int nbPile) {
        super(nbBlock, nbPile);
        this.actions = buildActions();
    }

    private Set<Action> buildActions() {
        Set<Action> actions = new HashSet<>();

        // Pour chaque bloc à déplacer
        for (Variable onB : getScope().getOnB().values()) {
            // Pour chaque position possible du bloc
            for (Object value : onB.getDomain()) {
                // CAS 1: Bloc est sur un autre bloc
                if ((int) value >= 0) {
                    // Action 1: Bloc vers bloc
                    for (Variable fixedBlock : getScope().getFixedB().values()) {
                        int currentBlock = Integer.parseInt(onB.getName().substring(2));
                        int destBlock = Integer.parseInt(fixedBlock.getName().substring(5));

                        if (currentBlock != destBlock && (int) value != destBlock) {
                            // Préconditions
                            Map<Variable, Object> preconditions = new HashMap<>();
                            preconditions.put(onB, value); // bloc est à sa position initiale
                            preconditions.put(getScope().getFixedB().get(currentBlock), false); // bloc est déplaçable
                            preconditions.put(fixedBlock, false); // destination est libre

                            // Effets
                            Map<Variable, Object> effects = new HashMap<>();
                            effects.put(onB, destBlock); // déplace le bloc
                            effects.put(fixedBlock, true); // destination devient fixée
                            effects.put(getScope().getFixedB().get((int) value), false); // ancienne position devient
                                                                                         // libre

                            actions.add(new BasicAction(preconditions, effects, 1));
                        }
                    }

                    // Action 2: Bloc vers pile
                    for (Variable freePile : getScope().getFreeP().values()) {
                        Map<Variable, Object> preconditions = new HashMap<>();
                        Map<Variable, Object> effects = new HashMap<>();

                        // Préconditions
                        preconditions.put(onB, value);
                        preconditions.put(getScope().getFixedB().get(Integer.parseInt(onB.getName().substring(2))),
                                false);
                        preconditions.put(freePile, true);

                        // Effets
                        effects.put(onB, Integer.parseInt(freePile.getName().substring(4))); // met dans la pile
                        effects.put(freePile, false); // pile n'est plus libre
                        effects.put(getScope().getFixedB().get((int) value), false);

                        actions.add(new BasicAction(preconditions, effects, 1));
                    }
                }
                // CAS 2: Bloc est dans une pile
                else {
                    // Action 3: Pile vers bloc
                    for (Variable fixedBlock : getScope().getFixedB().values()) {
                        int currentBlock = Integer.parseInt(onB.getName().substring(2));
                        int destBlock = Integer.parseInt(fixedBlock.getName().substring(5));

                        if (currentBlock != destBlock) {
                            Map<Variable, Object> preconditions = new HashMap<>();
                            preconditions.put(onB, value);
                            preconditions.put(getScope().getFixedB().get(currentBlock), false);
                            preconditions.put(fixedBlock, false);

                            Map<Variable, Object> effects = new HashMap<>();
                            effects.put(onB, destBlock);
                            effects.put(fixedBlock, true);
                            effects.put(getScope().getFreeP().get((int) value), true);

                            actions.add(new BasicAction(preconditions, effects, 1));
                        }
                    }

                    // Action 4: Pile vers pile
                    for (Variable freePile : getScope().getFreeP().values()) {
                        int pileValue = Integer.parseInt(freePile.getName().substring(4));
                        if (pileValue != (int) value) {
                            Map<Variable, Object> preconditions = new HashMap<>();
                            preconditions.put(onB, value);
                            preconditions.put(getScope().getFixedB().get(Integer.parseInt(onB.getName().substring(2))),
                                    false);
                            preconditions.put(freePile, true);

                            Map<Variable, Object> effects = new HashMap<>();
                            effects.put(onB, pileValue);
                            effects.put(freePile, false);
                            effects.put(getScope().getFreeP().get((int) value), true);

                            actions.add(new BasicAction(preconditions, effects, 1));
                        }
                    }
                }
            }
        }
        return actions;
    }

    public Set<Action> getActions() {
        return actions;
    }
}