package cp;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import modelling.Variable;

public class DomainSizeVariableHeuristic implements VariableHeuristic {

    private boolean greatest;

    public DomainSizeVariableHeuristic(boolean greatest) {
        this.greatest = greatest;
    }

    public boolean isGreatest() {
        return greatest;
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Comparator<Variable> comparator = (var1, var2) -> {
            int size1 = domains.get(var1).size();
            int size2 = domains.get(var2).size();
            return Integer.compare(size1, size2); // on compare
        };

        // Sélection de la variable avec le domaine le plus petit ou soit le plus grand
        return isGreatest() ? Collections.max(variables, comparator) : Collections.min(variables, comparator);
    }

    @Override
    public String toString() {
        return "DomainSizeVariableHeuristic [greatest=" + greatest + "]";
    }



}
