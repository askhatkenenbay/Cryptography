
/*
P = finite set of possible plaintexts
C = finite set of possible ciphertexts
K = finite set of possible keys
 */
public class CipherEncrypter {


	private static final int ASCII_VALUE_OF_CAPITAL_A = 65;
	private static final int ASCII_VALUE_OF_CAPITAL_Z = 90;
	private static final int ASCII_VALUE_OF_LOWER_A = 97;
	private static final int ASCII_VALUE_OF_LOWER_Z = 122;
	private static final int NUMBER_OF_LETTERS_IN_ENGLISH = 26;

	/**
	 * P = C = K = Z(26)
	 *
	 * @param key       for how many letters each char will be moved
	 * @param plaintext original text
	 * @return if int key is negative, will return null
	 * else, will return ciphertext as String
	 */
	public static String shiftCipher(int key, String plaintext) {
		if (key < 0) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		int validKey = key % NUMBER_OF_LETTERS_IN_ENGLISH;
		for (int i = 0; i < plaintext.length(); i++) {
			char currentChar = plaintext.charAt(i);
			if ((int) currentChar >= ASCII_VALUE_OF_LOWER_A) {
				if (currentChar + validKey <= ASCII_VALUE_OF_LOWER_Z) {
					result.append((char) (currentChar + validKey));
				} else {
					result.append((char) (currentChar + validKey - NUMBER_OF_LETTERS_IN_ENGLISH));
				}
			} else {
				if (currentChar + validKey <= ASCII_VALUE_OF_CAPITAL_Z) {
					result.append((char) (currentChar + validKey));
				} else {
					result.append((char) (currentChar + validKey - NUMBER_OF_LETTERS_IN_ENGLISH));
				}
			}
		}
		return result.toString();
	}

	/**
	 * P = C = Z(26)
	 * K = 26!
	 *
	 * @param lowerCase substitution for lower case letters
	 * @param upperCase substitution for upper case letters
	 * @param plaintext original text
	 * @return if either of lowerCase,upperCase equal to null, or have length other than 26, will return null string
	 * else, will return ciphertext as String
	 */
	public static String substitutionCipher(char[] lowerCase, char[] upperCase, String plaintext) {
		if (lowerCase == null || upperCase == null || lowerCase.length != NUMBER_OF_LETTERS_IN_ENGLISH || upperCase.length != NUMBER_OF_LETTERS_IN_ENGLISH) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		int position;
		char currentChar;
		for (int i = 0; i < plaintext.length(); i++) {
			currentChar = plaintext.charAt(i);
			if (currentChar >= ASCII_VALUE_OF_LOWER_A && currentChar <= ASCII_VALUE_OF_LOWER_Z) {
				position = currentChar - ASCII_VALUE_OF_LOWER_A;
				result.append(lowerCase[position]);
			} else if (currentChar >= ASCII_VALUE_OF_CAPITAL_A && currentChar <= ASCII_VALUE_OF_CAPITAL_Z) {
				position = currentChar - ASCII_VALUE_OF_CAPITAL_A;
				result.append(upperCase[position]);
			} else {
				result.append(currentChar);
			}
		}
		return result.toString();
	}

	/*
	if plaintext consists of only lower case letters
	 */
	public static String substitutionCipher(char[] lowerCase, String plaintext) {
		if (lowerCase == null || lowerCase.length != NUMBER_OF_LETTERS_IN_ENGLISH) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		int position;
		char currentChar;
		for (int i = 0; i < plaintext.length(); i++) {
			currentChar = plaintext.charAt(i);
			if (currentChar >= ASCII_VALUE_OF_LOWER_A && currentChar <= ASCII_VALUE_OF_LOWER_Z) {
				position = currentChar - ASCII_VALUE_OF_LOWER_A;
				result.append(lowerCase[position]);
			} else {
				result.append(currentChar);
			}
		}
		return result.toString();
	}

