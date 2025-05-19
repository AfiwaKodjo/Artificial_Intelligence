package blocksworld;

import modelling.*;
import planning.*;
import java.util.*;

public class BlocksworldHeuristic implements Heuristic {
    private Blocksworld blocksworld;

    public BlocksworldHeuristic() {
        this.blocksworld = blocksworld;
    }

    // Heuristique qui retourne le nombre de blocks dans l'état courant
    @Override
    public float estimate(Map<Variable, Object> state) {
        if (blocksworld == null) {
            return 0;
        }
        return blocksworld.getOn().size();
    }

}
