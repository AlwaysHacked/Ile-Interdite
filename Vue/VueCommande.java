package Vue;

import Modele.Ile;
import Controlleur.Controlleur;

import javax.swing.*;
import java.awt.*;

public class VueCommande extends JPanel {
    /**
     * Pour que le bouton puisse transmettre ses ordres, on garde une
     * référence au modèle.
     */
    private Ile ile;

    /** Constructeur. */
    public VueCommande(Ile ile) {
        this.ile = ile;
        /**
         * On crée un nouveau bouton, de classe [JButton], en précisant le
         * texte qui doit l'étiqueter.
         * Puis on ajoute ce bouton au panneau [this].
         */
//        JButton boutonAvance = new JButton(">");
//        this.add(boutonAvance);
//        Controlleur ctrl = new Controlleur(ile);
//        /** Enregistrement du contrôleur comme auditeur du bouton. */
//        boutonAvance.addActionListener(ctrl);

        JButton nextButton = new JButton("Next Turn");
        this.add(nextButton);

//        Dimension dim = new Dimension(VueGrille.TAILLE* ile.getSize(),((VueGrille.TAILLE *2+5) * ile.getSize())/4);
//        this.setPreferredSize(dim);

        Controlleur next = new Controlleur(ile);
        /** Enregistrement du contrôleur comme auditeur du bouton. */
        nextButton.addActionListener(next);


        /**
         * Variante : une lambda-expression qui évite de créer une classe
         * spécifique pour un contrôleur simplissime.
         *
         JButton boutonAvance = new JButton(">");
         this.add(boutonAvance);
         boutonAvance.addActionListener(e -> { ile.avance(); });
         *
         */

    }
}
/** Fin de la vue. */
