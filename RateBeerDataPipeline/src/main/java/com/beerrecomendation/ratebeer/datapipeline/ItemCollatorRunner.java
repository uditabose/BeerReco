

package com.beerrecomendation.ratebeer.datapipeline;

import com.beerrecomendation.ratebeer.datapipeline.mapper.DataSorterMapper;
import com.beerrecomendation.ratebeer.datapipeline.mapper.ItemCollatorMapper;
import com.beerrecomendation.ratebeer.datapipeline.reducer.DataSorterReducer;
import com.beerrecomendation.ratebeer.datapipeline.reducer.ItemCollatorReducer;
import java.io.File;
import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

/**
 *
 * @author Udita
 */
public class ItemCollatorRunner {
    
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Invalid arguements");
            return;
        }
        
        JobConf dataSorterJobConf = new JobConf();
        dataSorterJobConf.setJobName("RateBeer_ItemCollator_Job");
        dataSorterJobConf.setMapperClass(ItemCollatorMapper.class);
        dataSorterJobConf.setReducerClass(ItemCollatorReducer.class);
        
        dataSorterJobConf.setMapOutputKeyClass(Text.class);
        dataSorterJobConf.setMapOutputValueClass(DoubleWritable.class);
        dataSorterJobConf.setOutputKeyClass(Text.class);
        dataSorterJobConf.setOutputValueClass(DoubleWritable.class);
        dataSorterJobConf.setInputFormat(TextInputFormat.class);
        dataSorterJobConf.setOutputFormat(TextOutputFormat.class);

        File outDir = new File(args[1]); 
        if (outDir.exists() && outDir.isDirectory()) {
            String newName = args[1] + System.currentTimeMillis();
            outDir.renameTo(new File(newName));
            outDir.delete();
        }
        
        FileInputFormat.setInputPaths(dataSorterJobConf, new Path(args[0]));
        FileOutputFormat.setOutputPath(dataSorterJobConf, new Path(args[1]));
        
        JobClient.runJob(dataSorterJobConf);
    }

}
