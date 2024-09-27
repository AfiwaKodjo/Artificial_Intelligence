package planning;

import java.util.*;

import modelling.Variable;

public class BasicAction implements Action {
    private Map<Variable, Object> precondition;
    private Map<Variable, Object> effet;
    private int cout;

    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effet, int cout){
        this.precondition = precondition;
        this.effet = effet;
        this.cout = cout;
    }

    @Override
    public boolean isApplicable(Map<Variable, Object> etat) {
            for(Variable var : precondition.keySet()){
                if(!etat.containsKey(var)|| !etat.get(var).equals(precondition.get(var))){
                    return true;
                }
            }

        return false;
        
    }

    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> etat) {
        // On crée la copie de l'état
        Map<Variable, Object> nouvEtat = new HashMap<>(etat);
        // Applique les effets
        nouvEtat.putAll(effet);
        return nouvEtat;

    }

    @Override
    public int getCost() {
        return cout;
       
    }

    @Override
    public String toString() {
        return "preconditions=" + this.precondition +
                ", effets=" + this.effet +
                ", coût=" + this.cout ;
}


}
