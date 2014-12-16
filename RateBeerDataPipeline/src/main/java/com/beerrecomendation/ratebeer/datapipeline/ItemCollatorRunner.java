

package com.beerrecomendation.ratebeer.datapipeline;

import com.beerrecomendation.ratebeer.datapipeline.mapper.ItemCollatorMapper;
import com.beerrecomendation.ratebeer.datapipeline.reducer.ItemCollatorReducer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/Resource/FinalReduce/ItemCollator"};
        
        
        if (args.length < 2) {
            System.out.println("Invalid arguements");
            return;
        }
        
        File outDir = new File(args[1]); 
        if (outDir.exists() && outDir.isDirectory()) {
            String newName = args[1] + System.currentTimeMillis();
            outDir.renameTo(new File(newName));
            outDir.delete();
        }
        //outDir.mkdirs();
        
        List<File> jobFile = splitJobs(args[0]);
        int jobId = 1;
        for (File job : jobFile) {
            System.out.println(" Starting job id >>> " + jobId + " - \n" + job.getAbsolutePath());
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

            FileInputFormat.setInputPaths(itemCollatorJobConf, new Path(job.getAbsolutePath()));
            FileOutputFormat.setOutputPath(itemCollatorJobConf, new Path(args[1] + jobId++));

            JobClient.runJob(itemCollatorJobConf);
            
//            try {
//                Thread.sleep(100000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(ItemCollatorRunner.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }

    private static List<File> splitJobs(String mainInputPath) {
        
        BufferedReader mainInputReader = null;
        BufferedWriter splitWriter = null;
        List<File> splitJobFile = new ArrayList<>();
        try {
            File mainInput = new File(mainInputPath);
            String mainDirPath = mainInput.getParent();
            int lineCount = 0;
            String line = null;
            File splitFile = null;
            mainInputReader = new BufferedReader(new FileReader(mainInput));
            StringBuilder splitBuilder = new StringBuilder();
            
            while((line = mainInputReader.readLine()) != null) {
                splitBuilder.append(line).append("\n");
                lineCount++;
                if (lineCount % 1000 == 0) {
                    splitFile = new File(mainDirPath + "/" + (lineCount/1000));
                    splitWriter = new BufferedWriter(new FileWriter(splitFile));
                    splitWriter.write(splitBuilder.toString());
                    splitWriter.flush();
                    splitJobFile.add(splitFile);
                    splitBuilder = new StringBuilder();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ItemCollatorRunner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ItemCollatorRunner.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                mainInputReader.close();
            } catch (IOException ex) {
                Logger.getLogger(ItemCollatorRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return splitJobFile;
    }

}
