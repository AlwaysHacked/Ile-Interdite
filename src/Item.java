package src;

abstract class Item {

    public enum Type{EAU,TERRE,FEU,AIR};

    private final Type type;

    public Item(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
