package datamining;

import java.util.*;
import modelling.*;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner{

    protected BooleanDatabase base;

    public AbstractAssociationRuleMiner(BooleanDatabase base){
        this.base = base;

    }

    public BooleanDatabase getBase() {
        return base;
    }

    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsSets) {
       for(Itemset item : itemsSets){
        if (item.getItems().equals(items)){ 
            return item.getFrequency();  
        }
       }
       throw new IllegalArgumentException("l'item n'a pas été trouvé dans les itemsSets");
    }
  

   public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> frequent){
        float confiance;
        Set<BooleanVariable> union = new HashSet<>();
        union.addAll(premise);
        union.addAll(conclusion);
        
        confiance = frequency(union, frequent) / frequency(premise, frequent);
        return confiance;
        }
        
}



