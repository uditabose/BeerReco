

package com.beerrecomendation.ratebeer.datapipeline;

import com.beerrecomendation.ratebeer.datapipeline.mapper.ItemCollatorMapper;
import com.beerrecomendation.ratebeer.datapipeline.reducer.ItemCollatorReducer;
import java.io.File;
import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
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
        
        /**
         * DEBUG --
         */
        args = new String[]{
            "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/Resource/DataSorter/part-00000", 
            "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/Resource/ItemCollator"};
       
        
        if (args.length < 2) {
            System.out.println("Invalid arguements");
            return;
        }
        
        JobConf itemCollatorJobConf = new JobConf();
        itemCollatorJobConf.setJobName("RateBeer_ItemCollator_Job");
        itemCollatorJobConf.setMapperClass(ItemCollatorMapper.class);
        itemCollatorJobConf.setReducerClass(ItemCollatorReducer.class);
        
        itemCollatorJobConf.setMapOutputKeyClass(Text.class);
        itemCollatorJobConf.setMapOutputValueClass(DoubleWritable.class);
        itemCollatorJobConf.setOutputKeyClass(Text.class);
        itemCollatorJobConf.setOutputValueClass(DoubleWritable.class);
        itemCollatorJobConf.setInputFormat(TextInputFormat.class);
        itemCollatorJobConf.setOutputFormat(TextOutputFormat.class);

        File outDir = new File(args[1]); 
        if (outDir.exists() && outDir.isDirectory()) {
            String newName = args[1] + System.currentTimeMillis();
            outDir.renameTo(new File(newName));
            outDir.delete();
        }
        
        FileInputFormat.setInputPaths(itemCollatorJobConf, new Path(args[0]));
        FileOutputFormat.setOutputPath(itemCollatorJobConf, new Path(args[1]));
        
        JobClient.runJob(itemCollatorJobConf);
    }

}
