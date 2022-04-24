package Controlleur;

import Modele.Ile;
import Modele.Case;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ControlleurCase implements MouseListener {

    private Ile ile;
    private Case c;
    JPopupMenu pop;
    ArrayList<JMenuItem> items;


    public ControlleurCase(Ile g, Case c) {
        this.c = c;
        this.ile = g;
        this.items = new ArrayList<>();
        this.pop = new JPopupMenu();
        this.items.add(new JMenuItem("Bouger"));
        this.items.add(new JMenuItem("Secher"));
        this.items.add(new JMenuItem("Fouille"));
        this.pop.add(items.get(0));
        this.pop.add(items.get(1));
        this.pop.add(items.get(2));
        var m = new ControlleurChoix(ile, c, "Bouger");
        items.get(0).addActionListener(m);
        var n = new ControlleurChoix(ile, c, "Secher");
        items.get(1).addActionListener(n);
        var l = new ControlleurChoix(ile, c, "Fouille");
        items.get(2).addActionListener(l);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

        if(SwingUtilities.isLeftMouseButton(e) && ile.estEnJeu() && ile.getActionRest() > 0)
//            System.out.print(c.toString());
            this.pop.show(e.getComponent(), e.getX(), e.getY());;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return "{ case = " + c.toString() + " }";
    }
}
