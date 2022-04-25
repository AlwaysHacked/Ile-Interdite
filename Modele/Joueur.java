package Modele;

import Obs.Observable;

import java.util.ArrayList;

public class Joueur {
    private Ile ile;
    private Case position;
    private ArrayList<Item> inventaire = new ArrayList<>();
    private int numero;
    private boolean evacue;

    public Joueur(Ile ile, Case position, int numero) {
        this.ile = ile;
        this.position = position;
        this.numero = numero;
        this.evacue = false;
    }

    /** Getter */
    public Case getPosition() {
        return position;
    }
    public ArrayList<Item> getInventaire() {
        return inventaire;
    }
    public String getStringInventaire() {
        String tmp = "Inventaire de joueur : " + numero;
        for (Item item : inventaire)
            tmp += item.toString();
        return tmp;
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

    /** Setter et modificateur */
    public void setPosition(Case position) {
        this.position = position;
    }
    public void addInventaire(Item item) {
        if (item.type != Item.Type.DECHET)
            inventaire.add(item);
    }
    public void evacuation(){
        this.evacue = true;
        this.position.enleveJoueur();
    }
    public void utiliseCle(Item.Type type){
        inventaire.removeIf(cle -> cle instanceof Cle && cle.type == type);
    }

    /** Test */
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
            this.position.enleveJoueur();
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
    public boolean estEvacue(){
        return this.evacue;
    }

    public boolean evacuable(){
        return this.ile.getArtSize() == 0 &&
                this.position.getType() == Item.Type.HELIPORT;
    }

    protected boolean canCollect(Case c){
        if (c != position) return false;
        for (Item item : inventaire)
            if (item instanceof Cle)
                if (item.type == c.type)
                    return true;
        return false;
    }

}
