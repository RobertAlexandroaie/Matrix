/**
 * 
 */
package demo;

import model.MatrixFactory;
import model.RussianMatrix;

/**
 * @author Robert
 *
 */
public class Demo {

    /**
     * @param args
     */
    public static void main(String[] args) {
	RussianMatrix A = MatrixFactory.buildRandomRussianMatrix("square", 7);
	RussianMatrix B = MatrixFactory.buildRandomRussianMatrix("square", 7);

	System.out.println("A:");
	System.out.println(A);

	System.out.println("B:");
	System.out.println(B);

	RussianMatrix C = A.mulMatrix(B);

	System.out.println("Result:");
	System.out.println(C);
    }

}
