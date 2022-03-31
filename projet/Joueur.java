package projet;

import java.util.ArrayList;

public class Joueur {
    private Case position;
    private ArrayList<Item> inventaire = new ArrayList<>();

    public Joueur(Case position) {
        this.position = position;
    }

    public Case getPosition() {
        return position;
    }

    public void setPosition(Case position) {
        this.position = position;
    }

    public ArrayList<Item> getInventaire() {
        return inventaire;
    }

    public void setInventaire(ArrayList<Item> inventaire) {
        this.inventaire = inventaire;
    }

    public Case getDirection(char c){
        Case cs;
        switch (c){
            case 'h': cs = this.position.getVoisinH(); break;
            case 'b': cs = this.position.getVoisinB(); break;
            case 'g': cs = this.position.getVoisinG(); break;
            case 'd': cs = this.position.getVoisinD(); break;
            default : throw new IllegalArgumentException("Unknown direction");
        }
        return cs;
    }

    public boolean move(char c){
        Case cs = getDirection(c);

        if (cs != null && cs.canCross()) {
            this.position = cs;
            return true;
        }
        return false;
    }

    public boolean dry(char c){
        Case cs = c == 'x' ? this.position : getDirection(c);

        if(cs != null && cs.canDry()) {
            cs.setEtat(Case.State.SEC);
            return true;
        }
        return false;
    }
}
