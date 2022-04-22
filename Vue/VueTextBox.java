package Vue;

import Modele.TextBox;
import Modele.Ile;
import Obs.Observer;

import javax.swing.*;
import java.awt.*;

public class VueTextBox extends JPanel implements Observer{

    private Ile ile;
    private TextBox box;
    private final int width = VueGrille.TAILLE* ile.getSize();
    private final int height = (((VueGrille.TAILLE *2+5) * ile.getSize())/3)*2;


    public VueTextBox(Ile g) {
        this.ile = g;
//        this.box = this.ile.getTextBox();
        this.box.addObserver(this);
        this.ile.addObserver(this);
        Dimension dim = new Dimension(width, height);
        this.setPreferredSize(dim);
        setOpaque(true);
    }


    @Override
    public void update() {
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paint(g);
    }

    public void paint(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRoundRect(0, 0, width,
                height, 35, 35);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(5, 5, width-10, height-10, 25, 25);

        g2.drawString(this.box.getText(), 40 , height / 2);
    }

}
