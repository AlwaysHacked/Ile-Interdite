package projet;

import java.util.ArrayList;

public class Joueur {
    private Ile ile;
    private Case position;
    private ArrayList<Item> inventaire = new ArrayList<>();

    public Joueur(Ile ile, Case position) {
        this.ile = ile;
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
        return switch (c) {
            case 'h' -> this.position.getVoisinH();
            case 'b' -> this.position.getVoisinB();
            case 'g' -> this.position.getVoisinG();
            case 'd' -> this.position.getVoisinD();
            default -> throw new IllegalArgumentException("Unknown direction");
        };
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

    public boolean collect(){
        if (canCollect(position.type)){
            Item tmp = ile.collect(position.type);
            if (tmp == null)
                return false;
            inventaire.add(tmp);
            while (inventaire.remove(new Cle(position.type)));
            return true;
        }
        return false;
    }

    private boolean canCollect(Item.Type type){
        for (Item item : inventaire)
            if (item instanceof Cle)
                if (item.type == type)
                    return true;
        return false;
    }
}
