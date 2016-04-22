package global;

import java.util.ArrayList;

import information_interpretation.Information;
import extraction_classification.Dataset;
import extraction_classification.MultivariateShapelet;

public class App {
	
	String training_d;
	String unclassified_d;
	double perc;
	String mode; // "test" ou "execution_normale"
	int min_shap_len;
	int max_shap_len;
	
	public void run(){
		
		Information info = new Information(training_d, true );
		Dataset training_dataset = info.getData();
		
		info = new Information( unclassified_d, false);
		Dataset unclassified_dataset = info.getData();
		
	    ArrayList<MultivariateShapelet> shapelets = new ArrayList<MultivariateShapelet>();
		
		shapelets = training_dataset.shapeletDetection(min_shap_len, max_shap_len, perc);
		training_dataset.classification(unclassified_dataset, shapelets, perc);
	}
	

}
