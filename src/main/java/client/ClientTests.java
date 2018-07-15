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
    public static void main(String[] args) {
        String query = "{\"query\": \"{ getPerson(name: \\\"Tom Hanks\\\") { name born } }\" }";

        CloseableHttpClient httpClientForGraphql = null;
        CloseableHttpResponse httpResponseFromGraphql = null;

        httpClientForGraphql = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/graphql");

        try {
            StringEntity params = new StringEntity(query);

            httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
            httpPost.setEntity(params);
            httpResponseFromGraphql = httpClientForGraphql.execute(httpPost);

            HttpEntity httpEntity = httpResponseFromGraphql.getEntity();
            System.out.println("query sent: "+query);
            if(httpEntity != null) {
                System.out.println("\nresponse:\n");
                System.out.println(EntityUtils.toString(httpEntity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
