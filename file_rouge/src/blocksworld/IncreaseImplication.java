package blocksworld;
import modelling.*;

public class IncreaseImplication{

    //Méthode qui permet de vérifier si la valeur de mon block courant est supérieure à celle du block en dessous
    public boolean isIncrease(Blocksworld blockworld){
       for (Variable onB : blockworld.getOn().keySet()) {
            //On recupère le numero du block courant
            Integer currentBlock = Integer.parseInt(onB.getName().substring(2, 3));
            //On recupère le numero du block posé en dessous block courrant
            Integer secondBlock = blockworld.getOn().get(onB);
            if(secondBlock>=0){
                if((currentBlock == null || secondBlock == null)){
                    return false;
                }
                else if(currentBlock < secondBlock){
                    return false;
                }
            }
        }
        return true;
    }
}