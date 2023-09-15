package org.search_engine.utils;

import com.google.gson.Gson;
import static org.search_engine.constant.GoogleAPIsConst.*;
import org.search_engine.model.http.ResponseItem;
import org.search_engine.model.http.SearchResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * UTILITY FOR GOOGLE_API
 * */
public class GoogleAPIUtils {

    // for converting json to java objects, and vice versa
    private final Gson gson = new Gson();

    public String search(String query){

        SearchResult searchResult = null;
        StringBuilder response = new StringBuilder();

        // search url
        String url = String.format("%s?key=%s&q=%s&cx=%s", URL, API_KEY, query, SEARCH_ENGINE);

        System.out.println(url);

        try {
            // Create a URL object from the specified URL
            URL requestUrl = new URL(url);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

            // Set the request method (GET by default)
            connection.setRequestMethod("GET");

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // interpreting the response to java object
            searchResult = gson.fromJson(response.toString(), SearchResult.class);

            // get only top three result
            List<ResponseItem> topThreeSearchResult = searchResult.getItems().subList(0, 3);
            searchResult.setItems(topThreeSearchResult);

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // convert java object to json
        return gson.toJson(searchResult);
    }


}
