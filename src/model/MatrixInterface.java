/**
 * 
 */
package model;

/**
 * @author Robert
 */
public interface MatrixInterface<T> {
    public abstract AbstractMatrix<T> sumMatrix(AbstractMatrix<T> secondMatrix);
    public abstract AbstractMatrix<T> mulMatrix(AbstractMatrix<T> secondMatrix);

    public abstract boolean padColumns(int paddingSize);
    public abstract boolean padRows(int paddingSize);
    public abstract boolean padMatrix(int paddingSize); 
}
