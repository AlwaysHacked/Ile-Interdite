package projet;

public class Ile {
//    size is always >2, otherwise it's not interesting
//    sizeGrille, Serge removed `final` for instance to be able to write 2 constructors
    private int sizeGrille;
    private final int defaultGrilleSize = 10;
    private Case[][] Grille;

    private final Artefact eau = new Artefact(Item.Type.EAU);
    private final Artefact terre = new Artefact(Item.Type.TERRE);
    private final Artefact feu = new Artefact(Item.Type.FEU);
    private final Artefact air = new Artefact(Item.Type.AIR);

    public Ile(){
        new Ile(this.defaultGrilleSize);
    }

    public Ile(int sizeGrille){
        this.sizeGrille = sizeGrille;
        int s = this.sizeGrille;
        this.Grille = new Case[s][s];
        initGrille();
        caseLinking();
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

    public int getSizeGrille(){ return this.sizeGrille;}
}
