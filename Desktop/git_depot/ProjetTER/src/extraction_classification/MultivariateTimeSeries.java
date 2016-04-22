package extraction_classification;
/**
 * Multivariate Time Series 
 * 
 * @author Melissa
 *
 */

public class MultivariateTimeSeries {
	
	private String name;
	private String classification;
	private Matrix data;
	private int length;
	private int EML;
	
	/*** GETTERS ***/
	
	public String getName(){
		return this.name;
	}
	
	public String getClassification(){
		return this.classification;
	}
	
	public Matrix getData(){
		return this.data;
	}
	
	public int getLength(){
		return this.length;
	}
	
	public int getEML(){
		return this.EML;
	}
	
	/**
	 * @param j attribute
	 * @return double[] of the values of attribute number j 
	 */
	public double[] getAttribute_Values( int j ){
		return this.data.getColumn(j);
	}
	
	/**
	 * 
	 * @param i timestamp
	 * @return double[] values of the time series at a timestamp i
	 */
	public double[] getTimestamp_Values( int i ){
		return this.data.getRow(i);
	}
	
	
	/*** SETTERS ***/
	
	public void setName( String n){
		this.name = n;
	}
	
	public void setClassification( String c){
		this.classification = c;
	}
	
	public void setData( Matrix m){
		this.data = m;
	}
	
	public void setLength( int l){
		this.length = l;
	}
	
	public void setEML ( int e){
		this.EML = e;
	}
	
	/*** CONSTRUCTOR ***/
	
	public MultivariateTimeSeries(){
		this.EML = Integer.MAX_VALUE;
		this.classification = "Undefined";
	}
	
	public MultivariateTimeSeries(String n, String c, Matrix m){
		this.classification = c;
		this.name = n;
		this.data = m;
		this.length = m.getNmb_rows();
	
	}

	/*** METHODS ***/
	
	/**
	 * extracts from a time series a shapelet from 
	 * a given time stamp and of given length  
	 * 
	 * @param timeStamp starting time stamp of extraction 
	 * @param length length of the shapelet extracted
	 * 
	 */
	public MultivariateShapelet extract_shapelet(int timeStamp, int length){
		return new MultivariateShapelet( this.name, this.classification, this.data.subMatrix(timeStamp, length));
	}
	
	/**
	 * computes the distance between 2 multivariate 
	 * time series taking into account 
	 * that they might be of different lengths 
	 * 
	 * @param mts multivariate time series 
	 * @return distance between this time series and mts
	 */
    public double[] distance( MultivariateTimeSeries mts){
    	double[] dist = new double[this.data.getNmb_columns()];
    	for( int i = 0; i < dist.length; i++){
    		dist[i] = Lib.distance_1d( this.data.getColumn(i), mts.data.getColumn(i));
    	}
    	return dist;
    	
    }
 
    /**
     *  
     * @param ms multivariate shapelet 
     * @param perc : percentage extraction and classification parameter
     * 
     * @return true if the distance between this time series 
     *         and the ms shapelet is < to ms's distance threshold 
     */
	public boolean contains( MultivariateShapelet ms, double perc){
         return ( Lib.cmp(this.distance(ms), ms.getDistThreshold()) >= perc );
	}
	
	/**
	 * multivariate time series to string
	 */
	public String toString(){
		String s = "name : "+ this.name + "\n";
	    s += "classification : "+ this.classification +"\n"; 
		s += "data : " + this.data.toString()+"\n";
		return s;
	}
	
}
