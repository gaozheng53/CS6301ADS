/*
 * <h1>Fall 2017 Long Project 1: Integer arithmetic with arbitrarily large numbers</h1>
 * <p>
 * Unit test case for Num class.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-23
 */

package cs6301.g16;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class NumTest {
    private static final String pi =
            "314159265358979323846264338327950288419716939937510582097494459230781640628620" +
                    "899862803482534211706798214808651328230664709384460955058223172535940812848111" +
                    "745028410270193852110555964462294895493038196442881097566593344612847564823378" +
                    "678316527120190914564856692346034861045432664821339360726024914127372458700660" +
                    "631558817488152092096282925409171536436789259036001133053054882046652138414695" +
                    "194151160943305727036575959195309218611738193261179310511854807446237996274956" +
                    "735188575272489122793818301194912983367336244065664308602139494639522473719070" +
                    "217986094370277053921717629317675238467481846766940513200056812714526356082778";
    private static final String prime = "999836351599";
    private static final String as = "1024";
    private static final String bs = "1919";
    private static final String cs = "123456789";
    private static final long al = 1024;
    private static final long bl = 1919;
    private static final long cl = 123456789;
    private static long base = 2333;
    private static final Num primeNum = new Num(prime, base);
    private static final Num primeNumMinus = new Num("-" + prime, base);
    private static final Num piNum = new Num(pi, base);
    private static final Num piNumMinus = new Num("-" + pi, base);

    @Test
    public void getHalf() throws Exception {
        assertEquals(0, new Num(5430).getHalf().compareTo(new Num(2715)));
        assertEquals(0, new Num(5431).getHalf().compareTo(new Num(2715)));
        assertEquals(0, new Num(5430, 3).getHalf().compareTo(new Num(2715, 3)));
        assertEquals(0, new Num(5431, 3).getHalf().compareTo(new Num(2715, 3)));
        assertEquals(0, new Num(5432, 3).getHalf().compareTo(new Num(2716, 3)));
    }

    @Test
    public void shift() throws Exception {
        Num a = new Num(prime);
        a.shift(-1);
        assertEquals(0, a.compareTo(new Num(prime.substring(0, prime.length() - 1))));
        a.shift(-4);
        assertEquals(0, a.compareTo(new Num(prime.substring(0, prime.length() - 5))));

        Num b = new Num(prime);
        b.shift(1);
        assertEquals(0, b.compareTo(new Num(prime + "0")));
        b.shift(4);
        assertEquals(0, b.compareTo(new Num(prime + "00000")));
    }

    @Test
    public void split() throws Exception {
        Long[] al = {(long) 1, (long) 2, (long) 3, (long) 4, (long) 0, (long) 0, (long) 6, (long) 7, (long) 8, (long) 9};
        Long[] alowl = {(long) 1, (long) 2, (long) 3, (long) 4};
        Long[] ahighl = {(long) 0, (long) 6, (long) 7, (long) 8, (long) 9};

        LinkedList<Long> a = new LinkedList<>(Arrays.asList(al));
        LinkedList<Long> alow = new LinkedList<>();
        LinkedList<Long> ahigh = new LinkedList<>();
        Num.split(a, alow, ahigh, 5);
        assertArrayEquals(alowl, alow.toArray(new Long[alow.size()]));
        assertArrayEquals(ahighl, ahigh.toArray(new Long[ahigh.size()]));

        Long[] alp = {(long) 0, (long) 0, (long) 0, (long) 4, (long) 0, (long) 0, (long) 6, (long) 0, (long) 8, (long) 9};
        Long[] alowlp = {(long) 0, (long) 0, (long) 0, (long) 4};
        Long[] ahighlp = {(long) 0, (long) 6, (long) 0, (long) 8, (long) 9};
        LinkedList<Long> ap = new LinkedList<>(Arrays.asList(alp));
        LinkedList<Long> alowp = new LinkedList<>();
        LinkedList<Long> ahighp = new LinkedList<>();
        Num.split(ap, alowp, ahighp, 5);
        assertArrayEquals(alowlp, alowp.toArray(new Long[alowp.size()]));
        assertArrayEquals(ahighlp, ahighp.toArray(new Long[ahighp.size()]));
    }

    @Test
    public void karatsubaProduct() throws Exception {
        assertEquals(0, Num.Karatsuba(piNum, primeNum).compareTo(Num.standardProduct(piNum, primeNum)));
        assertEquals(0, Num.Karatsuba(primeNumMinus, piNum).compareTo(Num.Karatsuba(primeNum, piNumMinus)));
        assertEquals(0, Num.Karatsuba(primeNumMinus, piNum).compareTo(Num.standardProduct(primeNum, piNumMinus)));
    }

    @Test
    public void standardProduct() throws Exception {
        assertEquals(0, new Num("999672729978799149856801", base).compareTo(Num.standardProduct(primeNum, primeNum)));
        assertEquals(0, new Num(al * bl).compareTo(Num.standardProduct(new Num(as), new Num(bs))));
        assertEquals(0, new Num(al * cl).compareTo(Num.standardProduct(new Num(as), new Num(cs))));
        assertEquals(0, new Num(0).compareTo(Num.standardProduct(new Num(0), new Num((long) 898765432))));
        assertEquals(0, new Num(0).compareTo(Num.standardProduct(new Num(391203485), new Num(0))));
    }

    @Test
    public void SingleDigitProduct() throws Exception {
        Num a = new Num("43142341235323425658679", base);
        assertEquals(0, new Num("388281071117910830928111", base).compareTo(Num
                .SingleDigitProduct(a, 9)));
        assertEquals(0, new Num("301996388647263979610753", base).compareTo(Num
                .SingleDigitProduct(a, 7)));
        assertEquals(0, new Num("172569364941293702634716", base).compareTo(Num
                .SingleDigitProduct(a, 4)));

        // Test for other base.
//        Num b = new Num(1024, 2);
//        assertEquals(0, new Num(2048, 2).compareTo(Num.SingleDigitProduct(b, 2)));
    }

    @Test
    public void add() throws Exception {
        assertEquals(0, Num.add(new Num("388281071117910830928111"), new Num("301996388647263979610753")).compareTo(new Num("690277459765174810538864")));
        assertEquals(0, Num.add(new Num("1"), new Num("-100")).compareTo(new Num("-99")));
        assertEquals(0, Num.add(new Num("99999999"), new Num("1")).compareTo(new Num("100000000")));
        assertEquals(0, Num.add(new Num("100"), new Num("-100")).compareTo(new Num("0")));
        assertEquals(0, Num.add(new Num("99999999", base), new Num("-99999999", base)).compareTo(new Num("00000000", base)));
    }

    @Test
    public void subtract() throws Exception {
        Num a = new Num("823456789");
        Num am = new Num("-823456789");
        Num b = new Num("43142341235323425658679");
        Num bm = new Num("-43142341235323425658679");

        assertEquals(0, Num.subtract(a, b).compareTo(new Num("-43142341235322602201890")));
        assertEquals(0, Num.subtract(am, bm).compareTo(new Num("43142341235322602201890")));
        assertEquals(0, Num.subtract(am, b).compareTo(new Num("-43142341235324249115468")));
        assertEquals(0, Num.subtract(a, bm).compareTo(new Num("43142341235324249115468")));
//        assertEquals(0, Num.subtract(b, b).compareTo(new Num("0")));
        assertEquals(0, new Num(80).compareTo(Num.subtract(new Num(81), new Num(1))));
        assertEquals(0, new Num(16).compareTo(Num.subtract(new Num(80), new Num(64))));
        assertEquals(0, new Num(16).compareTo(Num.subtract(Num.subtract(new Num(81), new Num(1)), new Num(64))));
        assertEquals(0, new Num(1).compareTo(Num.subtract(new Num(999999), new Num(999998))));
        assertEquals(0, new Num(0).compareTo(Num.subtract(new Num(999999), new Num(999999))));

    }

    @Test
    public void product() throws Exception {
        long start, end, k, s;
        StringBuilder str1 = new StringBuilder(pi);
        str1.append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi);
        StringBuilder str2 = new StringBuilder(pi);
        str2.append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi);
        Num a = new Num(str1.toString());
        Num b = new Num(str2.toString());

        start = System.currentTimeMillis();
        Num sr = Num.standardProduct(a, b);
        end = System.currentTimeMillis();
        s = end - start;

        start = System.currentTimeMillis();
        Num kr = Num.Karatsuba(a, b);
        end = System.currentTimeMillis();
        k = end - start;

        System.out.println(k + " " + s);
        assertEquals(0, kr.compareTo(sr));
    }

    @Test
    public void power() throws Exception {
        Num a = new Num("12345678987654321");
        long b = 21;
        Num r = new Num("8352463477820448199777617700324962304106287175347690105944921968195235" +
                "688212630315464343856569020091366278098323848411577707164615290649420210175441312" +
                "727896979783727143588026098528646303551841951492211743067727895007705686080712984" +
                "281726366063954173675140635861156744996067805236974345894683997200233059198031868" +
                "9008927023033091471684721");
        assertEquals(0, Num.power(a, b).compareTo(r));
        assertEquals(0, Num.power(a.negate(), b).compareTo(r.negate()));
    }

    @Test
    public void divide() throws Exception {
        assertEquals(0, Num.divide(primeNum, new Num(cs, base)).compareTo(new Num(8098, base)));
        assertEquals(0, Num.divide(primeNumMinus, new Num(cs, base)).compareTo(new Num(-8098, base)));
        assertEquals(0, Num.divide(primeNum, new Num(cs, base).negate()).compareTo(new Num(-8098, base)));
        assertEquals(0, Num.divide(primeNumMinus, new Num(cs, base).negate()).compareTo(new Num(8098, base)));
    }

    @Test
    public void mod() throws Exception {
        assertEquals(0, Num.mod(primeNum, new Num(cs, base)).compareTo(new Num(83274277, base)));
        assertEquals(0, Num.mod(primeNum, new Num(cs, base).negate()).compareTo(new Num(83274277, base)));
        assertEquals(0, Num.mod(primeNumMinus, new Num(cs, base)).compareTo(new Num(-83274277, base)));
        assertEquals(0, Num.mod(primeNumMinus, new Num(cs, base).negate()).compareTo(new Num(-83274277, base)));
    }

    @Test
    public void power1() throws Exception {
        assertEquals(0, Num.power(new Num(1234), new Num(43)).compareTo(new Num(
                "8444070818514022596549979552282327371402704912029539539975124776396362" +
                        "012575765533518001581932360102790680823472889113531408453730304")));

        Num r = new Num("2939976925796893818723029497423166070426275504068816903169306380597339" +
                "6558728028827270293634952319533584393569647871332615517781558224170083" +
                "7984286407883085203115510421771394937233263245994871427535719640734249" +
                "5965555861276986875986431234939015998105882236419708730735595765139432" +
                "1788985506292779792139383450157787600955404559667363801374268362356629" +
                "3618931504260137066156747240539618353846888242700199816784237219803963" +
                "8441035957225718806177597492139544053913985807455644023279314658163769" +
                "6479048772378048169113114833085795450437087820668500532222646760580971" +
                "3769168910618836042195464236718101527462375741317957310448094666041630" +
                "5052663278826023762710888688592410664189059099109852785598746533730954" +
                "7867109220307273745161952645167787978485986928941507612924715590079487" +
                "0621662923902738918093611479787634029806248040701550635622396910194180" +
                "9705203370126414035211944587100905636940829011190878174359930951230330" +
                "19667987");
        assertEquals(0, Num.power(new Num(123), new Num(439)).compareTo(r));
        assertEquals(0, Num.power(new Num(-123), new Num(439)).compareTo(r.negate()));
    }

    @Test
    public void squareRoot() throws Exception {
        assertEquals(0, Num.squareRoot(primeNum).compareTo(new Num(999918, base)));
        assertEquals(0, Num.squareRoot(new Num(cs, base)).compareTo(new Num(11111, base)));
    }

    @Test
    public void compareTo() throws Exception {

        Num a = new Num((long) 123456788);
        Num ap = new Num((long) 123556789);
        Num app = new Num((long) 123546999);
        Num b = new Num("43142341235323425658679");
        Num c = new Num((long) -123456789);
        Num cp = new Num("-123456789");
        Num d = new Num("-43142341235323425658679");
        Num dp = new Num("-43142341235323425658679");

        assertEquals(-1, a.compareTo(ap));
        assertEquals(1, ap.compareTo(app));
        assertEquals(0, b.compareTo(b));
        assertEquals(0, c.compareTo(cp));
        assertEquals(0, d.compareTo(dp));

        assertEquals(-1, a.compareTo(b));
        assertEquals(1, b.compareTo(a));
        assertEquals(1, a.compareTo(c));
        assertEquals(1, a.compareTo(d));
        assertEquals(1, c.compareTo(d));
        assertEquals(-1, d.compareTo(c));
    }

}