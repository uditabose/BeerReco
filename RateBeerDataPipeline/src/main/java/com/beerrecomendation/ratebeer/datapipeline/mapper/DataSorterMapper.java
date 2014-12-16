

package com.beerrecomendation.ratebeer.datapipeline.mapper;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 *
 * @author Udita
 */
public class DataSorterMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

    @Override
    public void map(LongWritable mapperKey, Text mapperValue, OutputCollector<Text, 
            Text> outputCollector, Reporter reporter) throws IOException {
        
        // Assumption :
        // Data format is 
        // beer_id, brewery_id, avg_ratings, appearance, aroma, taste, palate, overall, user_id, review
        /**
         *  RB-223810,46, 1.5  , 2/5  , 2/10  , 3/10  , 2/5  , 6/20, DeanF , 
         * Randomly found in Veracruz, as random as that can be, well what the heck, why not try it. 
         * Pours with a light-yellow body with plenty of carbonation and a bit of head, 
         * like any other piss-poor lager. Aroma of lemon and just a hint of fusel alcohols. 
         * Flavour is lemony cardboard, with a hint of hops, and a dry finish. 
         * Well sort of drinkable, but it certainly doesnt stand out, and is the 
         * regionally acceptable version of a higher-alcohol beer that still isnt 
         * really high alcohol. apparently this appeals to some people, and I say good on ya. 
         * Because I own Heineken stock., Mexico
         */
        
        String mapInputString = mapperValue.toString();
        String[] dataFields = mapInputString.split(",");
        
        String userId = getFieldValue(dataFields, 8);
        String beerId = getFieldValue(dataFields, 0);
        if ("".equals(userId) || "".equals(beerId)) {
            System.out.println("No user id or beer id");
            return;  
        }
        
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(beerId).append(",")
                .append(getFieldValue(dataFields, 2)).append(",")
                .append(getFieldValue(dataFields, 3)).append(",")
                .append(getFieldValue(dataFields, 4)).append(",")
                .append(getFieldValue(dataFields, 5)).append(",")
                .append(getFieldValue(dataFields, 6)).append(",")
                .append(getFieldValue(dataFields, 7));
        
        outputCollector.collect(new Text(userId), new Text(outputBuilder.toString()));
        
    }

    private String getFieldValue(String[] dataFields, int fieldPosition) {
        String value = null;
        if (fieldPosition < dataFields.length) {
            value = (dataFields[fieldPosition] == null) ? "" : dataFields[fieldPosition];
        }
        return value; 
    }    
    
}
