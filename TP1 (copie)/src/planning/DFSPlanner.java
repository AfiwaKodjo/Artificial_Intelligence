package planning;

import java.util.*;

import modelling.Variable;

public class DFSPlanner implements Planner{
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;

    public DFSPlanner(Map<Variable, Object> initialState,Set<Action> actions, Goal goal){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    @Override
    public List<Action> plan() {
        Stack<Action> actionsStack = new Stack<>();
        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(initialState);
        List<Action> callPlan = dfs(initialState, actionsStack, closed);
        
        // Retourne une liste vide s'il n'y a pas de plan trouvé
        if(callPlan.isEmpty()){ 
        	return null;
        }
        return callPlan;
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

    private List<Action> dfs(Map<Variable, Object> instantiation, Stack<Action> plan, Set<Map<Variable,Object>> closed){
        if(goal.isSatisfiedBy(instantiation)){
            return plan;
        }
        else
        for(Action action: actions){
                if (action.isApplicable(instantiation)){
                    Map<Variable,Object> next = action.successor(instantiation);
                    if (!closed.contains(next)){
                        plan.push(action);
                        closed.add(next);
                        List<Action> subplan = dfs(next, plan, closed);
                        if(!subplan.isEmpty()){
                            return subplan;
                        }else{
                            plan.pop();
                        }

                    }

                }

        }
        return new ArrayList<>();



    }

}
