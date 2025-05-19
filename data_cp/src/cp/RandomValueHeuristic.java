package cp;

import java.util.*;

import modelling.*;

public class RandomValueHeuristic implements ValueHeuristic{
    private Random random;


    public RandomValueHeuristic(Random random){
        this.random = random;

    }

    //Méthode retournant les valeurs du modèle
    @Override
    public List<Object> ordering(Variable variable, Set<Object> domain) {
        List<Object> order = new ArrayList<>(domain);
        Collections.shuffle(order, random);
        return order;
 
        
    }

    @Override
    public String toString() {
        return "RandomValueHeuristic [random=" + random + "]";
    }


    
}
