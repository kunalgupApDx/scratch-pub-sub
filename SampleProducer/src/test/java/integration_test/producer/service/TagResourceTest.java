package integration_test.producer.service;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


/**
 * Created by kunal.gupta on 4/7/14.
 */
public class TagResourceTest {

    private static Client client;

    public static final String SVC_URL = "http://localhost:8080/demo/tag?name=%s";
    public static final String FILEPATH = "/big.txt";

    @BeforeClass
    public void setUp() {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
    }

    @Test(enabled = true)
    public void canCreateOrUpdateTagCount() throws IOException {
        WebResource webResource;
        Scanner sc = new Scanner(new File(this.getClass().getResource(FILEPATH).getFile()));
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] tokens = line.split(" ");
            for(String token : tokens){
                token = token.replaceAll("[^a-zA-Z0-9\\s]","").replaceAll("\\s+","").toLowerCase();
                if(!Strings.isNullOrEmpty(token)) {
                    String url = String.format(SVC_URL, token);
                    System.out.println(url);
                    webResource = client.resource(url);
                    ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
                    String responseXml = response.getEntity(String.class);
                    System.out.println(responseXml);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Assert.assertTrue(responseXml.length() > 0);
                }
            }
        }
    }

}
