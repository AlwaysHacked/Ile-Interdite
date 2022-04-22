package Modele;

import Obs.Observable;
import Vue.VueGrille;

import java.util.Random;
import java.util.ArrayList;

public class Ile extends Observable {
//    size is always >2, otherwise it's not interesting
//    sizeGrille, Serge removed `final` for instance to be able to write 2 constructors
    private int sizeGrille;
    private final int defaultGrilleSize = 10;
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
                    System.out.print(' ' + Grille[i-1][j-1].toString() + ' ');
            }
            System.out.println();
        }
    }

    public boolean movePlayer()

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

    //    private void inondation() {
//        int X[] = new int[] {-1, -1, -1};
//        int Y[] = new int[] {-1, -1, -1};
//
//        boolean done = false;
//
//        for (int i = 0; i < 3; i++){
//            int x = rand.nextInt(sizeGrille - 1);
//            int y = rand.nextInt(sizeGrille - 1);
//
//            for(int j = 0; j < i; j++) {
//                if (x == X[i] && y == Y[i])
//                    j--;
//                else
//                    X[i] = x;
//
//            }
//        }
//    }

//    public static void main(String[] args) {
//        Ile i = new Ile(10);
//        i.afficheGrille();
//    }


}

