import java.math.BigInteger;

public class RSA_Cryptosystem {
	private BigInteger x;
	private BigInteger b;
	private BigInteger n;
	private BigInteger m;
	private BigInteger key;
	private final static BigInteger minusOne = new BigInteger("-1");

	public RSA_Cryptosystem(BigInteger p, BigInteger q, BigInteger b) {
		n = p.multiply(q); // n = p * q
		m = p.add(minusOne).multiply(q.add(minusOne)); // m = (p-1)(q-1)
		if (EuclideanAlgorithm.euclideanAlgorithm(m, b).compareTo(BigInteger.ONE) != 0) {
			throw new IllegalArgumentException("Invalid b");
		} else {
			this.b = b;
		}
		key = EuclideanAlgorithm.multiplicativeInverse(b, m);
		System.out.println("key=" + key);
	}

	public BigInteger encryption(BigInteger input) {
		BigInteger result = power(input, b);
		return result.mod(n);
	}

	public BigInteger decryption(BigInteger input) {
		BigInteger result = power(input, key);
		return result.mod(n);
	}

	public BigInteger getB() {
		return b;
	}

	public BigInteger getN() {
		return n;
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
}
