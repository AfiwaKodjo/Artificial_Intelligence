package cp;


import java.util.*;
import modelling.*;
public interface ValueHeuristic {

    public List<Object> ordering(Variable variable, Set<Object> domain);
    
}
