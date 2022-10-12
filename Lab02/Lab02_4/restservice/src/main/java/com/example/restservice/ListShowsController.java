package com.example.restservice;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListShowsController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("api/shows")
    public List<Show> shows() {
        List<Show> shows = new ArrayList<>();
        try {

            URL url = new URL("https://movie-quote-api.herokuapp.com/v1/shows/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                    //System.out.println(inline);
                }

                //Close the scanner
                scanner.close();

                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONArray data_arr = (JSONArray) parse.parse(inline);
                for (int i = 0; i < data_arr.size(); i++) {

                    JSONObject new_obj = (JSONObject) data_arr.get(i);
                    Show newshow = new Show(counter.incrementAndGet(),new_obj.get("name").toString(),new_obj.get("slug").toString());
                    //System.out.println(new_obj.get("name"));
                    //System.out.println(new_obj.get("slug"));
                    shows.add(newshow);
                }
                return shows;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shows;
    }



}
