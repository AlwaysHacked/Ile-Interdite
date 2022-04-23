package Modele;

import Obs.Observable;

import java.util.Random;
import java.util.ArrayList;

public class Ile extends Observable {
//    size is always >2, otherwise it's not interesting
//    sizeGrille, Serge removed `final` for instance to be able to write 2 constructors
    private int sizeGrille;
    private final int defaultGrilleSize = 6;
    private Case[][] Grille;

    Random rand = new Random();

    private int joueurCourant;
    private int actionRest;
    private ArrayList<Joueur> joueurs = new ArrayList<>();

    private ArrayList<Item> stock = new ArrayList<>();

    public Ile(){
        new Ile(this.defaultGrilleSize);
    }

    public Ile(int sizeGrille){
        this.sizeGrille = sizeGrille;
        int s = this.sizeGrille;
        this.Grille = new Case[s][s];
        initGrille();
        caseLinking();
        makeStock();
        initJoueur(1);
    }

    private void makeStock() {
        stock.add(new Artefact(Item.Type.EAU));
        stock.add(new Artefact(Item.Type.TERRE));
        stock.add(new Artefact(Item.Type.FEU));
        stock.add(new Artefact(Item.Type.AIR));
    }

    private void caseLinking(){
        for (int i = 1; i < sizeGrille-1; i++){
            for (int j = 1; j < sizeGrille-1; j++) {
                if (i == 1){
                    if (j == 1)
                        Grille[i][j] = Grille[i][j].setCases(null,Grille[i+1][j], null, Grille[i][j+1]);
                    else if (j == sizeGrille-2)
                        Grille[i][j] = Grille[i][j].setCases(null, Grille[i+1][j], Grille[i][j-1], null);
                    else
                        Grille[i][j] = Grille[i][j].setCases(null,Grille[i+1][j], Grille[i][j-1], Grille[i][j+1]);
                }
                else if (i == sizeGrille-2){
                    if (j == 1)
                        Grille[i][j] = Grille[i][j].setCases(Grille[i-1][j],null, null, Grille[i][j+1]);
                    else if (j == sizeGrille-2)
                        Grille[i][j] = Grille[i][j].setCases(Grille[i-1][j],null, Grille[i][j-1], null);
                    else
                        Grille[i][j] = Grille[i][j].setCases(Grille[i-1][j],null, Grille[i][j-1], Grille[i][j+1]);
                }
                else if (j == 1)
                    Grille[i][j] = Grille[i][j].setCases(Grille[i-1][j],Grille[i+1][j], null, Grille[i][j+1]);
                else if (j == sizeGrille-2)
                    Grille[i][j] = Grille[i][j].setCases(Grille[i-1][j],Grille[i+1][j], Grille[i][j-1], null);
                else
                    Grille[i][j] = Grille[i][j].setCases(Grille[i-1][j],Grille[i+1][j], Grille[i][j-1], Grille[i][j+1]);
            }
        }
    }

    private void initGrille(){
        for (int i = 0; i < sizeGrille; i++) {
            for (int j = 0; j < sizeGrille; j++) {
                if (i == 0 || i == sizeGrille - 1 ||
                        j == 0 || j == sizeGrille - 1)
                    Grille[i][j] = new Case(false);
                else
                    Grille[i][j] = new Case(true);
            }
        }
    }

    public Case getCase(int x, int y){
        return Grille[x][y];
    }

    public Item collect(Item.Type type){
        for (Item artefact:stock)
            if (artefact.type == type) {
                stock.remove(artefact);
                return artefact;
            }
        return null;
    }

    public int getSizeGrille(){
        return this.sizeGrille;
    }
    public int getSize(){ return this.sizeGrille; }

    public void afficheGrille(){
        int max = this.sizeGrille + 2;
        for (int i = 0; i < max; i++){
            for (int j = 0; j < max; j++){
                if (i == 0 || i == max-1) {
                    if (j == 0 || j == max-1)
                        System.out.print('+');
                    else System.out.print("─────");
                }
                else if (j == 0 || j == max-1)
                    System.out.print('│');
                else
                    System.out.print(' ' + Grille[i-1][j-1].toString());
            }
            System.out.println();
        }
    }

    public void movePlayer(Case c){
        boolean t = this.joueurs.get(this.joueurCourant).move(c);
        this.actionRest = t == true ? this.actionRest - 1 : this.actionRest;
//        return t;
    }

    public void seche(Case c){ // verifier si la case donnee est adjacente
        boolean t = this.joueurs.get(this.joueurCourant).dry(c);
        this.actionRest = t == true ? this.actionRest - 1 : this.actionRest;
//        return t;
    }

    public int getJoueur() {
        return joueurCourant;
    }

    public boolean tourSuivant(){
        this.joueurCourant = this.joueurCourant == 3 ? 0 : this.joueurCourant+1;
        this.actionRest = 3;
//        this.inondation();
        notifyObservers();
        return true;
    }

    public int getActionRest() {
        return actionRest;
    }

    private ArrayList<Case> casesAleat(int nb) {
        boolean putIntoList = false;
        ArrayList<int[]> couples = new ArrayList<>();
        ArrayList<Case> cases = new ArrayList<>();

        for (int i = 0; i < nb; i++) {
            putIntoList = true;
            int c[] = {1 + rand.nextInt(sizeGrille - 2), 1 + rand.nextInt(sizeGrille - 2)};

            for (int j = 0; j < i; j++) {
                if (c[0] == (couples.get(j))[0] && c[1] == (couples.get(j))[1] &&
                        this.getCase(c[0], c[1]).getEtat() == Case.State.SUBMERGEE) {
                    i--;
                    putIntoList = false;
                    break;
                }
            }
            if (putIntoList)
                couples.add(c);
        }
        for (int i = -1; ++i < nb;)
            System.out.println("Cases aleat :\t" + couples.get(i)[0] + " " + couples.get(i)[1]);
        System.out.println();

         for (int i = -1; ++i < nb;)
             cases.add(getCase(couples.get(i)[0], couples.get(i)[1]));

         return cases;
    }

    public void inondation() {
        ArrayList<Case> cases = casesAleat(3);
        for(Case c : cases)
            c.inonde();
    }

    private void initJoueur(int nb){
        ArrayList<Case> cases = casesAleat(nb);
        for(Case c : cases) {
            this.joueurs.add(new Joueur(this, c));
            c.setJoueur(this.joueurs.get(this.joueurs.size() - 1));
        }
    }
//    public static void main(String[] args) {
//        Ile i = new Ile(10);
//        i.afficheGrille();
//    }


}

