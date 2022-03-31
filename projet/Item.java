package projet;

abstract class Item {

    public enum Type{EAU,TERRE,FEU,AIR};

    protected final Type type;

    public Item(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
