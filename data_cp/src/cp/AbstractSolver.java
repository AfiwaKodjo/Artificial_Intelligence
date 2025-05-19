package cp;

import modelling.*;
import java.util.*;
public abstract class AbstractSolver implements Solver{
    
    protected Set<Variable> variables; //ensemble de variables
    protected Set<Constraint> constraints; //ensemble de contraintes

    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints){
        this.constraints = constraints;
        this.variables = variables;
    }


    public Set<Variable> getVariables(){
        return variables;
    }

    public Set<Constraint> getConstraints(){
        return constraints;
    }

    //Méthode qui retourne true si l'affectation partielle des variables satisfait toutes les contraintes et false sinon
    public boolean isConsistent(Map<Variable, Object> partialInstantiation){
        for(Constraint constraint : constraints){
            if(partialInstantiation.keySet().containsAll(constraint.getScope())){
                if(!constraint.isSatisfiedBy(partialInstantiation)){
                    return false;
                }
            }

        }
        return true;

    }


    // Méthode d'affichage de AbstractSolver
    @Override
    public String toString() {
        return "AbstractSolver [variables=" + variables + ", constraints=" + constraints + "]";
    }

    


    
}
