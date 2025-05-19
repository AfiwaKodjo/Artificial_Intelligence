package cp;

import java.util.*;
import modelling.*;

public class ArcConsistency {

    private Set<Constraint> constraints;
    private Set<Constraint> unaryConstraint;
    private Set<Constraint> binaryConstraint;
    private Set<Variable> variable;

    public Set<Constraint> getConstraints() {
        return constraints;
    }

    public Set<Constraint> getUnaryConstraint() {
        return unaryConstraint;
    }

    public Set<Constraint> getBinaryConstraint() {
        return binaryConstraint;
    }

    public Set<Variable> getVariable() {
        return variable;
    }

    public ArcConsistency(Set<Constraint> constraints) {
        this.unaryConstraint = new HashSet<Constraint>();
        this.binaryConstraint = new HashSet<Constraint>();
        this.variable = new HashSet<Variable>();
        // vérifie si les constraints ne sont pas toutes unaires ou binaires
        // sinon, renvoie une exception
        for (Constraint constraint : constraints) {
            Set<Variable> scope = constraint.getScope();
            if (scope.size() == 1) {
                Variable var = scope.iterator().next();
                variable.add(var);
                unaryConstraint.add(constraint);
            } else if (scope.size() == 2) {
                Iterator<Variable> it = scope.iterator();
                Variable var1 = it.next();
                Variable var2 = it.next();
                binaryConstraint.add(constraint);
            } else {
                throw new IllegalArgumentException("Ni unaire ni binaire ");
            }
            this.constraints = constraints;
        }

    }

    // Méthode qui supprime les valeurs ne respectant pas les contraintes unaires
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domains) {
        Map<Variable, Set<Object>> satisfiedDomain = new HashMap<>();
        satisfiedDomain.putAll(domains);
        for (Variable var : variable) {
            for (Object value : domains.get(var)) {
                for (Constraint constraint : unaryConstraint) {
                    Map<Variable, Object> cons = Collections.singletonMap(var, value);
                    if (constraint.getScope().contains(var)) {
                        if (!constraint.isSatisfiedBy(cons)) {
                            Set<Object> smallDomain = new HashSet<>(satisfiedDomain.get(var));
                            smallDomain.remove(value);
                            satisfiedDomain.put(var, smallDomain);
                        }

                    }
                }

            }

        }
        for (Variable variable : domains.keySet()) {
            domains.put(variable, satisfiedDomain.get(variable));
        }

        // on vérifie si toutes les valeurs de domaines ont été supprimées après avoir
        // appliquer la contrainte
        for (Map.Entry<Variable, Set<Object>> keyValue : domains.entrySet()) {
            Set<Object> domain = keyValue.getValue();
            if (domain.isEmpty()) {
                return false;
            }

        }
        return true;

    }

    // Méthode qui vérifie si une valeur de domain1 a un support dans domain2
    public boolean revise(Variable variable1, Set<Object> domain1, Variable variable2, Set<Object> domain2) {

        boolean delete = false; // valeur pour dire si une valeur est supprimée ou pas
        Set<Object> RemoveDomain = new HashSet<>();
        HashMap<Variable, Object> partialInstantiation = new HashMap<>();
        for (Object object1 : domain1) {
            boolean satisfies = false;
            for (Object object2 : domain2) {
                boolean satisfies2 = true;
                for (Constraint constraint : binaryConstraint) {
                    if (constraint.getScope().contains(variable1) && constraint.getScope().contains(variable2)) {
                        partialInstantiation.put(variable1, object1);
                        partialInstantiation.put(variable2, object2);
                        if (!constraint.isSatisfiedBy(partialInstantiation)) {
                            satisfies2 = false;
                        }
                    }
                }
                if (satisfies2) {
                    satisfies = true;

                }
            }

            if (!satisfies) {
                RemoveDomain.add(object1);
                delete = true;
            }

        }
        domain1.removeAll(RemoveDomain);
        return delete;

    }

    public boolean ac1(Map<Variable, Set<Object>> domains) {
        boolean value = false;
        if (!enforceNodeConsistency(domains)) {
            return false;
        }
        do {
            value = false;
            for (Variable variable1 : domains.keySet()) {
                Set<Object> domain = new HashSet<>(domains.get(variable1));
                for (Variable variable2 : domains.keySet()) {
                    if (!variable2.equals(variable1) && revise(variable1, domain, variable2, domains.get(variable2))) {
                        value = true;
                    }
                }
                domains.put(variable1, domain);
            }
        } while (value);

        for (Variable variable : domains.keySet()) {
            if (domains.get(variable).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // Méthode d'affichage
    @Override
    public String toString() {
        return "ArcConsistency [constraints=" + constraints + ", unaryConstraint=" + unaryConstraint
                + ", binaryConstraint=" + binaryConstraint + ", variable=" + variable + "]";
    }

}