	/**
	 * P = C = Z(26)
	 * K = {(a,b) in Z(26) x Z(26) : gcd(a,26) = 1}
	 *
	 * @param a         used for equation
	 * @param b         used for equation
	 *                  encryption = (a*x+b) mod 26
	 *                  where x is current char
	 * @param plaintext original text
	 * @return if either of a or b is negative number, or gcd of a and 26 is not 1, will return null String
	 * else, ciphertext as String
	 */
	public static String affineCipher(int a, int b, String plaintext) {
		if (a < 0 || b < 0 || gcdOfTwoNumber(a, NUMBER_OF_LETTERS_IN_ENGLISH) != 1) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		char currentChar;
		int temp;
		for (int i = 0; i < plaintext.length(); i++) {
			currentChar = plaintext.charAt(i);
			if (currentChar >= ASCII_VALUE_OF_LOWER_A && currentChar <= ASCII_VALUE_OF_LOWER_Z) {
				temp = (a * (currentChar - ASCII_VALUE_OF_LOWER_A) + b) % NUMBER_OF_LETTERS_IN_ENGLISH;
				result.append((char) (temp + ASCII_VALUE_OF_LOWER_A));
			} else if (currentChar >= ASCII_VALUE_OF_CAPITAL_A && currentChar <= ASCII_VALUE_OF_CAPITAL_Z) {
				temp = (a * (currentChar - ASCII_VALUE_OF_CAPITAL_A) + b) % NUMBER_OF_LETTERS_IN_ENGLISH;
				result.append((char) (temp + ASCII_VALUE_OF_CAPITAL_A));
			} else {
				result.append(currentChar);
			}
		}
		return result.toString();
	}

	private static int gcdOfTwoNumber(int first, int second) {
		int result = 1;
		for (int i = 1; i <= first && i <= second; i++) {
			if (first % i == 0 && second % i == 0) {
				result = i;
			}
		}
		return result;
	}

	/**
	 * P = C = K = (Z(26))^m wherem is length of key
	 *
	 * @param key       String which will be used as key, must not contain other symbols except letters
	 *                  validity of key left to user
	 * @param plaintext original text
	 * @return if key null or length of key =0, will return null,
	 * else will return ciphertext as String
	 */
	public static String vigenereCipher(String key, String plaintext) {
		if (key == null || key.length() == 0) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		int[] keyArray = new int[key.length()];
		for (int i = 0; i < keyArray.length; i++) {
			char currentChar = key.charAt(i);
			if (currentChar >= ASCII_VALUE_OF_LOWER_A && currentChar <= ASCII_VALUE_OF_LOWER_Z) {
				keyArray[i] = currentChar - ASCII_VALUE_OF_LOWER_A;
			} else if (currentChar >= ASCII_VALUE_OF_CAPITAL_A && currentChar <= ASCII_VALUE_OF_CAPITAL_Z) {
				keyArray[i] = currentChar - ASCII_VALUE_OF_CAPITAL_A;
			}
		}
		for (int i = 0, j = 0; i < plaintext.length(); i++) {
			char currentChar = plaintext.charAt(i);
			if (currentChar >= ASCII_VALUE_OF_LOWER_A && currentChar <= ASCII_VALUE_OF_LOWER_Z) {
				if (currentChar + keyArray[j] <= ASCII_VALUE_OF_LOWER_Z) {
					result.append((char) (currentChar + keyArray[j]));
				} else {
					result.append((char) (currentChar + keyArray[j] - NUMBER_OF_LETTERS_IN_ENGLISH));
				}
			} else if (currentChar >= ASCII_VALUE_OF_CAPITAL_A && currentChar <= ASCII_VALUE_OF_CAPITAL_Z) {
				if (currentChar + keyArray[j] <= ASCII_VALUE_OF_CAPITAL_Z) {
					result.append((char) (currentChar + keyArray[j]));
				} else {
					result.append((char) (currentChar + keyArray[j] - NUMBER_OF_LETTERS_IN_ENGLISH));
				}
			}
			if (j < keyArray.length - 1) {
				j++;
			} else {
				j = 0;
			}
		}
		return result.toString();
	}

