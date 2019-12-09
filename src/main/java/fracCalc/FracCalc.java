/**
 * @author Mr. Rasmussen
 */

package fracCalc;

import java.util.Scanner;

public class FracCalc {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean done = false;

		// Sets up a while loop to continue the calculation until they type quit
		while (!done) {
			System.out.println("Enter your fractional calculation (Type \"quit\" to quit): ");
			String input = scan.nextLine();
			if (input.equals("quit")) {
				done = true;
			} else {
				System.out.println(produceAnswer(input));
			}
		}
		// Close scanner to stop resource leakage
		scan.close();
		// TODO: Read the input from the user and call produceAnswer with an equation
	}

	// ** IMPORTANT ** DO NOT DELETE THIS FUNCTION. This function will be used to
	// test your code
	// This function takes a String 'input' and produces the result
	//
	// input is a fraction string that needs to be evaluated. For your program, this
	// will be the user input.
	// e.g. input ==> "1/2 + 3/4"
	//
	// The function should return the result of the fraction after it has been
	// calculated
	// e.g. return ==> "1_1/4"

	// Grabs the input and breaks it up before operating
	public static String produceAnswer(String input) {
		// TODO: Implement this function to produce the solution to the input
		// Scanner to separate tokens
		Scanner scanInput = new Scanner(input);
		String result = "";
		String operator = "";
		String fraction = "";
		int loop = 0;
		// Loop to continue searching until the entire input is operated on
		while (scanInput.hasNext()) {
			if (loop == 0) {
				// Separates first fraction and check for invalid fraction
				result = scanInput.next();
				if (!isValidFraction(result)) {
					scanInput.close();
					return "Error: Fraction is in an invalid format.";
				} else if (getDenominator(result) == 0) {
					scanInput.close();
					return "Error: Cannot divide by zero.";
				} else if (result.length() > 10) {
					scanInput.close();
					return "Error: Fraction is too long.";
				}
			} else if (loop % 2 == 1) {
				// Separates the operator and check for invalid operator
				operator = scanInput.next();
				if (!isValidOperator(operator)) {
					scanInput.close();
					return "Error: Operator is in an invalid format.";
				}
			} else {
				// Separates subsequent fractions and check for invalid fraction
				fraction = scanInput.next();
				if (!isValidFraction(fraction)) {
					scanInput.close();
					return "Error: Fraction is in an invalid format.";
				} else if (getDenominator(fraction) == 0) {
					scanInput.close();
					return "Error: Cannot divide by zero.";
				} else if (fraction.length() > 10) {
					scanInput.close();
					return "Error: Fraction is too long.";
				}
				result = operate(result, operator, fraction);
			}
			loop++;
		}
		scanInput.close();
		return result;
	}

	// TODO: Fill in the space below with any helper methods that you think you will
	// need

	// Check for if it should print invalid fraction error message
	public static boolean isValidFraction(String input) {
		boolean returnValue = true;
		int uScoreI = input.indexOf('_');
		int fracI = input.indexOf('/');
		int negI = input.indexOf('-');
		char inChar;
		// Checks to make sure the fraction is all numbers besides delimiters
		for (int i = 0; i < input.length(); i++) {
			inChar = input.charAt(i);
			if (i != uScoreI && i != fracI && i != negI) {
				if (!(inChar == '0' || inChar == '1' || inChar == '2' || inChar == '3' || inChar == '4' || inChar == '5'
						|| inChar == '6' || inChar == '7' || inChar == '8' || inChar == '9')) {
					returnValue = false;
				}
			}
		}
		// Checks to make sure underscores, negative sign, and division symbol
		// is used properly
		if (negI > 0) {
			returnValue = false;
		} else if (uScoreI == 0 || fracI == 0) {
			returnValue = false;
		} else if (uScoreI - 1 == negI || uScoreI + 1 == fracI) {
			returnValue = false;
		} else if (uScoreI > 0 && fracI > 0 && fracI < uScoreI) {
			returnValue = false;
		} else if (uScoreI < 0 && negI + 1 == fracI) {
			returnValue = false;
		}
		return returnValue;
	}

	// Check for if it should print operator error message
	public static boolean isValidOperator(String input) {
		boolean returnValue = false;
		if (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/")) {
			returnValue = true;
		}
		return returnValue;
	}

	// Returns whole number of any valid fraction
	public static int getWholeNumber(String input) {
		int firstUnderScore = input.indexOf('_');
		int divisionSymbol = input.indexOf('/');
		if (firstUnderScore == -1 && divisionSymbol == -1) {
			return parseInt(input);
		} else if (firstUnderScore <= 0) {
			return 0;
		} else {
			String stringWholeNumber = input.substring(0, firstUnderScore);
			return parseInt(stringWholeNumber);
		}
	}

	// Returns numerator of any valid fraction
	public static int getNumerator(String input) {
		int firstUnderScore = input.indexOf('_');
		int divisionSymbol = input.indexOf('/');
		if (divisionSymbol == -1) {
			return 0;
		} else {
			String numerator = input.substring(firstUnderScore + 1, divisionSymbol);
			return parseInt(numerator);
		}
	}

	// Returns denominator of any valid fraction
	public static int getDenominator(String input) {
		int divisionSymbol = input.indexOf('/');
		if (divisionSymbol == -1) {
			return 1;
		} else {
			String denominator = input.substring(divisionSymbol + 1);
			return parseInt(denominator);
		}
	}

	// Takes an improper fraction and returns it in mixed number form
	public static String improperToForm(int numerator, int denominator) {
		int whole = numerator / denominator;
		int remainingNumerator = numerator % denominator;
		if (remainingNumerator == 0) {
			return "" + whole;
		} else if (whole == 0) {
			return reduceFraction(whole, remainingNumerator, denominator);
		} else {
			String reducedFraction = reduceFraction(whole, remainingNumerator, denominator);

			return "" + whole + "_" + reducedFraction;
		}
	}

	// Returns fraction with reduced numerator and denominator
	public static String reduceFraction(int whole, int numerator, int denominator) {
		int checkCount = 1;
		int gCF = 1;
		int modCheckNum = 0;
		int modCheckDen = 0;
		if (whole != 0) {
			numerator = Math.abs(numerator);
			denominator = Math.abs(denominator);
		}
		// Searches for all possible common denominators
		if (Math.abs(numerator) < Math.abs(denominator)) {
			checkCount = Math.abs(numerator);
		} else {
			checkCount = Math.abs(denominator);
		}
		// Sets common factor that was last searched as gCF
		for (int i = 1; i <= checkCount; i++) {
			modCheckNum = numerator % i;
			modCheckDen = denominator % i;
			if (modCheckNum == 0 && modCheckDen == 0) {
				gCF = i;
			}
		}
		int newNumerator = numerator / gCF;
		int newDenominator = denominator / gCF;
		if (newDenominator < 0) {
			newDenominator = Math.abs(newDenominator);
			newNumerator = -newNumerator;
		}
		return newNumerator + "/" + newDenominator;
	}

	// Returns the resultant string after operating with two valid fractions
	public static String operate(String fraction1, String operator, String fraction2) {
		// Multiplies the two fractions
		if (operator.equals("*")) {
			int whole1 = getWholeNumber(fraction1);
			int numerator1 = getNumerator(fraction1);
			if (whole1 < 0) {
				numerator1 = -numerator1;
			}
			int denominator1 = getDenominator(fraction1);
			int whole2 = getWholeNumber(fraction2);
			int numerator2 = getNumerator(fraction2);
			if (whole2 < 0) {
				numerator2 = -numerator2;
			}
			int denominator2 = getDenominator(fraction2);
			int improperNumerator1 = whole1 * denominator1 + numerator1;
			int improperNumerator2 = whole2 * denominator2 + numerator2;
			int newDenominator = denominator1 * denominator2;
			int newNumerator = improperNumerator1 * improperNumerator2;
			return improperToForm(newNumerator, newDenominator);
			// Divides two fractions
		} else if (operator.equals("/")) {

			int whole1 = getWholeNumber(fraction1);
			int numerator1 = getNumerator(fraction1);
			if (whole1 < 0) {
				numerator1 = -numerator1;
			}
			int denominator1 = getDenominator(fraction1);
			int whole2 = getWholeNumber(fraction2);
			int numerator2 = getNumerator(fraction2);
			if (whole2 < 0) {
				numerator2 = -numerator2;
			}
			int denominator2 = getDenominator(fraction2);
			int improperNumerator1 = whole1 * denominator1 + numerator1;
			int improperNumerator2 = whole2 * denominator2 + numerator2;
			int newDenominator = denominator1 * improperNumerator2;
			int newNumerator = improperNumerator1 * denominator2;
			return improperToForm(newNumerator, newDenominator);
			// Subtracts two fractions
		} else if (operator.equals("-")) {

			int whole1 = getWholeNumber(fraction1);
			int numerator1 = getNumerator(fraction1);
			if (whole1 < 0) {
				numerator1 = -numerator1;
			}
			int denominator1 = getDenominator(fraction1);
			int whole2 = getWholeNumber(fraction2);
			int numerator2 = getNumerator(fraction2);
			if (whole2 < 0) {
				numerator2 = -numerator2;
			}
			int denominator2 = getDenominator(fraction2);
			int improperNumerator1 = whole1 * denominator1 + numerator1;
			int improperNumerator2 = whole2 * denominator2 + numerator2;
			int cDNumerator1 = improperNumerator1 * denominator2;
			int cDNumerator2 = improperNumerator2 * denominator1;
			int cDDenominator = denominator1 * denominator2;
			int subtractedNumerator = cDNumerator1 - cDNumerator2;
			return improperToForm(subtractedNumerator, cDDenominator);
			// Adds fractions
		} else {

			int whole1 = getWholeNumber(fraction1);
			int numerator1 = getNumerator(fraction1);
			if (whole1 < 0) {
				numerator1 = -numerator1;
			}
			int denominator1 = getDenominator(fraction1);
			int whole2 = getWholeNumber(fraction2);
			int numerator2 = getNumerator(fraction2);
			if (whole2 < 0) {
				numerator2 = -numerator2;
			}
			int denominator2 = getDenominator(fraction2);
			int improperNumerator1 = whole1 * denominator1 + numerator1;
			int improperNumerator2 = whole2 * denominator2 + numerator2;
			int cDNumerator1 = improperNumerator1 * denominator2;
			int cDNumerator2 = improperNumerator2 * denominator1;
			int cDDenominator = denominator1 * denominator2;
			int subtractedNumerator = cDNumerator1 + cDNumerator2;
			return improperToForm(subtractedNumerator, cDDenominator);

		}
	}

	// Converts pure string to an integer
	public static int parseInt(String input) {
		int digits = input.length() - 1;
		int returnValue = 0;
		int startIndex = 0;
		String charCompare = "";
		String currentNumber = "";
		if (input.charAt(0) == '-') {
			startIndex++;
		}
		// Search match with numbers 0-9 and adds it to the integer
		for (int i = startIndex; i < input.length(); i++) {
			for (int j = 0; j < 10; j++) {
				charCompare = j + "";
				currentNumber = input.charAt(i) + "";
				if (charCompare.equals(currentNumber)) {
					returnValue += j * Math.pow(10, digits - i);
				}
			}
		}
		// Make negative for strings with "-" at the start
		if (input.charAt(0) == '-') {
			returnValue *= -1;
		}
		return returnValue;
	}
}