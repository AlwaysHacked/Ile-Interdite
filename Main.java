import Modele.Ile;
import Vue.CVue;

import java.awt.*;

public class Main {
    /**
     * L'amorçage est fait en créant le modèle et la vue, par un simple appel
     * à chaque constructeur.
     * Ici, le modèle est créé indépendamment (il s'agit d'une partie autonome
     * de l'application), et la vue prend le modèle comme paramètre (son
     * objectif est de faire le lien entre modèle et utilisateur).
     */
    public static void main(String[] args) {
        /**
         * Pour les besoins du jour on considère la ligne EvenQueue... comme une
         * incantation qu'on pourra expliquer plus tard.
         */
        EventQueue.invokeLater(() -> {
            /** Voici le contenu qui nous intéresse. */
            Ile ile = new Ile(10);
            CVue vue = new CVue(ile);
            ile.afficheGrille();
        });
    }
}
/** Fin de la classe principale. */
