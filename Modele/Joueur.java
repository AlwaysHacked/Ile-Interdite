package Modele;

import Obs.Observable;

import java.util.ArrayList;

public class Joueur extends Observable {
    private Ile ile;
    private Case position;
    private ArrayList<Item> inventaire = new ArrayList<>();
    private int numero;

    public Joueur(Ile ile, Case position, int numero) {
        this.ile = ile;
        this.position = position;
        this.numero = numero;
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

    public int getNumero() {
        return numero;
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

    public boolean caseVoisine(Case cs){
        ArrayList<Case> voisins = this.position.getVoisins();
        if(!voisins.contains(cs) && cs != this.position)
            return false;
        System.out.println("Case voisine");
        return true;

    }

    public boolean move(char c){
        Case cs = getDirection(c);
        return move(cs);
    }

    public boolean move(Case cs){
        if(!caseVoisine(cs))    return false;
        if (cs != null && cs.canCross()) {
            if(!cs.setJoueur(this))
                return false;
            this.position.leveJoueur();
            this.position = cs;
            return true;
        }
        return false;
    }

    public boolean dry(char c){
        Case cs = c == 'x' ? this.position : getDirection(c);
        return dry(cs);
    }

    public boolean dry(Case cs){
        if(!caseVoisine(cs))    return false;
        if(cs != null && cs.canDry()) {
            cs.seche();
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
