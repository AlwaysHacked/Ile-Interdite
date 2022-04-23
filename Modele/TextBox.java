package Modele;

import Obs.Observable;

public class TextBox extends Observable {

    private Ile ile;
    private String text;

    public TextBox(Ile ile) {
        this.ile = ile;
        this.text = ile.getActionRest() + " actions restantes";
    }

    public String getText() { return this.text; }

    public void setCurrentText() {
        if (ile.getActionRest() == 0)
            this.text = "Il ne reste plus d'action !";
        else
            this.text = ile.getActionRest() + ile.getActionRest() > 1 ? " actions restantes" : " action restante";

        notifyObservers();
    }
}
