package datamining;

import java.util.*;

import modelling.*;

public class Main {
    public static void main(String[] args) {
        // Création des instances de Variable et BooleanVariable
       // Variable x = new Variable("X", Set.of(1, 2, 3));
        BooleanVariable t = new BooleanVariable("T");
        BooleanVariable w = new BooleanVariable("W");
        BooleanVariable y = new BooleanVariable("Y");
        BooleanVariable z = new BooleanVariable("Z");

        Set<BooleanVariable> bVariables = new HashSet<>();
        bVariables.add(y);
        bVariables.add(z);
        bVariables.add(t);

        Set<BooleanVariable> bVariabless = new HashSet<>();
        bVariabless.add(t);
        // bVariabless.add(w);

        SortedSet<BooleanVariable> bVariables2 = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        bVariables2.add(t);
        bVariables2.add(w);

        SortedSet<BooleanVariable> bVariables3 = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        bVariables3.add(t);
        bVariables3.add(z);

        Collection<SortedSet<BooleanVariable>> var = new ArrayList<>();

        SortedSet<BooleanVariable> subset = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        subset.add(t);
        var.add(subset);

        SortedSet<BooleanVariable> subset2 = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        subset2.add(z);
        var.add(subset2);

        BooleanDatabase database = new BooleanDatabase(bVariables);

        database.add(bVariables);
        System.out.println("Affichage des items et des transactions\n");
        System.out.println(database.toString());

        Set<Itemset> items = new HashSet<>();
        // Ajout de tous les sous-ensembles possibles avec leurs fréquences
        items.add(new Itemset(bVariables, 1.0f));

        Set<BooleanVariable> yOnly = new HashSet<>();
        yOnly.add(y);
        items.add(new Itemset(yOnly, 1.0f));

        Set<BooleanVariable> zOnly = new HashSet<>();
        zOnly.add(z);
        items.add(new Itemset(zOnly, 1.0f));

        // Test de la classe Apriori
        System.out.println("\nAffichage des itemsSets: " + items);
        Apriori apriori = new Apriori(database);
        System.out.println("Affichage des itemsSets fréquents : ");
        System.out.println(apriori.frequentSingletons(0.5f));

        System.out.println("Affichage issu de la combinaison de 2 ensembles d'items : ");
        System.out.println(Apriori.combine(bVariables2, bVariables3));

        System.out.println("\nTest de allSubsetsFrequent:");
        System.out.println("Ensemble à tester: " + bVariables3);
        System.out.println("Sous-ensembles disponibles: " + var);
        System.out.println("Résultat: " + Apriori.allSubsetsFrequent(bVariables3, var));

        // Test de la méthode extract
        System.out.println("\nTest de la méthode extract:");

        System.out.println("Résultat: " + apriori.extract(1.0f));

        // Test de la classe BruteForceAssociationRuleMiner
        BruteForceAssociationRuleMiner ruleMiner = new BruteForceAssociationRuleMiner(database);
        System.out.println("Fréquence: " + BruteForceAssociationRuleMiner.frequency(yOnly, items));
        //System.out.println("Confiance: " + BruteForceAssociationRuleMiner.confidence(yOnly, zOnly, items));
        System.out.println("\nTest de la méthode allCandidatePremises:");

        System.out.println("Affichage du test de extract : " + ruleMiner.extract(0.5f, 0.7f));
        System.out.println("----Affichage----");
        System.out.println(BruteForceAssociationRuleMiner.allCandidatePremises(bVariables));
    }

}
