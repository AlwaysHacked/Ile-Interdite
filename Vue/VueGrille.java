package Vue;

import Controlleur.ControlleurCase;
import Modele.Case;
import Modele.Ile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Modele.Artefact;
import Modele.Item;
import Obs.Observer;


public class VueGrille extends JPanel implements Observer {
    /** On maintient une référence vers le modèle. */
    private Ile ile;
    /** Les cases sont stockes ici */
    private ArrayList<JLabel> jl = new ArrayList<>();
    /** Définition d'une taille (en pixels) pour l'affichage des cellules. */
    public final static int TAILLE = 20;

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
                TAILLE*ile.getSizeGrille() * 2 + 3 * this.TAILLE);
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
        if (c.getEtat() == Case.State.INONDE)
            n = "Ressources/case_innonde.png";
        else if (c.getEtat() == Case.State.SUBMERGEE)
            n = "Ressources/case_submerge.png";
        else if (c.getEtat() == Case.State.SEC)
            n = "Ressources/case_normal.png";

        this.newFrame(n, x, y, cnt, g);

        if (c.getType() != null) {
            if (c.getType() == Item.Type.FEU) {
                n = "Ressources/art_fire.png";
            }
            else if(c.getType() == Item.Type.AIR) {
                n = "Ressources/art_wind.png";
            }
            else if(c.getType() == Item.Type.TERRE) {
                n = "Ressources/art_earth.png";
            }
            else if(c.getType() == Item.Type.EAU)
                n = "Ressources/art_water.png";
            else if(c.getType() == Item.Type.HELIPORT)
                n = "Ressources/heliport.png";
        }

        this.newFrame(n, x, y, cnt, g);

        if (c.contientJoueur() && c.getJoueur().estEnVie()) {
            /** Pour indiquer le joueur courant */
            if(c.getJoueur().getNumero() == ile.getJoueur())
                this.newFrame("Ressources/case_joueur.png", x, y, cnt, g);
            n = "Ressources/player" + c.getJoueur().getNumero() + ".png";
            this.newFrame(n, x, y, cnt, g);

        }
    }


    public void newFrame(String n, int x, int y, int c, Graphics g) {
        int step = x*3;
        int abs = step + this.TAILLE/2;
        int ord = (ile.getSize() * 2 * TAILLE) - y*2 - TAILLE*2;

//        System.out.println(n);
        ImageIcon temp = new ImageIcon();
        ImageIcon temp2 = new ImageIcon(this.getClass().getResource(n));

        int width = (n.contains("player") || n.contains("art") ? TAILLE : TAILLE * 2);

//        g.drawImage()
        g.drawImage(temp2.getImage(),
                n.contains("player") ? abs - this.TAILLE/3 : n.contains("art") ? abs + this.TAILLE/3 : step, // abs
                n.contains("art")||n.contains("player") ? ord - this.TAILLE/4 : ord, // ord
                width + (n.contains("player") || n.contains("art") ? this.TAILLE/3 : this.TAILLE/2), // largeur
                width + (n.contains("player") || n.contains("art") ? this.TAILLE/2 : 0), this); // longueur

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
