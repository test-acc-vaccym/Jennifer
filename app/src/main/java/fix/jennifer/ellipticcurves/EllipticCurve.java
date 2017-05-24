package fix.jennifer.ellipticcurves;


import fix.jennifer.algebra.Operations;
import fix.jennifer.algebra.Point;

import java.math.BigInteger;


/* Характеристика поля > 3, поэтому кривая описывается уравнением:
             y = x^3 + ax + b
*/

public class EllipticCurve
{
    private BigInteger a;
    private BigInteger b;
    private BigInteger r;
    public final Point INF = new Point(BigInteger.ZERO, BigInteger.ZERO);

    public EllipticCurve(String userID)
    {
        generateRandomCurve(userID);
    }

    public Point getBasePoint()
    {
        /* Выбирается случайная точка на кривой, которая является основой для открытого ключа.
         */
        //return new Point(new BigInteger("602046282375688656758213480587526111916698976636884684818"),
        //        new BigInteger("174050332293622031404857552280219410364023488927386650641"));
        return new Point(new BigInteger("2"),
                         new BigInteger("4"));
    }

    public Point doublePoint(Point point)
    {
        BigInteger x, y, l;

        l = new BigInteger("3");
        l = l.multiply(point.getX().multiply(point.getX())); // 3 * x^2
        l = l.add(a); // 3 * x^2 + a
        l = l.multiply(point.getY().multiply(Operations.TWO).modInverse(Operations.P));  // l * (2 * y) ^ -1
        l = l.mod(Operations.P);
        x = l.multiply(l).add(Operations.P.subtract(Operations.TWO.multiply(point.getX()))); // x = l^2 + (p - 2 * x)
        y = point.getY().add(l.multiply(x.subtract(point.getX())));
        return new Point(x.mod(Operations.P), y.mod(Operations.P));
    }

    public Point sum(Point p, Point q)
    {
        BigInteger x, y, l;

        l = q.getY().subtract(p.getY()); // Qy - Py
        x = q.getX().subtract(p.getX()); // Qx - Px
        l = l.multiply(x.modInverse(Operations.P)); // (Qy - Py) * (Qx - Px) ^ -1
        l = l.mod(Operations.P);
        x = l.multiply(l).subtract(p.getX()).subtract(q.getX()); // x = l^2 - Px - Qx
        y = p.getY().add(l.multiply(x.subtract(p.getX())));   // y = Py + l * (x - Px)
        return new Point(x.mod(Operations.P), y.mod(Operations.P));
    }

    public BigInteger getY2(BigInteger x)
    {
        BigInteger y2 = x.multiply(x.multiply(x));  // x ^ 3;
        y2 = y2.add(x.multiply(a)); // x^3 + a * x
        y2 = y2.add(b);    // x^3 + ax + b
        return y2.mod(Operations.P);
    }

    private void generateRandomCurve(String userID)
    {
        /* Кривая задается двумя параметрами а и b, которые определяются
           алгоритмом Шофа или разложением на комплексные составляющие.
           Пока берем кривую из стандарта NIST
        */
        //a = new BigInteger("6277101735386680763835789423207666416083908700390324961276");
        //b = new BigInteger("2455155546008943817740293915197451784769108058161191238065");
        //r = new BigInteger("6277101735386680763835789423176059013767194773182842284081");

        a = new BigInteger("1");
        b = new BigInteger("1");
        //r = new BigInteger("6277101735386680763835789423176059013767194773182842284081");
    }
}
