package com.sdcp.assignment2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class CSVLineRecordReader extends RecordReader<Text, Text> {

    private List<String> lines = new ArrayList<String>();
    private LongWritable lineCounter = null;
    private Text key = new Text();
    private Text value = null;
    int i = 0;
    String line = "";
    @Override
    public void initialize(InputSplit genericSplit, TaskAttemptContext context)
            throws IOException, InterruptedException {

        FileSplit split = (FileSplit) genericSplit;
        Configuration job = context.getConfiguration();
        final Path file = split.getPath();
    /*
	 * The below code contains the logic for opening the file and seek to
	 * the start of the split. Here we are applying the Pdf Parsing logic
	 */
        key.set(split.getPath().getName());
        FileSystem fs = file.getFileSystem(job);
        BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(file)));
        try {
            line=br.readLine();
            while (line != null){
                lines.add(line);
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {

        if (lineCounter == null) {
            lineCounter = new LongWritable();
            lineCounter.set(1);
            value = new Text();
            value.set(lines.get(0));
        } else {
            int temp = (int) lineCounter.get();
            if (temp < (lines.size() - 1)) {
                int count = (int) lineCounter.get();
                value = new Text();
                value.set(lines.get(count));
                count = count + 1;
                lineCounter = new LongWritable(count);
            } else {
                return false;
            }

        }
        if (lineCounter == null || value == null) {
            return false;
        } else {
            return true;
        }

    }

    /*@Override
    public LongWritable getCurrentKey() throws IOException,
            InterruptedException {

        return key;
    }
    */
    @Override
    public Text getCurrentValue() throws IOException, InterruptedException {

        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {

        return 0;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        return key;
    }

}
