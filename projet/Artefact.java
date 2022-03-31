package projet;

public class Artefact extends Item{
    Artefact(Type type){
        super(type);
    }

    @Override
    public String toString() {
        return "Clé " + super.getType().toString();
    }
}
