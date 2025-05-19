package modelling;

import java.util.*;

public class BooleanVariable extends Variable{

    public BooleanVariable(String name) {

        super(name, new HashSet<>(Set.of(true,false)));

    }
       
    @Override
    public int hashCode() {
        
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
       
        return super.equals(obj);
    }


}
