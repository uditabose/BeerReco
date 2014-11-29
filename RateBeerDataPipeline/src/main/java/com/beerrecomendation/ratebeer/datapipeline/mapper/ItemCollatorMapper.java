

package com.beerrecomendation.ratebeer.datapipeline.mapper;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
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
        implements Mapper<IntWritable, Text, Text, DoubleWritable>{

    @Override
    public void map(IntWritable mapperKey, Text mapperValue, OutputCollector<Text, 
                    DoubleWritable> outputCollector, Reporter reporter) throws IOException {
        
    }

}
