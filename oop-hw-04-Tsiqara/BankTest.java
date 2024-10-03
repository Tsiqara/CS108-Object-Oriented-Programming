import junit.framework.TestCase;

public class BankTest extends TestCase {
    public void testSmall(){
        assertEquals("""
                acct:0 bal:999 trans:1
                acct:1 bal:1001 trans:1
                acct:2 bal:999 trans:1
                acct:3 bal:1001 trans:1
                acct:4 bal:999 trans:1
                acct:5 bal:1001 trans:1
                acct:6 bal:999 trans:1
                acct:7 bal:1001 trans:1
                acct:8 bal:999 trans:1
                acct:9 bal:1001 trans:1
                acct:10 bal:999 trans:1
                acct:11 bal:1001 trans:1
                acct:12 bal:999 trans:1
                acct:13 bal:1001 trans:1
                acct:14 bal:999 trans:1
                acct:15 bal:1001 trans:1
                acct:16 bal:999 trans:1
                acct:17 bal:1001 trans:1
                acct:18 bal:999 trans:1
                acct:19 bal:1001 trans:1
                """, Bank.mainTest(new String[]{"small.txt", "4"}).toString());
    }

    public void test5K(){
        for (int i = 0; i < 1000; i ++) {
            assertEquals(Bank.mainTest(new String[]{"5k.txt", "1"}).toString(), Bank.mainTest(new String[]{"5k.txt", "10"}).toString());
        }
        for (int i = 0; i < 1000; i ++) {
            assertEquals(Bank.mainTest(new String[]{"5k.txt", "1"}).toString(), Bank.mainTest(new String[]{"5k.txt", "100"}).toString());
        }
    }

    public void test100K(){
        //takes about 3 mins
        for (int i = 0; i < 100; i ++) {
            assertEquals(Bank.mainTest(new String[]{"100k.txt", "100"}).toString(), Bank.mainTest(new String[]{"100k.txt", "500"}).toString());
        }
        for (int i = 0; i < 100; i ++) {
            assertEquals(Bank.mainTest(new String[]{"100k.txt", "1"}).toString(), Bank.mainTest(new String[]{"100k.txt", "200"}).toString());
        }
    }
}
