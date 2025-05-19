package planning;

import java.util.*;
import modelling.*;

public interface Heuristic {
    public float estimate(Map<Variable, Object> state);

}
