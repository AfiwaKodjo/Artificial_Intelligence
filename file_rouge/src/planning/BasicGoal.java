package planning;

import java.util.*;

import modelling.Variable;

public class BasicGoal implements Goal {
    private Map<Variable, Object> instantiation;

    public BasicGoal(Map<Variable, Object> instantiation){
        this.instantiation = instantiation;

    }

    // Méthode qui vérifie si un état donné satisfait l'objectif
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> satisfy) {
        
        for (Map.Entry<Variable, Object> entry : instantiation.entrySet()) {
                Variable var = entry.getKey();
                Object wishValue = entry.getValue();

                if (!satisfy.containsKey(var) || !satisfy.get(var).equals(wishValue)) {
                    return false;
                }
            }
        return true;

}

    @Override
    public String toString() {
        return "BasicGoal [instantiation=" + instantiation + "]";
    }



 




}
