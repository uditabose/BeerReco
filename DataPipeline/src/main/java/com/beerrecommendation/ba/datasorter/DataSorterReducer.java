package com.beerrecommendation.ba.datasorter;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by maverick on 11/29/14.
 */
public class DataSorterReducer extends MapReduceBase implements org.apache.hadoop.mapred.Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
        StringBuffer val = new StringBuffer();
        while(values.hasNext()) {
            val.append(values.next().toString());
            if(values.hasNext()) {
                val.append("^");
            }
        }
        output.collect(key, new Text(val.toString()));
    }
}
