package b07lab1;

import java.io.File;
import java.io.IOException;

public class Driver {
	public static void main(String args[]) throws IOException {
		Polynomial p = new Polynomial();
		System.out.println("The polynomial evaluated at 3 is = " + p.evaluate(3));
		
		double[] c1 = {6, 1, 3, 5};
		int[] e1 = {0, 1, 2, 3};
		Polynomial p1 = new Polynomial(c1, e1);
		
		double[] c2 = {1, -2, 0, 4, 2};
		int[] e2 = {3, 2, 1, 4, 7};
		Polynomial p2 = new Polynomial(c2, e2);
		
		Polynomial result1 = p1.add(p2);
		for (int i = 0; i < result1.coefficients.length; i++) {
			System.out.println(result1.coefficients[i]);
			System.out.println(result1.exponents[i]);
		}
		
		
		if (result1.hasRoot(1)) {
			System.out.println("1 is a root of s");
		}
		
		else {
			System.out.println("1 is not a root of s");
		}
		
		Polynomial result2 = p1.multiply(p2);
		for (int i = 0; i < result2.coefficients.length; i++) {
			System.out.println(result2.coefficients[i]);
			System.out.println(result2.exponents[i]);
		}
		
		// Reading polynomial from a file
		File file = new File("my_polynomial.txt");
		Polynomial polynomialFromFile = new Polynomial(file);
		System.out.println("The polynomial loaded from file is : " + polynomialFromFile);
		
		// Saving polynomial to a file
		result2.saveToFile("result2_polynomial");
		System.out.println("The polynomial is saved to a file");
	}
};
