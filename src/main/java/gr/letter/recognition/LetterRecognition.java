package gr.letter.recognition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.encog.ConsoleStatusReportable;
import org.encog.Encog;
import org.encog.bot.BotUtil;
import org.encog.ml.MLRegression;
import org.encog.ml.data.MLData;
import org.encog.ml.data.versatile.NormalizationHelper;
import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.data.versatile.columns.ColumnDefinition;
import org.encog.ml.data.versatile.columns.ColumnType;
import org.encog.ml.data.versatile.sources.CSVDataSource;
import org.encog.ml.data.versatile.sources.VersatileDataSource;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.model.EncogModel;
import org.encog.util.csv.CSVFormat;
import org.encog.util.csv.ReadCSV;
import org.encog.util.simple.EncogUtility;
import javax.swing.*;

public class LetterRecognition {
	private String tempPath;
	
	public File createData() throws MalformedURLException {
		
		tempPath = System.getProperty("user.dir");
		
		File letterRecognitionFile = new File(tempPath, "letter.csv");
		try {
			FileWriter myWriter = new FileWriter("letter.csv");
			String[] ab = new String[] {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
			for(int k=0; k<ab.length;k++) {
				for (int i = 0; i < new File(tempPath + "/letters/"+ab[k]).list().length; i++) {
					Attributes attr = new Attributes((String) ("letters/" + ab[k]+"/" + new File(tempPath + "/letters/"+ab[k]).list()[i]));
					String[] attributes = attr.getAttributes();
					myWriter.write(ab[k]);
					for (int j = 0; j < attributes.length; j++) {
						String m = "," + attributes[j];
						myWriter.write(m);
					}
					myWriter.write("\n");
				}				
			}
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("Adding values to dataset: " + letterRecognitionFile);
		return letterRecognitionFile;
	}

	public char run(String imagePath) {
		int [] counters = new int[26];
		char[] ab = new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		
		for(int x=0; x<100; x++) {
			try {
				// Download the data that we will attempt to model.
				File letterRecognitionFile = createData();

				// Define the format of the data file.
				// This area will change, depending on the columns and
				// format of the file that you are trying to model.
				VersatileDataSource source = new CSVDataSource(letterRecognitionFile, false, CSVFormat.DECIMAL_POINT);
				VersatileMLDataSet data = new VersatileMLDataSet(source);
				data.defineSourceColumn("x-box", 1, ColumnType.continuous);
				data.defineSourceColumn("y-box", 2, ColumnType.continuous);
				data.defineSourceColumn("width", 3, ColumnType.continuous);
				data.defineSourceColumn("high", 4, ColumnType.continuous);
				data.defineSourceColumn("onpix", 5, ColumnType.continuous);
				data.defineSourceColumn("x-bar", 6, ColumnType.continuous);
				data.defineSourceColumn("y-bar", 7, ColumnType.continuous);
				data.defineSourceColumn("x2bar", 8, ColumnType.continuous);
				data.defineSourceColumn("y2bar", 9, ColumnType.continuous);
				data.defineSourceColumn("xybar", 10, ColumnType.continuous);
				data.defineSourceColumn("x2ybr", 11, ColumnType.continuous);
				data.defineSourceColumn("xy2br", 12, ColumnType.continuous);
				data.defineSourceColumn("x-ege", 13, ColumnType.continuous);
				data.defineSourceColumn("xegvy", 14, ColumnType.continuous);
				data.defineSourceColumn("y-ege", 15, ColumnType.continuous);
				data.defineSourceColumn("yegvx", 16, ColumnType.continuous);

				// Define the column that we are trying to predict.
				ColumnDefinition outputColumn = data.defineSourceColumn("letter", 0, ColumnType.nominal);

				// Analyze the data, determine the min/max/mean/sd of every column.
				data.analyze();

				// Map the prediction column to the output of the model, and all
				// other columns to the input.
				data.defineSingleOutputOthersInput(outputColumn);

				// Create feedforward neural network as the model type.
				EncogModel model = new EncogModel(data);
				model.selectMethod(data, MLMethodFactory.TYPE_FEEDFORWARD);

				// Send any output to the console.
				model.setReport(new ConsoleStatusReportable());

				// Now normalize the data. Encog will automatically determine the correct
				// normalization type based on the model you chose in the last step.
				data.normalize();

				// Hold back some data for a final validation.
				// Shuffle the data into a random ordering.
				// Use a seed of 1001 so that we always use the same holdback and will get more
				// consistent results.
				model.holdBackValidation(0, true, 1001);

				// Choose whatever is the default training type for this model.
				model.selectTrainingType(data);

				// Use a 5-fold cross-validated train. Return the best method found.
				MLRegression bestMethod = (MLRegression) model.crossvalidate(5, true);

				
				// Display our normalization parameters.
				NormalizationHelper helper = data.getNormHelper();
				//System.out.println(helper.toString());

				// Display the final model.
				//System.out.println("Final model: " + bestMethod);

				
				String[] line = new String[16];
				MLData input = helper.allocateInputVector();
				Attributes test = new Attributes(imagePath);
				String[] attributes = test.getAttributes();
				
				for (int i = 0; i < 16; i++) {
					line[i] = attributes[i];
				}

				helper.normalizeInputVector(line, input.getData(), false);
				
				MLData output = bestMethod.compute(input);
				String letterChosen = helper.denormalizeOutputVectorToString(output)[0];
				
				//find frequency of predicted letters
				int index = new String(ab).indexOf(letterChosen.charAt(0));
				counters[index]++;

				//result.append(Arrays.toString(line));
				//result.append(" -> predicted: ");
				//result.append(letterChosen);

				//System.out.println(result.toString());
				
				// Delete data file and shut down.
				// letterRecognitionFile.delete();
				Encog.getInstance().shutdown();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println();
		
		//the final predicted letter is the one with the highest prediction frequency
		int max = counters[0];
		int indx = 0;
		for(int b=0;b<26;b++) {
			System.out.print(ab[b] + ":" + counters[b] + " ");
			if(counters[b]>max) {
				max = counters[b];
				indx = b;
			}
		}
		return ab[indx];
	}

	public static void main(String[] args) {
		String imagePath;
		
		// Choose image from file system to recognize
		final JFrame frame = new JFrame();
		final JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
		int retVal = fc.showOpenDialog(frame);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			imagePath = selectedFile.getAbsolutePath();
			LetterRecognition prg = new LetterRecognition();
			System.out.println("\n\nLetter predicted is: " + prg.run(imagePath));
		}
		else
			System.out.println("No image chosen - Program exited");
	}
}
