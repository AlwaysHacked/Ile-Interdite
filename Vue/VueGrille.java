package Vue;

import Controlleur.ControlleurCase;
import Modele.Case;
import Modele.Ile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Modele.Artefact;
import Obs.Observer;


public class VueGrille extends JPanel implements Observer {
    /** On maintient une référence vers le modèle. */
    private Ile ile;
    /** Les cases sont stockes ici */
    private ArrayList<JLabel> jl = new ArrayList<>();
    /** Définition d'une taille (en pixels) pour l'affichage des cellules. */
    public final static int TAILLE = 30;

    /** Constructeur. */
    public VueGrille(Ile ile) {
        this.ile = ile;
        /** On enregistre la vue [this] en tant qu'observateur de [ile]. */
        ile.addObserver(this);
        /**
         * Définition et application d'une taille fixe pour cette zone de
         * l'interface, calculée en fonction du nombre de cellules et de la
         * taille d'affichage.
         */
        Dimension dim = new Dimension(TAILLE*ile.getSizeGrille() * 3,
                TAILLE*ile.getSizeGrille() * 3);
        this.setPreferredSize(dim);
        for (int i = 0; i < ile.getSizeGrille(); i++) {
            for (int j = 0; j < ile.getSizeGrille(); j++) {
                if (ile.getCase(i, j) != null) {
                    int step = j * 3;
                    ImageIcon temp = new ImageIcon();
                    JLabel object = new JLabel();

                    object.setBounds(step, (ile.getSize() * 2 * VueGrille.TAILLE) - i*2 - VueGrille.TAILLE*2,
                            VueGrille.TAILLE * 2 - 10,
                            VueGrille.TAILLE * 2 - 10);
                    object.setIcon(temp);
                    ControlleurCase ctrl = new ControlleurCase(this.ile, this.ile.getCase(i, j));
                    object.addMouseListener(ctrl);

                    this.add(object);
                    jl.add(object);
                }
            }
        }

    }

    /**
     * L'interface [Observer] demande de fournir une méthode [update], qui
     * sera appelée lorsque la vue sera notifiée d'un changement dans le
     * modèle. Ici on se content de réafficher toute la grille avec la méthode
     * prédéfinie [repaint].
     */
    public void update() { repaint(); }

    /**
     * Les éléments graphiques comme [JPanel] possèdent une méthode
     * [paintComponent] qui définit l'action à accomplir pour afficher cet
     * élément. On la redéfinit ici pour lui confier l'affichage des cellules.
     *
     * La classe [Graphics] regroupe les éléments de style sur le dessin,
     * comme la couleur actuelle.
     */
    public void paintComponent(Graphics g) {
        super.repaint();
        int c = -1;
        /** Pour chaque cellule... */
        for(int i = 0; i < ile.getSizeGrille(); i++) {
            for(int j = 0; j < ile.getSizeGrille(); j++) {
                if(ile.getCase(i,j) != null)
                    paint(g, ile.getCase(i, j), (i-1)*TAILLE, (j-1)*TAILLE, ++c); // commence a c+1 => -1+1 = 0

            }
        }
    }
    /**
     * Fonction auxiliaire de dessin d'une cellule.
     * Ici, la classe [Cellule] ne peut être désignée que par l'intermédiaire
     * de la classe [ile] à laquelle elle est interne, d'où le type
     * [ile.Cellule].
     * Ceci serait impossible si [Cellule] était déclarée privée dans [ile].
     */
    private void paint(Graphics g, Case c, int x, int y, int cnt) {
        String n = null;
//        System.out.println(c);
        if(c.getType() == null) {
            if (c.getEtat() == Case.State.INONDE)
                n = "Ressources/Innonde.png";
            else if (c.getEtat() == Case.State.SUBMERGEE)
                n = "Ressources/Submerge.png";
            else
                n = "Ressources/Normal.png";
        }
        else {
            if (c.getType() != null) {
                n = "Ressources/Normal2.png";
            }
//                n = "Ressources/art
        }
        this.newFrame(n, x, y, cnt, g);

        if(c.contientJoueur()){
            /** Pour indiquer le joueur courant */
            if(c.getJoueur().getNumero() == ile.getJoueur()) {
                n = "Ressources/move_3.png";
                this.newFrame(n, x, y, cnt, g);
            }

            n = "Ressources/player1.png";
            this.newFrame(n, x, y, cnt, g);
       }

    }


    public void newFrame(String n, int x, int y, int c, Graphics g) {
        int step = (n == "Ressources/player1.png" ? x*3 + 25 : x*3);

//        System.out.println(n);
        ImageIcon temp = new ImageIcon();
        ImageIcon temp2 = new ImageIcon(this.getClass().getResource(n));

        int width = (n == "Ressources/player1.png" ? TAILLE : TAILLE * 2);

        g.drawImage(temp2.getImage(), step, (ile.getSize() * 2 * TAILLE) - y*2 - TAILLE*2,
                width + (n == "Ressources/player1.png" ? 0 : 20),
                width, this);

        // JLabel object = new JLabel();
        jl.get(c).setBounds(step, (ile.getSize() *2* TAILLE) - y*2 - TAILLE*2,
                TAILLE * 2 - 10,
                TAILLE * 2 - 10);
        // object.setIcon(temp);
        // ZoneController ctrl = new ZoneController(this.grille.getZone(x/TAILLE,
        // y/TAILLE));
        // object.addMouseListener(ctrl);
        // this.add(object);
        jl.get(c).setIcon(temp);

    }

}
