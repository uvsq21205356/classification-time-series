package extraction_classification;


/**
 * This file contains functions needed in 
 * some methods in other classes of the package.
 * 
 * It's basically a library, to wield 
 * vectors ( double[] ) or to compute 
 * mathematical functions.
 * 
 * @author Melissa
 *
 */
public class Lib {
	
	/**
	 * This function is private, because it's called from another function.
	 * It makes the assumption that s.length = h.length, so it must be called 
	 * after this verification, and never without.
	 *
	 *
	 * @return the distance between s and h when s.length = h.length
	 */
	private static double distance_1d_equal_length( double[] s, double[] h){
		 double result = 0.0;
	     for (int i = 0; i < s.length; i++) {
	            result += Math.pow(s[i] - h[i], 2);
	        }
	        result = Math.sqrt(result);
	        return result;
	}
	
	/**
	 * This function is private, because it's called from another function.
	 * It makes the assumption that s.length > h.length, so it must be called 
	 * after this verification, and never without.
	 * 
	 * 
	 * @return the distance between s and h when s.length > h.length
	 */
	private static double distance_1d_inequal_length( double[] s, double[] h){
		  double result = Double.MAX_VALUE;
	      for (int k = 0; k < s.length - h.length + 1 ; k++){
	    	  double[] window = new double[h.length];
	    	  for(int i = 0; i < h.length; i++){
	    		  window[i] = s[ i + k ];
	    	  }
	  		
	    	  double candidateResult = distance_1d_equal_length(h, window);
	          
	          if ( candidateResult < result ) {
	                  result = candidateResult;
	          }
	       }
	       return result;
	}
	
	/** 
	 * compute the distance between 2 vectors, taking into 
	 * consideration that they might be of different lengths
	 *
	 *
	 * @return distance between s and h 
	 */
	public static double distance_1d( double[] s, double[] h){
		if( s.length == h.length) return distance_1d_equal_length( s, h);
		else if( s.length > h.length) return distance_1d_inequal_length( s, h);
		else return distance_1d_inequal_length( h, s);
	}
	
	
	/**
	 * this methods compares 2 vectors a and b, and returns 
	 * the percentage of values of a that are < to the matching 
	 * ( by index ) values of b
	 * here a and b must be of equal length and not null
	 * 
	 * 
	 * @return the rate of values from a that are < to the values of b
	 */
	public static double cmp( double[] a, double[] b){
	    	double res = 0.0; 
	    	for(int i=0; i< a.length; i++){
	    		if(a[i] < b[i]) res ++;
	    	}
	    	return ((double)res/(double)a.length);
	}
	
	/**
	 * computes the entropy of d1 and d2
	 *
	 *@return (d1/d2)*log(d1/d2)
	 */
	public static double entropy(double d1, double d2) {
	        double result = d1 / d2;
	        result *= (Math.log((d1 / d2)) / Math.log(2));
	        return result;
	}
	
	public static double entropyHelper(double c1, double c2){
        return (c2 != 0 ? -1.0 * entropy(c1, c1 + c2) - 1.0 * entropy(c2, c1 + c2)
                    : -1.0 * entropy(c1, c1 + c2));
    }
		

	/**
	 * double[] to string
	 * 
	 */
	public static String toString( double[] a){
		String s = "";
		for( int i = 0; i < a.length; i++){
			s += a[i] +",";
		}
		return s ;
	}
	
	/**
	 * double[][] to string 
	 * 
	 * @param nb_r numbrer of rows of m 
	 * @param nb_c number of columns of m
	 * 
	 */
	public static String toString( double[][] m, int nb_r, int nb_c){
		String s = "( ";
		for(int i = 0; i < nb_r; i++){
			for(int j = 0; j < nb_c; j++ ){
				s += m[i][j] + "  ";
			}
			s += "\n";
		}
		return (s .substring(0,s.length()-2)+ ")");
	}
	
}
