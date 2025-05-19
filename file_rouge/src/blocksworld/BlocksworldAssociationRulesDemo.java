package blocksworld;

import modelling.*;
import datamining.*;
import java.util.*;

public class BlocksworldAssociationRulesDemo {
    public static void main(String[] args) {
        int nbBlocks = 5;
        int nbPiles = 3;
        int nbInstances = 10000;
        float minFrequency = 2.0f / 3.0f; 
        float minConfidence = 0.95f;

        BlocksworldDataMining bbw = new BlocksworldDataMining(nbBlocks, nbPiles);

        BooleanDatabase db = new BooleanDatabase(bbw.getAllVariables());

        // Generation des instances aleatoires
        Random random = new Random();
        System.out.println("Generation de " + nbInstances + " instances");
        for (int i = 0; i < nbInstances; i++) {
            // Creation d'un etat aleatoire
            List<List<Integer>> state = generateRandomState(nbBlocks, nbPiles, random);

            // Conversion en instance de variables booleennes
            Set<BooleanVariable> instance = bbw.createInstance(state);

            // Ajout à la base de donnees
            db.add(instance);
        }

        System.out.println("\nBase de donnees creee avec : ");
        System.out.println("- " + db.getTransactions().size() + " instances");
        System.out.println("- " + db.getItems().size() + " variables booleennes");

        // Extraction des motifs frequents
        System.out.println("\nExtraction des motifs frequents (frequence minimale = " + minFrequency + ")...");
        Apriori apriori = new Apriori(db);
        Set<Itemset> frequentItemsets = apriori.extract(minFrequency);

        System.out.println("\nMotifs frequents trouves : " + frequentItemsets.size());
        printFrequentItemsets(frequentItemsets);

        // Extraction des regles d'association
        System.out.println("\nExtraction des regles d'association (confiance minimale = " + minConfidence + ")...");
        BruteForceAssociationRuleMiner ruleMiner = new BruteForceAssociationRuleMiner(db);
        Set<AssociationRule> rules = ruleMiner.extract(minFrequency, minConfidence);

        System.out.println("\nRegles d'association trouvees : " + rules.size());
        printAssociationRules(rules);
    }

    private static List<List<Integer>> generateRandomState(int nbBlocks, int nbPiles, Random random) {
        List<List<Integer>> state = new ArrayList<>();
        for (int i = 0; i < nbPiles; i++) {
            state.add(new ArrayList<>());
        }

        // Liste des blocs disponibles
        List<Integer> availableBlocks = new ArrayList<>();
        for (int i = 0; i < nbBlocks; i++) {
            availableBlocks.add(i);
        }

        // Distribution aleatoire des blocs dans les piles
        while (!availableBlocks.isEmpty()) {
            int pileIndex = random.nextInt(nbPiles);
            int blockIndex = random.nextInt(availableBlocks.size());
            int block = availableBlocks.remove(blockIndex);
            state.get(pileIndex).add(block);
        }

        return state;
    }

    private static void printFrequentItemsets(Set<Itemset> itemsets) {
        List<Itemset> sortedItemsets = new ArrayList<>(itemsets);
        // Tri par taille puis par frequence
        sortedItemsets.sort((i1, i2) -> {
            int sizeCompare = Integer.compare(i1.getItems().size(), i2.getItems().size());
            if (sizeCompare != 0)
                return sizeCompare;
            return Float.compare(i2.getFrequency(), i1.getFrequency());
        });

        System.out.println("\nTop 10 motifs frequents les plus interessants :");
        int count = 0;
        for (Itemset itemset : sortedItemsets) {
            if (itemset.getItems().size() > 1) { // Affiche seulement les motifs de taille > 1
                System.out.println("Motif de taille " + itemset.getItems().size() +
                        " (frequence = " + String.format("%.3f", itemset.getFrequency()) + "):");
                for (BooleanVariable var : itemset.getItems()) {
                    System.out.println("  " + var.getName());
                }
                System.out.println();
                count++;
                if (count >= 10)
                    break;
            }
        }
    }

    private static void printAssociationRules(Set<AssociationRule> rules) {
        List<AssociationRule> sortedRules = new ArrayList<>(rules);
        // Tri par confiance puis par frequence
        sortedRules.sort((r1, r2) -> {
            int confCompare = Float.compare(r2.getConfidence(), r1.getConfidence());
            if (confCompare != 0)
                return confCompare;
            return Float.compare(r2.getFrequency(), r1.getFrequency());
        });

        System.out.println("\nTop 10 regles d'association les plus interessantes :");
        int count = 0;
        for (AssociationRule rule : sortedRules) {
            if (!rule.getPremise().isEmpty() && !rule.getConclusion().isEmpty()) {
                System.out.println("Regle (conf=" + String.format("%.3f", rule.getConfidence()) +
                        ", freq=" + String.format("%.3f", rule.getFrequency()) + "):");
                System.out.println("Premisse :");
                for (BooleanVariable var : rule.getPremise()) {
                    System.out.println("  " + var.getName());
                }
                System.out.println("Conclusion :");
                for (BooleanVariable var : rule.getConclusion()) {
                    System.out.println("  " + var.getName());
                }
                System.out.println();
                count++;
                if (count >= 10)
                    break;
            }
        }
    }
}