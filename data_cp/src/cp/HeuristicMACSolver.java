package cp;


import java.util.*;
import modelling.*;
public class HeuristicMACSolver extends AbstractSolver{

    private VariableHeuristic heuristicVariable;
    private ValueHeuristic heuristicValue;

    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic heuristicVariable, ValueHeuristic heuristicValue){
        super(variables, constraints);
        this.heuristicValue = heuristicValue;
        this.heuristicVariable = heuristicVariable;

    }

    //Méthode principale
    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> domains = new HashMap<>();
        for (Variable variable : this.variables) {
            domains.put(variable, variable.getDomain());
        }
        return this.mac(new HashMap<>(), new LinkedList<>(this.variables), domains);
        
    }

   //Méthode qui applique l'algorithme de BackTracking avec l'arc consistency et retourne une solution qui étend la solution partielle
    public Map<Variable, Object> mac(Map<Variable, Object> partialInstantiation, LinkedList<Variable> variableNotInstantiate, Map<Variable, Set<Object>> domains) {
        if (variableNotInstantiate.isEmpty()){
            return partialInstantiation;
        }else {
            ArcConsistency arcConsistency = new ArcConsistency( this.constraints);
            if (!arcConsistency.ac1(domains)){
                return  null;
            }
            Variable variable = heuristicVariable.best(new HashSet<>(variableNotInstantiate),domains);
            variableNotInstantiate.remove(variable);
            for (Object objet: heuristicValue.ordering(variable,domains.get(variable))){
                Map<Variable, Object> partial = new HashMap<Variable, Object>(partialInstantiation);
                partial.put(variable,objet);
                Map<Variable, Set<Object>> domaine= new HashMap<>();
                domaine.putAll(domains);
                domaine.put(variable, new HashSet<>(Collections.singleton(objet)));
                if (isConsistent(partial)){
                    Map<Variable,Object> resultat = this.mac(partial,variableNotInstantiate,domains);
                    if (resultat!=null){
                        return resultat;
                    }
                }
            }
            variableNotInstantiate.add(variable);
            return null;
        }
    }

    //Méthode d'affichage
    @Override
    public String toString() {
        return "HeuristicMACSolver [heuristicVariable=" + heuristicVariable + ", heuristicValue=" + heuristicValue
                + "]";
    }

    

    
}
