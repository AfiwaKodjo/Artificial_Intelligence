package datamining;

import java.util.*;
import modelling.*;

public class Itemset {

    private Set<BooleanVariable> items; //ensemble d'items
    private float frequency; //fréquence

    public Itemset(Set<BooleanVariable> items, float frequency) {
        this.items = items;

        //fréquence doit être entre 0.0 et 1.0
        if(frequency<0.0 || frequency>1.0){
            throw new IllegalArgumentException("La fréquence doit être entre 0.0 et 1.0");
        }
        this.frequency = frequency;

    }

    public Set<BooleanVariable> getItems() {
        return items;
    }

    public float getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return "Itemset [items=" + items + ", frequency=" + frequency + "]";
    }

    
    
}
