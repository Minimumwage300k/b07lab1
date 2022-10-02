import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
	double [] coefficients; //non-zero coefficients
	int [] exponents; //degree of x-term
	
	public Polynomial() { //CHECK
		coefficients = new double[1];
		coefficients[0] = 0;
		exponents = new int[1];
		exponents[0] = 0;
	}
	
	public Polynomial(double[] other_c, int[] other_e) { //CHECK
		int length = other_c.length;
		coefficients = new double[length];
		exponents = new int[length];
		for (int i = 0; i < length; i++) {
			coefficients[i] = other_c[i];
			exponents[i] = other_e[i];
		}
	}
	
	public Polynomial(File s) throws FileNotFoundException { //added
		Scanner scan = new Scanner(s);
		String line1 = scan.nextLine();
		scan.close();
		int length1 = line1.length();
		
		//Split the Polynomial and record the seperate terms in an array
		String line2 = "";
		for(int i = 0; i < length1; i++) {
			if(line1.charAt(i) == '-') {
				line2 += "+" + line1.charAt(i);
			}
			else line2 += line1.charAt(i);
		}
		String[] terms = line2.split("\\+");
		
		//Record the terms in fields coefficient and exponent
		int length2 = terms.length; //the number of terms
		coefficients = new double[length2]; //initialize coefficients
		exponents = new int[length2]; //initialize exponents
		
		String term; //the term
		boolean at_coefficient;
		String term_coefficient; //the term coefficient
		String term_exponent; //the term exponent
		
		for(int i = 0; i < length2; i++) { //loop through the terms
			term = terms[i]; //record the term
			at_coefficient = true;
			term_coefficient = "";
			term_exponent = "";
			for(int j = 0; j < term.length(); j++) { //loop through the term
				if(at_coefficient == true) { //if not yet reached variable
					if(Character.isDigit(term.charAt(j)) || term.charAt(j) == '-' || term.charAt(j) == '.') { //if character is part of the coefficient
						term_coefficient += term.charAt(j); //collect the coefficient
						
					}
					else { //if reached variable
						at_coefficient = false;
					}
				}
				else { //if reached exponent
					term_exponent += term.charAt(j); //collect the exponent
				}
			}
			if(term_coefficient == "") term_coefficient = "1";
			coefficients[i] = Double.parseDouble(term_coefficient); //record the coefficient
			if(term_exponent == "" && at_coefficient == true) term_exponent = "0";
			if (term_exponent == "" && at_coefficient == false) term_exponent = "1";
			exponents[i] = Integer.parseInt(term_exponent); //record the exponent
		}
	}
	
	public Polynomial add(Polynomial other) { //CHECK
		int length1 = this.coefficients.length;
		int length2 = other.coefficients.length;
		
		Polynomial x = new Polynomial(this.coefficients, this.exponents);
		Polynomial y = new Polynomial(other.coefficients, other.exponents);
		
		//add like terms
		for(int i = 0; i < length1; i++) {
			for(int j = 0; j < length2; j++) {
				if(this.exponents[i] == other.exponents[j]) {
					x.coefficients[i] += y.coefficients[j];
					y.coefficients[j] = 0;
					y.exponents[j] = -1;
					
				}
			}
		}
			
		//tally the remaining of terms not added
		int terms_remaining = 0;
		for(int i = 0; i < length2; i++) {
			if(y.exponents[i] != -1) terms_remaining++;
		}
		
		int length3 = length1 + terms_remaining;
		double[] rC = new double[length3];
		int[] rE = new int[length3];
		
		//copy terms already added
		for(int i = 0; i < length1; i++) {
			rC[i] = x.coefficients[i];
			rE[i] = x.exponents[i];
		}
		
		//add remaining terms
		for(int i = length1; i < length3; i++) {
			if(y.exponents[i] != -1) {
				rC[i] = y.coefficients[i];
				rE[i] = y.exponents[i];
			}
		}
		
		Polynomial result = new Polynomial(rC, rE);
		return result;
	}
	
	public double evaluate(double x) { //CHECK
		double s = 0;
		for(int i = 0; i < coefficients.length; i++) {
			s += coefficients[i] * Math.pow(x, exponents[i]);
		}
		return s;
	}
	
	public boolean hasRoot(double x) { //CHECK
		double s = evaluate(x);
		if (s == 0) return true;
		else return false;
	}
	
	public Polynomial collectLikeTerms(Polynomial s) { //added
		int length1 = s.coefficients.length;

		//find the number of terms after collecting like terms
		int length2 = length1;
		for(int i = 0; i < length1; i++) {
			for(int j = 0; j < length1; j++) {
				if(i != j && s.exponents[i] == s.exponents[j]) {
					length2--;
				}
			}
		}
		
		//identify duplicate terms
		double[] new_coefficients = new double[length2];
		int[] new_exponents = new int[length2];
		for(int i = 0; i < length1; i++) {
			for(int j = 0; j < length1; j++) {
				if(i != j && s.exponents[i] == s.exponents[j]) {
					s.coefficients[i] += s.coefficients[i];
					s.coefficients[j] = 0;
					s.exponents[j] = -1;
				}
			}
		}
		
		//collect like terms
		for(int i = 0; i < length1; i++) {
			if(s.coefficients[i] != 0) new_coefficients[i] = s.coefficients[i];
			if(s.exponents[i] != -1) new_exponents[i] = s.exponents[i];
		}
		
		Polynomial a = new Polynomial(new_coefficients, new_exponents);
		return a;
	}
	
	public Polynomial multiply(Polynomial other) { //added
		int length1 = coefficients.length;
		int length2 = other.coefficients.length;
		
		double[] distributeC = new double[length1 * length2];
		int[] distributeE = new int[length1 * length2];
		
		//FOIL
		int loop_counter = 0;
		for(int i = 0; i < length1; i++) {
			for(int j = 0; j < length2; j++) {
				distributeC[j+loop_counter*length2] = coefficients[i] * other.coefficients[j];
				distributeE[j+loop_counter*length2] = exponents[i] + other.exponents[j];
			}
			loop_counter++;
		}
		
		Polynomial s1 = new Polynomial(distributeC, distributeE);
		Polynomial s = collectLikeTerms(s1);
		
		return s;
	}
	
	public void saveToFile(String fileName) throws IOException { //added
		FileWriter fw = new FileWriter(fileName);
		String s = "";
		boolean firstTerm = true;
		for(int i = 0; i < coefficients.length; i++) {
			if(coefficients[i] < 0) {
				if(firstTerm == false) {
					s = s + String.valueOf(coefficients[i]) + "x" + String.valueOf(exponents[i]);
				}
				else {
					s = String.valueOf(coefficients[i]) + "x" + String.valueOf(exponents[i]);
					firstTerm = false;
				}

			}
			else {
				if(firstTerm == false) {
					s = s + "+" + String.valueOf(coefficients[i]) + "x" + String.valueOf(exponents[i]);
				}
				else {
					s = String.valueOf(coefficients[i]) + "x" + String.valueOf(exponents[i]);
					firstTerm = false;
				}
				

			}
		}
		fw.write(s);
		fw.close();
	
	}
}