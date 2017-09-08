package com.sdcp.assignment2;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
//import org.apache.log4j.Logger;

public class CerealProductionAnalysisMain {
    //private static Logger logger = Logger.getLogger(CerealProductionAnalysisMain.class);

    public static void main(String[] args) {
        Configuration conf = new Configuration();
        Job job;
        try {
            job = Job.getInstance(conf, "CerealProductionAnalysis");
            job.setJarByClass(CerealProductionAnalysisMain.class);
            job.setJobName("Cereal Production Analysis");
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(FloatWritable.class);
            job.setMapperClass(CerealProductionAnalysisMapper.class);
            job.setReducerClass(CerealProductionAnalysisReducer.class);
            job.setCombinerClass(CerealProductionAnalysisReducer.class);
            job.setInputFormatClass(CSVInputFormat.class);
            job.setOutputFormatClass(TextOutputFormat.class);
            //FileInputFormat.addInputPath(job, new Path(args[0] + "/cereal_production_statistics_maha_season_2006.csv"));
            FileInputFormat.addInputPath(job, new Path(args[0] + "/cereal_production_statistics_yala_season_2009.csv"));
            //FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, (new Path(args[1])));
            System.exit(job.waitForCompletion(true) ? 0 : 1);

        } catch (IOException e) {
            //logger.error(e.getMessage());
        } catch (InterruptedException e) {
           // logger.error(e.getMessage());
        } catch (ClassNotFoundException e) {
           // logger.error(e.getMessage());
        }

    }
}
