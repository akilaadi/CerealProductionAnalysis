package com.sdcp.assignment2;

import java.io.Console;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ProductionEffectivenessByDistrictMapper extends
        Mapper<Text, Text, Text, FloatWritable> {
    private Text word = new Text();
    private FloatWritable val = new FloatWritable();
    Logger logger = Logger.getLogger(CerealProductionAnalysisMapper.class);

    //valid item list
    String[] cereals = {"KURAKKAN","MAIZE","SORGHUM","MENERI","GREEN GRAM.COWPEA","SOYA BEANS","BLACK GRAM","GINGELLY",
            "GROUND NUTS","MANIOC","SWEET POTATOES","POTATOES","RED ONIONS","BIG ONIONS","CHILLIES (GREEN)","MUSTERD",
            "CIGAR. TOBACCO","BEEDI/CIGAR TOBA","LUFFA","BANDAKKA","BRINJALS","BITTER GOURD","SNAKE GOURD","TOMATOES",
            "CUCUMBER","CABBAGE","CARROT","KNOLKHOL","BEETROOT","RADDISH","BEANS","LEEKS","ASH PUMPKIN","RED PUMPKIN",
            "ASH PLANTAIN","CAPSICUM","CINNAMON","COFFEE","COCOA","PEPPER","CARDAMOMS","CLOVES","ARECANUT","CASHEW",
            "ORANGES"};

    public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        try{
            String[] lineItems = value.toString().replaceAll("(?<=\\d)\\,(?=\\d+\\s\\\")","")
                    .replace("\"", "")
                    .split(",");
            if(lineItems != null && lineItems.length > 0 && !lineItems[0].trim().equals("DISTRICT")){
                for (int i = 0;i< cereals.length;i++) {
                    if(2*i + 2 < lineItems.length){
                        float area = lineItems[2*i + 1] == null ||  lineItems[2*i + 1]
                                .trim().replaceAll("[\\D]", "")
                                .isEmpty() ? 0 : Float.parseFloat(lineItems[2*i + 1]);
                        float production = lineItems[2*i + 2] == null ||  lineItems[2*i + 2].
                                trim().replaceAll("[\\D]","").isEmpty() ? 0 : Float.parseFloat(lineItems[2*i + 2]);
                        word.set(cereals[i]+"-"+lineItems[0]);
                        val.set(production == 0 || area == 0 ? 0 : production/area);
                        context.write(word,val);
                    }
                }
            }
        }
        catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }
}

