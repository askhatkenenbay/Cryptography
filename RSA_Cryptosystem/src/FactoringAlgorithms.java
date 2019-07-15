import java.math.BigInteger;

public class FactoringAlgorithms {

	public static boolean trivialPrimeValidator(long n) {
		if (n % 2 == 0) {
			return false;
		}
		long p = 3;
		while (p < Math.sqrt((double) n)) {
			if (n % p == 0) {
				return false;
			}
			p += 2;
		}
		return true;
	}

	// The Pollard p-1 algorithm
	public static BigInteger pollardFactoringAlgorithm(BigInteger n, BigInteger B) {
		BigInteger a = new BigInteger("2");
		BigInteger j = new BigInteger("0");
		while (j.compareTo(B) != 1) {
			j = j.add(BigInteger.ONE);
			a = power(a, j);
			a = EuclideanAlgorithm.mod(a, n);
		}
		BigInteger minusOne = new BigInteger("-1");
		BigInteger temp = a.add(minusOne);
		BigInteger d = temp.gcd(n);
		if (d.compareTo(BigInteger.ONE) == 1 && d.compareTo(n) == -1) {
			return d;
		}
		return null;//failure
	}

	private static BigInteger power(BigInteger first, BigInteger second) {
		BigInteger result = new BigInteger("1");
		BigInteger i = new BigInteger("0");
		while (i.compareTo(second) == -1) {
			result = result.multiply(first);
			i = i.add(BigInteger.ONE);
		}
		return result;
	}

	//The Pollard Rho factoring algorithm
	public static BigInteger pollardRhoFactoringAlgorithm(BigInteger n, BigInteger x1) {
		BigInteger x = x1;
		BigInteger y = externalFunction(x);
		y = EuclideanAlgorithm.mod(y, n);
		BigInteger p = EuclideanAlgorithm.euclideanAlgorithm(x.add(y.negate()), n);
		while (p.compareTo(BigInteger.ONE) == 0) {
			x = externalFunction(x);
			x = EuclideanAlgorithm.mod(x, n);

			y = externalFunction(y);
			y = EuclideanAlgorithm.mod(y, n);
			y = externalFunction(y);
			y = EuclideanAlgorithm.mod(y, n);

			p = EuclideanAlgorithm.euclideanAlgorithm(x.add(y.negate()), n);
		}
		if (p.compareTo(n) == 0) {
			return null;
		} else {
			return p;
		}
	}

	// f(x) = x^2 + 1
	private static BigInteger externalFunction(BigInteger x) {
		return x.pow(2).add(BigInteger.ONE);
	}
}
