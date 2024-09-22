public class Polynomial {
	
	// fields
	double[] coefficients;
	
	// constructor 1
	public Polynomial() {
		this.coefficients = new double[1];
	}
	
	// constructor 2
	public Polynomial(double[] coefficients) {
		this.coefficients = coefficients;
	}
	
	// methods
	public Polynomial add(Polynomial p2) {
		
		// we need to find the max degree of the 2 polynomials (the highest degree becomes the length of 'result')
		int maxDegree = Math.max(this.coefficients.length, p2.coefficients.length);
		
		// we must make a new double array to store the result of the combined coefficients
		double[] result = new double[maxDegree];
		
		// using for loop, we add the coefficients of the 1st polynomial to the result array
		for (int i = 0; i < this.coefficients.length; i++) {
			result[i] = this.coefficients[i];
		}
		
		// using for loop, we add the coefficients of the 2nd polynomial to the result array
		for (int i = 0; i < p2.coefficients.length; i++) {
			result[i] = result[i] + p2.coefficients[i];
		}
		
		// finally, we create a new polynomial object with the contents 'result'
		return new Polynomial(result);
	}
	
	
	public double evaluate(double x) {
		
		// we initialize the variable to store the result
		double result = 0.0;
		
		// using for loop, we evaluate from the highest order in the array
		// example : [6, 3, 0, 2] --> 6 + 3x + 0x^2 + 2x^3
		// example : x = 2
		
		for (int i = this.coefficients.length - 1; i >= 0; i--) {
			result += this.coefficients[i] * Math.pow(x, i);
		}
		
		return result;
	}
	
	public boolean hasRoot(double x) {
		
		// we make a variable to store the evaluated polynomial
		double evaluatedPolynomial = evaluate(x);
		
		// then, we check if the polynomial evaluated with x resulted to 0
		return evaluatedPolynomial == 0.0;
	}
	
	
}
