abstract class Item {

    public enum Type{EAU,TERRE,FEU,AIR};

    private Type type;

    public Item(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

public class Artefact extends Item {

    public Artefact(Type type) {
        super(type);
    }
}
