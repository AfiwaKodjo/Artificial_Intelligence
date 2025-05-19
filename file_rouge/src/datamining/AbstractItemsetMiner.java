package datamining;

import modelling.*;
import java.util.*;


public abstract class AbstractItemsetMiner implements ItemsetMiner{

    protected BooleanDatabase base;

    public static final Comparator<BooleanVariable> COMPARATOR = 
    (var1, var2) -> var1.getName().compareTo(var2.getName());


    public AbstractItemsetMiner(BooleanDatabase base) {
        this.base = base;

    }

    public static Comparator<BooleanVariable> getCOMPARATOR() {
        return COMPARATOR;
    }

    public BooleanDatabase getBase() {
        return base;
    }

    public float frequency(Set<BooleanVariable> items){
        float counter = 0;
        for(Set<BooleanVariable> var: getBase().getTransactions()){
            if(var.containsAll(items)){
                counter++;
            }
        }
    return counter/getBase().getTransactions().size();

    }



}
