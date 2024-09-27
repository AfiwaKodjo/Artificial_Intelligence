package planning;

import java.util.*;

import modelling.Variable;

public class DijkstraPlanner implements Planner{
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;

    public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    @Override
    public List<Action> plan() {
        return dijkstra();
        
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return initialState;
    }  

    @Override
    public Set<Action> getActions() {
        return actions;
        
    }

    @Override
    public Goal getGoal() {
        return goal;
        
    }

    private List<Action> dijkstra(){
        Map<Map<Variable,Object>, Action> plan = new HashMap<>();
        Map<Map<Variable,Object>,Double> distance = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Set<Map<Variable, Object>> goals;
        father.put(initialState,null);
        distance.put(initialState,0.0);
        Set<Map<Variable, Object>> open = new HashSet<>();
        open.add(initialState);
        while(!open.isEmpty()){
            Map<Variable, Object> instantiation = null;
            double minDistance = Double.MAX_VALUE;
            // Trouver l'état avec la distance minimale
            for (Map<Variable, Object> state : open) {
                double stateDistance = distance.getOrDefault(state, Double.MAX_VALUE);
                if (stateDistance < minDistance) {
                    minDistance = stateDistance;
                    instantiation = state;
                }
            }
            open.remove(instantiation);
            if(goal.isSatisfiedBy(instantiation)){
                goals.add(instantiation);
            }
            for(Action action: actions){
                if(action.isApplicable(instantiation)){
                    Map<Variable, Object> next = action.successor(instantiation);
                    if(!distance.containsKey(next)){
                        distance.put(next, Double.MAX_VALUE);
                    }
                    if(distance.get(next) > distance.get(instantiation)+action.getCost()){
                        double newDistance = distance.get(instantiation)+action.getCost();
                        distance.put(next, newDistance);
                        father.put(next,instantiation);
                        plan.put(next,action);
                        open.add(next);

                    }
                }
            }
        }
    if (goals.isEmpty()){
        return new ArrayList<>();

    }else{
        return getDijkstraPlan(father,plan,goals,distance);
    }

        
    
    }

    public List<Action> getDijkstraPlan(Map<Map<Variable, Object>,Map<Variable,Object>> father, Map<Map<Variable,Object>,Action> plan, Map<Variable, Object> goals, Map<Map<Variable,Object>, Double> distance){
        List<Action> DIJ_plan = new LinkedList<>();
        double minDistance = Double.MAX_VALUE;
        // Trouver l'état avec la distance minimale
        for (Map<Variable, Object> state : open) {
            double stateDistance = distance.getOrDefault(state, Double.MAX_VALUE);
            if (stateDistance < minDistance) {
                minDistance = stateDistance;
                instantiation = state;
            }
        }
         // Si aucun objectif valide n'a été trouvé, retourne une liste vide
            if (goal == null) {
                return DIJ_plan;  // Retourne une liste vide
            }
        while(goal != null && father.get(goal) != null){
            Action action = plan.get(goal);
            if (action != null) {
                DIJ_plan.add(0, action);
            }
            goal = father.get(goal);

        }

   Collections.reverse(DIJ_plan);
    return DIJ_plan;
    }

}
