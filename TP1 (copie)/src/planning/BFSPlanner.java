package planning;

import java.util.*;

import modelling.Variable;

public class BFSPlanner implements Planner {
   private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;

    public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    @Override
    public List<Action> plan() {
        return bfs();
        
        
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

private List<Action> bfs(){
    Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>();
    Map<Map<Variable, Object>,Action> plan = new HashMap<>();
    Set<Map<Variable, Object>> closed = new HashSet<>();
    Queue<Map<Variable, Object>> open = new LinkedList<>();
    open.add(initialState);
    father.put(initialState,null);
    if(goal.isSatisfiedBy(initialState)){
        return new ArrayList<>();
    }
    while(!open.isEmpty()){
        Map<Variable, Object> instantiation = open.remove();
        closed.add(instantiation);
        for(Action action: actions){
            if(action.isApplicable(instantiation)){
                Map<Variable, Object> next = action.successor(instantiation);
                if(!closed.contains(next) && !open.contains(next)){
                    father.put(next, instantiation);
                    plan.put(next,action);
                    if(goal.isSatisfiedBy(next)){
                        return getBfsPlan(father, plan, next);
                    }else{
                        open.add(next);
                    }
                }


            }

        }
    }
    return null;




}

private List<Action> getBfsPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> goal) {
        List<Action> bfsPlan = new LinkedList<>();

        while (goal != null) {
            Action action = plan.get(goal);
            if (action != null) {
                bfsPlan.add(0, action);
            }
            goal = father.get(goal);
        }

        return bfsPlan;
    }

}
