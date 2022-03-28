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
}
