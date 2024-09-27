package planning;

import java.util.*;
import modelling.*;

public class Main {
    public static void main(String[] args) {
        //Test pour la classe BasicAction
        //Domaines
        Set<Object> xDomain = new HashSet<>();
        xDomain.add(1);
        xDomain.add(2);
        xDomain.add(3);

        Set<Object> yDomain = new HashSet<>();
        yDomain.add(2);
        yDomain.add(4);

        Set<Object> zDomain = new HashSet<>();
        zDomain.add(2);
        zDomain.add(3);

        Set<Object> tDomain = new HashSet<>();
        tDomain.add(4);
        tDomain.add(5);

        Set<Object> uDomain = new HashSet<>();
        tDomain.add(4);
        tDomain.add(6);

        Set<Object> vDomain = new HashSet<>();
        tDomain.add(3);
        tDomain.add(6);

        Set<Object> wDomain = new HashSet<>();
        tDomain.add(2);
        tDomain.add(5);

        //Variables avec le nom et le domaine
        Variable x = new Variable("x", xDomain);
        Variable y = new Variable("y", yDomain);
        Variable z = new Variable("z", zDomain);
        Variable t = new Variable("t", tDomain);
        Variable u = new Variable("u", uDomain);
        Variable v = new Variable("v", vDomain);
        Variable w = new Variable("w", wDomain);

        //Préconditions et effets pour une action
        Map<Variable, Object> preconditions = new HashMap<>();
        preconditions.put(x, 1);
        preconditions.put(z, 2);

        Map<Variable, Object> effets = new HashMap<>();
        effets.put(x, 3);
        effets.put(y, 3);

        // etat
        Map<Variable, Object> etat = new HashMap<>();
        etat.put(x, 1);
        etat.put(y, 2);
        etat.put(z, 3);
        etat.put(t, 4);

        Map<Variable, Object> etat2 = new HashMap<>();
        etat2.put(x, 1);
        etat2.put(y, 3);
        etat2.put(z, 2);
        etat2.put(t, 4);

        BasicAction action = new BasicAction(preconditions, effets, 5);
        System.out.println("Action applicable ou non: "+action.isApplicable(etat));
        System.out.println("Action applicable ou non: "+action.isApplicable(etat2));

        
        // Application de l'action
        Map<Variable, Object> nouvEtat = action.successor(etat);

        // Affichage des résultats
        System.out.println("État initial : " + etat);
        System.out.println("État après application de l'action : " + nouvEtat);

        Map<Variable, Object> instantiation = new HashMap<>();
        instantiation.put(x, "a");
        instantiation.put(z, "c");
        instantiation.put(t, "d");

        BasicGoal goal = new BasicGoal(instantiation);

        // Vérifier si l'état satisfait l'objectif
        boolean estSatisfait = goal.isSatisfiedBy(etat);
        
        // Afficher le résultat
        System.out.println("L'état satisfait ou non l'objectif: " + estSatisfait);
        
        Map<Variable, Object> state = new HashMap<>();
        state.put(x, "a");
        state.put(y, "b");
        state.put(z, "c");
        state.put(t, "d");
        state.put(u, "e");
        state.put(v, "f");
        state.put(w, "g");

        Set<Action> actions = new HashSet<>();
        actions.add(new BasicAction(preconditions, effets, 5));
        actions.add(new BasicAction(Map.of(x, 'a'), Map.of(x, 'b'), 1)); // Exemples d'autres actions
        actions.add(new BasicAction(Map.of(x, 'b'), Map.of(x, 'c'), 1));
        actions.add(new BasicAction(Map.of(z, 'c'), Map.of(z, 'd'), 1));


        //Recherche en profondeur (DFS)
        DFSPlanner dfsPlanner = new DFSPlanner(state, actions, goal);
        List<Action> dfsRoad = dfsPlanner.plan();
        // Affichage du chemin trouvé pour DFS
        if (dfsRoad != null && !dfsRoad.isEmpty()) {
            System.out.println("Chemin DFS pour atteindre l'objectif : ");
            for (Action act : dfsRoad) {
                System.out.println(act.toString());
            }
        } else {
            System.out.println("Aucun plan DFS trouvé.");
        }

        // Recherche en largeur (BFS)
        BFSPlanner bfsPlanner = new BFSPlanner(state, actions, goal);
        List<Action> bfsRoad = bfsPlanner.plan();
        
        // Affichage du chemin trouvé pour BFS
        if (bfsRoad != null && !bfsRoad.isEmpty()) {
            System.out.println("Chemin BFS pour atteindre l'objectif : ");
            for (Action act : bfsRoad) {
                System.out.println(act.toString());
            }
        } else {
            System.out.println("Aucun plan BFS trouvé.");
        }
    }
}
