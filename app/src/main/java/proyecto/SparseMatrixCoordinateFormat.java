package proyecto;

import javax.naming.OperationNotSupportedException;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SparseMatrixCoordinateFormat {

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
        int[] valores1 = new int[cnt];
        int[] filas1 = new int[cnt];
        int[] col1 = new int[cnt];

        int k = 0;
            for(int b = 0; b < matrix.length; b++){
                for(int n = 0; n < matrix[2].length; n++){
                    if(matrix[b][n] != 0){
                        valores1[k] = matrix[b][n];
                        filas1[k] = b;
                        col1[k] = n;
                        k += 1;
                    }
                }
            }

            this.setValues(valores1);
            this.setRows(filas1);
            this.setColumns(col1);

    }

    public int getElement(int i, int j) throws OperationNotSupportedException
    {
        //No usar this.matrix aqui.
        int resultado = 0;
        for(int x = 0; x < values.length; x++){
            if(i == rows[x] && j == columns[x]){
                resultado = values[x];
            }
        }
        return  resultado;
    }

    public int[] getRow(int i) throws OperationNotSupportedException
    {
        //No usar this.matrix aqui.
        int[] fila = new int[matrix[2].length];
        for(int j = 0; j < rows.length; j++){
            if(rows[j] == i){
                fila[columns[j]] = values[j];
            }
        }
        return fila;
    }

    public int[] getColumn(int j) throws OperationNotSupportedException
    {
        //No usar this.matrix aqui.
        int[] columna = new int[matrix.length];
        for(int i = 0; i < columns.length; i++){
            if(columns[i] == j){
                columna[rows[i]] = values[i];
            }
        }
        return columna;
    }

    public void setValue(int i, int j, int value) throws OperationNotSupportedException
    {
        //Cambiar los atributos rows, cols, values y matrix aqui
        int cnt = 0;
        int[] valores = new int[values.length+1];
        int[] columnas = new int[values.length+1];
        int[] filas = new int[values.length+1];

        for(int a=0; a< values.length;a++){
            if(i == rows[a]){
                valores[a] = values[a];
                filas[a] = rows[a];
                columnas[a] = columns[a];
                cnt++;
            }else{
                valores[a] = value;
                filas[a] = i;
                columnas[a] = j;
                break;
            }
        }

        for(int x=cnt; x<values.length;x++){
            valores[x+1] = values[x];
            filas[x+1] = rows[x];
            columnas[x+1] = columns[x];
        }
        this.setValues(valores);
        this.setRows(filas);
        this.setColumns(columnas);
    }

    /*
    * This method returns a representation of the Squared matrix
    * @return object that contests the squared matrix;
     */
    public SparseMatrixCoordinateFormat getSquareMatrix() throws OperationNotSupportedException
    {
        SparseMatrixCoordinateFormat squaredMatrix = new SparseMatrixCoordinateFormat();
        //Usar los metodos Set aqui de los atributos
        int[] cuadrado = new int[values.length];
        for(int i = 0; i < values.length; i++){
            cuadrado[i] = values[i] * values[i];
        }
        squaredMatrix.setValues(cuadrado);
        squaredMatrix.setRows(rows);
        squaredMatrix.setColumns(columns);
        return squaredMatrix;
    }

    /*
     * This method returns a representation of the transposed matrix
     * @return object that contests the transposed matrix;
     */
    public SparseMatrixCoordinateFormat getTransposedMatrix() throws OperationNotSupportedException
    {
        SparseMatrixCoordinateFormat squaredMatrix = new SparseMatrixCoordinateFormat();
        //Usar los metodos Set aqui de los atributos
        int[][] transposed = new int[matrix[2].length][matrix.length];
        int cnt = 0;
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[2].length; j++){
                transposed[j][i] = matrix[i][j];
                if(matrix[i][j] != 0){
                    cnt++;
                }
            }
        }
        squaredMatrix.setMatrix(transposed);

        int[] valores1 = new int[cnt];
        int[] filas1 = new int[cnt];
        int[] col1 = new int[cnt];

        int k = 0;
        for(int b = 0; b < transposed.length; b++){
            for(int n = 0; n < transposed[2].length; n++){
                if(transposed[b][n] != 0){
                    valores1[k] = transposed[b][n];
                    filas1[k] = b;
                    col1[k] = n;
                    k += 1;
                }
            }
        }
        squaredMatrix.setRows(filas1);
        squaredMatrix.setValues(valores1);
        squaredMatrix.setColumns(col1);

        return squaredMatrix;
    }

}
