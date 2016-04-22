package extraction_classification;
/**
 * Dataset 
 * 
 * @author Melissa
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Dataset {
	
	private ArrayList<MultivariateTimeSeries> data;
	private double entropy;
	private int min_shapelet_length;
	private HashSet<String> classes;
	
	/*** GETTERS ***/
	
	public ArrayList<MultivariateTimeSeries> getData(){
		return this.data;
	} 
	
	public double getEntropy(){
		return this.entropy;
	}
	
	public int getMin_shapelet_length(){
		return this.min_shapelet_length;
	}
	
	/*** SETTERS ***/
	
	public void setData( ArrayList<MultivariateTimeSeries> d){
		this.data = d;
	}
	
	public void setEntropy( double e){
		this.entropy = e;
	}

	public void setMin_shapelet_length( int l){
		this.min_shapelet_length = l;
	}
	
	/*** CONSTRUCTOR ***/
	
	public Dataset(ArrayList<MultivariateTimeSeries> d){
		this.data = new ArrayList<MultivariateTimeSeries>(d);
		this.entropy = 0;
		this.min_shapelet_length = 0;
		this.classes = new HashSet<String>();	
		for( int i = 0; i < d.size(); i++){
			this.classes.add(d.get(i).getClassification());
		}
	}
	
	/*** METHODS ***/
	
	/**
	 * computes the entropy of a dataset
	 */
	public void computeEntropy() {
	        double ent = 0.0;
	        System.out.println(this.classes.size());
	        double[] dataC = new double[this.classes.size()];
	        int i=0;
	        for (String s : this.classes) {
	            for (MultivariateTimeSeries t : this.data) {
	                if (s.equalsIgnoreCase(t.getClassification())) {
	                    dataC[i]++;
	                }
	            }
	            ent += Lib.entropy(dataC[i], data.size());
	            i++;
	        }
	        this.entropy = -ent;
	}
	
	/**
	 * sorts the shapelets in ascending order of utility score
	 */
	public static void sortShapelets( ArrayList<MultivariateShapelet> shapelets){
		   Collections.sort( shapelets, new Comparator<MultivariateShapelet>() {
	       public int compare(MultivariateShapelet a, MultivariateShapelet b)
	       { return Double.compare(a.getUtilityScore(),b.getUtilityScore());}
	   });
	}

	/**
	 * prunes the shapelets based on their 
	 * utiliy score and coverage in the dataset
	 * 
	 * @param perc : percentage extraction and classification parameters
	 */
    public void shapeletPruning( ArrayList<MultivariateShapelet> shapelets, double perc){
		ArrayList<MultivariateShapelet> filteredShapelets = new ArrayList<MultivariateShapelet>();
		ArrayList<MultivariateTimeSeries> listTimeSeries = new ArrayList<MultivariateTimeSeries>(data);
		
		sortShapelets(shapelets);
		int min_shap_len = Integer.MAX_VALUE;
		
		int i = shapelets.size()-1;
		
		while( i >= 0 && listTimeSeries.size() > 0){
			
			for( int j = 0; j < listTimeSeries.size(); j++){
				
				if( shapelets.get(i).covers(listTimeSeries.get(j), perc)){
					filteredShapelets.add(shapelets.get(i));
					listTimeSeries.remove(j);
					int shap_len = shapelets.get(i).getLength();
					if( shap_len < min_shap_len){
						min_shap_len = shap_len;
					}
				}
			}
			
			i--;
		}
		
		shapelets = filteredShapelets;
		this.min_shapelet_length = min_shap_len;
	}

    /**
     * extracts and selects from a dataset 
     * the most useful shapelets of length l 
     * such as minL <= l <= maxL
     *  
     * @param minL minimal shapelet length
     * @param maxL maximal shapelet length
     * @param perc : percentage extraction and classification parameters
     */
	public ArrayList<MultivariateShapelet> shapeletDetection( int minL, int maxL, double perc){
		
		ArrayList<MultivariateShapelet> shapelets = new ArrayList<MultivariateShapelet>();
		
		for(int i = 0; i < this.data.size(); i++){  // for each time series 
			
			MultivariateTimeSeries T = this.data.get(i);
			
			for( int length = minL; length <= maxL; length++){   // for each shapelet length
				for( int k = 1; k <= T.getLength() - length + 1; k++){  
					MultivariateShapelet extractedShapelet = T.extract_shapelet(k, length);
					extractedShapelet.computeDistances(this);
					extractedShapelet.computeDistanceThreshold(this, perc);
					extractedShapelet.calculateUtilityScore(this, perc);
					shapelets.add(extractedShapelet);		
				}
			}
		}
		shapeletPruning(shapelets, perc);
		return ( shapelets);
	}
	
	/**
	 * if classifies a list of multivariate time series 
	 * with a set of extracted shapelets from the dataset
	 * 
	 * @param to_classify list of time series to classify 
	 * @param perc : percentage extraction and classification parameters
	 */
	public void classification( Dataset to_classify, ArrayList<MultivariateShapelet> shapelets, double perc){
		
		MultivariateTimeSeries T;
		boolean classified; 
		int length;
		MultivariateShapelet current_shapelet;

		for( int i = 0; i < to_classify.data.size(); i++){
			
			length = this.min_shapelet_length;
			T = to_classify.data.get(i).extract_shapelet(0, length);
			classified = false;
			
			
			while ( !classified && length <= T.getLength()){
				
				int j = 0;
				
				while( !classified && j < shapelets.size()){
					
					current_shapelet = shapelets.get(j);
					
					if( Lib.cmp( T.distance(current_shapelet), current_shapelet.getDistThreshold()) >= perc ) {
						
						to_classify.data.get(i).setClassification(current_shapelet.getClassification());
						to_classify.classes.add(current_shapelet.getClassification());
						classified = true;
						to_classify.data.get(i).setEML(length);
					
					} else {
						j++;
					}
				}
				
				length++;
				T = to_classify.data.get(i).extract_shapelet(0, length);
			}
			
			
		}
	}
	
	
	public String toString(){
		String s = ""; 
		for(int i = 0; i < this.data.size(); i++){
			s += this.data.get(i).toString() +"\n";
		}
		return s;
	}
}
