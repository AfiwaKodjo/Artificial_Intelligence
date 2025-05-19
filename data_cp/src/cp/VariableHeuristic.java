package cp;


import java.util.*;
import modelling.*;
public interface VariableHeuristic {

    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains);
    
}
