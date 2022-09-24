public class Polynomial {
	double [] coefficients;
	
	public Polynomial() {
		coefficients = new double[1];
		coefficients[0] = 0;
	}
	
	public Polynomial(double [] array) {
		coefficients = new double[array.length];
		for (int i = 0; i < array.length; i++) {
			coefficients[i] = array[i];
		}
	}
	
	public Polynomial add(Polynomial other) {
		Polynomial s = new Polynomial(other.coefficients);
		for(int i = 0; i < s.coefficients.length; i++) {
			if (i < coefficients.length) s.coefficients[i] += coefficients[i];
		}
		return s;
	}
	
	public double evaluate(double x) {
		double s = 0;
		for(int i = 0; i < coefficients.length; i++) {
			s += coefficients[i] * Math.pow(x, i);
		}
		return s;
	}
	
	public boolean hasRoot(double x) {
		double s = evaluate(x);
		if (s == 0) return true;
		else return false;
	}
}