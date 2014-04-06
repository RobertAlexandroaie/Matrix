package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Robert
 */

@SuppressWarnings("unchecked")
abstract class AbstractMatrix<T extends Object> implements MatrixInterface<T> {
    protected static Logger logger = Logger.getLogger("MatrixLogger");

    protected Class<T> type;

    protected ArrayList<ArrayList<T>> info;
    protected int rows;
    protected int columns;

    public AbstractMatrix(Class<?> type) {
	this.type = (Class<T>) type;
	rows = 1;
	columns = 1;
	info = new ArrayList<ArrayList<T>>();
	ArrayList<T> row = new ArrayList<T>();
	row.add(null);
	info.add(row);
    }

    public AbstractMatrix(Class<?> type, int... dims) {
	this.type = (Class<T>) type;
	switch (dims.length) {
	case 1:
	    rows = columns = dims[0];
	    break;
	case 2:
	    rows = dims[0];
	    columns = dims[1];
	}
	try {
	    createMatrix();
	    if (dims.length < 1 || dims.length > 2) {
		throw new IllegalArgumentException();
	    }

	} catch (IllegalArgumentException e) {
	    logger.log(Level.SEVERE, "check args for matrix");
	    e.printStackTrace();
	}
    }

    protected void createMatrix() {
	info = new ArrayList<ArrayList<T>>(rows);
	for (int i = 0; i < rows; i++) {
	    ArrayList<T> row = new ArrayList<T>(columns);
	    for (int j = 0; j < columns; j++) {
		row.add(getDefaultElement());
	    }
	    info.add(row);
	}
    }

    /**
     * @return the info
     */
    public ArrayList<ArrayList<T>> getInfo() {
	return info;
    }

    /**
     * @return the rows
     */
    public int getRows() {
	return rows;
    }

    /**
     * @param rows
     *            the rows to set
     */
    public void setRows(int rows) {
	try {
	    if (rows <= 0) {
		throw new IllegalArgumentException();
	    } else {
		this.rows = rows;
	    }
	} catch (IllegalArgumentException e) {
	    logger.log(Level.SEVERE, "#rows > 0!!");
	}
    }

    /**
     * @return the columns
     */
    public int getColumns() {
	return columns;
    }

    /**
     * @param columns
     *            the columns to set
     */
    public void setColumns(int columns) {
	try {
	    if (columns <= 0) {
		columns = 0;
		throw new IllegalArgumentException();
	    } else {
		this.columns = columns;
	    }
	} catch (IllegalArgumentException e) {
	    logger.log(Level.SEVERE, "#columns > 0!!");
	}
    }

    /**
     * @return the type
     */
    public Class<T> getType() {
	return type;
    }

    public void setRandomValues() {
	for (int i = 0; i < rows; i++) {
	    for (int j = 0; j < columns; j++) {
		setElement(i, j, getRandom());
	    }
	}
    }

    protected abstract T getRandom();

    public void setFileValues(String filename) {
	BufferedReader reader = null;
	int rows, columns;
	try {
	    reader = new BufferedReader(new FileReader(filename));
	    rows = Integer.parseInt(reader.readLine());
	    columns = Integer.parseInt(reader.readLine());
	    setColumns(columns);
	    setRows(rows);
	    info.clear();
	    createMatrix();
	    setReadValues(reader);
	} catch (FileNotFoundException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} catch (NumberFormatException | IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    if(reader!= null) {
		try {
		    reader.close();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}
    }

    public abstract void setReadValues(BufferedReader reader);

    /**
     * @param i
     * @param j
     * @param element
     * @return
     */
    public T setElement(int i, int j, T element) {
	try {
	    if (i < 0 || i > rows || j < 0 || j > columns) {
		throw new IllegalArgumentException();
	    }
	    return info.get(i).set(j, element);
	} catch (IllegalArgumentException e) {
	    logger.log(Level.SEVERE, "illegal indexes");
	    e.printStackTrace();
	}

	return null;
    }

    public T getElement(int i, int j) {
	try {
	    if (i < 0 || i > rows || j < 0 || j > columns) {
		throw new IllegalArgumentException();
	    }
	    return info.get(i).get(j);
	} catch (IllegalArgumentException e) {
	    logger.log(Level.SEVERE, "illegal indexes");
	    e.printStackTrace();
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * @see model.MatrixInterface#padColumns(int)
     */
    @Override
    public boolean padColumns(int paddingSize) {
	if (paddingSize > 0) {
	    try {
		columns += paddingSize;

		for (int i = 0; i < rows; i++) {
		    ArrayList<T> row = info.get(i);
		    for (int j = 0; j < paddingSize; j++) {
			row.add(getDefaultElement());
		    }
		}
		return true;
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "padding columns failed");
		e.printStackTrace();
		return false;
	    }
	} else {
	    return false;
	}
    }

    /*
     * (non-Javadoc)
     * @see model.MatrixInterface#padRows(int)
     */
    @Override
    public boolean padRows(int paddingSize) {
	if (paddingSize > 0) {

	    try {
		rows += paddingSize;

		for (int i = 0; i < paddingSize; i++) {
		    ArrayList<T> row = new ArrayList<>();
		    for (int j = 0; j < columns; j++) {
			row.add(getDefaultElement());
		    }
		    info.add(row);
		}
		return true;
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "padding columns failed");
		e.printStackTrace();
		return false;
	    }
	} else {
	    return false;
	}
    }

    /*
     * (non-Javadoc)
     * @see model.MatrixInterface#padMatrix(int)
     */
    @Override
    public boolean padMatrix(int paddingSize) {
	if (paddingSize > 0) {
	    return padColumns(paddingSize) && padRows(paddingSize);
	} else {
	    return false;
	}
    }

    public abstract T getDefaultElement();
}
