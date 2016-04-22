package extraction_classification;

/**
 * This class allows us to store a double[][] 
 * in a more convenient shape and makes it 
 * easy to handle and manipulate
 * 
 * @author Melissa
 *
 */

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class Matrix {
	
	private double[][] values;
	private int nb_columns;
	private int nb_rows;
	
	/*** GETTERS ***/
	
	public double[][] getValues(){
		return this.values;
	}
	
	public int getNmb_columns(){
		return this.nb_columns;
	}
	
	public int getNmb_rows(){
		return this.nb_rows;
	}
	
	public double[] getRow( int i ){
		return this.values[i];
	}

	public double[] getColumn( int j ){
		double[] d = new double[this.nb_rows];
		for( int i=0; i< this.nb_rows; i++){
			d[i] = this.values[i][j];
		}
		return d;
	}
	
	/*** SETTERS ***/
	
	public void setValues(double[][] v){
		this.values = v;
	}
	
	public void setNb_columns( int c){
		this.nb_columns = c;
	}
	
	public void setNb_rows(int r){
		this.nb_rows = r;
	}
	
	/*** CONSTRUCTOR ***/
	
	
	
	public Matrix( int nb_r, int nb_c){
		this.values = new double[nb_r][nb_c];
		this.nb_rows = nb_r;
		this.nb_columns = nb_c;
		}
	
	/**
	 * builds a matrix from a double[][]
	 * 
	 * @param v values of the matrix
	 * @param nb_r number of rows in the matrix
	 * @param nb_c numbre of columns in the matrix
	 */
	
	public Matrix(double[][] v, int nb_r, int nb_c){
		this.values = new double[nb_r][nb_c];
		for( int i = 0; i < nb_r; i++){
			for( int j = 0; j < nb_c; j++){
				this.values[i][j] = v[i][j];
			}
		}
		this.nb_columns = nb_c;
		this.nb_rows = nb_r;
	}
	
	
	/*** METHODS ***/
	
	/**
	 * builds a submatrix from an original matrix
	 * 
	 * @param starting_row the row from which the submatrix starts
	 *                     ( in this case a timestamp )  
	 * @param nb_rows length of the submatrix to be extracted
	 * 
	 * @return submatrix of the original matrix
	 */
	
	public Matrix subMatrix(int starting_row, int nb_rows){
		double[][] sub_m = new double[nb_rows][this.nb_columns];
		
		for( int i = 0; i< nb_rows; i++ ){
			sub_m[i] = this.values[i + starting_row];
		}
		
		return new Matrix( sub_m, nb_rows, this.nb_columns );
	}

	/**
	 * creates a new matrix which is a copy 
	 * of the original matrix but with its rows 
	 * sorted individually 
	 * 
	 * @return matrix with sorted rows
	 */
	public Matrix sort_rows(){
		Matrix m = new Matrix( this.values, this.nb_rows, this.nb_columns);
		for( int i = 0; i < m.nb_rows; i++){
			Arrays.sort(m.getRow(i));
		}
		return m;
		
	}
	
	/**
	 * Matrix to string 
	 * 
	 */
	public String toString() {
		String s ="";
		s += Lib.toString(this.values, this.nb_rows, this.nb_columns);
        return s;
    }
	
}
