

package com.beerrecomendation.ratebeer.datapipeline.reducer;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 *
 * @author Udita
 */
public class ItemCollatorReducer extends MapReduceBase 
                            implements Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    @Override
    public void reduce(Text reducerKey, Iterator<DoubleWritable> reducerValueIterator, 
            OutputCollector<Text, DoubleWritable> outputCollector, Reporter reporter) throws IOException {
        
    }

}
