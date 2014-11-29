package com.beerrecommendation.ba.datasorter;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;

/**
 * Created by maverick on 11/29/14.
 */
public class DataSorterJob {
    public static void main(String args[]) throws IOException{
        JobConf conf = new JobConf(DataSorterJob.class);
        conf.setJobName("Beer Advocate Data Sorting Job");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);

        conf.setMapperClass(DataSorterMapper.class);
        conf.setCombinerClass(DataSorterReducer.class);
        conf.setReducerClass(DataSorterReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
    }
}