	/**
	 * P = C = (Z(26))^m where m is dimention of m x m matrix
	 * K = { m x m invertible matrices over Z(26)}
	 * encryption is = x * Key
	 *
	 * @param key       nxn matrix of integers used as key
	 * @param plaintext original text
	 * @return if plaintext or key is not valid, will return null String
	 * else ciphertext as String
	 */
	public static String hillCipher(int[][] key, String plaintext) {
		if (key == null || key.length != key[0].length || key.length < 2 || key[0].length < 2) {
			return null;
		} else if (plaintext.length() % key.length != 0 || !validateString(plaintext) || determinantOfMatrix(key, key.length) == 0) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < plaintext.length(); i = i + key.length) {
			int[][] temp = new int[1][key.length];
			for (int j = 0; j < key.length; j++) {
				if (plaintext.charAt(i + j) >= ASCII_VALUE_OF_LOWER_A && plaintext.charAt(i + j) <= ASCII_VALUE_OF_LOWER_Z) {
					temp[0][j] = plaintext.charAt(i + j) - ASCII_VALUE_OF_LOWER_A;
				} else {
					temp[0][j] = plaintext.charAt(i + j) - ASCII_VALUE_OF_CAPITAL_A;
				}
			}
			temp = matrixMultiplyMod26(temp, key);
			for (int j = 0; j < temp[0].length; j++) {
				result.append((char) (temp[0][j] + ASCII_VALUE_OF_LOWER_A));
			}
		}
		return result.toString();
	}

	/*
	helper for hill cipher, multiplies two matrices in Z(26)
	 */
	private static int[][] matrixMultiplyMod26(int[][] first, int[][] second) {
		int[][] result = new int[first.length][second[0].length];
		for (int i = 0; i < first.length; i++) {
			for (int j = 0; j < first[0].length; j++) {
				int temp = 0;
				for (int k = 0; k < second.length; k++) {
					temp = temp + first[i][k] * second[k][j];
				}
				result[i][j] = temp % 26;
			}
		}
		return result;
	}

	/*
	helper for hill cipher validates that plaintext does not contain other symbols except letters
	 */
	private static boolean validateString(String input) {
		int i =0;
		while(i<input.length()){
			int currentChar = input.charAt(i);
			i++;
			if (currentChar >= ASCII_VALUE_OF_LOWER_A && currentChar <= ASCII_VALUE_OF_LOWER_Z) {
				continue;
			}
			if (currentChar >= ASCII_VALUE_OF_CAPITAL_A && currentChar <= ASCII_VALUE_OF_CAPITAL_Z) {
				continue;
			}
			return false;
		}
		return true;
	}

	/*
	helper method for finding determinant
	as input takes matrix, and two ints p and q to find cofactor of matrix[p][q]
	 */
	private static int[][] getCofactor(int[][] key, int p, int q) {
		int i = 0;
		int j = 0;
		int[][] result = new int[key.length][key.length];
		for (int row = 0; row < key.length; row++) {
			for (int col = 0; col < key.length; col++) {
				if (row != p && col != q) {
					result[i][j++] = key[row][col];
					if (j == key.length - 1) {
						j = 0;
						i++;
					}
				}
			}
		}
		return result;
	}

	/*
	helper method for hill cipher, finds determinant of matrix
	as input takes matrix and its dimention
	recursive function
	 */
	private static int determinantOfMatrix(int[][] key, int size) {
		int[][] cofactor;
		int result = 0;
		if (size == 1) {
			return key[0][0];
		}
		int sign = 1;
		for (int i = 0; i < size; i++) {
			cofactor = getCofactor(key, 0, i);
			result = result + sign * key[0][i] * determinantOfMatrix(cofactor, size - 1);
			sign = -sign;
		}
		return result;
	}


	private CipherEncrypter() {
		throw new IllegalStateException("Utility class");
	}
}
