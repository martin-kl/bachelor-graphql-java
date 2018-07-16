package client;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ClientTests {

    private static CloseableHttpClient httpClientForGraphql = null;
    private static CloseableHttpResponse httpResponseFromGraphql = null;
    private static String connectionUrl = "http://localhost:8080/graphql";

    private static QueryString queryTomHanks = new QueryString("{\"query\": \"{ getPerson(name: \\\"Tom Hanks\\\") { name born } }\" }", "1956");
    private static QueryString queryRiverPhoenix = new QueryString("{\"query\": \"{ getPerson(name: \\\"River Phoenix\\\") { name born } }\" }", "1970");
    private static QueryString queryNancyMeyers = new QueryString("{\"query\": \"{ getPerson(name: \\\"Nancy Meyers\\\") { name born } }\" }", "1949");
    private static QueryString queryAllPersons = new QueryString("{\"query\": \"{ allPersons { name born } }\" }", "Bonnie Hunt");

    private static QueryString queryTopGun = new QueryString("{\"query\": \"{ getMovie(title: \\\"Top Gun\\\") {title tagline cast { name job } } }\" }", "I feel the need, the need for speed.");
    private static QueryString queryDaVinciCode = new QueryString("{\"query\": \"{ getMovie(title: \\\"The Da Vinci Code\\\") {title tagline cast { name job } } }\" }", "2006");
    private static QueryString queryVendetta = new QueryString("{\"query\": \"{ getMovie(title: \\\"V for Vendetta\\\") {title tagline cast { name job } } }\" }", "Lilly Wachowski");
    private static QueryString queryAllMovies = new QueryString("{\"query\": \"{ allMovies { title tagline released } }\" }", "What Dreams May Come");

    private static QueryString[] queries = {
            queryTomHanks, queryRiverPhoenix, queryNancyMeyers, queryAllPersons,
            queryTopGun, queryDaVinciCode, queryVendetta, queryAllMovies
    };

    public static void main(String[] args) {
        httpClientForGraphql = HttpClients.createDefault();

        for(QueryString s : queries) {
            requestAndVerify(s.query, s.verification, true);
        }
    }


    private static void requestAndVerify(String query, String verifyString, boolean verifyResponse) {
        HttpPost httpPost = new HttpPost(connectionUrl);

        try {
            StringEntity params = new StringEntity(query);

            httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
            httpPost.setEntity(params);
            httpResponseFromGraphql = httpClientForGraphql.execute(httpPost);

            HttpEntity httpEntity = httpResponseFromGraphql.getEntity();
            if (httpEntity != null) {
                assert !verifyResponse || EntityUtils.toString(httpEntity).contains(verifyString);
                System.out.println("query: " + query);
                System.out.println("response: " + EntityUtils.toString(httpEntity));
                System.out.println();
            } else {
                System.err.println("error, httpEntity is null on query=" + query);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class QueryString {
        String query;
        String verification;

        QueryString(String query, String verification) {
            this.query = query;
            this.verification = verification;
        }
    }
}
