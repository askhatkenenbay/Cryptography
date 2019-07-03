

// a plaintext and ciphertext will both be binary arrays of length lm
public class AdvancedCipherEncrypter {
	private static final int[] DEFAULT_S_BOX = {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7};
	private static final int[] DEFAULT_P_BOX = {1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16};
	private static final int[] DEFAULT_KEY = {0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1};
	private static final int DEFAULT_ROUND = 5;

	public static int[] substitutionPermutationNetwork(int[] plaintext) {
		if (plaintext == null || plaintext.length == 0 || plaintext.length % 4 != 0) {
			return new int[0];
		}
		int currentRound = 0;
		while (currentRound < DEFAULT_ROUND - 2) {
			xorWithKey(plaintext, DEFAULT_KEY, currentRound);
			substitutionBox(plaintext, DEFAULT_S_BOX);
			permutationBox(plaintext, DEFAULT_P_BOX);
			currentRound++;
		}
		xorWithKey(plaintext, DEFAULT_KEY, currentRound);
		substitutionBox(plaintext, DEFAULT_S_BOX);
		xorWithKey(plaintext, DEFAULT_KEY, currentRound + 1);
		return plaintext;
	}

	public static int[] substitutionPermutationNetwork(int[] plaintext, int numberOfRounds, int[] key, int[] substitutionBoxIn, int[] permutationBox) {
		if (!validateInput(plaintext, numberOfRounds, key, substitutionBoxIn, permutationBox)) {
			return new int[0];
		}
		int currentRound = 0;
		while (currentRound < numberOfRounds - 2) {
			xorWithKey(plaintext, key, currentRound);
			substitutionBox(plaintext, substitutionBoxIn);
			permutationBox(plaintext, permutationBox);
			currentRound++;
		}
		xorWithKey(plaintext, key, currentRound);
		substitutionBox(plaintext, substitutionBoxIn);
		xorWithKey(plaintext, key, currentRound + 1);
		return plaintext;
	}

	private static boolean validateInput(int[] plaintext, int numberOfRounds, int[] key, int[] substitutionBox, int[] permutationBox) {
		if (plaintext == null || plaintext.length == 0 || plaintext.length % 4 != 0) {
			return false;
		}
		if (numberOfRounds < 2) {
			return false;
		}
		if (key == null || key.length == 0) {
			return false;
		}
		for (int value : key) {
			if (value > 1 || value < 0) {
				return false;
			}
		}
		if (substitutionBox == null || substitutionBox.length != 16) {
			return false;
		}
		return permutationBox != null && permutationBox.length >= 3 && (plaintext.length % permutationBox.length) == 0;
	}

	private static int[] xorWithKey(int[] text, int[] key, int currentRound) {
		int j = currentRound * 4;
		if (j > key.length - 1) {
			j = j % key.length;
		}
		for (int i = 0; i < text.length; i++) {
			text[i] = (text[i] + key[j]) % 2;
			if (j == key.length - 1) {
				j = 0;
			} else {
				j++;
			}
		}
		return text;
	}

	private static int[] substitutionBox(int[] text, int[] substitutionBoxIn) {
		int tempInt = 0;
		for (int i = 0; i < text.length; i = i + 4) {
			for (int j = i, k = 3; j < i + 4; j++, k--) {
				tempInt = tempInt + text[j] * (int) Math.pow(2, k);
			}
			tempInt = substitutionBoxIn[tempInt];
			int[] tempArr = new int[4];
			for (int j = 0; j < tempArr.length; j++) {
				if (tempInt % 2 == 1) {
					tempInt = tempInt / 2;
					tempArr[j] = 1;
				} else {
					tempInt = tempInt / 2;
					tempArr[j] = 0;
				}
			}
			for (int j = i, k = 3; j < i + 4; j++, k--) {
				text[j] = tempArr[k];
			}
		}
		return text;
	}

	private static int[] permutationBox(int[] text, int[] permutationBox) {
		int[] clone = new int[text.length];
		System.arraycopy(text, 0, clone, 0, text.length);
		int i = 0;
		int j;
		int temp = 0;
		while (i < text.length) {
			j = 0;
			while (j < permutationBox.length) {
				if (i == text.length) {
					break;
				}
				text[permutationBox[j] + temp] = clone[i];
				i++;
				j++;
			}
			temp = temp + j;
		}
		return text;
	}
	
	private AdvancedCipherEncrypter() {
		throw new IllegalStateException("Utility Class");
	}
}
