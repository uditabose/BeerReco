package com.beerrecommendation.ba.indexgenerator;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;

/**
 * Created by maverick on 11/29/14.
 */
public class IndexGeneratorJob  {
    public static void main(String args[]) throws IOException {
        JobConf conf = new JobConf(IndexGeneratorJob.class);
        conf.setJobName("BA Index Generator Job");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(DoubleWritable.class);

        conf.setMapperClass(IndexGeneratorMapper.class);
        conf.setReducerClass(IndexGeneratorReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf,new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
    }
}
