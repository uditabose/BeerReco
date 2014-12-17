

package com.beerrecomendation.ratebeer.datapipeline;

import com.beerrecomendation.ratebeer.datapipeline.mapper.DataSorterMapper;
import com.beerrecomendation.ratebeer.datapipeline.reducer.DataSorterReducer;
import java.io.File;
import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

/**
 * com.beerrecomendation.ratebeer.datapipeline.DataSorterRunner
 * @author Udita
 */
public class DataSorterRunner {
    public static void main(String[] args) throws IOException {
        
        
        /**
         * DEBUG --
         */
        
        args = new String[]{
            "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/Resource/By_Review", 
            "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/Resource/DataSorter"
        };
        if (args.length < 2) {
            System.out.println("Invalid arguements");
            return;
        }
        
        JobConf dataSorterJobConf = new JobConf();
        dataSorterJobConf.setJobName("RateBeer_DataSorter_Job");
        dataSorterJobConf.setMapperClass(DataSorterMapper.class);
        dataSorterJobConf.setReducerClass(DataSorterReducer.class);
        
        dataSorterJobConf.setMapOutputKeyClass(Text.class);
        dataSorterJobConf.setMapOutputValueClass(Text.class);
        dataSorterJobConf.setOutputKeyClass(Text.class);
        dataSorterJobConf.setOutputValueClass(Text.class);
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
