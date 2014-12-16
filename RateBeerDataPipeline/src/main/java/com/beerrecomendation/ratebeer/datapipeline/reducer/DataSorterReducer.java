

package com.beerrecomendation.ratebeer.datapipeline.reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 *
 * @author Udita
 */
public class DataSorterReducer extends MapReduceBase 
implements Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text reduceKey, Iterator<Text> reducerValueIterator, 
            OutputCollector<Text, Text> outputCollector, Reporter reporter) throws IOException {

        StringBuilder outputBuilder = new StringBuilder();
        while(reducerValueIterator.hasNext()) {
            outputBuilder.append(reducerValueIterator.next().toString())
                    .append(":");
        }
        
        outputCollector.collect(reduceKey, new Text(outputBuilder.toString()));
    }
}
