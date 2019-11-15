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
		return "whole:" + getWholeNumber(fraction2) + " numerator:" + getNumerator(fraction2) + " denominator:"
				+ getDenominator(fraction2);
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
}