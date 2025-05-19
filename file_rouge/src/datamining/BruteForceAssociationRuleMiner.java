package datamining;

import java.util.*;
import modelling.*;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    public BruteForceAssociationRuleMiner(BooleanDatabase base) {
        super(base);
    }

    public static final Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> result = new HashSet<>();
        generateSubsets(new ArrayList<>(items), new HashSet<>(), 0, result);
        result.remove(new HashSet<>()); // retire ensemble vide
        result.remove(items);  // retire ensemble complet
        return result;
    }
    
    private static void generateSubsets(List<BooleanVariable> items, Set<BooleanVariable> current, int index, Set<Set<BooleanVariable>> result) {
        if (index == items.size()) {
            result.add(new HashSet<>(current));
            return;
        }
        
        // Ne pas prendre l'élément
        generateSubsets(items, current, index + 1, result);
        
        // Prendre l'élément
        current.add(items.get(index));
        generateSubsets(items, current, index + 1, result);
        current.remove(items.get(index));
    }

    
    @Override
    public BooleanDatabase getDatabase() {
        return this.base;
    }

    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfidence) {
        Set<AssociationRule> result = new HashSet<>();
        Set<Itemset> frequents = new Apriori(this.base).extract(minFrequency);
        for (Itemset itemset : frequents) {
            Set<Set<BooleanVariable>> premises = allCandidatePremises(itemset.getItems());

            for (Set<BooleanVariable> premise : premises) {
                Set<BooleanVariable> conclusion = new HashSet<>(itemset.getItems());
                conclusion.removeAll(premise);
                float conf = confidence(premise, itemset.getItems(), frequents);
                if (conf >= minConfidence) {
                    result.add(new AssociationRule(premise, conclusion, itemset.getFrequency(), conf));
                }
            }

        }
        return result;
    }
}