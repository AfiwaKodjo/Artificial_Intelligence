package datamining;

import java.util.*;
import modelling.*;

public class BooleanDatabase {
    private Set<BooleanVariable> items;
    private List<Set<BooleanVariable>> transactions;

    public BooleanDatabase(Set<BooleanVariable> items) {
        this.items = new HashSet<>(items);
        this.transactions = new ArrayList<>();

    }

    public void add (Set<BooleanVariable> transaction){
        this.items.addAll(transaction);
        this.transactions.add(new HashSet<>(transaction));
    }

    public Set<BooleanVariable> getItems() {
        return items;
    }

    public List<Set<BooleanVariable>> getTransactions() {
        return transactions;
    }

   
    
}
