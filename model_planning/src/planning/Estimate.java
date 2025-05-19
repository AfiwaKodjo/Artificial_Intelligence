package planning;

import java.util.Map;

import modelling.Variable;

public class Estimate  implements Heuristic{

    @Override
    public float estimate(Map<Variable, Object> state) {
        return 0.0f;
    }
    
}
