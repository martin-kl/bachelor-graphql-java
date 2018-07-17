package rest;

import org.springframework.web.client.RestTemplate;
import utils.QueryString;

import java.util.Random;

public class RestClientTest {
    private static final int TESTSIZE = 500;
    private static RestTemplate restTemplate = new RestTemplate();
    private final static String CONNECTIONURL = "http://localhost:8080";

    private static QueryString queryTomHanks = new QueryString("/person/Tom Hanks", "1956");
    private static QueryString queryRiverPhoenix = new QueryString("/person/River Phoenix", "1970");
    private static QueryString queryNancyMeyers = new QueryString("/person/Nancy Meyers", "1949");
    private static QueryString queryAllPersons = new QueryString("/persons", "Bonnie Hunt");

    private static QueryString queryTopGun = new QueryString("/movie/Top Gun", "I feel the need, the need for speed.");
    private static QueryString queryDaVinciCode = new QueryString("/movie/The Da Vinci Code", "Audrey Tautou");
    private static QueryString queryVendetta = new QueryString("/movie/V for Vendetta", "Lilly Wachowski");
    private static QueryString queryAllMovies = new QueryString("/movies", "What Dreams May Come");

    private static QueryString[] queries = {
            queryTomHanks, queryRiverPhoenix, queryNancyMeyers, queryAllPersons,
            queryTopGun, queryDaVinciCode, queryVendetta, queryAllMovies
    };

    public static void main(String[] args) {
        Random rand = new Random();
        long[] avgTimes = new long[TESTSIZE];
        long minTime = Long.MAX_VALUE;
        long maxTime = 0;
        long maxTimeStartingFromSecondQuery = 0;

        long startTime;
        long endTime;
        for (int i = 0; i < TESTSIZE; i++) {
            if (i % 10 == 0) System.out.print(". ");
            int n = rand.nextInt(queries.length);
            startTime = System.nanoTime();
            requestAndVerify(queries[n].getQuery(), queries[n].getVerification(), false); //verification is disable to avoid having an impact on the times, but verification was done beforehand
            endTime = System.nanoTime();
            avgTimes[i] = endTime - startTime;
            if(avgTimes[i] < minTime) minTime = avgTimes[i];
            if(avgTimes[i] > maxTime) maxTime = avgTimes[i];
            if(i != 0 && avgTimes[i] > maxTimeStartingFromSecondQuery) maxTimeStartingFromSecondQuery = avgTimes[i];
        }

        System.out.println("\nAll tests done...");
        double avgTime = avgTimes[0] / 1000000;
        for (int i = 1; i < avgTimes.length; i++) {
            avgTime += avgTimes[i] / 1000000; //convert nanoseconds to milliseconds
        }
        avgTime /= avgTimes.length;
        System.out.println("\nResults:");
        System.out.println("Maximum time: "+maxTime / 1000000.0);
        System.out.println("Maximum time after first query: "+maxTimeStartingFromSecondQuery / 1000000.0);
        System.out.println("Minimum time: "+ minTime / 1000000.0);
        System.out.println("Avg time: "+ avgTime);
    }

    private static void requestAndVerify(String query, String verification, boolean verifyResponse) {
        String result = restTemplate.getForObject(CONNECTIONURL + query, String.class);
        assert !verifyResponse || result.contains(verification);
    }
}
