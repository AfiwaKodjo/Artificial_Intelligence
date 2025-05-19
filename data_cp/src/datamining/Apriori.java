package datamining;

import java.util.*;
import modelling.*;

public class Apriori extends AbstractItemsetMiner {

    public Apriori(BooleanDatabase base) {
        super(base);
    }

    public BooleanDatabase getDatabase() {
        return getBase();
    }

    //Méthode permettant de retourner des itemsets singletons 
    public Set<Itemset> frequentSingletons(float minFrequency) {
        Set<Itemset> itemSets = new HashSet<Itemset>();
        for (BooleanVariable item : getDatabase().getItems()) {
            Set<BooleanVariable> items = new HashSet<>();
            items.add(item);
            if (super.frequency(items) >= minFrequency) {
                itemSets.add(new Itemset(items, super.frequency(items)));
            }

        }
        return itemSets;
    }

   //Méthode permettant de combiner 2 ensembles d'items en respectant certaines conditions
    public static final SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> item,
            SortedSet<BooleanVariable> item2) {
        SortedSet<BooleanVariable> combineItem = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        if (item.isEmpty() || item2.isEmpty()) {
            return null;
        }
        if ((item.size() == item2.size()) && item.headSet(item.last()).equals(item2.headSet(item2.last()))
                && (!item.last().equals(item2.last()))) {
            combineItem.addAll(item);
            combineItem.add(item2.last());
            return combineItem;
        }
        return null;

    }

    //Méthode renvoyant true si lorsqu'on supprime un élément de l'ensemble, les autres éléments sont dans la collection 
    public static final boolean allSubsetsFrequent(Set<BooleanVariable> item1, Collection<SortedSet<BooleanVariable>> items) {
        for (BooleanVariable item : item1) {
            Set<BooleanVariable> itemTmp = new HashSet<>(item1);
            itemTmp.remove(item);
            if (!items.contains(itemTmp)) {
                return false;
            }
        }
        return true;
    }

    //Méthode permettant de renvoyer des items de k à k+1 tout en prenant en compte les singletons
    @Override
    public Set<Itemset> extract(float minFrequency) {
        // on stocke les itemSets fréquents
        Set<Itemset> results = new HashSet<>();
        List<Itemset> items = new ArrayList<>();
        items.addAll(frequentSingletons(minFrequency));
        results.addAll(items);

        List<SortedSet<BooleanVariable>> frequentItemSet = new ArrayList<>();
        for (Itemset item : items) {
            SortedSet<BooleanVariable> frequentItems = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            frequentItems.addAll(item.getItems());
            frequentItemSet.add(frequentItems);

        }
        while (!frequentItemSet.isEmpty()) {
            List<SortedSet<BooleanVariable>> list = new ArrayList<>();
            for (int i = 0; i < frequentItemSet.size() - 1; i++) {
                for (int j = i + 1; j < frequentItemSet.size(); j++) {
                    SortedSet<BooleanVariable> item1 = frequentItemSet.get(i);
                    SortedSet<BooleanVariable> item2 = frequentItemSet.get(j);
                    SortedSet<BooleanVariable> combineItems = combine(item1, item2);
                    // on vérifie si tous les sous-ensembles sont fréquents

                    if (combineItems != null) {
                        if (allSubsetsFrequent(combineItems, frequentItemSet)) {
                            float frequency = super.frequency(combineItems);
                            if (frequency >= minFrequency) {
                                results.add(new Itemset(combineItems, frequency));
                                list.add(combineItems);
                            }

                        }

                    }
                }
            }
            frequentItemSet = list;
        }
        return results;

    }




}
