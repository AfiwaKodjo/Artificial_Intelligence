package blocksworld;

import java.util.Map;

import modelling.Variable;
import planning.Heuristic;

public class BlocksworldHeuristic2 implements Heuristic{
    private Blocksworld blocksworld;

    public BlocksworldHeuristic2(Blocksworld blocksworld) {
        this.blocksworld = blocksworld;
    }

    //Heuristique qui renvoie le nombre de piles non libres
    @Override
    public float estimate(Map<Variable, Object> state) {
        int count = 0;
       for(Boolean p: blocksworld.getFree().values()){
        if(!p){
            count++;  //on compte le nombre de piles qui ne sont pas vides
        }
       }
       return count;
    }

}
