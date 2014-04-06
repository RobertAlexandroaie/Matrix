/**
 * 
 */
package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

import utils.Constants;

/**
 * @author Robert
 */
public class BooleanMatrix<T> extends AbstractMatrix<Boolean> {

    BooleanMatrix(Class<T> type) {
	super(type);
    }

    BooleanMatrix(Class<T> type, int... dims) {
	super(type, dims);
    }

    /*
     * (non-Javadoc)
     * @see model.AbstractMatrix#add(model.AbstractMatrix)
     */
    @Override
    public BooleanMatrix<Boolean> sumMatrix(AbstractMatrix<Boolean> secondMatrix) {
	BooleanMatrix<Boolean> C = (BooleanMatrix<Boolean>) MatrixFactory.buildRandomMatrix(Constants.MATRIX_ORDINARY, Boolean.class, this.rows,
		secondMatrix.getColumns());
	try {
	    if (rows != secondMatrix.getRows() || columns != secondMatrix.getColumns()) {
		throw new IllegalArgumentException();
	    } else {
		for (int i = 0; i < rows; i++) {
		    for (int j = 0; j < columns; j++) {
			if (this.type.isInstance(Boolean.class)) {
			    Boolean obj1 = getElement(i, j);
			    Boolean obj2 = secondMatrix.getElement(i, j);
			    C.setElement(i, j, sumElements(obj1, obj2));
			}
		    }
		}
	    }
	} catch (IllegalArgumentException e) {
	    logger.log(Level.SEVERE, "second matrix does not match");
	}
	return C;
    }

    private Boolean sumElements(Boolean obj1, Boolean obj2) {
	return obj1 | obj2;
    }

    /*
     * (non-Javadoc)
     * @see model.MatrixInterface#mulMatrix(model.AbstractMatrix)
     */
    @Override
    public BooleanMatrix<Boolean> mulMatrix(AbstractMatrix<Boolean> secondMatrix) {
	BooleanMatrix<Boolean> C = (BooleanMatrix<Boolean>) MatrixFactory.buildRandomMatrix(Constants.MATRIX_ORDINARY, Boolean.class, this.rows,
		secondMatrix.getColumns());
	Boolean sum = new Boolean(false);

	for (int i = 0; i < rows; i++) {
	    for (int j = 0; j < secondMatrix.columns; j++) {
		for (int k = 0; k < columns; k++) {
		    Boolean obj1 = getElement(i, k);
		    Boolean obj2 = secondMatrix.getElement(k, j);
		    sum = sumElements(sum, mulElements(obj1, obj2));
		}
		C.setElement(i, j, sum);
		sum = false;
	    }
	}
	return C;
    }

    private Boolean mulElements(Boolean obj1, Boolean obj2) {
	Boolean result = null;
	try {
	    result = obj1 & obj2;
	} catch (NullPointerException e) {
	    logger.log(Level.SEVERE, "multiplication with null elements");
	    e.printStackTrace();
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * @see model.AbstractMatrix#getDefaultElement()
     */
    @Override
    public Boolean getDefaultElement() {
	return new Boolean(false);
    }

    /*
     * (non-Javadoc)
     * @see model.AbstractMatrix#getRandom()
     */
    @Override
    protected Boolean getRandom() {
	Random rand = new Random();
	return rand.nextBoolean();
    }

    /*
     * (non-Javadoc)
     * @see model.AbstractMatrix#setReadValues(java.io.BufferedReader)
     */
    @Override
    public void setReadValues(BufferedReader reader) {
	String line;
	int lineIndex = 0;
	try {
	    while ((line = reader.readLine()) != null) {
		String values[] = line.split(" ");
		for (int column = 0; column < values.length; column++) {
		    setElement(lineIndex, column, (values[column].trim().equals("1")) ? true : false);
		}
	    }
	} catch (IOException e) {
	    logger.log(Level.SEVERE, "cannot read line");
	    e.printStackTrace();
	}
    }
}
