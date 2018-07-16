package client;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Random;

/**
 * To enable the assertion in the verify method, add the keyword "-ea" to vm options of your IDE
 */
public class ClientTests {

    private static CloseableHttpClient httpClientForGraphql = null;
    private static String connectionUrl = "http://localhost:8080/graphql";
    private final static int TESTSIZE = 1000;

    private static QueryString queryTomHanks = new QueryString("{\"query\": \"{ getPerson(name: \\\"Tom Hanks\\\") { name born } }\" }", "1956");
    private static QueryString queryRiverPhoenix = new QueryString("{\"query\": \"{ getPerson(name: \\\"River Phoenix\\\") { name born } }\" }", "1970");
    private static QueryString queryNancyMeyers = new QueryString("{\"query\": \"{ getPerson(name: \\\"Nancy Meyers\\\") { name born } }\" }", "1949");
    private static QueryString queryAllPersons = new QueryString("{\"query\": \"{ allPersons { name born } }\" }", "Bonnie Hunt");

    private static QueryString queryTopGun = new QueryString("{\"query\": \"{ getMovie(title: \\\"Top Gun\\\") {title tagline cast { name job } } }\" }", "I feel the need, the need for speed.");
    private static QueryString queryDaVinciCode = new QueryString("{\"query\": \"{ getMovie(title: \\\"The Da Vinci Code\\\") {title tagline cast { name job } } }\" }", "Audrey Tautou");
    private static QueryString queryVendetta = new QueryString("{\"query\": \"{ getMovie(title: \\\"V for Vendetta\\\") {title tagline cast { name job } } }\" }", "Lilly Wachowski");
    private static QueryString queryAllMovies = new QueryString("{\"query\": \"{ allMovies { title tagline released } }\" }", "What Dreams May Come");

    private static QueryString[] queries = {
            queryTomHanks, queryRiverPhoenix, queryNancyMeyers, queryAllPersons,
            queryTopGun, queryDaVinciCode, queryVendetta, queryAllMovies
    };

    public static void main(String[] args) {
        httpClientForGraphql = HttpClients.createDefault();
        Random rand = new Random();
        long[] avgTimes = new long[TESTSIZE];
        long minTime = 0;
        long maxTime = 0;

        long startTime;
        long endTime;
        for (int i = 0; i < TESTSIZE; i++) {
            if (i % 10 == 0) System.out.print(". ");
            int n = rand.nextInt(queries.length);
            startTime = System.currentTimeMillis();
            requestAndVerify(queries[n].query, queries[n].verification, false);
            endTime = System.currentTimeMillis();
            avgTimes[i] = endTime - startTime;
            if(avgTimes[i] < minTime) minTime = avgTimes[i];
            else if(avgTimes[i] > maxTime) maxTime = avgTimes[i];
        }
        System.out.println("\nAll tests done...");
        long avgTime = avgTimes[0];
        for (int i = 1; i < avgTimes.length; i++) {
            avgTime += avgTimes[i];
        }
        avgTime /= avgTimes.length;
        System.out.println("\nResults:");
        System.out.println("Maximum time: "+maxTime);
        System.out.println("Minimum time: "+ minTime);
        System.out.println("Avg time: "+ avgTime);
    }


    private static void requestAndVerify(String query, String verifyString, boolean verifyResponse) {
        HttpPost httpPost = new HttpPost(connectionUrl);

        try {
            StringEntity params = new StringEntity(query);

            httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
            httpPost.setEntity(params);
            CloseableHttpResponse httpResponseFromGraphql = httpClientForGraphql.execute(httpPost);

            HttpEntity httpEntity = httpResponseFromGraphql.getEntity();
            if (httpEntity != null) {
                String response = EntityUtils.toString(httpEntity);

                assert !verifyResponse || response.contains(verifyString);

                /*
                System.out.println("query: " + query);
                System.out.println("response: " + response);
                System.out.println("verification: " + response.contains(verifyString));
                System.out.println();
                */
            } else {
                System.err.println("error, httpEntity is null on query=" + query);
                System.exit(0);
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
