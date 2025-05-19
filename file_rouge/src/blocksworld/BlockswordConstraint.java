package blocksworld;

import java.util.*;
import modelling.*;

public class BlockswordConstraint {
    // Méthode qui vérifie le fait qu'on ne peut pas avoir 2 blocs sur un même block
    public boolean isSatisfiedBy(Blocksworld blockworld) {
        // Verifie la contrainte {b,b'} : onb != onb'
        for (Variable onB1 : blockworld.getOn().keySet()) {
            for (Variable onB2 : blockworld.getOn().keySet()) {
                if (!onB1.equals(onB2)) {
                    DifferenceConstraint differenceConstraint = new DifferenceConstraint(onB1, onB2);
                    Map<Variable, Object> blocks = new HashMap<>();
                    blocks.put(onB1, blockworld.getOn().get(onB1));
                    blocks.put(onB2, blockworld.getOn().get(onB2));

                    if (!differenceConstraint.isSatisfiedBy(blocks)) {
                        return false;
                    }
                }
            }
        }

        // Verifie la contrainte {b,b'} : onb == b' ====> fixedb' == true
        for (Variable onB : blockworld.getOn().keySet()) {
            for (int b : blockworld.getScope().getOnB().keySet()) {
                Set<Object> s1 = new HashSet<>();
                s1.add(b);
                Set<Object> s2 = new HashSet<>();
                s2.add(true);

                Implication implication = new Implication(onB, s1, blockworld.getScope().getFixedB().get(b), s2);
                Map<Variable, Object> value = new HashMap<>();
                value.put(onB, blockworld.getOn().get(onB));
                value.put(blockworld.getScope().getFixedB().get(b),
                        blockworld.getFixed().get(blockworld.getScope().getFixedB().get(b)));

                if (implication.isSatisfiedBy(value)) {
                    return false;
                }

            }

        }
        // Verifie la contraint onb == -(p+1)==> freep == false
        for (Variable onB : blockworld.getOn().keySet()) {
            for (int p : blockworld.getScope().getFreeP().keySet()) {
                Set<Object>s1 = new HashSet<>();
                s1.add(p);
                Set<Object> s2 = new HashSet<>();
                s2.add(false);

                Implication implication = new Implication(onB, s1, blockworld.getScope().getFreeP().get(p), s2);
                Map<Variable, Object> value = new HashMap<>();
                value.put(onB,blockworld.getOn().get(onB));
                value.put(blockworld.getScope().getFreeP().get(p),
                blockworld.getFree().get(blockworld.getScope().getFreeP().get(p)));

                if (implication.isSatisfiedBy(value)) {
                    return false;
                }

            }
        }
        return true;
    }

}