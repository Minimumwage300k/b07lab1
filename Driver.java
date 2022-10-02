import java.io.*;

public class Driver { 
 public static void main(String [] args) {
/* OLD DRIVER TESTS
  Polynomial p = new Polynomial(); 
  System.out.println(p.evaluate(3)); 
  double [] c1 = {6,0,0,5}; 
  Polynomial p1 = new Polynomial(c1); 
  double [] c2 = {0,-2,0,0,-9}; 
  Polynomial p2 = new Polynomial(c2); 
  Polynomial s = p1.add(p2); 
  System.out.println("s(0.1) = " + s.evaluate(0.1)); 
  if(s.hasRoot(1)) 
   System.out.println("1 is a root of s"); 
  else 
   System.out.println("1 is not a root of s");
 */
	 
//LIST OF CONSTRUCTORS: empty constructor, array constructor, file constructor
//LIST OF METHODS: add, evaluate, hasRoot, multiply, saveToFile
//TEST 1: empty constructor
	 Polynomial p1 = new Polynomial();
	 System.out.println(p1.evaluate(0));

//TEST 2: array constructor, add, evaluate
	  double [] c1 = {6,0,0,5};
	  int[] e1 = {0,1,2,3};
	  Polynomial p2 = new Polynomial(c1, e1); 
	  double [] c2 = {0,-2,0,0,-9};
	  int[] e2 = {0,1,2,3,4};
	  Polynomial p3 = new Polynomial(c2, e2); 
	  Polynomial s = p2.add(p3); 
	  System.out.println("s(0.1) = " + s.evaluate(0.1));

  
//TEST 3: hasRoot
 if(s.hasRoot(1)) 
	   System.out.println("1 is a root of s"); 
	  else 
	   System.out.println("1 is not a root of s");
 
 //TEST 4: multiply
 	double [] c4 = {1,1};
 	int[] e4 = {0,1};
 	Polynomial p4 = new Polynomial(c4, e4); 
 	double [] c5 = {1};
 	int[] e5 = {0};
 	Polynomial p5 = new Polynomial(c5, e5); 
 	Polynomial m = p4.multiply(p5);
 	System.out.println("m(0) = " + m.evaluate(0));
 
 /*
 //TEST 5: file constructor
 	File read = new File("C:\\Users\\elatt\\eclipse-workspace\\b07lab1\\abc.txt");
 	try {
		Polynomial p6 = new Polynomial(read);
		System.out.println(p6.exponents[0]);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
*/
 	//TEST 6: saveToFile
 	 try {
		p2.saveToFile("C:\\Users\\elatt\\eclipse-workspace\\b07lab1\\abc.txt");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
} 
 