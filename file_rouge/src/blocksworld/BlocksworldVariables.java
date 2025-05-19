package blocksworld;

import java.util.*;
import modelling.*;

public class BlocksworldVariables {
    private int nbBlock;
    private int nbPile;
    private Map<Integer, Variable> onB;
    private Map<Integer, BooleanVariable> fixedB;
    private Map<Integer, BooleanVariable> freeP;
    private Set<Object> domaineOnB;

    public BlocksworldVariables(int nbBlock, int nbPile) {

        //Lève une exception lorsque le nombre de block ou de pile saisit est négatif
        if (nbPile < 0 || nbBlock < 0) {
            throw new IllegalArgumentException("Le nombre de pile ou de block ne peut pas être négatif");
        }

        this.onB = new HashMap<>();
        this.fixedB = new HashMap<>();
        this.freeP = new HashMap<>();
        this.nbBlock = nbBlock;
        this.nbPile = nbPile;

        // On initialise le domaine général puis on ajoute les blocks et les piles
        this.domaineOnB = new HashSet<>();
        for (int i = 0; i < nbBlock; i++) {
            domaineOnB.add(i);
        }

        for (int j = 1; j <= nbPile; j++) {
            domaineOnB.add(-j);
        }

        
        //On définie les differents variable de notre monde avec leurs domaines respectifs

        for (int i = 0; i < nbBlock; i++) {
            // On definit le domaine de la variable onB du block i sans la valeur de i
            Set<Object> domaine = new HashSet<>(this.domaineOnB);
            domaine.remove(i);
            String name = "On" + i;
            this.onB.put(i, new Variable(name, domaine));
        }

        for (int i = 0; i < nbBlock; i++) {
            String name = "Fixed" + i;
            this.fixedB.put(i, new BooleanVariable(name));
        }

        for (int i = -1; i >= -nbPile; i--) {
            String name = "Free" + i;
            this.freeP.put(i, new BooleanVariable(name));
        }

    }

    // Methodes qui nous renvoie la liste de nos variables et leurs domaines respectifs
    public Map<Integer, Variable> getOnB() {
        return this.onB;
    }

    public Map<Integer, BooleanVariable> getFixedB() {
        return this.fixedB;
    }

    public Map<Integer, BooleanVariable> getFreeP() {
        return this.freeP;
    }

    public int getNbBlock() {
        return nbBlock;
    }

    public int getNbPile() {
        return nbPile;
    }

    @Override
    public String toString() {
        return "BlocksworldVariables [nbBlock=" + nbBlock + ", nbPile=" + nbPile + ", onB=" + onB + ", fixedB=" + fixedB
                + ", freeP=" + freeP + ", domaineOnB=" + domaineOnB + "]";
    }

}