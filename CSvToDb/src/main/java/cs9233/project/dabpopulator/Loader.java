

package cs9233.project.dabpopulator;

import cs9233.project.util.ConnectionUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Udita
 */
public class Loader {
    
    public static void main(String[] args) {
        try {
            String dataPath = "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/Resource/By_Beer";
            BufferedReader dataReader = new BufferedReader(new FileReader(dataPath));
            Connection connection = ConnectionUtil.getInstance().getFinalRdsConnection();
            connection.setAutoCommit(false);
            String insertQuery = "INSERT INTO beerRating.Beer (BEER_ID, BEER_NAME "
                    + ") VALUES (?, ?)";
            
            int beerSeq = 0;
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            if (connection != null) {
                String dataLine = null;
                try {
                    System.out.println("Going to update data");
                    while((dataLine = dataReader.readLine()) != null) {
                        
                        String[] splittedData = dataLine.split(",");
                        preparedStatement.setInt(1, ++beerSeq);
                        preparedStatement.setString(2, splittedData[1].trim());
                        preparedStatement.addBatch();

                        if (beerSeq % 100 == 0) {
                            System.out.println("Updating - " + beerSeq + " - " + dataLine);
                            //System.out.println(splittedData[0] + " - " + splittedData[1]);
                            preparedStatement.executeBatch();
                            connection.commit();
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
