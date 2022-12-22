/*
    Alejando mesa - 2060102
    Joann Esteban Bedoya - 2059906
    Willian David correa - 2060016
*/
package proyecto;
import javax.naming.OperationNotSupportedException;
import lombok.Getter;
import lombok.Setter;
import java.io.FileNotFoundException;



public class SparseMatrixCSC {
    private LoadFile loader = LoadFile.getInstance();
    private int[][] matrix;
    @Getter
    @Setter
    private int[] rows;
    @Getter
    @Setter
    private int[] columns;
    @Getter
    @Setter
    private int[] values;

    public void createRepresentation(String inputFile) throws OperationNotSupportedException, FileNotFoundException {
        //Load data
        loader.loadFile(inputFile);
        matrix = loader.getMatrix();

        int count = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] != 0) count++;
            }
        }

        int[] cols = new int[matrix[0].length + 1];
        int[] filas = new int[count];
        int[] valor = new int[count];

        cols[0] = 0;
        int countValores = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            cols[i + 1] = cols[i];
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] != 0) {
                    cols[i + 1]++;
                    valor[countValores] = matrix[j][i];
                    filas[countValores] = j;
                    countValores++;
                }
            }
        }

        this.setValues(valor);
        this.setRows(filas);
        this.setColumns(cols);

    }

    public int getElement(int i, int j) throws OperationNotSupportedException
    {
        int resultado = 0;
        for (int k = columns[j]; k < columns[j + 1]; k++) {
            if (rows[k] == i) {
                resultado = values[k];
                break;
            }
        }
        return resultado;
    }

    public int[] getRow(int i) throws OperationNotSupportedException
    {
        int[] fila = new int[columns.length - 1];
        for (int j = 0; j < columns.length - 1; j++) {
            if (columns[j] != columns[j + 1]) {
                for (int k = columns[j]; k < columns[j + 1]; k++) {
                    if (rows[k] == i) {
                        fila[j] = values[k];
                        break;
                    }
                }
            }
        }
        return fila;
    }

    public int[] getColumn(int j) throws OperationNotSupportedException
    {
        int numCol = this.matrix.length;
        int[] cols = new int[numCol];
        for (int i = columns[j]; i < columns[j + 1]; i++) {
            cols[rows[i]] = values[i];
        }
        return cols;
    }

    public void setValue(int i, int j, int value) throws OperationNotSupportedException
    {

        int[] fil = new int[rows.length + 1];
        int[] val = new int[values.length + 1];
        int[] col = new int[columns.length];

        //Encontrar la posicion donde se insertara value
        int pos = columns[j];
        int k = 0;
        for (k = columns[j]; k < columns[j + 1]; k++) {
            if ((k == columns[j] && i < rows[k]) || (i < rows[k] && i > rows[k + 1]) || k == columns[j + 1] - 1) {
                pos = k;
                break;
            }
        }

        //Agregar el valor a value y la poscion en la fila
        for (k = 0; k < val.length; k++) {
            if (k < pos) {
                val[k] = values[k];
                fil[k] = rows[k];
            } else if (k == pos) {
                val[k] = value;
                fil[k] = i;
            } else if (k > pos) {
                val[k] = values[k - 1];
                fil[k] = rows[k - 1];
            }
        }

        //Sumar 1 a las columnas
        for (k = 0; k < col.length; k++) {
            if (k <= j) col[k] = columns[k];
            else if (k > j) col[k] = columns[k] + 1;
        }

        this.setColumns(col);
        this.setValues(val);
        this.setRows(fil);

    }

    /*
     * This method returns a representation of the Squared matrix
     * @return object that contests the squared matrix;
     */
    public SparseMatrixCSC getSquareMatrix() throws OperationNotSupportedException
    {
        SparseMatrixCSC squaredMatrix = new SparseMatrixCSC();
        squaredMatrix.rows = rows;
        squaredMatrix.columns = columns;
        squaredMatrix.values = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            squaredMatrix.values[i] = values[i] * values[i];
        }
        return squaredMatrix;
    }

    /*
     * This method returns a representation of the transposed matrix
     * @return object that contests the transposed matrix;
     */
    public SparseMatrixCSC getTransposedMatrix() throws OperationNotSupportedException
    {
        SparseMatrixCSC squaredMatrix = new SparseMatrixCSC();
        squaredMatrix.values = new int[values.length];
        squaredMatrix.columns = new int[matrix.length + 1];
        squaredMatrix.rows = new int[rows.length];

        int countValues = 0;
        for (int i = 0; i < squaredMatrix.columns.length; i++) {
            for (int j = 0; j < rows.length; j++) {
                if (rows[j] == i) {
                    squaredMatrix.columns[i + 1]++;
                    squaredMatrix.values[countValues] = values[j];

                    for (int k = 0; k < columns.length; k++) {
                        if (k == columns.length || (j >= columns[k] && j < columns[k + 1])) {
                            squaredMatrix.rows[countValues] = k;
                            break;
                        }
                    }
                    countValues++;
                }
            }
        }
        //Hacer la sumatoria de las columnas
        for (int i = 1; i < squaredMatrix.columns.length; i++) squaredMatrix.columns[i] += squaredMatrix.columns[i - 1];

        return squaredMatrix;
    }


}

