package src;

public class Case {

    public enum State {SEC, INONDE, SUBMERGEE};

    private State etat;
    private Item item;

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

    public void setCases(Case haut, Case bas, Case g, Case d){
        this.haut = haut;
        this.bas = bas;
        this.gauche = g;
        this.droit = d;
    }

    public State getEtat() {
        return etat;
    }

    public Item getItem() {
        return item;
    }

    public void setEtat(State etat) {
        this.etat = etat;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
