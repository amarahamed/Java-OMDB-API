package com.example.omdbapi;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

public class APIUtility {

    /* this method will read the file called "jasonData" in the root of the project and create an ApiResponse object
    * */
    public static ApiResponse getMoviesJsonFile() {
        // create a GSON Object
        Gson gson = new Gson();
        ApiResponse response = null;

        try(
                FileReader fileReader = new FileReader("jsonData.json");
                JsonReader jsonReader = new JsonReader(fileReader);
                ) {
            // creates an instance of the APIResponse class
            response = gson.fromJson(jsonReader, ApiResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * This will call the OMDB api with the specified search term
     */
    public static ApiResponse getMovieFromOMDB(String searchTerm) throws IOException, InterruptedException {
        // replace the " " with %20 to handle errors when passed to the browser
        searchTerm = searchTerm.trim().replace(" ", "%20");

        String uri = String.format("https://www.omdbapi.com/?apikey=a4c505fd&s=%s", searchTerm);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(uri)).build();

        /*HttpResponse<Path> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofFile(Paths.get("jsonData.json")));

        return getMoviesJsonFile();*/

        // this approach stores the API response to a string and then creates objects
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        String jsonString = response.body();
        Gson gson = new Gson();
        ApiResponse apiResponse = null;

        try {
             apiResponse = gson.fromJson(jsonString, ApiResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }
}