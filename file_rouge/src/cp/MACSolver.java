package cp;

import java.util.*;
import modelling.*;

public class MACSolver extends AbstractSolver{

    private ArcConsistency arcConsistency;
    
    public MACSolver(Set<Variable> variables, Set<Constraint> constraints){
        super(variables, constraints);
        this.arcConsistency = new ArcConsistency(constraints);

    }

    
    public ArcConsistency getArcConsistency() {
        return arcConsistency;
    }
        
    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> domains = new HashMap<Variable, Set<Object>>();
        for (Variable variable : variables) {
            domains.put(variable, variable.getDomain());
        }
		return mac(new HashMap<>(), new LinkedList<Variable>(variables), domains);
	}


    //Méthode qui étend la solution partielle si elle existe ou null s'il n'y pas de solution
    public Map<Variable, Object> mac(HashMap<Variable, Object> partialInstantiation, LinkedList<Variable> variableNotInstantiate, Map<Variable, Set<Object>> domains) {
            
        if(variableNotInstantiate.isEmpty()) {
                return partialInstantiation;
        }else{
            ArcConsistency arcConsistency = new ArcConsistency(constraints);
            if(!arcConsistency.ac1(domains)){
                return null;
            }
            Variable xi =variableNotInstantiate.poll();
            for(Object vi: xi.getDomain()){
                HashMap<Variable, Object> N = new HashMap<>(partialInstantiation);
                N.put(xi,vi);
                Map<Variable, Set<Object>> domain = new HashMap<>();
                domain.putAll(domains);
                domain.put(xi, new HashSet<>(Collections.singleton(vi)));
                if(isConsistent(N)){
                    Map<Variable, Object> newGoal = new HashMap<>();
                    newGoal = mac(N, variableNotInstantiate, domain);
                    if(newGoal !=null){
                        return newGoal;
                    }
                }

            }
            variableNotInstantiate.add(xi);
            return null;
            
        }
      
    }



   
    }

