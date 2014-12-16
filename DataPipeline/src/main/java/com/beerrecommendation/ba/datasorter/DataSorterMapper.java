package com.beerrecommendation.ba.datasorter;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;

/**
 * Created by maverick on 11/28/14.
 */
public class DataSorterMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text >{

    public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
        String str = value.toString();
        try{

            String [] rawValues = str.split(","); // split csv into components

            if (rawValues.length < 9){
                System.out.println("less value detected "+str);
                return;
            }

            StringBuffer values = new StringBuffer();   // keep the useful part in values
            for(int i = 0; i < 8; i++) { // first 7 indexes have useful info
                values.append(rawValues[i]);
                if(i != 7) {
                    values.append(",");
                }

            }
            String username = rawValues[8];
            output.collect(new Text(username), new Text(values.toString()));
        } catch(Exception e) {
            e.printStackTrace();
            throw new IOException("problem string is " + str);
        }

    }
}
