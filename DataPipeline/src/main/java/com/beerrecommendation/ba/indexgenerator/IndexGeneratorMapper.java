package com.beerrecommendation.ba.indexgenerator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;

/**
 * Created by maverick on 11/29/14.
 */
public class IndexGeneratorMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, DoubleWritable> {

    public void map(LongWritable key, Text value, OutputCollector<Text, DoubleWritable> output, Reporter reporter)throws IOException {
        String val = value.toString();
        double distance = 0.0;
        int sep = val.indexOf("\t");
        //String username = val.substring(0,sep);
        String reviewstring = val.substring(sep+1, val.length()-1);
        String[] reviews = reviewstring.split("^");
        // for debugging only
//        if(reviews.length > 1) {
//            System.out.println("candidate review");
//            System.out.println(val);
//        }
        for(int i = 0; i < reviews.length-1; i++) {
            for(int j = i+1; j < reviews.length; j++) {
                distance = calculateEuclidanDistance(reviews[i],reviews[j]);
                int id1 = findBeerId(reviews[i]);
                int id2 = findBeerId(reviews[j]);
                if (id1 < id2) {
                    output.collect(new Text(id1+" "+id2), new DoubleWritable(distance));
                }
                else {
                    output.collect(new Text(id2+" "+id1), new DoubleWritable(distance));
                }
            }
        }
    }

    private int findBeerId(String review) {
        String id = review.substring(0,review.indexOf(","));
        return Integer.parseInt(id);
    }

    private double calculateEuclidanDistance(String review1, String review2) {
        double temp1, temp2, val = 0;
        String[] review1Components = review1.split(",");
        String[] review2Components = review2.split(",");
        for(int i = 2; i < review1Components.length; i++) {
            try {
                temp1 = Double.parseDouble(review1Components[i]);
                temp2 = Double.parseDouble(review2Components[i]);
                val += (temp1 - temp2) * (temp1 - temp2);
            } catch(Exception e) {

            }

        }
        return Math.sqrt(val);
    }
}
