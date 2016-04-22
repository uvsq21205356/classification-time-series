package gui;
import java.awt.Color;
import java.io.FileReader;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BasicStroke; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities;

import com.opencsv.CSVReader;

import extraction_classification.Matrix;
import extraction_classification.MultivariateTimeSeries;

import org.jfree.chart.plot.XYPlot;
import org.apache.commons.lang3.math.NumberUtils;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class XYLineChart_AWT extends JFrame
{
	private String name;           //filename
	private String classification;
	
	                //value of the checkbox
	private int nbLigne;   
	private int nbCol;
	private double[][]values;  
	private Color[] colorTab;                   //tableau des couleur utilisées pour les graphes
	
 
   public XYLineChart_AWT( String applicationTitle, String chartTitle,String name)
   {
	  super(applicationTitle);
	  this.name=name;
	
	  //separator
	  MultivariateTimeSeries mts=new MultivariateTimeSeries();
	  classification="";
		
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
			values =new double[nbLigne][nbCol];			
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
			mts=new MultivariateTimeSeries(name, classification, matrice);
			System.out.println(classification);
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	  
	  //separator
	  JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle ,
         "Time" ,
         "Value" ,
         createDataset() ,
         PlotOrientation.VERTICAL ,
         true , true , false);
      
      
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      final XYPlot plot = xylineChart.getXYPlot( );
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      
      //color setup
      
      Random rand = new Random();
     
      for(int i=0;i<nbCol;i++){
    	   renderer.setSeriesPaint(i,new Color(rand.nextInt(0xFFFFFF)));
      }
      
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel ); 
      //color setup
   
   
   }
   
   private XYDataset createDataset( ){          //for creating the full dataset
	   XYSeries [] xyTab=new XYSeries[nbCol];
	   char [] nameTab= new char  [nbCol];
	   
	   
	   
		   		
	   for(int i=0;i <nbCol;i++){                        
		   char serieName=(char)(65+i);
		   xyTab[i]=new XYSeries( serieName );
	   }
	   

	   
	   for(int i=0;i<nbCol;i++){
		   for(int j=0;j<nbLigne;j++){     
			   xyTab[i].add(j,values[j][i]);
		   }
	   }
	   final XYSeriesCollection dataset = new XYSeriesCollection( );
	   for(int i=0;i<xyTab.length;i++){
		   dataset.addSeries( xyTab[i] );
	   }
	   return dataset;
		   
	 
      
   }


}
