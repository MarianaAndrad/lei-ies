package com.example.restservice;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class quoteSpecificShowCOntroller {
    @GetMapping("api/quotes")
    public Quote quote(
            @RequestParam(value = "show", required = true) String idshow
    ) {
        ListShowsController listShowsController = new ListShowsController();
        List<Show> shows = listShowsController.shows();
        String slug = "";
        //System.out.println(idshow);

        for (Show show : shows) {
            if (show.getId() == Long.parseLong(idshow)) {
                slug = show.getSlug();
            }
        }
        //System.out.println(slug);
        try {

            URL url = new URL("https://movie-quote-api.herokuapp.com/v1/shows/" + slug + "/");
            //URL url = new URL("https://movie-quote-api.herokuapp.com/v1/shows/mindhunter/");
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
                    System.out.println(inline);
                }

                //Close the scanner
                scanner.close();

                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);

                //Get the required object from the above created object
                /*JSONObject obj = (JSONObject) data_obj.get("Global");*/

                //Get the required data using its key
                System.out.println(data_obj.get("role"));
                System.out.println(data_obj.get("quote"));
                System.out.println(data_obj.get("show"));

                return new Quote(data_obj.get("quote").toString(), data_obj.get("role").toString(), data_obj.get("show").toString(), Boolean.parseBoolean(data_obj.get("contain_adult_lang").toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
