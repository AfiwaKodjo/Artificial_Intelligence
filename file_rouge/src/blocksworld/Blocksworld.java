package blocksworld;

import java.util.*;
import modelling.*;

public class Blocksworld {
    // Les attributs freePiles, onBlockset et fixedBlocks vont nous permettre de
    //representer nos variables et leurs affectations(Valeurs) respectives desblocks
    private Map<Variable, Boolean> freePiles;
    private Map<Variable, Integer> onBlocks;
    private Map<Variable, Boolean> fixedBlocks;
    private BlocksworldVariables variables;

    public Blocksworld(int nbBlock, int nbPile) {
        // On créé les variables de notre monde
        this.variables = new BlocksworldVariables(nbBlock, nbPile);
        // On initialise les valeurs par defaut à true de la variable freeP de chaque pile
        this.onBlocks = new HashMap<>();
        this.fixedBlocks = new HashMap<>();
        this.freePiles = new HashMap<>();
        for (int p = -1; p >= -nbPile; p--) {
            this.freePiles.put((this.variables.getFreeP().get(p)), true);
        }
        // On initialise les valeurs par defaut à false de la variable fixedB de chaque block
        for (int b = 0; b < nbBlock; b++) {
            this.fixedBlocks.put((this.variables.getFixedB().get(b)), false);
        }
    }

    // On renvoie l'ensembles des variables de notre monde
    public BlocksworldVariables getScope() {
        return this.variables;
    }

    public Map<Variable, Boolean> getFree() {
        return this.freePiles;
    }

    public Map<Variable, Integer> getOn() {
        return this.onBlocks;
    }

    public Map<Variable, Boolean> getFixed() {
        return this.fixedBlocks;
    }

    // Methode permettant de liberer un block
    public void setfixed(Variable fixed) {
        this.fixedBlocks.put(fixed, false);
    }

    // Methode permettant de liberer une pile
    public void setFree(Variable free) {
        this.freePiles.put(free, true);
    }

    // Méthode permettant de déplacer un block sur un autre block
    public void putOn(int block1, int block2) {
        this.onBlocks.put(this.variables.getOnB().get(block1), block2);
        this.fixedBlocks.put((this.variables.getFixedB().get(block2)), true);
    }

    // Méthode permettant de deplacer un block dans une pile
    public void putInPile(int block, int pile) {
        this.onBlocks.put(this.variables.getOnB().get(block), pile);
        this.freePiles.put(this.variables.getFreeP().get(pile), false);
    }

}