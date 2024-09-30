package b07lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
	
	// Fields
	double[] coefficients;
	int[] exponents;
	
	// Constructor 1
	public Polynomial() {
		this.coefficients = new double[1];
		this.exponents = new int[1];
	}
	
	// Constructor 2
	public Polynomial(double[] coefficients, int[] exponents) {
		this.coefficients = coefficients;
		this.exponents = exponents;
	}
	
	// Constructor 3
	public Polynomial(File file) throws FileNotFoundException {
	
		// My Example
		// 7-2x5+7x4
		
		// We read the file using Scanner Class
		Scanner scanner = new Scanner(file);
		
		// We transform the file into String
		String line = scanner.nextLine();
		
		// Create an array to store the symbols
		String symbols = null;
		for (int i = 0; i < line.length(); i++) {
			
			// If the 1st character in String line is not -, we assume its +
			if (line.charAt(0) != '-') {
				symbols = "+";
			}
			
			else if (line.charAt(i) != '+' || line.charAt(i) != '-') {
				continue;
			}
			
			else {
				symbols += line.charAt(i);
			}
				
		}
		
		// We split the string to find the coefficients
		String[] split1 = line.split("\\+|-");
		// Result = [7, -2x5, 7x4]
		
		// Create a new polynomial that sets to default value
		Polynomial polynomial = new Polynomial();
		
		// Using for loop, we split the string again using the delimiter "x"
		String strCoef;
		String strExpo;
		
		for (int i = 0; i < split1.length; i++) {
			
			String term = split1[i].trim();

	        if (term.isEmpty()) {
	            continue;
	        }

	        // We split the term by delimiter "x"
	        String[] parts = term.split("x");

	        // We store the coefficient to strCoef
	        strCoef = parts[0].trim();
	        double coef = Double.parseDouble(strCoef); 

	        // We store the exponent to strExpo
	        // This is default value (assuming its constant)
	        int expo = 0; 
	        
	        // This is for non-constants
	        if (parts.length > 1) {
	            strExpo = parts[1].trim(); 
	            
	            if (!strExpo.isEmpty()) {
	            	expo = Integer.parseInt(strExpo);
	            }
	        }

	        // We create a new polynomial
	        double[] coefArray = {coef};
	        int[] expoArray = {expo};
	        Polynomial p1 = new Polynomial(coefArray, expoArray);

	        // Then, we add the polynomial using the add method
	        polynomial = polynomial.add(p1);
		}
		
	}


	// Methods
	public Polynomial add(Polynomial other) {
		
		int diffCount1 = 0;
		int identicalCount1 = 0;
		for (int i = 0; i < this.exponents.length; i++) {
			for (int j = 0; j < other.exponents.length; j++) {
				if (this.exponents[i] != other.exponents[j]) {
					continue;
				}
				else {
					identicalCount1++;
				}
			}
			diffCount1++;
		}
		
		int diffCount2 = 0;
		int identicalCount2 = 0;
		for (int i = 0; i < other.exponents.length; i++) {
			for (int j = 0; j < this.exponents.length; j++) {
				if (other.exponents[i] != this.exponents[j]) {
					continue;
				}
				else {
					identicalCount2++;
				}
			}
			diffCount2++;
		}
		
		
		// We must make a new double array to store the result of the combined coefficients
		int size = diffCount1 + identicalCount1 + diffCount2;
		double[] tempCoef = new double[size];
		int[] tempExpo = new int[size];
		
		// Adding the 1st polynomial
		// Using for loop, we add the coefficients of the 1st polynomial to the temporary coefficients array
		for (int i = 0; i < this.exponents.length; i++) {
			tempCoef[i] += this.coefficients[i];
			tempExpo[i] = this.exponents[i];
		}
		
		// Adding the 2nd polynomial
		// Using for loop, we add the coefficients of the 2nd polynomial to the temporary coefficients array
		for (int i = 0; i < other.exponents.length; i++) {
			
			for (int j = 0; j < tempExpo.length; j++) {
				
				if (other.exponents[i] != tempExpo[j]) {
					
					if (tempCoef[j] == 0.0) {
						tempExpo[j] = other.exponents[i];
						tempCoef[j] = other.coefficients[i];
						break;
					}
					else {
						continue;
					}
				}
				
				else {
					tempCoef[j] += other.coefficients[i];
					break;
				}
			}
		}
		
		// We loop the tempCoef array count how many elements that is non-zero
		int nonZeroCount = 0;
		for (int i = 0; i < tempCoef.length; i++) {
			
			// If the coefficient is not 0, we add the coefficient to resultCoef & add exponent to resultExpo
			if (tempCoef[i] != 0.0) {
				nonZeroCount++;
			}
			else {
				nonZeroCount = nonZeroCount;
			}
		}
		
		// Now, we want to make a new array consisting the end result of coefficient & exponents (without coefficients that is 0)
		double[] resultCoef = new double[nonZeroCount];
		int[] resultExpo = new int[nonZeroCount];
		
		// Now, we add all of the coefficients in tempCoef & all exponents in tempExpo to resultCoef & resultExpo
		for (int i = 0; i < resultCoef.length; i++) {
			resultCoef[i] += tempCoef[i];
			resultExpo[i] += tempExpo[i];
		}
		
		return new Polynomial(resultCoef, resultExpo);
	}
	
	
	public double evaluate(double x) {
		
		// We initialize the variable to store the result
		double result = 0.0;
		
		// Using for loop, we evaluate from the highest order in the array
		// Example : [6, 3, 0, 2] --> 6 + 3x + 0x^2 + 2x^3
		// Example : x = 2
		
		for (int i = this.coefficients.length - 1; i >= 0; i--) {
			result += this.coefficients[i] * Math.pow(x, i);
		}
		
		return result;
	}
	
	public boolean hasRoot(double x) {
		
		// Ee make a variable to store the evaluated polynomial
		double evaluatedPolynomial = evaluate(x);
		
		// Then, we check if the polynomial evaluated with x resulted to 0
		return evaluatedPolynomial == 0.0;
	}
	
	// This method multiplies the argument's polynomial into the calling object's polynomial
	public Polynomial multiply(Polynomial other) {
	
		// We create tempCoef array to store the multiplied values
		double[] tempCoef1 = new double[this.coefficients.length * other.coefficients.length];
		int[] tempExpo1 = new int[this.coefficients.length * other.coefficients.length];
		
		// We multiply all the elements and put it inside the temporary arrays
		// The tempExpo array may include redundant exponents
		int k = 0;
		for (int i = 0; i < this.coefficients.length; i++) {
			for (int j = 0; j < other.coefficients.length; j++) {
				
				double totalCoef = this.coefficients[i] * other.coefficients[j];
				int totalExpo = this.exponents[i] + other.exponents[j];
				
				tempCoef1[k] = totalCoef;
				tempExpo1[k] = totalExpo;
				k++;
			}
		}
		
		// We create 2nd temporary array to delete all redundant exponents (adding those who have the same exponents)
		double[] tempCoef2 = new double[this.coefficients.length * other.coefficients.length];
		int[] tempExpo2 = new int[this.coefficients.length * other.coefficients.length];
		
		
		// We combine the elements that have same exponents
	    int index = 0;
	    for (int i = 0; i < tempExpo1.length; i++) {
	        int currentExpo = tempExpo1[i];
	        double currentCoef = tempCoef1[i];

	        // Then, we check if the exponent already exist in tempExpo2
	        boolean found = false;
	        for (int j = 0; j < index; j++) {
	            if (tempExpo2[j] == currentExpo) {
	               
	            	// If the exponent already exists, add the coefficient to the existing one
	                tempCoef2[j] += currentCoef;
	                found = true;
	                break;
	            }
	        }

	        // If the exponent was not found, add it to the next available position in tempExpo2 and tempCoef2
	        if (!found) {
	            tempCoef2[index] = currentCoef;
	            tempExpo2[index] = currentExpo;
	            index++;
	        }
	    }

	    // Count the non-zero coefficients to create the result arrays
	    int nonZeroCount = 0;
	    for (int i = 0; i < index; i++) {
	        if (tempCoef2[i] != 0.0) {
	            nonZeroCount++;
	        }
	    }

	    // We create the real result array to store the coefficients & exponents
	    double[] resultCoef = new double[nonZeroCount];
	    int[] resultExpo = new int[nonZeroCount];
	    
	    int resultIndex = 0;
	    for (int i = 0; i < index; i++) {
	        if (tempCoef2[i] != 0.0) {
	            resultCoef[resultIndex] = tempCoef2[i];
	            resultExpo[resultIndex] = tempExpo2[i];
	            resultIndex++;
	        }
	    }

	    // Return the resulting polynomial
	    return new Polynomial(resultCoef, resultExpo);
	}
		
		

	
	public void saveToFile(String fileName) throws IOException {
		
		// Create a new file using FileWriter
		FileWriter paper = new FileWriter("fileName.txt");
		
		// Create a new string to store the textual formal of the polynomial
		String strPolynomial = "";
		
		// Loop the coefficients array & exponents array of the polynomial
		for (int i = 0; i < this.coefficients.length; i++) {
			
			// If the polynomial is bigger than 0, then we add a + in front of it
			if (this.coefficients[i] >= 0) {
				strPolynomial = "+" + this.coefficients[i] + "x" + this.exponents[i];
			}
			
			// If exponents[i] = 0, we only store the coefficients (or we know as constant)
			else if (this.exponents[i] == 0) {
				strPolynomial += this.coefficients[i];
			}
			
			// Else
			else {
				strPolynomial += this.coefficients[i] + "x" + this.exponents[i];
			}
		}
		
		// We write the file using the string we already created
		paper.write(strPolynomial);
		paper.close();
		
	}
};
