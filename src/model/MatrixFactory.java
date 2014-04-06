/**
 * 
 */
package model;

import static utils.Constants.MATRIX_ORDINARY;
import static utils.Constants.MATRIX_SQUARE;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Robert
 */

@SuppressWarnings("unchecked")
public class MatrixFactory {

    public static RussianMatrix buildRandomRussianMatrix(String matrixType, int... dims) {
	try {
	    if (dims.length >= 1) {
		RussianMatrix rmatrix = new RussianMatrix(Boolean.class, dims[0]);
		rmatrix.setRandomValues();
		return rmatrix;
	    } else {
		throw new IllegalArgumentException();
	    }
	} catch (IllegalArgumentException e) {
	    Logger.getGlobal().log(Level.SEVERE, "cannot build random russian matrix!");
	    e.printStackTrace();
	    return null;
	}
    }

    public static RussianMatrix buildRussianMatrixFromFile(String filename) {
	RussianMatrix rmatrix = new RussianMatrix(Boolean.class);
	rmatrix.setFileValues(filename);
	return rmatrix;
    }

    public static <T> AbstractMatrix<T> buildRandomMatrix(String matrixType, Class<T> type, int... dims) {
	try {
	    switch (matrixType.trim().toLowerCase()) {
	    case MATRIX_ORDINARY:
		if (dims.length >= 2) {
		    return returnMatrix(type, dims[0], dims[1]);
		} else {
		    throw new IllegalArgumentException();
		}
	    case MATRIX_SQUARE:
		if (dims.length >= 1) {
		    return returnMatrix(type, dims[0], dims[0]);
		} else {
		    throw new IllegalArgumentException();
		}
	    }
	} catch (IllegalArgumentException e) {
	    Logger.getGlobal().log(Level.SEVERE, "not enough arguments for size");
	    e.printStackTrace();
	}
	return null;
    }

    @SuppressWarnings("rawtypes")
    private static <T> AbstractMatrix<T> returnMatrix(Class<T> type, int rows, int columns) {
	if (type != null) {
	    if (Number.class.isAssignableFrom(type)) {
		return new NumberMatrix(type, rows, columns);
	    } else if (Boolean.class.isAssignableFrom(type)) {
		return new BooleanMatrix(type, rows, columns);
	    } else {
		return null;
	    }
	} else {
	    return null;
	}
    }

}
