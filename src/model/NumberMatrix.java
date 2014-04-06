/**
 * 
 */
package model;

import java.io.BufferedReader;
import java.util.Random;
import java.util.logging.Level;

import utils.Constants;

/**
 * @author Robert
 */

public class NumberMatrix<T extends Number> extends AbstractMatrix<Number> {
    NumberMatrix(Class<T> type) {
	super(type);
    }

    NumberMatrix(Class<T> type, int... dims) {
	super(type, dims);
    }

    /*
     * (non-Javadoc)
     * @see model.AbstractMatrix#add(model.AbstractMatrix)
     */
    @Override
    public NumberMatrix<?> sumMatrix(AbstractMatrix<Number> secondMatrix) {
	NumberMatrix<?> C = (NumberMatrix<?>) MatrixFactory.buildRandomMatrix(Constants.MATRIX_ORDINARY, Number.class, this.rows,
		secondMatrix.getColumns());
	try {
	    if (rows != secondMatrix.getRows() || columns != secondMatrix.getColumns()) {
		throw new IllegalArgumentException();
	    } else {
		for (int i = 0; i < rows; i++) {
		    for (int j = 0; j < columns; j++) {
			Number a = getElement(i, j);
			Number b = secondMatrix.getElement(i, j);
			C.setElement(i, j, sumElements(a, b));
		    }
		}
	    }
	} catch (IllegalArgumentException e) {
	    logger.log(Level.SEVERE, "second matrix does not match");
	}
	return C;
    }

    /**
     * @param a
     * @param b
     * @return
     */
    private Number sumElements(Number a, Number b) {
	String aClassName = a.getClass().getSimpleName();
	String bClassName = a.getClass().getSimpleName();

	try {
	    if (!aClassName.equals(bClassName)) {
		throw new IllegalArgumentException();
	    } else {
		switch (aClassName) {
		case "Byte":
		    return (Byte) a + (Byte) b;
		case "Short":
		    return (Short) a + (Short) b;
		case "Integer":
		    return (Integer) a + (Integer) b;
		case "Long":
		    return (Long) a + (Long) b;
		case "Float":
		    return (Float) a + (Float) b;
		case "Double":
		    return (Double) a + (Double) b;
		default:
		    if (a instanceof ElementInterface && b instanceof ElementInterface) {
			ElementInterface e1 = (ElementInterface) a;
			ElementInterface e2 = (ElementInterface) b;

			return (Number) e1.sum(e2);
		    }
		}
	    }
	} catch (IllegalArgumentException e) {
	    logger.log(Level.SEVERE, "matrixes type don't match");
	    e.printStackTrace();
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * @see model.MatrixInterface#mulMatrix(model.AbstractMatrix)
     */
    @Override
    public NumberMatrix<?> mulMatrix(AbstractMatrix<Number> secondMatrix) {
	NumberMatrix<?> C = (NumberMatrix<?>) MatrixFactory.buildRandomMatrix(Constants.MATRIX_ORDINARY, Number.class, this.rows,
		secondMatrix.getColumns());
	Number sum = getElement(0, 0);
	sum = 0;

	for (int i = 0; i < rows; i++) {
	    for (int j = 0; j < secondMatrix.columns; j++) {
		for (int k = 0; k < columns; k++) {
		    Number a = getElement(i, k);
		    Number b = secondMatrix.getElement(k, j);
		    sum = sumElements(sum, mulElements(a, b));
		}
		C.setElement(i, j, sum);
		sum = 0;
	    }
	}
	return C;
    }

    public Number mulElements(Number a, Number b) {
	String aClassName = a.getClass().getSimpleName();
	String bClassName = a.getClass().getSimpleName();

	try {
	    if (!aClassName.equals(bClassName)) {
		throw new IllegalArgumentException();
	    } else {
		switch (aClassName) {
		case "Byte":
		    return (Byte) a * (Byte) b;
		case "Short":
		    return (Short) a * (Short) b;
		case "Integer":
		    return (Integer) a * (Integer) b;
		case "Long":
		    return (Long) a * (Long) b;
		case "Float":
		    return (Float) a * (Float) b;
		case "Double":
		    return (Double) a * (Double) b;
		default:
		    if (a instanceof ElementInterface && b instanceof ElementInterface) {
			ElementInterface e1 = (ElementInterface) a;
			ElementInterface e2 = (ElementInterface) b;
			return (Number) (e1.mul(e2));
		    }
		}
	    }
	} catch (IllegalArgumentException e) {
	    logger.log(Level.SEVERE, "matrixes type don't match!\na of type: " + aClassName + ", b of type: " + bClassName);
	    e.printStackTrace();
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * @see model.AbstractMatrix#getDefaultElement()
     */
    @Override
    public Number getDefaultElement() {
	String className = type.getSimpleName();

	switch (className) {
	case "Byte":
	    return new Byte((byte) 0);
	case "Short":
	    return new Short((short) 0);
	case "Integer":
	    return new Integer(0);
	case "Long":
	    return new Long(0);
	case "Float":
	    return new Float(0.0);
	case "Double":
	    return new Double(0.0);
	default:
	    if (this instanceof ElementInterface) {
		return (Number) (((ElementInterface) this).getDefaultElement());
	    }
	}

	return new Byte((byte) 0);
    }

    /*
     * (non-Javadoc)
     * @see model.AbstractMatrix#getRandom()
     */
    @Override
    protected Number getRandom() {
	Random random = new Random();
	switch (type.getSimpleName()) {
	case "Byte":
	    return new Byte((byte) random.nextInt());
	case "Short":
	    return new Short((short) random.nextInt());
	case "Integer":
	    return new Integer(random.nextInt());
	case "Long":
	    return new Long(random.nextLong());
	case "Float":
	    return new Float(random.nextFloat());
	case "Double":
	    return new Double(random.nextDouble());
	default:
	    if (this instanceof ElementInterface) {
		return (Number) (((ElementInterface) this).getRandom());
	    }
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * @see model.AbstractMatrix#setReadValues(java.io.BufferedReader)
     */
    @Override
    public void setReadValues(BufferedReader reader) {
	// TODO Auto-generated method stub

    }
}
