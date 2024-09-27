package planning;

import java.util.*;
import modelling.*;

public interface Action{
    public boolean isApplicable(Map<Variable, Object> etat);

    public Map<Variable, Object> successor(Map<Variable, Object> nouvEtat);

    public int getCost();


}