import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void FlikTest() {
        int x = 128;
        int y = 128;
        int z = 127;
        int z1 = 127;
        String num = "127";
        System.out.println("127 comparing to 127" );
        assertTrue(num,Flik.isSameNumber(z, z1));
        System.out.println("expected compare ok, and result is ok" );
        //System.out.println("128 comparing to 128" );
        //assertTrue(Flik.isSameNumber(x, y));
        //System.out.println("expected compare ok, and result is ok" );
        System.out.println("128 comparing to 127" );
        assertTrue(Flik.isSameNumber(y, z));
        System.out.println("expected compare NG, and result is ok" );
    }
}
