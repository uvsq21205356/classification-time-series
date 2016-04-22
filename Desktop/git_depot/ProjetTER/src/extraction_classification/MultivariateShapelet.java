package extraction_classification;

/**
 * Multivariate Shapelet
 * 
 * @author Melissa
 *
 */


public class MultivariateShapelet extends MultivariateTimeSeries {

	private Matrix distances;
	private double[] distanceThreshold;
	private double utilityScore;
	private int tp, fp, tn, fn;
	

	/*** GETTERS ***/
	
	public double[] getDistThreshold(){
		return this.distanceThreshold;
	}
	
	public Matrix getDistances(){
		return this.distances;
	}
	
	public double getUtilityScore(){
		return this.utilityScore;
	}

	public int getTp(){
		return (this.tp);
	}
	
	public int getTn(){
		return (this.tn);
	}
	
	public int getFp(){
		return (this.fp);
	}
	
	public int getFn(){
		return (this.fn);
	}
	
	
	/*** SETTERS ***/
	
	public void setDistThreshold( double[] dthr ){
		this.distanceThreshold = dthr;
	}
	
	public void setDistances( Matrix dists){
		this.distances = dists;
	}
	
	public void setUtilityScore( double us){
		this.utilityScore = us;
	}
	
	public void setTp( int true_positive){
		this.tp = true_positive;
	}
	
	public void setTn( int true_negative){
		this.tn = true_negative;
	}
	
	public void setFp( int false_positive){
		this.fp = false_positive;
	}
	
	public void setFn( int false_negative){
		this.fn = false_negative;
	}
	
	
	/*** CONSTRUCTOR ***/
	
	public MultivariateShapelet(String n, String c, Matrix m){
		super(n, c, m);
		this.utilityScore = 0;
		this.distanceThreshold = new double[m.getNmb_columns()];
		this.fn = 0;
		this.tp = 0;
		this.tn = 0;
		this.tp = 0;
	}
	
	/*** METHODS ***/
	
	/**
	 * a shapelet covers the time series if the distance between 
	 * this shapelet and the time series is < to the distance threshold 
	 * of the shapelet and the shapelet and the time series have 
	 * the same classification
	 * 
	 * 
	 * @param mt multivariate time series
	 * @param perc : percentage extraction and classification parameters
	 * @return true if the shapelet covers the time series, else false
	 */
	public boolean covers(MultivariateTimeSeries mt, double perc){
		return ( (Lib.cmp(this.distance(mt), this.getDistThreshold()) >= perc) 
				&& (this.getClassification().equalsIgnoreCase(mt.getClassification())));
	}
	
	/**
	 * computes the distances between a shapelet and 
	 * all the time series in dataset 
	 * 
	 * @param dataset
	 */
	public void computeDistances(Dataset dataset){
	    
		int nmb_series = dataset.getData().size();
		int nmb_attributes = dataset.getData().get(0).getData().getNmb_columns();
		
		this.distances = new Matrix( nmb_attributes, nmb_series);
		
		for( int j = 0; j < nmb_series; j++){
			for( int i = 0; i < nmb_attributes; i++){
				this.distances.getValues()[i][j] =  this.distance(dataset.getData().get(j))[i];
			}
			
		}
		
	}
	
	/**
	 * computes the distance threshold of a shapelet 
	 * 
	 * @param dataset
	 * @param perc : percentage extraction and classification parameters
	 */
	public void computeDistanceThreshold(Dataset dataset, double perc){
		
		double[] CandThr = new double[this.distances.getNmb_rows()];
		Matrix SortedDist = this.distances.sort_rows();
		
		double EL, ER, InfoGain;
		this.utilityScore = 0 ;
		int candtp , candfp , candtn , candfn ;
		int N_attributes = this.distances.getNmb_rows();
		int N_timeseries = this.distances.getNmb_columns();
		
		for( int j = 0; j < N_timeseries - 1 ; j++){ // for each time series
			
			EL = 0; ER = 0; InfoGain = 0;
			candtp = 0; candfp = 0; candtn = 0; candfn = 0;
			
			for( int k = 0; k < N_attributes; k++){ // for each attribute
				CandThr[k] = ( SortedDist.getValues()[k][j] + SortedDist.getValues()[k][j+1] ) /2.0;
			}
			
			for( int i = 0; i < N_timeseries ; i++){ // for each time series
				if( Lib.cmp(this.distances.getRow(i), CandThr) >= perc){
					if( dataset.getData().get(i).getClassification().equalsIgnoreCase(this.getClassification())){
						candtp++;
					} else{
						candfp++;
					}
					
				} else{
					if( dataset.getData().get(i).getClassification().equalsIgnoreCase(this.getClassification())){
						candtn++;
					} else{
						candfn++;
					}
				}
			}
	
			EL = - Lib.entropyHelper(candtp, candtp + candfp) - Lib.entropyHelper( candfp , candfp + candtp );
			
			ER = - Lib.entropyHelper(candtn, candtn + candfn) - Lib.entropyHelper( candfn , candfn + candtn );
			
			InfoGain = dataset.getEntropy() - ((candtp+candfp )/N_attributes) * EL - ((candtn+candfn)/N_attributes) * ER;
			if( InfoGain > this.utilityScore ){
				this.utilityScore = InfoGain;
				fp = candfp;
				fn = candfn;
				tp = candtp;
				tn = candtn;
				this.distanceThreshold = CandThr;
			}
			
		}
	}
	
	public void calculateUtilityScore(Dataset dataset, double perc){
		
		
		
	}
	
	/**
	 * multivariate shapelet to string 
	 */
	public String toString(){
		String s = super.toString();
		s += "distances : " + this.distances.toString() + "\n";
		s += "distance threshold : " + Lib.toString(this.distanceThreshold) + "\n";
		s += "utility score : " + this.utilityScore + "\n";
		s += "tp : "+ this.tp +", tn : " + this.tn +", fp : " + this.fp + ", fn : "+ this.fn + "\n";
		return s;
	}
}
