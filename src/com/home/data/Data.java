package com.home.data;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import com.home.dao.Dao;

public class Data {

    final private static String PATH = "./dataFile.json";
    private Dao dao = new Dao();

    public boolean isKeyValid(String key){
        return dao.isConnectionSuccess(key,new File(PATH));
    }

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
        return dao.getCurrencyValue(currency, new File(PATH));
    }

    public String getLastUpdate(){
        return dao.getLastUpdate(new File(PATH));
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
