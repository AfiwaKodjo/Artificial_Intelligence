package planning;

import java.util.*;

import modelling.Variable;

public class BasicGoal implements Goal {
    private Map<Variable, Object> instanciation;

    public BasicGoal(Map<Variable, Object> instanciation){
        this.instanciation = instanciation;

    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> satisfait) {
        
        for (Map.Entry<Variable, Object> entry : instanciation.entrySet()) {
                Variable var = entry.getKey();
                Object valeurSouhaitee = entry.getValue();

                if (!satisfait.containsKey(var) || !satisfait.get(var).equals(valeurSouhaitee)) {
                    return true;
                }
            }
        return false;

}

}
