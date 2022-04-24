package Modele;

public class Artefact extends Item{
    Artefact(Type type){
        super(type);
    }

    @Override
    public String toString() {
        return " Artéfact " + super.getType().toString();
    }
}
