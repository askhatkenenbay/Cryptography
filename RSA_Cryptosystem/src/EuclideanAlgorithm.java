import java.math.BigInteger;

public class EuclideanAlgorithm {
	/**
	 * Computes the greatest common divisor of two positive integers.
	 * Since the Euclidean algorithm computes greatest common divisors,
	 * it can be used to determine if a positive integer b < n has a multiplicative
	 * inverse modulo n, by calling Euclidean algorithm(n,b) anc
	 * checking to see if result is one.
	 *
	 * @param first  input1
	 * @param second input2
	 * @return greatest common divisor, if invalid input -1 will be returned
	 */
	public static int euclideanAlgorithm(int first, int second) {
		if (second == 0) {
			return first;
		}
		return euclideanAlgorithm(second, first % second);
	}

	public static long euclideanAlgorithm(long first, long second) {
		if (second == 0) {
			return first;
		}
		return euclideanAlgorithm(second, first % second);
	}

	public static BigInteger euclideanAlgorithm(BigInteger first, BigInteger second) {
		if (second.compareTo(BigInteger.ZERO) == 0) {
			return first;
		}
		return euclideanAlgorithm(second, first.mod(second));
	}

	/**
	 * Takes two integers first and second as input and computes integers x, y and z
	 * such that x = gcd(first, second) and x = first * y + second * z
	 *
	 * @param first  int
	 * @param second int
	 * @return returns array [3] { x, y ,z} where x is gcd(first,second) and
	 * x = first * y + second * z
	 */
	public static int[] extendedEuclideanAlgorithm(int first, int second) {
		if (second == 0) {
			return new int[]{first, 1, 0};
		}
		int[] result = extendedEuclideanAlgorithm(second, first % second);
		return new int[]{result[0], result[2], result[1] - (first / second) * result[2]};
	}

	public static BigInteger[] extendedEuclideanAlgorithm(BigInteger first, BigInteger second) {
		if (second.compareTo(BigInteger.ZERO) == 0) {
			return new BigInteger[]{first, BigInteger.ONE, BigInteger.ZERO};
		}
		BigInteger[] result = extendedEuclideanAlgorithm(second, first.mod(second));
		return new BigInteger[]{result[0], result[2], result[1].add(first.divide(second).multiply(result[2]).negate())};
	}

	/**
	 * first^(-1) mod second
	 * Finds multiplicative Inverse of first input mod second input
	 * uses extendedEuclideanAlgorithm
	 *
	 * @param first  input int
	 * @param second input int
	 * @return multiplicative Inverse as int
	 */
	public static int multiplicativeInverse(int first, int second) {
		int[] temp = extendedEuclideanAlgorithm(first, second);
		return mod(temp[1], second);
	}

	public static BigInteger multiplicativeInverse(BigInteger first, BigInteger second) {
		BigInteger[] temp = extendedEuclideanAlgorithm(first, second);
		return mod(temp[1], second);
	}

	public static int mod(int first, int second) {
		int result = first % second;
		if (result < 0) {
			result += second;
		}
		return result;
	}

	public static long mod(long first, long second) {
		long result = first % second;
		if (result < 0) {
			result += second;
		}
		return result;
	}

	public static BigInteger mod(BigInteger first, BigInteger second) {
		BigInteger result = first.mod(second);
		if (result.compareTo(BigInteger.ZERO) == -1) {
			result = result.add(second);
		}
		return result;
	}
}
