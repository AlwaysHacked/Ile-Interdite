package projet;

import org.junit.Test;
//
// import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class JoueurTest {
    Ile i = new Ile(10);
    Case c = i.getCase(5,5);
    Joueur j = new Joueur(i, c);

    @Test
    public void basicTest(){
        assertEquals(j.getPosition(), c);
        c = i.getCase(4,4);
        j.setPosition(c);
        assertEquals(j.getPosition(), c);
        assertEquals(j.getInventaire().size(), 0);
    }

    @Test
    public void moveDryTest(){
        c = i.getCase(5,5);
        j.setPosition(c);

        c.getVoisinH().setEtat(Case.State.INONDE);
        assertTrue(j.move('h'));
        assertEquals(j.getPosition(), i.getCase(4,5));
        assertTrue(j.dry('x'));
        assertEquals(c.getVoisinH().getEtat(), Case.State.SEC);

        assertTrue(j.move('b'));
        assertEquals(j.getPosition(), c);

        c.getVoisinG().setEtat(Case.State.SUBMERGEE);
        assertFalse(j.move('g'));
        assertFalse(j.dry('g'));
        assertEquals(j.getPosition(), c);

        assertTrue(j.move('d'));
        assertEquals(j.getPosition(), c.getVoisinD());
    }
}
