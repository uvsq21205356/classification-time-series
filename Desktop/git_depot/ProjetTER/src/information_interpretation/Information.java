package information_interpretation;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.opencsv.CSVReader;

import extraction_classification.MultivariateTimeSeries;
import extraction_classification.Dataset;
import extraction_classification.Lib;
import extraction_classification.Matrix;
import extraction_classification.MultivariateShapelet;
import extraction_classification.MultivariateTimeSeries;

public class Information {
	private String name;
	private Dataset data;
	
	public Dataset getData(){
		return this.data;
	}
	
	//constructor
	public Information(String name){
		this.name = name;
		
		File folder = new File(name);
    	File[] listOfFiles = folder.listFiles();       //obtention de la liste des noms des fichier contenus dans le reertoir
    	ArrayList<MultivariateTimeSeries> mtsData = new ArrayList<MultivariateTimeSeries>(listOfFiles.length);
    	   
    	for (int i = 0; i < listOfFiles.length; i++) {
    		MultivariateTimeSeries mts = genererMTS( name + "/" + listOfFiles[i].getName());
    	    mtsData.add(mts);
    	}
		data = new Dataset(mtsData);
		
	}
	//method for generating the multivariate time series
		public static MultivariateTimeSeries genererMTS(String name){
	    	
			MultivariateTimeSeries mts = new MultivariateTimeSeries();
			String classification="";
			int nbLigne=0;   
			int nbCol=0;
			//in case the classification is present in the file
			
			try{
				CSVReader reader=new CSVReader(new FileReader(name));
				List<String[]> list =reader.readAll();
				nbCol=(list.get(1)).length;
				
				
				int i=0;
				for(String[] s : list){      
					if(i<list.size()-1){
						
						i++;
					}
					else{
						for(int u=0;u<s.length;u++){
							if(!(NumberUtils.isNumber(s[u]))){
								classification=s[u];  // afin d'obtenir la classification à la dernière ligne
								nbLigne=list.size()-1;   
								
								break;
							}
							classification="Not classified";
							nbLigne=list.size();   
							
						
						}
						
						
					
					
					
					
					}
					
				}
				double [][] values =new double[nbLigne][nbCol];			
				int indice=0;  // afin de savoir si on atteit le nombre de ligne voulu
				for(String[]s : list){
					if(indice<nbLigne){	
						for(int j=0;j<nbCol;j++){
							values[indice][j]=Double.parseDouble(s[j]);
							
						}
						indice++;
					}	
					else{
						break;
					}
				
				}
				
				
				
				Matrix matrice =new Matrix(values,nbLigne,nbCol);
				mts = new MultivariateTimeSeries(name, classification, matrice);
				System.out.println(classification);
				
			}
			catch(Exception e){
				System.out.println(e);
			}
		
		//not classified
		
		
		return mts;         
		}
	}
