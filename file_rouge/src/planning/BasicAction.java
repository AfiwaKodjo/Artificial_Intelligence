package planning;

import java.util.*;

import modelling.Variable;

public class BasicAction implements Action {
    private Map<Variable, Object> precondition;
    private Map<Variable, Object> effect;
    private int cost;

    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effect, int cost){
        this.precondition = precondition;
        this.effect = effect;
        this.cost = cost;
    }


    //Méthode qui retourne un booléen vérifiant si l'effet est applicable sur l'état
    @Override
    public boolean isApplicable(Map<Variable, Object> state) {
            for(Variable var : precondition.keySet()){
                if(!state.containsKey(var)|| !state.get(var).equals(precondition.get(var))){
                    return false;
                }
            }

        return true;
        
    }

    //Cette méthode applique l'effet et retourne un nouvel état
    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> state) {
        // On crée la copie de l'état
        Map<Variable, Object> nouvEtat = new HashMap<>(state);
        // Applique les effets
        nouvEtat.putAll(effect);
        return nouvEtat;

    }

    @Override
    public int getCost() {
        return cost;
       
    }

    @Override
    public String toString() {
        return "[preconditions=" + this.precondition +
                ", effets=" + this.effect +
                ", coût=" + this.cost +"]";
}


}
