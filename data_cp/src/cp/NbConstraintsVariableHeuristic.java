package cp;

import java.util.*;

import modelling.*;

public class NbConstraintsVariableHeuristic implements VariableHeuristic{
    private Set<Constraint> constraints;
    private boolean most; //true si on veut le meilleur, false si on veut le pire


    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean most){
        this.constraints = constraints;
        this.most = most;
    }

    

    public Set<Constraint> getConstraints() {
        return constraints;
    }



    public boolean isMost() {
        return most;
    }



    //Méthode qui prend un ensemble de variables et un ensemble de domaines et retourne la meilleure variable selon l'heuristique
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Map<Variable,Integer> bestVariable = new HashMap<>();

        for (Variable variable : variables) {
            int nbConstraints = 0;
            for (Constraint constraint : constraints) {
                if (constraint.getScope().contains(variable)) {
                    nbConstraints++;
                }
            }
            bestVariable.put(variable,nbConstraints);
        }
            if (most == true) {
                Map.Entry<Variable, Integer> max1 = Collections.max(bestVariable.entrySet(), Map.Entry.comparingByValue());
                return max1.getKey();
            }else{
                Map.Entry<Variable, Integer> min = Collections.min(bestVariable.entrySet(),Map.Entry.comparingByValue());
                return min.getKey();
            }
                
                
            }



    @Override
    public String toString() {
        return "NbConstraintsVariableHeuristic [constraints=" + constraints + ", most=" + most + "]";
    }
 

        
    }
    

