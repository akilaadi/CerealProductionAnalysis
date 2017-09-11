package com.sdcp.assignment2;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DistrictContributionAnalysisReducer extends
        Reducer<Text, FloatWritable, Text, FloatWritable> {
        @Override
        public void reduce(Text key, Iterable<FloatWritable> values, Context context)
                throws IOException, InterruptedException {
            float cerealProductionSum = 0;
            int count = 9; //number of years
            for (FloatWritable val : values) {
                cerealProductionSum += val.get();

            }
            //calculate the average effectiveness
            context.write(key, new FloatWritable(cerealProductionSum /count));
        }
}

