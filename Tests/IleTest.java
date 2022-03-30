import static org.junit.Assert.*;
import org.junit.*;

public class IleTest {
    Ile i = new Ile();
    int s = i.getSizeGrille();

    @Test
    void etatDeGrille(){
        for (int t = 0; t < s; t++){
            for (int j = 0; j < s; j++){
                if(t == 0 || j == 0 || t == s-1 || j == s-1)
                    assertEquals(i.getCase(t,j), Case.State.SUBMERGEE);
                else
                    assertEquals(i.getCase(t,j), Case.State.SEC);
            }
        }
    }

    @Test
    void coinHautGauche(){;
        Case c = i.getCase(0,0);

        assertNull(c.getVoisinH());
        assertEquals(c.getVoisinB(), i.getCase(1,0));
        assertNull(c.getVoisinG());
        assertEquals(c.getVoisinD(), i.getCase(0,1));
    }

    @Test
    void coinHautDroit(){;
        Case c = i.getCase(0,s-1);

        assertNull(c.getVoisinH());
        assertEquals(c.getVoisinB(), i.getCase(1,s-1));
        assertEquals(c.getVoisinG(), i.getCase(0,s-2));
        assertNull(c.getVoisinD());
    }

    @Test
    void coinBasGauche(){;
        Case c = i.getCase(s-1,0);

        assertEquals(c.getVoisinH(), i.getCase(s-3, 0));
        assertNull(c.getVoisinB());
        assertNull(c.getVoisinG());
        assertEquals(c.getVoisinD(), i.getCase(s-2,1));
    }

    @Test
    void coinBasDroit(){;
        Case c = i.getCase(s-1,s-1);

        assertNull(c.getVoisinH());
        assertEquals(c.getVoisinB(), i.getCase(1,s-1));
        assertEquals(c.getVoisinG(), i.getCase(s-1,s-2));
        assertNull(c.getVoisinD());
    }
}