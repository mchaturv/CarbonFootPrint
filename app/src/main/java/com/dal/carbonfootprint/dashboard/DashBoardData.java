package com.dal.carbonfootprint.dashboard;

import java.time.Month;
import java.util.Date;
import java.util.Random;

public class DashBoardData {

    public DashBoardData() {
    }

    public float calculateCarbonFootprint(Month month){
        Random r = new Random();
        int day = month.maxLength();
        float result = (float)(day*(r.nextInt((int)((190-0)*10+1))+0*10) / 10.0);
        return result;
    }

    public float calculateCarbonFootprint(int date){
        Random r = new Random();

        float result = (float)((r.nextInt((int)((190-0.1)*10+1))+0.1*10) / 10.0);
        return result;
    }
}
