package com.sdcp.assignment2;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DistrictContributionAnalysisMapper  extends
        Mapper<Text, Text, Text, FloatWritable> {
    private Text word = new Text();
    private FloatWritable val = new FloatWritable();
    Logger logger = Logger.getLogger(DistrictContributionAnalysisMapper.class);

    String[] districts  = {"COLOMBO","GAMPAHA","KALUTARA","KANDY", "MATALE", "NUWARA ELIYA", "GALLE", "MATARA", "HAMBANTOTA",
            "JAFFNA", "KILINOCHCHI", "MANNAR", "VAVUNIYA", "MULLAITIVU", "BATTICALOA", "AMPARA", "TRINCOMALEE", "KURUNEGALA",
            "PUTTALAM", "ANURADHAPURA", "POLONNARUWA", "BADULLA", "MONARAGALA", "RATNAPURA", "KEGALLE"};

    String[] cereals = {"KURAKKAN","MAIZE","SORGHUM","MENERI","GREEN GRAM.COWPEA","SOYA BEANS","BLACK GRAM","GINGELLY",
            "GROUND NUTS","MANIOC","SWEET POTATOES","POTATOES","RED ONIONS","BIG ONIONS","CHILLIES (GREEN)","MUSTERD",
            "CIGAR. TOBACCO","BEEDI/CIGAR TOBA","LUFFA","BANDAKKA","BRINJALS","BITTER GOURD","SNAKE GOURD","TOMATOES",
            "CUCUMBER","CABBAGE","CARROT","KNOLKHOL","BEETROOT","RADDISH","BEANS","LEEKS","ASH PUMPKIN","RED PUMPKIN",
            "ASH PLANTAIN","CAPSICUM","CINNAMON","COFFEE","COCOA","PEPPER","CARDAMOMS","CLOVES","ARECANUT","CASHEW",
            "ORANGES"};

    public void map(Text key, Text value, Context context) throws IOException, InterruptedException
    {

        try{
            String[] lineItems = value.toString().replaceAll("(?<=\\d)\\,(?=\\d+\\s\\\")","")
                    .replace("\"", "")
                    .split(",");
            for(int j=0; j< districts.length; j++ ) {
                //To skip the header line
                if (lineItems != null && lineItems.length > 0 && !lineItems[0].trim().equals("DISTRICT")) {
                    for (int i = 0; i < cereals.length; i++) {
                        if (2 * i + 2 < lineItems.length) {
                            float production = lineItems[2 * i + 2] == null || lineItems[2 * i + 2].
                                    trim().replaceAll("[\\D]", "").isEmpty() ? 0 : Float.parseFloat(lineItems[2 * i + 2]);
                            word.set(districts[i]);
                            val.set(production);
                            context.write(word, val);
                        }
                    }
                }
            }
        }
        catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }

}
