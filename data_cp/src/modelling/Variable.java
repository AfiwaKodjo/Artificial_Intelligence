package modelling;

import java.util.Set;

public class Variable {
    protected String name; //nom de la variable
    protected Set<Object> domain; //domain de la variable

    public Variable(String name, Set<Object> domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public Set<Object> getDomain() {
        return domain;
    }

    // Méthode pour calculer le code de hachage de la variable
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    // Méthode pour vérifier l'égalité entre 2 variables
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true; 
            }
         if (obj == null){
            return false;
             }
        if (!(obj instanceof Variable)){
            return false;
            }
        Variable other = (Variable) obj;
        if (name == null) {
            return other.name == null;
            } else{
            return name.equals(other.name); }
    }
    @Override
    public String toString() {
        return name;
    }


   
}
