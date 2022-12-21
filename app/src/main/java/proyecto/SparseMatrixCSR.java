package proyecto;

import javax.naming.OperationNotSupportedException;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SparseMatrixCSR {
    private LoadFile loader = LoadFile.getInstance();
    @Setter
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
        int cnt = 0;
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[2].length; j++){
                if(matrix[i][j] != 0){
                    cnt++;
                }
            }
        }
        values = new int[cnt];
        columns = new int[cnt];
        int a = 0;
        for(int b = 0; b < matrix.length; b++){
            for(int c = 0; c < matrix[2].length; c++){
                if(matrix[b][c] != 0){
                    values[a] = matrix[b][c];
                    columns[a] = c;
                    a++;
                }
            }
        }
        rows = new int[matrix.length + 1];
        int suma = 0;
        int indi = 0;
        for(int x = 0; x < matrix.length; x++){
            rows[indi] = suma;
            for(int y = 0; y < matrix[2].length; y++){
                if(matrix[x][y] != 0){
                    suma++;
                }
            }
            indi++;
        }
        rows[indi] = suma;

    }

    public int getElement(int i, int j) throws OperationNotSupportedException
    {
        int resultado = 0;
        for(int x = rows[i]; x < rows[i+1] ; x++){
            if(columns[x] == j){
                resultado = values[x];
            }
        }
        return resultado;
    }

    public int[] getRow(int i) throws OperationNotSupportedException
    {
        int[] filas = new int[matrix[2].length];
        int iterador = rows[i];
        for(int x = 0; x < rows[i+1] - rows[i]; x++){
            filas[columns[iterador]] = values[iterador];
            iterador++;
        }
        return filas;
    }

    public int[] getColumn(int j) throws OperationNotSupportedException
    {
        int[] columnas = new int[matrix.length];
        for(int i = 0; i < columnas.length; i++){
            for(int x = rows[i]; x < rows[i+1]; x++){
                if(columns[x] == j){
                    columnas[i] = values[x];
                }
            }
        }

        return columnas;
    }

    public void setValue(int i, int j, int value) throws OperationNotSupportedException
    {
        int[] col = new int[values.length+1];
        int[] val = new int[values.length+1];
        int[] fil = new int[rows.length];
        int pos = rows[i+1] - rows[i];
        for(int c = 0; c < pos; c++){
            if(columns[c] > j){
                col[pos-1] = j;
                val[pos-1] = value;
                for(int x = 0; x < columns.length;x++){
                    if(x >= pos-1){
                        col[x+1] = columns[x] ;
                        val[x+1] = values[x];
                    }else{
                        col[x] = columns[x];
                        val[x] = values[x];
                    }
                }

            }else{
                col[pos] = j;
                val[pos] = value;
                for(int x = 0; x < columns.length;x++){
                    if(x >= pos){
                        col[x+1] = columns[x] ;
                        val[x+1] = values[x];
                    }else{
                        col[x] = columns[x];
                        val[x] = values[x];
                    }
                }
            }

        }


        for(int k = 0; k < rows.length; k++){
            if(k > 0){
                fil[k] = rows[k] + 1;
            }
        }
        this.setValues(val);
        this.setColumns(col);
        this.setRows(fil);
    }

    /*
     * This method returns a representation of the Squared matrix
     * @return object that contests the squared matrix;
     */
    public SparseMatrixCSR getSquareMatrix() throws OperationNotSupportedException
    {
        SparseMatrixCSR squaredMatrix = new SparseMatrixCSR();
        int[] cuadrado = new int[values.length];
        for(int i = 0; i < values.length; i++){
            cuadrado[i] = values[i] * values[i];
        }
        squaredMatrix.setValues(cuadrado);
        squaredMatrix.setRows(rows);
        squaredMatrix.setColumns(columns);
        return  squaredMatrix;
    }

    /*
     * This method returns a representation of the transposed matrix
     * @return object that contests the transposed matrix;
     */
    public SparseMatrixCSR getTransposedMatrix() throws OperationNotSupportedException
    {
        SparseMatrixCSR squaredMatrix = new SparseMatrixCSR();
        int[][] transposed = new int[matrix[2].length][matrix.length];
        int[] valores = new int[values.length];
        int[] columnas = new int[values.length];
        int[] filas = new int[transposed.length + 1];

        for(int i=0; i < matrix.length; i++){
            for(int j = 0; j < matrix[2].length; j++){
                transposed[j][i] = matrix[i][j];
            }
        }
        int k = 0;
        int cnt = 0;
        int count = 0;
        for(int a = 0; a < transposed.length; a++){
            filas[count] = cnt;
            for(int b = 0; b < transposed[2].length; b++){
                if(transposed[a][b] != 0){
                    valores[k] = transposed[a][b];
                    columnas[k] = b;
                    k++;
                    cnt++;
                }
            }
            count++;
        }
        filas[count] = cnt;
        squaredMatrix.setRows(filas);
        squaredMatrix.setColumns(columnas);
        squaredMatrix.setValues(valores);
        return squaredMatrix;
    }

}
