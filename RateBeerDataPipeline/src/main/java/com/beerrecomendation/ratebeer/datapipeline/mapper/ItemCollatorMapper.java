

package com.beerrecomendation.ratebeer.datapipeline.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.hadoop.io.DoubleWritable;
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
public class ItemCollatorMapper extends MapReduceBase 
        implements Mapper<LongWritable, Text, Text, DoubleWritable>{

    @Override
    public void map(LongWritable mapperKey, Text mapperValue, OutputCollector<Text, 
                    DoubleWritable> outputCollector, Reporter reporter) throws IOException {
        /**
         * Leighton 	RB-67102, 2.6  , 2/5  , 4/10  , 5/10  , 3/5  , 12/20:RB-41008, 3.7  , 3/5  , 7/10  , 7/10  , 3/5  , 17/20:
         *
         * avg_ratings, appearance, aroma, taste, palate, overall 
         */
        //System.out.println(mapperValue.toString());
        
        Map<String, List<Float>> reviewMap = getReviewMap(mapperValue.toString());
        Map<String, Float> reviewComarisonMap = getComparison(reviewMap);

       // multiple collect
        for (Map.Entry<String, Float> reviewComarison : reviewComarisonMap.entrySet()) {
            outputCollector.collect(new Text(reviewComarison.getKey()), new DoubleWritable(reviewComarison.getValue()));
        }
        
    }

    private List<Float> getRatings(String[] partReview) {
        List<Float> ratings = new ArrayList<>();
        char blank = (char) 160;
        for (int i = 1; i < partReview.length; i++) {
            String aPart = partReview[i].replaceAll(blank + "", "").trim();
            if (aPart.contains(".")) {
                ratings.add(Float.parseFloat(aPart));
            } else if (partReview[i].contains("/")) {
                String[] decimalParts = aPart.split("/");
                ratings.add(Float.parseFloat(decimalParts[0])/Float.parseFloat(decimalParts[1]));
            } 
        }
        return ratings;
    }

    private Map<String, List<Float>> getReviewMap(String reviewCollections) {
        Map<String, List<Float>> reviewMap = new HashMap<>();
        if (reviewCollections.indexOf("RB-") < 0) {
            return reviewMap;
        }
        reviewCollections = reviewCollections.substring(reviewCollections.indexOf("RB-"));
        String[] reviewByBeer = reviewCollections.split(":");
        
        List<Float> ratings = null;
        for (String review : reviewByBeer) {
            String[] partReview = review.split(",");
            ratings = getRatings(partReview);
            reviewMap.put(partReview[0].trim(), ratings);
        }
        
        return reviewMap;
    }

    private Map<String, Float> getComparison(Map<String, List<Float>> reviewMap) {
        String[] reviewKeys = reviewMap.keySet().toArray(new String[0]);
        Map<String, Float> compareMap = new HashMap<>();
        
        for (int i = 0; i < reviewKeys.length - 1; i++) {
            String revKey = reviewKeys[i];
            List<Float> revList = reviewMap.get(revKey);
            for (int j = i + 1; j < reviewKeys.length; j++) {
                String compareKey = reviewKeys[j];
                List<Float> compList = reviewMap.get(compareKey);
                compareMap.put(revKey + " " + compareKey, computeEuclidianDistance(revList, compList));
            }
        }
        
        return compareMap;
    }

    private Float computeEuclidianDistance(List<Float> revList, List<Float> compList) {
        int lengthToTraverse = revList.size() >= compList.size() ? revList.size() : compList.size();
        float euclidianDistance = 0.0f;
        for (int i = 0; i < lengthToTraverse; i++) {
            if (validFloat(revList, i) && validFloat(compList, i)) {
                euclidianDistance += Math.pow(revList.get(i) - compList.get(i), 2);
            }
        }
        return (float)Math.sqrt(euclidianDistance);
    }

    private boolean validFloat(List<Float> floatList, int idx) {
        if (floatList.size() > idx && floatList.get(idx) != null && !floatList.get(idx).isNaN() ) {
            return true;
        }
        
        return false;
    }
}
