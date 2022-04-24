package Controlleur;

import Modele.Ile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlleur implements ActionListener {
    /**
     * On garde un pointeur vers le modèle, car le contrôleur doit
     * provoquer un appel de méthode du modèle.
     * Remarque : comme cette classe est interne, cette inscription
     * explicite du modèle est inutile. On pourrait se contenter de
     * faire directement référence au modèle enregistré pour la classe
     * englobante [VueCommandes].
     */
    Ile ile;
    public Controlleur(Ile ile) { this.ile = ile; }
    /**
     * Action effectuée à réception d'un événement : appeler la
     * méthode [avance] du modèle.
     */
    public void actionPerformed(ActionEvent e) {
        if(ile.estEnJeu())
            ile.tourSuivant();
    }
}
/** Fin du contrôleur. */
