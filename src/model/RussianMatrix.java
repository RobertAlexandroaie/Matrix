/**
 * 
 */
package model;

import static utils.Constants.MATRIX_ORDINARY;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * @author Robert
 */
public class RussianMatrix extends BooleanMatrix<Boolean> {
    private static int n = 0, m = 0, p = 0;
    private static RussianMatrix result = null;
    private static ArrayList<ArrayList<ArrayList<Boolean>>> Ci = null;
    private static int resRows = 0;
    private static int resColumns = 0;

    /**
     * @param type
     */
    public RussianMatrix(Class<Boolean> type) {
	super(type);
    }

    public RussianMatrix(Class<Boolean> type, int... dims) {
	super(type, dims);
    }

    private void setParameters() {
	n = rows;
	m = (int) (Math.log(n) / Math.log(2));
	p = (int) Math.ceil(n*1.0 / m*1.0);
    }

    @Override
    public RussianMatrix mulMatrix(AbstractMatrix<Boolean> secondMatrix) {
	try {
	    if (!secondMatrix.getClass().getSimpleName().equals("RussianMatrix") || columns != secondMatrix.getRows()) {
		throw new IllegalArgumentException();
	    }

	    resRows = this.rows;
	    resColumns = secondMatrix.getColumns();
	    setParameters();

	    result = MatrixFactory.buildRandomRussianMatrix(MATRIX_ORDINARY, resRows, resColumns);
	    int mod = n % m;
	    if (mod != 0) {
		int paddingSize = m - mod;
		padMatrix(paddingSize);
		secondMatrix.padMatrix(paddingSize);
		result.padMatrix(paddingSize);
		n += paddingSize;
	    }
	    resRows = 0;
	    resColumns = 0;

	    Ci = new ArrayList<ArrayList<ArrayList<Boolean>>>();
	    for (int i = 0; i < p; i++) {
		computeCi(result, i, secondMatrix.getInfo());
	    }

	    return result;
	} catch (IllegalArgumentException e) {
	    logger.log(Level.SEVERE, "check secondMatrix argument");
	    e.printStackTrace();
	    return null;
	}
    }

    @SuppressWarnings("unchecked")
    private void computeCi(RussianMatrix multiplied, int index, ArrayList<ArrayList<Boolean>> secondMatrix) {
	ArrayList<ArrayList<Boolean>> C = new ArrayList<>();
	for (int i = 0; i < n; i++) {
	    C.add(new ArrayList<Boolean>());
	    for (int j = 0; j < n; j++) {
		C.get(i).add(Boolean.FALSE);
	    }
	}

	ArrayList<Boolean> row = new ArrayList<Boolean>(n);
	for (int i = 0; i < n; i++) {
	    initRow(row, n);
	    for (int j = m * index; j < m * (index + 1); j++) {
		if (getElement(i, j) == Boolean.TRUE) {
		    row = sumRows(row, secondMatrix.get(j));
		}
	    }
	    C.set(i, (ArrayList<Boolean>) row.clone());
	}

	Ci.add(C);
	System.out.print("C[" + (index + 1) + "]:");
	display(C);
	orMatrixes(multiplied, C);
    }

    private void initRow(ArrayList<Boolean> row, int size) {
	row.clear();
	for (int i = 0; i < size; i++) {
	    row.add(false);
	}
    }

    private void orMatrixes(RussianMatrix m1, ArrayList<ArrayList<Boolean>> m2) {
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < n; j++) {
		m1.getInfo().get(i).set(j, m2.get(i).get(j) | m1.getInfo().get(i).get(j));
	    }
	}
    }

    private ArrayList<Boolean> sumRows(ArrayList<Boolean> row1, ArrayList<Boolean> row2) {
	ArrayList<Boolean> result = new ArrayList<Boolean>(n);
	for (int i = 0; i < n; i++) {
	    result.add(row1.get(i) | row2.get(i));
	}
	return result;
    }

    @Override
    public String toString() {
	String result = "";
	for (ArrayList<Boolean> row : info) {
	    for (Boolean item : row) {
		result += (item) ? "1 " : "0 ";
	    }
	    result += System.lineSeparator();
	}
	return result;
    }

    public void display(ArrayList<ArrayList<Boolean>> matrix) {
	for (ArrayList<Boolean> row : matrix) {
	    System.out.print(System.lineSeparator());
	    for (Boolean item : row) {
		System.out.print((item) ? "1 " : "0 ");
	    }
	}
	System.out.println(System.lineSeparator());
    }

    /**
     * @return the result
     */
    public static RussianMatrix getResult() {
	return result;
    }

    /**
     * @return the ci
     */
    public static ArrayList<ArrayList<ArrayList<Boolean>>> getCi() {
	return Ci;
    }

    /**
     * @return the m
     */
    public static int getM() {
	return m;
    }

    /**
     * @return the p
     */
    public static int getP() {
	return p;
    }

}
