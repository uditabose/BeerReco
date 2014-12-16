package com.beerrecommendation.ba.indexgenerator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by maverick on 11/29/14.
 */
public class IndexGeneratorReducer extends MapReduceBase implements Reducer<Text, DoubleWritable, Text, DoubleWritable> {
    public void reduce(Text key, Iterator<DoubleWritable> values, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
        double sum = 0;
        int count = 0;
        while(values.hasNext()) {
            double val = values.next().get();
            System.out.println("key is "+key+"value is "+val);
            sum += val;
            count ++;
        }
        sum = sum / count;
        output.collect(key, new DoubleWritable(sum));
    }
}
