package Controlleur;

import Modele.Case;
import Modele.Ile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlleurChoix implements ActionListener {

    private Ile ile;
    private Case c;
    private String text;

    public ControlleurChoix(Ile ile, Case c, String s) {
        this.ile = ile;
        this.c = c;
        this.text = s;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (this.text) {
            case "Bouger":        this.ile.movePlayer(c); break;
            case "Secher":    this.ile.seche(this.c);
                System.out.println("sec"); break;
            default : throw new IllegalArgumentException("[In `ControlleurChoix`] : Unknown action");
        }
    }

}
