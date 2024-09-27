package planning;

import java.util.*;

import modelling.*;

public class AStarPlanner implements Planner {
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private Heuristic heuristic;

    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
    }

    @Override
    public List<Action> plan() {
        return;
        
        
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


    public List<Action> astar(){
        Map<Map<Variable,Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable,Object>,Double> distance = new HashMap<>();
        Map<Map<Variable,Object>,Double> value = new HashMap<>();
        Set<Map<Variable,Object>> open = new HashSet<>(initialState.size());
        father.put(initialState,null);
        distance.put(initialState,0.0);
        value.put(initialState,heuristic.estimate(initialState));
        while(open!= null){  
            Map<Variable, Object> instantiation = chooseMin(open, value);
            if(goal.isSatisfiedBy(instantiation)){
                return getBfsPlan(father, plan, instantiation);
            }else{
                open.remove(instantiation);
            }
            for(Action action: actions){
                if(action.isApplicable(instantiation)){
                    Map<Variable, Object> next = action.successor(instantiation);
                    if(!distance.containsKey(next)){
                        distance.put(next, Double.MAX_VALUE);
                    }
                    double newDistance = distance.get(instantiation) + action.getCost();
                    if(distance.get(next)> newDistance){
                        distance.put(next,newDistance);
                        value.put(next, distance.get(next) + heuristic.estimate(next));
                        father.put(next,instantiation);
                        plan.put(next,action);
                        open.add(next);
                        }
        }

    }

}
return new ArrayList<>();

 }
}

