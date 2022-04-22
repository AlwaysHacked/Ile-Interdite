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
        // TODO Auto-generated method stub
        switch (this.text) {
            case "Move":
                if (this.ile.isRemainingMoves()) {
                    boolean can = this.ile.movePlayer(this.ile.getCurrentPlayer() , c);
                    if (can) {
                        this.ile.decreaseMoveNb();
                    } else {
                        System.out.print("déplacement impossible");
                    }
                } else {
                    System.out.print("déplacement impossible, no more moves");
                }
                break;
            case "Shore Up":
                if (this.ile.isRemainingMoves()) {
                    this.ile.shoreUp(this.c);
                } else {
                    System.out.print("déplacement impossible, no more moves");
                    break;
                }

            default:
                break;
        }

    }

}
