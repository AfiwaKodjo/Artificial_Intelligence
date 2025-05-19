package planning;

import java.util.*;
import modelling.*;

public interface Action{
    public boolean isApplicable(Map<Variable, Object> state);

    public Map<Variable, Object> successor(Map<Variable, Object> newState);

    public int getCost();


}