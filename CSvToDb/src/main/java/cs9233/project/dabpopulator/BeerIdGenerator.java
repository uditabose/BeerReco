

package cs9233.project.dabpopulator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 *
 * @author Udita
 */
public class BeerIdGenerator {
    
    public static void main(String[] args) {
        BufferedReader dataReader = null;
        BufferedWriter dataReWriter = null;
        
        String dataPath = "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/Resource/By_Beer";
        String dataReWritePath = "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/Resource/By_Beer_ReWrite";
            
        try {
            dataReader = new BufferedReader(new FileReader(dataPath));
            dataReWriter = new BufferedWriter(new FileWriter(dataReWritePath, true));
            
            String dataLine = null;
            long dataIdx = 0l;
            while((dataLine = dataReader.readLine()) != null) {
                String[] splittedData = dataLine.split(",");
                dataReWriter.append(++dataIdx + ",");
                dataReWriter.append(splittedData[0]);
                dataReWriter.append(",");
                dataReWriter.append(unEncodeString(splittedData[1]));
                dataReWriter.newLine();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                dataReWriter.close();
                dataReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String unEncodeString(String encodedString) {
        String unEncodedString = null;
        try {
            unEncodedString = URLDecoder.decode(encodedString, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            
        }
        return unEncodedString;
    }
}
