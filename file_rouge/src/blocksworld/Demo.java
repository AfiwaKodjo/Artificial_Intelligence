package blocksworld;

import java.util.*;
import modelling.*;

public class Demo{
    public static void main(String[] args){
        Blocksworld blocksworld = new Blocksworld(10,5);
        blocksworld.putOn(0,1);
        blocksworld.putOn(1,2);
        blocksworld.putOn(2,3);
        blocksworld.putOn(3,-2);
        blocksworld.putInPile(4,-1);

        /*BasicAction action = new BasicAction(blocksworld, 2);
        //On verifie si le deplacement du block 0 au dessus du block 1 vers le block 4 est possible
        System.out.println(action.isApplicable(0,1,4));
        //On effectue le deplacement du block 0 au dessus du block 1 vers le block 4
        Blocksworld new_state = action.successor(0,1,4);

        //On affiche les nouvelles valeurs de nos variables après avoir effectuer l'action
        Variable on0 = new_state.getScope().getOnB().get(0);
        Variable fixed1 = new_state.getScope().getFixedB().get(1);
        Variable fixed4 = new_state.getScope().getFixedB().get(4);
        System.out.println(new_state.getOn().get(on0));
        System.out.println(new_state.getFixed().get(fixed1));
        System.out.println(new_state.getFixed().get(fixed4));
        */

        /*
        List<Integer> p1 = new ArrayList<>();
        List<Integer> p2 = new ArrayList<>();
        p1.add(1);p1.add(2);p1.add(3);p1.add(4);p1.add(-2);
        p2.add(5);p2.add(6);p2.add(-1);
        List<List<Integer>> bw = new ArrayList<>();
        bw.add(p1);
        bw.add(p2);
        Blocksworld instantiation = createInstanciation(bw);

        //On affiche les variable on de notre instantiation
        for(Map.Entry<Variable, Integer> entry : instantiation.getOn().entrySet()){
            Variable onB = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(onB.getName() + " : " + value);
        }

        //On affiche les variable fixed de notre instantiation
        for(Map.Entry<Variable, Boolean> entry : instantiation.getFixed().entrySet()){
            Variable fixedB = entry.getKey();
            Boolean value = entry.getValue();
            System.out.print(fixedB.getName() + " : " + value +"; ");
        }

         System.out.println();
        //On affiche les variable free de notre instantiation
        for(Map.Entry<Variable, Boolean> entry : instantiation.getFree().entrySet()){
            Variable freeP = entry.getKey();
            Boolean value = entry.getValue();
            System.out.print(freeP.getName() + " : " + value +"; ");
        }
        */  
    }

    //Methode pour créer une instantiation grace à une liste de piles
    public static Blocksworld createInstanciation(List<List<Integer>> listPile){
        Blocksworld instantiation = new Blocksworld(10, 5);
        //On va créer notre instantanciation  en parcourant la liste des piles fourni
        for(List<Integer> pile : listPile){
            for(int i = 0; i<pile.size(); i++){
                //Block courant dans la pile
                int current = pile.get(i);
                //on verifie si l'élément suivtant existe
                if(i+1<pile.size()){
                    //Block suivant dans la pile
                    int next = pile.get(i+1);
                    //On verifie si l'element suivant est une pile ou un block
                    if(next>0)
                        //On place le block courant au-dessus du block suivant
                        instantiation.putOn(current, next);
                    else
                        //On place le block courant sur la table de la pile
                        instantiation.putInPile(current, next);
                }
            }
        }
        return instantiation;
    }
}