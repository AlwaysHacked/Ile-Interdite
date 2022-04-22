package Vue;

import Modele.Case;
import Modele.Ile;

import javax.swing.*;
import java.awt.*;
import Obs.Observer;


public class VueGrille extends JPanel implements Observer {
    /** On maintient une référence vers le modèle. */
    private Ile ile;
    /** Définition d'une taille (en pixels) pour l'affichage des cellules. */
    public final static int TAILLE = 12;

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
        Dimension dim = new Dimension(TAILLE*ile.getSizeGrille(),
                TAILLE*ile.getSizeGrille());
        this.setPreferredSize(dim);
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
        /** Pour chaque cellule... */
        for(int i=1; i <= ile.getSizeGrille(); i++) {
            for(int j=1; j <= ile.getSizeGrille(); j++) {
                /**
                 * ... Appeler une fonction d'affichage auxiliaire.
                 * On lui fournit les informations de dessin [g] et les
                 * coordonnées du coin en haut à gauche.
                 */
                paint(g, ile.getCase(i, j), (i-1)*TAILLE, (j-1)*TAILLE);
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
    private void paint(Graphics g, Case c, int x, int y) {
        /** Sélection d'une couleur. */
//        if (c.estVivante()) {
//            g.setColor(Color.BLACK);
//        } else {
//            g.setColor(Color.WHITE);
//        }
//        /** Coloration d'un rectangle. */
//        g.fillRect(x, y, TAILLE, TAILLE);
    }

    public static int getTAILLE() {
        return TAILLE;
    }
}
