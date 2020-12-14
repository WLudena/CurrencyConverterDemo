package com.home.dao;

//import org.json.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class Dao {

    private static final String ULR_STR = "http://data.fixer.io/api/latest?access_key=27dd1e88327514113f1c8bb939a67745";
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
    private Date date = new Date();

    public void writeInitialData(File f) {
        try {
            FileOutputStream os = new FileOutputStream(f, true);
            //Make request
            URL url = new URL(ULR_STR);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            //Convert to JSON
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(new InputStreamReader((InputStream) request.getContent()));

            root.put("lastupdate", formatter.format(date));
            try (FileWriter file = new FileWriter(f)) {
                file.write(root.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void updateData(File f) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(new FileReader(f.getName()));

            String lastUpdate = obj.get("lastupdate").toString().substring(obj.get("lastupdate").toString().indexOf(" ") + 1);
            String currentTime = formatter.format(date).substring(formatter.format(date).indexOf(" ") + 1);

            int val1 = (Integer.parseInt(lastUpdate.substring(0, 2)) * 60) + Integer.parseInt(lastUpdate.substring(3, 5)); // change time to minutes (00:00 = 0, 23:59 = 1439)
            int val2 = (Integer.parseInt(currentTime.substring(0, 2)) * 60) + Integer.parseInt(currentTime.substring(3, 5));


            System.out.println(val1);
            System.out.println(val2);
            updateFile(obj,f);
//            if (val2 > val1) { // if 60 min/1hr has passed since last update
//                if (val2 - val1 >= 60) {
//                    updateFile(obj, f);
//                } else {
//                    System.out.println("No update needed");
//                }
//
//            } else if (val2 < val1) {
//                if ((1439 - val1) + val2 >= 60) {
//                    updateFile(obj, f);
//                }
//            } else {
//                System.out.println("No update needed");
//            }

            JSONObject s = (JSONObject) obj.get("rates");
            Set keys = s.keySet();
            for (Object a : keys) {
                System.out.println(a + ": " + s.get(a));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


    }

    private void updateFile(JSONObject obj, File f) {
        obj.remove("lastupdate");
        obj.remove("rates");
        JSONObject newRates = latestRates(f);
        if (newRates != null) {
            obj.put("lastupdate", formatter.format(date));
            obj.put("rates", newRates);

            try (FileWriter file = new FileWriter(f)) {
                file.write(obj.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject getRates(File f) {
        JSONObject rates = null;
        try{
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(new FileReader(f.getName()));
            rates = (JSONObject) obj.get("rates");
        }catch(IOException | ParseException e){
            e.printStackTrace();
        }
        return rates;
    }

    public Set getCurrencies(File f){
        return getRates(f).keySet();
    }

    public double getCurrencyValue(String curr, File f){
        return Double.parseDouble(getRates(f).get(curr).toString());
    }

    public String getLastUpdate(File f){
        String lastUpdate = "";
        try{
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(new FileReader(f.getName()));

            lastUpdate = obj.get("lastupdate").toString();
        }catch(ParseException | IOException e){
            e.printStackTrace();
        }
        return lastUpdate;
    }

    private JSONObject latestRates(File f) {
        JSONObject rates = null;
        try {
            //Make request
            URL url = new URL(ULR_STR);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            //Convert to JSON
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(new InputStreamReader((InputStream) request.getContent()));

            rates = (JSONObject) root.get("rates");

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return rates;
    }

    public void viewPoints() {
        try {
            JSONParser parser = new JSONParser();
//            JSONObject a = new JSONObject(new FileReader("C:/Users/wilso/Desktop/Projects/currencyConverter/jsonExampleFile.json"));
            JSONObject o = (JSONObject) parser.parse(new FileReader("jsonExampleFile.json"));
//            String date = a.get("date").toString();
            JSONObject values = (JSONObject) o.get("rates");

            Set keys = values.keySet();
            System.out.println(keys.size());
            Iterator it = keys.iterator();

            for (Object s : keys) {
                System.out.println(s + ": " + values.get(s));
            }
            System.out.println("GBP to USD");

            double cur1 = (double) values.get("GBP");
            double cur2 = (double) values.get("UYU");
            double cur3 = (double) values.get("USD");
            System.out.println(cur1);
            System.out.println(cur2);
            System.out.println(cur3);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }


}
