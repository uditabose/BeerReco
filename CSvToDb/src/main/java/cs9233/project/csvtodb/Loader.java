package cs9233.project.csvtodb;

import cs9233.project.util.ConnectionUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Udita
 */
public class Loader {

    public static void main(String[] args) {
        try {
            
            args = new String[]{"/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/Resource/By_Review"};
            readAndLoadDataInDB(args[0]);
        } catch (SQLException ex) {
            System.err.println(" Can not execute SQL command : " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println(" Can not read data file : " + ex.getMessage());
        }

    }

    private static int getGetNumberOfRows(Connection connection) throws SQLException {
        Statement countStatement = connection.createStatement();
        ResultSet numberOfReviewsResultSet = countStatement
                .executeQuery("SELECT COUNT(*) FROM beer_db.beer_review_details");
        numberOfReviewsResultSet.next();
        int numberOfReviews = numberOfReviewsResultSet.getInt(1);
        System.out.println("Reviews stored - " + numberOfReviews);
        numberOfReviewsResultSet.close();
        countStatement.close();
        return numberOfReviews;
    }

    private static void readAndLoadDataInDB(String dataPath) throws SQLException, 
            FileNotFoundException, IOException {
        
        BufferedReader dataReader = new BufferedReader(new FileReader(dataPath));
        String dataSchema = dataReader.readLine();
        //System.out.println("dataSchema");
        String schemaNames[] = dataSchema.split(",");
        Map<String, Integer> schemaMap = new HashMap<String, Integer>();
        int counter = 0;
        for (String schema : schemaNames) {
            schemaMap.put(schema.trim(), counter++);
        }

        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection connection = connectionUtil.getTempRdsConnection();
        int numberOfReviews = getGetNumberOfRows(connection);

        connection.setAutoCommit(false);
        String insertQuery = "INSERT INTO beer_db.beer_review_details (user_seq, user_id, "
                + "beer_id, brewery_id, avg_ratings, appearance, aroma, "
                + "taste, palate, overall, review) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int beerSeq = numberOfReviews;
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        
        String dataLine = null;
        while((dataLine = dataReader.readLine()) != null) {
            // beer_id, brewery_id, avg_ratings, appearance, aroma, taste, palate, overall, user_id, review
            
            String[] splittedData = dataLine.split(",");
            preparedStatement.setInt(1, ++beerSeq);
            preparedStatement.setString(2, getSchemaValue("user_id", schemaMap, splittedData));
            preparedStatement.setString(3, getSchemaValue("beer_id", schemaMap, splittedData));
            preparedStatement.setString(4, getSchemaValue("brewery_id", schemaMap, splittedData));
            preparedStatement.setString(5, getSchemaValue("avg_ratings", schemaMap, splittedData));
            preparedStatement.setString(6, getSchemaValue("appearance", schemaMap, splittedData));
            preparedStatement.setString(7, getSchemaValue("aroma", schemaMap, splittedData));
            preparedStatement.setString(8, getSchemaValue("taste", schemaMap, splittedData));
            preparedStatement.setString(9, getSchemaValue("palate", schemaMap, splittedData));
            preparedStatement.setString(10, getSchemaValue("overall", schemaMap, splittedData));
            preparedStatement.setString(11, getSchemaValue("review", schemaMap, splittedData));
            preparedStatement.addBatch();
            
            if (beerSeq % 100 == 0) {
                preparedStatement.executeBatch();
                connection.commit();
            }
        }
        
        dataReader.close();
    }

    private static String getSchemaValue(String schemaKey, Map<String, Integer> schemaMap, String[] dataSplit) {
        String value = dataSplit[schemaMap.get(schemaKey)];
        if (value == null || "".equals(value.trim())) {
            value = "-";
        }
        return value;
    }
}
