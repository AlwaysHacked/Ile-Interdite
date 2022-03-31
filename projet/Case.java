package projet;

public class Case {

    public enum State {SEC, INONDE, SUBMERGEE;

        @Override
        public String toString() {
            return switch (this) {
                case SEC -> "SEC";
                case INONDE -> "INO";
                case SUBMERGEE -> "SUB";
            };
        }
    };

    protected State etat;
    protected Item.Type type = null;

    private Case haut;
    private Case bas;
    private Case gauche;
    private Case droit;

    Case(boolean b){
        if (b)
            etat = State.SEC;
        else
            etat = State.SUBMERGEE;
    }

    Case(){
        new Case(true);
    }

    public Case setCases(Case haut, Case bas, Case g, Case d){
        this.haut = haut;
        this.bas = bas;
        this.gauche = g;
        this.droit = d;

        return this;
    }
    public Item.Type getType() { return type;}

    public void setType(Item.Type type) { this.type = type;}

    public State getEtat() {
        return etat;
    }

    public void setEtat(State etat) {
        this.etat = etat;
    }

    public boolean canCross(){
        return !(this.etat == State.SUBMERGEE);
    }

    public boolean canDry(){
        return this.etat == State.INONDE;
    }

    public Case[] getVoisins(){
        return new Case[] {this.haut, this.bas, this.gauche, this.droit};
    }

    public Case getVoisinH(){
        return this.haut;
    }
    public Case getVoisinB(){
        return this.bas;
    }
    public Case getVoisinG(){
        return this.gauche;
    }
    public Case getVoisinD(){
        return this.droit;
    }

    @Override
    public String toString() {
        return etat.toString();
    }
}
