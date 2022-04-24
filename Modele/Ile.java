package Modele;

import Obs.Observable;

import java.util.Random;
import java.util.ArrayList;

public class Ile extends Observable {
    /** Attributs de la grille */
    private int sizeGrille;
    private final int defaultGrilleSize = 6;
    private Case[][] Grille;

    /** Génération de l'aléatoire */
    Random rand = new Random();

    /** Attributs des joueurs */
    private final int nbJoueur = 4;
    private int joueurCourant;
    private int actionRest;
    private ArrayList<Joueur> joueurs = new ArrayList<>();

    /** Attributs des stocks */
    private ArrayList<Item> artefacts = new ArrayList<>();
    private ArrayList<Item> clés = new ArrayList<>();
    private int piocheMorte = 8;

    private boolean enJeu;

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
        makeStock(1);
        initJoueur(nbJoueur);
        initSpecialCase(1);
        this.actionRest = 3;
        this.joueurCourant = rand.nextInt(nbJoueur);
        this.enJeu = true;
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
    private void makeStock(int nb) {
        artefacts.add(new Artefact(Item.Type.EAU));
        artefacts.add(new Artefact(Item.Type.TERRE));
        artefacts.add(new Artefact(Item.Type.FEU));
        artefacts.add(new Artefact(Item.Type.AIR));
        for (int i = 0; i < nb; i++) {
            clés.add(new Cle(Item.Type.EAU));
            clés.add(new Cle(Item.Type.TERRE));
            clés.add(new Cle(Item.Type.FEU));
            clés.add(new Cle(Item.Type.AIR));
        }
        for (int i = 0; i < piocheMorte; i++)
            clés.add(new Cle(Item.Type.DECHET));
    }
    private void initJoueur(int nb){
        ArrayList<Case> cases = casesAleat(nb);
        for(Case c : cases) {
            this.joueurs.add(new Joueur(this, c, joueurs.size()));
            c.setJoueur(this.joueurs.get(this.joueurs.size() - 1));
        }
    }
    private void initSpecialCase(int nb){
        ArrayList<Case> cases = casesAleat(nb * 4 + 1);
        for (int i = 1; i < cases.size(); i++)
            cases.get(i).setType(artefacts.get(i%4).type);
        cases.get(0).setType(Item.Type.HELIPORT);
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
    public boolean estEnJeu(){
        return enJeu;
    }

    public int getArtSize(){
        return this.artefacts.size();
    }

    /** Setter */
    private void fin(){
        enJeu = false;
    }

    /** Les vérifications sur les joueurs à chaque tour de jeu
     * Utilise une fonction auxiliaire verifieJoueur(Joueur) en itérant sur chaque joueur */
    private void verifieJoueurs(){
        for (Joueur j :this.joueurs)
            if(!j.estEvacue())
                this.verifieJoueur(j);
    }

    private void verifieJoueur(Joueur j){
        ArrayList<Case> cases = j.getPosition().getVoisins();
        boolean entoure = true;

        /** Verifie si un joueur donné est entouré par des cases Submergées et le tue */
        for(Case c : cases)
            if(c != null && (c.getEtat() == Case.State.SEC || c.getEtat() == Case.State.INONDE)) {
                entoure = false;
                break;
            }

        if(entoure) { // mort, fin de jeu
            System.out.println("Joueur Mort : " + j.getNumero());
            System.out.println("Perdu, dommage");
            this.fin();
            return;
        }

        /** Si le joueur n'est pas entouré, vérifie s'il est lui même dans une case Submergée et le déplace */
        if(j.getPosition().getEtat() == Case.State.SUBMERGEE) {
            for (Case c : cases)
                if (c.getEtat() != Case.State.SUBMERGEE) {
                    this.movePlayer(j, c);
                    break;
                }
        }

    }
    public int getActionRest() {
        return actionRest;
    }

    /** Créé une liste de nb cases différentes et non submergées */
    private ArrayList<Case> casesAleat(int nb) {
        boolean putIntoList;
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
            cases.add(getCase(couples.get(i)[0], couples.get(i)[1]));

        return cases;
    }

    /** Vérifie si un joueur est arrivé (lui-même ou par chance d'inondation) à l'helico et peut être évacué */

    private void evacuation(){
        for(Joueur j : this.joueurs)
            if(!j.estEvacue() && j.evacuable())
                j.evacuation();
    }


    /** Méthode pour le controlleur */
    public void movePlayer(Case c){
        boolean t = movePlayer(this.joueurs.get(this.joueurCourant), c);
        if (!t)
            return;

        this.actionRest -= 1;

        notifyObservers();
        System.out.println("Il reste : " + this.actionRest + "action(s)");
    }

    private boolean movePlayer(Joueur j, Case c){
        return this.joueurs.get(j.getNumero()).move(c);
    }


    public void seche(Case c){ // verifier si la case donnee est adjacente
        boolean t = this.joueurs.get(this.joueurCourant).dry(c);
        if (t) System.out.println("sec");
        this.actionRest = t ? this.actionRest - 1 : this.actionRest;
        notifyObservers();
    }
    public void fouille(Case c){
        if (joueurs.get(joueurCourant).canCollect(c)
            && actionRest > 0) {
            Item tmp = collect(c.type);
            if (tmp != null) {
                joueurs.get(joueurCourant).addInventaire(tmp);
                joueurs.get(joueurCourant).utiliseCle(c.type);
                actionRest--;
                System.out.println(joueurs.get(joueurCourant).getStringInventaire());
                System.out.println(joueurCourant);
            }
        }
    }
    public void tourSuivant(){
        pioche();
        this.joueurCourant = this.joueurCourant == this.nbJoueur - 1 ? 0 : this.joueurCourant+1;
        if(this.joueurs.get(this.joueurCourant).estEvacue())
            tourSuivant();
        this.actionRest = 3;
        this.inondation();
        System.out.println(joueurs.get(joueurCourant).getStringInventaire());
//        afficheGrille();
        this.verifieJoueurs();
        notifyObservers();
    }

    private void pioche(){
        if(clés.size() > 0)
            joueurs.get(joueurCourant).addInventaire(clés.remove(rand.nextInt(clés.size())));
    }
    public Item collect(Item.Type type){
        for (Item artefact: artefacts)
            if (artefact.type == type) {
                artefacts.remove(artefact);
                return artefact;
            }
        return null;
    }
    private void inondation() {
        ArrayList<Case> cases = casesAleat(3);
        for(Case c : cases)
            c.inonde();
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

