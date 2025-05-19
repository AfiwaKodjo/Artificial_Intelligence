package blocksworld;
import modelling.*;

public class RegularImplication{

    public boolean isRegular(Blocksworld blockworld) {
        for (Variable onB : blockworld.getOn().keySet()) {
            //On recupère le numero du block courant
            Integer currentBlock = Integer.parseInt(onB.getName().substring(2));
            //On recupère le numéro du block poser en dessous block courrant
            Integer secondBlock = blockworld.getOn().get(onB);
            //On recupère le numero du block posé en dessous second block
            Integer thirdBlock = blockworld.getOn().get(blockworld.getScope().getOnB().get(secondBlock));
            if(secondBlock>=0   && thirdBlock>=0){
                if(currentBlock - secondBlock != secondBlock-thirdBlock)
                    return false;
            }
            //On parcourt une autre pile s'il y'en a
            else if(secondBlock<0)
                continue;
        }
        return true;
    }
}
