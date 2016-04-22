package extraction_classification;

import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class SaveFiles {
	
	public void ms_to_CSV(String name, MultivariateShapelet ms) throws IOException{
		CSVWriter writer = new CSVWriter(new FileWriter(name), ',');
	
		for(int i = 0; i < ms.getData().getNmb_rows(); i++){
			String[] s = ms.getData().getRow(i).toString().split(",");
			writer.writeNext(s);
		}
		
		String[] s = new String[1];
        s[0] = ms.getClassification();
		writer.writeNext(s);
		writer.close();
	}

}
