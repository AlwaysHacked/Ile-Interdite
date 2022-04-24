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

    private final int nbJoueur = 4;
    private int joueurCourant;
    private int actionRest;
    private ArrayList<Joueur> joueurs = new ArrayList<>();

    private ArrayList<Item> stock = new ArrayList<>();

    /** Constructeur et méthode pour contructeur */
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
        initJoueur(nbJoueur);
        this.actionRest = 3;
        this.joueurCourant = rand.nextInt(nbJoueur);
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
    private void makeStock() {
        stock.add(new Artefact(Item.Type.EAU));
        stock.add(new Artefact(Item.Type.TERRE));
        stock.add(new Artefact(Item.Type.FEU));
        stock.add(new Artefact(Item.Type.AIR));
    }
    private void initJoueur(int nb){
        ArrayList<Case> cases = casesAleat(nb);
        for(Case c : cases) {
            this.joueurs.add(new Joueur(this, c, joueurs.size()));
            c.setJoueur(this.joueurs.get(this.joueurs.size() - 1));
        }
    }

    /** Getter */
    public Case getCase(int x, int y){
        return Grille[x][y];
    }
    public int getSizeGrille(){
        return this.sizeGrille;
    }
    public int getSize(){ return this.sizeGrille; }
    public int getJoueur() {
        return joueurCourant;
    }
    public int getActionRest() {
        return actionRest;
    }

    /** Créé une liste de nb cases différentes et non submergées */
    private ArrayList<Case> casesAleat(int nb) {
        boolean putIntoList = false;
        ArrayList<int[]> couples = new ArrayList<>();
        ArrayList<Case> cases = new ArrayList<>();

        for (int i = 0; i < nb; i++) {
            putIntoList = true;
            int c[] = {1 + rand.nextInt(sizeGrille - 2), 1 + rand.nextInt(sizeGrille - 2)};

            for (int j = 0; j < i; j++) {
                if (c[0] == (couples.get(j))[0] && c[1] == (couples.get(j))[1]) {
                    i--;
                    putIntoList = false;
                    break;
                }
            }
            if (this.getCase(c[0], c[1]).getEtat() == Case.State.SUBMERGEE){
                i--;
                putIntoList = false;
            }

            if (putIntoList )
                couples.add(c);
        }
        for (int i = -1; ++i < nb;)
            System.out.println("Cases aleat :\t" + couples.get(i)[0] + " " + couples.get(i)[1]);
        System.out.println();

        for (int i = -1; ++i < nb;)
            cases.add(getCase(couples.get(i)[0], couples.get(i)[1]));

        return cases;
    }


    /** Méthode pour le controlleur */
    public void movePlayer(Case c){
        boolean t = this.joueurs.get(this.joueurCourant).move(c);
        this.actionRest = t == true ? this.actionRest - 1 : this.actionRest;
        notifyObservers();
        System.out.println(this.actionRest);
//        return t;
    }
    public void seche(Case c){ // verifier si la case donnee est adjacente
        boolean t = this.joueurs.get(this.joueurCourant).dry(c);
        if (t) System.out.println("sec");
        this.actionRest = t ? this.actionRest - 1 : this.actionRest;
        notifyObservers();
    }
    public Item collect(Item.Type type){
        for (Item artefact:stock)
            if (artefact.type == type) {
                stock.remove(artefact);
                return artefact;
            }
        return null;
    }
    public void inondation() {
        ArrayList<Case> cases = casesAleat(3);
        for(Case c : cases)
            c.inonde();
    }
    public boolean tourSuivant(){
        this.joueurCourant = this.joueurCourant == this.nbJoueur - 1 ? 0 : this.joueurCourant+1;
        System.out.println("Num de joueur : " + this.joueurCourant);
        this.actionRest = 3;
        this.inondation();
        notifyObservers();
        this.afficheGrille();
        return true;
    }

    /** Affichage dans la console */
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




//    public static void main(String[] args) {
//        Ile i = new Ile(10);
//        i.afficheGrille();
//    }


}

