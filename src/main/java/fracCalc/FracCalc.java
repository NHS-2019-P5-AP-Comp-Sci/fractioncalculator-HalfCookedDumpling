/**
 * @author Mr. Rasmussen
 */

package fracCalc;

import java.util.Scanner;

public class FracCalc {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean done = false;
		while (!done) {
			System.out.println("Enter your fractional calculation (Type 0 to quit): ");
			String input = scan.nextLine();
			if (input.equals("0")) {
				break;
			}

			System.out.println(produceAnswer(input));
		}
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
	public static String produceAnswer(String input) {

		// TODO: Implement this function to produce the solution to the input

		Scanner scanInput = new Scanner(input);

		String fraction1 = scanInput.next();
		String operator = scanInput.next();
		String fraction2 = scanInput.next();

		scanInput.close();
		return operate(fraction1, operator, fraction2);
	}

	// TODO: Fill in the space below with any helper methods that you think you will
	// need

	public static int getWholeNumber(String input) {
		int firstUnderScore = input.indexOf('_');
		int divisionSymbol = input.indexOf('/');
		if (firstUnderScore == -1 && divisionSymbol == -1) {
			return Integer.parseInt(input);
		} else if (firstUnderScore <= 0) {
			return 0;
		} else {
			String stringWholeNumber = input.substring(0, firstUnderScore);
			return Integer.parseInt(stringWholeNumber);
		}
	}

	public static int getNumerator(String input) {
		int firstUnderScore = input.indexOf('_');
		int divisionSymbol = input.indexOf('/');
		if (divisionSymbol == -1) {
			return 0;
		} else {
			String numerator = input.substring(firstUnderScore + 1, divisionSymbol);
			return Integer.parseInt(numerator);
		}
	}

	public static int getDenominator(String input) {
		int divisionSymbol = input.indexOf('/');
		if (divisionSymbol == -1) {
			return 1;
		} else {
			String denominator = input.substring(divisionSymbol + 1);
			return Integer.parseInt(denominator);
		}
	}

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

	public static String reduceFraction(int whole, int numerator, int denominator) {
		int checkCount = 1;
		int gCF = 1;
		int modCheckNum = 0;
		int modCheckDen = 0;
		if (whole != 0) {
			numerator = Math.abs(numerator);
			denominator = Math.abs(denominator);
		}
		if (Math.abs(numerator) < Math.abs(denominator)) {
			checkCount = Math.abs(numerator);
		} else {
			checkCount = Math.abs(denominator);
		}
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

	public static String operate(String fraction1, String operator, String fraction2) {
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

}