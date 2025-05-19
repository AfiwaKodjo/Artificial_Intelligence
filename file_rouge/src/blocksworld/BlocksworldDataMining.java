package blocksworld;

import modelling.*;
import java.util.*;

public class BlocksworldDataMining {
    private final int nbBlocks;
    private final int nbPiles;
    private final Set<BooleanVariable> allVariables;

    public BlocksworldDataMining(int nbBlocks, int nbPiles) {
        if (nbBlocks < 0 || nbPiles < 0) {
            throw new IllegalArgumentException("Le nombre de blocs et de piles doit être positif");
        }
        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;
        this.allVariables = createAllVariables();
    }

    // Méthode qui retourne toutes les variables booléennes possibles
    public Set<BooleanVariable> getAllVariables() {
        return new HashSet<>(allVariables);
    }

    // Méthode qui convertit un état en instance
    public Set<BooleanVariable> createInstance(List<List<Integer>> state) {
        if (state == null) {
            throw new IllegalArgumentException("L'état ne peut pas être null");
        }

        Set<BooleanVariable> instance = new HashSet<>();
        Set<Integer> blocksWithAbove = new HashSet<>();
        Set<Integer> usedPiles = new HashSet<>();

        // Traitement de chaque pile
        for (int p = 0; p < state.size(); p++) {
            List<Integer> pile = state.get(p);
            if (!pile.isEmpty()) {
                // Marquer la pile comme utilisée
                usedPiles.add(p + 1);

                // Le bloc du bas est sur la table
                instance.add(new BooleanVariable("onTable" + pile.get(0) + "_" + (p + 1)));

                // Traiter les relations entre blocs dans la pile
                for (int i = 0; i < pile.size() - 1; i++) {
                    int lower = pile.get(i);
                    int upper = pile.get(i + 1);
                    instance.add(new BooleanVariable("on" + upper + "_" + lower));
                    blocksWithAbove.add(lower);
                }
            }
        }

        // Ajouter fixed_b pour tous les blocs sauf ceux en haut des piles
        for (int b = 0; b < nbBlocks; b++) {
            //if (blocksWithAbove.contains(b)) {
                instance.add(new BooleanVariable("fixed" + b));
            //}
        }

        // Ajouter free_p pour les piles vides
        for (int p = 1; p <= nbPiles; p++) {
            if (!usedPiles.contains(p)) {
                instance.add(new BooleanVariable("free" + p));
            }
        }

        return instance;
    }

    private Set<BooleanVariable> createAllVariables() {
        Set<BooleanVariable> vars = new HashSet<>();

        // Variables on_b_b' pour chaque paire de blocs différents
        for (int b1 = 0; b1 < nbBlocks; b1++) {
            for (int b2 = 0; b2 < nbBlocks; b2++) {
                if (b1 != b2) {
                    vars.add(new BooleanVariable("on" + b1 + "_" + b2));
                }
            }
        }

        // Variables onTable_b_p pour chaque bloc et pile
        for (int b = 0; b < nbBlocks; b++) {
            for (int p = 1; p <= nbPiles; p++) {
                vars.add(new BooleanVariable("onTable" + b + "_" + p));
            }
        }

        // Variables fixed_b pour chaque bloc
        for (int b = 0; b < nbBlocks; b++) {
            vars.add(new BooleanVariable("fixed" + b));
        }

        // Variables free_p pour chaque pile
        for (int p = 1; p <= nbPiles; p++) {
            vars.add(new BooleanVariable("free" + p));
        }

        return vars;
    }
}