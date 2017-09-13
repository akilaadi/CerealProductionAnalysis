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
        Job job1,job2,job3;
        try {
            job1 = Job.getInstance(conf, "CerealProductionAnalysis");
            job1.setJarByClass(CerealProductionAnalysisMain.class);
            job1.setJobName("Cereal Production Analysis");
            job1.setOutputKeyClass(Text.class);
            job1.setOutputValueClass(FloatWritable.class);
            job1.setMapperClass(CerealProductionAnalysisMapper.class);
            job1.setReducerClass(CerealProductionAnalysisReducer.class);
            job1.setCombinerClass(CerealProductionAnalysisReducer.class);
            job1.setInputFormatClass(CSVInputFormat.class);
            job1.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.addInputPath(job1, new Path(args[0]));
            FileOutputFormat.setOutputPath(job1, (new Path(args[1]+"/job1")));

            job2 = Job.getInstance(conf, "ProductionEffectivenessByDistrict");
            job2.setJarByClass(CerealProductionAnalysisMain.class);
            job2.setJobName("Production Effectiveness By District");
            job2.setOutputKeyClass(Text.class);
            job2.setOutputValueClass(FloatWritable.class);
            job2.setMapperClass(ProductionEffectivenessByDistrictMapper.class);
            job2.setReducerClass(ProductionEffectivenessByDistrictReducer.class);
            job2.setCombinerClass(ProductionEffectivenessByDistrictReducer.class);
            job2.setInputFormatClass(CSVInputFormat.class);
            job2.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.addInputPath(job2, new Path(args[0]));
            FileOutputFormat.setOutputPath(job2, (new Path(args[1] + "/job2")));

            job3 = Job.getInstance(conf, "DistrictContributionAnalysisMapper");
            job3.setJobName("District Contribution Analysis Mapper");
            job3.setOutputKeyClass(Text.class);
            job3.setOutputValueClass(FloatWritable.class);
            job3.setMapperClass(DistrictContributionAnalysisMapper.class);
            job3.setReducerClass(DistrictContributionAnalysisReducer.class);
            job3.setCombinerClass(ProductionEffectivenessByDistrictReducer.class);
            job3.setInputFormatClass(CSVInputFormat.class);
            job3.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.addInputPath(job3, new Path(args[0]));
            FileOutputFormat.setOutputPath(job3, (new Path(args[1] + "/job3")));

            job1.submit();
            if(job1.waitForCompletion(true)){
                job2.submit();
                if(job2.waitForCompletion(true)){
                    System.exit(job3.waitForCompletion(true) ? 0 : 1);
                }
            }

        } catch (IOException e) {
            //logger.error(e.getMessage());
        } catch (InterruptedException e) {
           // logger.error(e.getMessage());
        } catch (ClassNotFoundException e) {
           // logger.error(e.getMessage());
        }

    }
}
