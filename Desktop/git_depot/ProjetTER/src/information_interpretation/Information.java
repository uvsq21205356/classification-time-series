package information_interpretation;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.opencsv.CSVReader;

import extraction_classification.Dataset;
import extraction_classification.Lib;
import extraction_classification.Matrix;
import extraction_classification.MultivariateShapelet;
import extraction_classification.MultivariateTimeSeries;

public class Information {
	private String name;
	private boolean valeurCheckBox;
	private Dataset data;
	
	public Dataset getData(){
		return this.data;
	}
	
	//constructor
	public Information(String name,boolean valeurCheckBox){
		this.name = name;
		this.valeurCheckBox = valeurCheckBox;
		
		File folder = new File(name);
    	File[] listOfFiles = folder.listFiles();       //obtention de la liste des noms des fichier contenus dans le reertoir
    	ArrayList<MultivariateTimeSeries> mtsData = new ArrayList<MultivariateTimeSeries>(listOfFiles.length);
    	   
    	for (int i = 0; i < listOfFiles.length; i++) {
    		MultivariateTimeSeries mts = genererMTS( name + "/" + listOfFiles[i].getName(),valeurCheckBox);
    	    mtsData.add(mts);
    	}
		data = new Dataset(mtsData);
		
	}
	//method for generating the multivariate time serie
	
	public static MultivariateTimeSeries genererMTS(String name,boolean valeurCheckBox){
    	
		MultivariateTimeSeries mts = new MultivariateTimeSeries();
		String classification="";
		
		//in case the classification is present in the file
		if( valeurCheckBox){
		try{
			CSVReader reader=new CSVReader(new FileReader(name));
			List<String[]> list =reader.readAll();
		
			int nbLigne=list.size()-1;   
			int nbCol=(list.get(1)).length;
			double [][] values =new double[nbLigne][nbCol];
			int i=0;
			
			
			for(String[] s : list){
				if(i<nbLigne){
					for(int j=0;j<s.length;j++){
						values[i][j]=Double.parseDouble(s[j]);
				
					}
					i++;
				}
				else{
					for(int u=0;u<s.length;u++){
						if(!(NumberUtils.isNumber(s[u]))){
							classification=s[u];       // afin d'obtenir la classification à la dernière ligne
						}
					}
				}
							
			}
			for(int u =0;u<nbLigne;u++){
				for(int v=0;v<nbCol;v++){
					System.out.println(values[u][v]);
				}
			}
			Matrix matrice =new Matrix(values,nbLigne,nbCol);
			mts=new MultivariateTimeSeries(name, classification, matrice);
			System.out.println(classification);
			
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	//not classified
	else{
		try {
	
			CSVReader reader=new CSVReader(new FileReader(name));
			List<String[]> list =reader.readAll();
		
			int nbLigne=list.size();   
			int nbCol=(list.get(1)).length;
			double [][] values =new double[nbLigne][nbCol];
			int i=0;
		
			for(String[] s : list){
				for(int j=0;j<s.length;j++){
					values[i][j]=Double.parseDouble(s[j]);
				
				}
				i++;
			}
			for(int u=0;u<nbLigne;u++){     // un simple affichage pour tester (facultatif)
				for(int v=0;v<nbCol;v++){
					System.out.println(values[u][v]+" \n");
				}
			
			}
			classification="Undefined";
		
			Matrix matrice = new Matrix(values,nbLigne,nbCol);
			mts = new MultivariateTimeSeries(name, classification, matrice);
		
			System.out.println(nbCol  + " "+nbLigne);
			reader.close();
			
	
		} 
		catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println(e);
		} 
	}
	return mts;         
	}
	

	public static void main(String[] args){
	   
	   Information info = new Information("test_dataset",true);
	   
	   MultivariateTimeSeries mts_test = info.data.getData().get(0);
	   
	   MultivariateShapelet ms_test = mts_test.extract_shapelet(1, 3);
	
	   MultivariateTimeSeries mts_test2 = info.data.getData().get(2);
	   
	   ms_test.getDistThreshold()[0] = 100;
	   ms_test.getDistThreshold()[1] = 100;
	   ms_test.getDistThreshold()[2] = 1;
	   
	   ms_test.computeDistances(info.data);
	   System.out.println( "\n" + ms_test.getDistances().toString()+ "\n");
	   
	   ms_test.computeDistanceThreshold(info.data, 0.8);
	   info.data.computeEntropy();
	   System.out.print( " entropy = " + info.data.getEntropy());
	   
	   
	}
}
