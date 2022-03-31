package projet;

import static org.junit.Assert.*;
import org.testng.annotations.Test;

public class IleTest {
    int s = 10;
    Ile i = new Ile(s);

    @Test
    void etatDeGrille(){
        for (int t = 0; t < s; t++){
            for (int j = 0; j < s; j++){
                if(t == 0 || j == 0 || t == s-1 || j == s-1)
                    assertEquals(i.getCase(t,j).getEtat(), Case.State.SUBMERGEE);
                else
                    assertEquals(i.getCase(t,j).getEtat(), Case.State.SEC);
            }
        }
    }

    @Test
    void coinHautGauche(){;
        Case c = i.getCase(1,1);

        assertNull(c.getVoisinH());
        assertEquals(c.getVoisinB(), i.getCase(2,1));
        assertNull(c.getVoisinG());
        assertEquals(c.getVoisinD(), i.getCase(1,2));
    }

    @Test
    void coinHautDroit(){;
        Case c = i.getCase(1,s-2);

        assertNull(c.getVoisinH());
        assertEquals(c.getVoisinB(), i.getCase(2,s-2));
        assertEquals(c.getVoisinG(), i.getCase(1,s-3));
        assertNull(c.getVoisinD());
    }

    @Test
    void coinBasGauche(){;
        Case c = i.getCase(s-2,1);

        assertEquals(c.getVoisinH(), i.getCase(s-3, 1));
        assertNull(c.getVoisinB());
        assertNull(c.getVoisinG());
        assertEquals(c.getVoisinD(), i.getCase(s-2,2));
    }

    @Test
    void coinBasDroit(){;
        Case c = i.getCase(s-2,s-2);

        assertEquals(c.getVoisinH(), i.getCase(s-3,s-2));
        assertNull(c.getVoisinB());
        assertEquals(c.getVoisinG(), i.getCase(s-2,s-3));
        assertNull(c.getVoisinD());
    }
}