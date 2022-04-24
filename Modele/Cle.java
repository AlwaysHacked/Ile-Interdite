package Modele;

public class Cle extends Item{
    Cle(Type type){super(type);}

    @Override
    public String toString() {
        return " Clé " + super.getType().toString();
    }
}
