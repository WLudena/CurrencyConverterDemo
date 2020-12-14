package com.home.data;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import com.home.dao.Dao;

public class Data {

    final private static String PATH = "./dataFile.json";
    private Dao dao = new Dao();

    public String[] getCurrencies(){
        File dataFile = new File(PATH);
        int numOfCur = dao.getCurrencies(dataFile).size();
        String[] currencies = new String[numOfCur];

        Iterator it  = dao.getCurrencies(dataFile).iterator();
        int i = 0;
        while(it.hasNext()){
            currencies[i] = it.next().toString();
            i++;
        }
        Arrays.sort(currencies);
        return currencies;
    }

    public double getCurrencyValue(String currency){
        File dataFile = new File(PATH);
        return dao.getCurrencyValue(currency,dataFile);
    }

    public String getLastUpdate(){
        File dataFile = new File(PATH);
        return dao.getLastUpdate(dataFile);
    }

    public void checkFile() {
        File dataFile = new File(PATH);
        if (!dataFile.exists()) {
            dao.writeInitialData(dataFile);
        } else {
            dao.updateData(dataFile);
        }
    }
}
