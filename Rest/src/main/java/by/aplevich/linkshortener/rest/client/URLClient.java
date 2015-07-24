package by.aplevich.linkshortener.rest.client;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class URLClient {

    protected static final Client CLIENT = Client.create();

    private static final Gson GSON = new Gson();

    private static final String POST_NEW_URL =  "/rest/postUrl";

    private static final String GET_URL = "/";

    private static final String GET_STAT = "/rest/statistic/";

    private static final String LOCAL_REST_URL = "http://localhost:8081";

    public static String postUrlToRest(String fullUrl){
        return post(LOCAL_REST_URL + POST_NEW_URL, fullUrl);
    }

    public static String getFullUrl(String shortUrl){

        WebResource webResource = CLIENT
                .resource(LOCAL_REST_URL + GET_URL + shortUrl);

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        String output = response.getEntity(String.class);
        return output;
    }

//    public static List<URLData.DataStat> getStat(String shortUrl){
//
//        WebResource webResource = CLIENT
//                .resource(RESTSettings.getRestUrl() + GET_STAT + shortUrl);
//
//        ClientResponse response = webResource.accept("application/json")
//                .get(ClientResponse.class);
//
//        if (response.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : "
//                    + response.getStatus());
//        }
//
//        String output = response.getEntity(String.class);
//        return Arrays.asList((URLData.DataStat[])GSON.fromJson(output, URLData.DataStat[].class));
//    }

    private static String post(String url, String fullUrl){
        WebResource webResource = CLIENT
                .resource(url);

        ClientResponse response = webResource
                .post(ClientResponse.class, fullUrl);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        System.out.println("Output from Server .... \n");
        String output = response.getEntity(String.class);
        System.out.println(output);
        return output;
    }
}
