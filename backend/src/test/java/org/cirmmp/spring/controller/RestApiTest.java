package org.cirmmp.spring.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Date;
/*
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes={ AppConfig.class }
)
*/
/**
 * This tests perform against Apache server run at port 80 and proxy configuration to
 * reverse proxy to backend process
 */
public class RestApiTest {
    @Before
    public void setUp() throws Exception {
HttpUriRequest request =new HttpGet("http://localhost/repositorytest/restcon/user");
HttpResponse response = HttpClientBuilder.create().build().execute(request);
if (!(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) throw new ConnectException("backend service not running");

    }

    @After
    public void tearDown() throws Exception {
    }

    /** Now we will test Rest API via HTTP,
     *  security and access for API
     */
    public static <T> T retrieveResourceFromResponse(HttpResponse response, Class<T> clazz)
            throws IOException {

        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        Gson gson = new Gson();
        return gson.fromJson(jsonFromResponse,clazz);
        //return mapper.readValue(jsonFromResponse, clazz);
    }

    @Test
    public void testLogAsDemoGetUserInfo() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost/repositorytest/restcon/user");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(
                response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK)
        );
        UserDTO resource = retrieveResourceFromResponse(response, UserDTO.class);
        assertThat(
                resource.getFirstName(), equalTo("Demo")
        );
        assertThat(
                resource.getLastName(), equalTo("User")
        );
    }
    @Test
    public void testLogAsDemo2GetUserInfo() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost/repositorytest2/restcon/user");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(
                response.getStatusLine().getStatusCode(),equalTo(HttpStatus.SC_OK)
        );
        UserDTO resource = retrieveResourceFromResponse(response,UserDTO.class);
        assertThat(
                resource.getFirstName(), equalTo("Demo")
        );
        assertThat(
                resource.getLastName(), equalTo("User2")
        );
    }
    @Test
    public void testLogAsDemoListProject() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost/repositorytest/restcon/user");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(
                response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK)
        );
        UserDTO user = retrieveResourceFromResponse(response, UserDTO.class);
        request = new HttpGet("http://localhost/repositorytest/restcon/project");
        response = HttpClientBuilder.create().build().execute(request);
        assertThat(
                response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK)
        );
        ProjectDTO[] resource = retrieveResourceFromResponse(response, ProjectDTO[].class);
        assertTrue(
                resource.length > 0
        );
        for (int i=0;i<resource.length;i++) {
            assertThat(
                    resource[i].getUserId().toString(), equalTo(user.getId())
            );
        }
    }

    @Test
    public void testLogAsDemo2ListProject() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost/repositorytest2/restcon/user");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(
                response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK)
        );
        UserDTO user = retrieveResourceFromResponse(response, UserDTO.class);
        request = new HttpGet("http://localhost/repositorytest2/restcon/project");
        response = HttpClientBuilder.create().build().execute(request);
        assertThat(
                response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK)
        );
        ProjectDTO[] resource = retrieveResourceFromResponse(response, ProjectDTO[].class);
        for (int i=0;i<resource.length;i++) {
            assertThat(
                    resource[i].getUserId().toString(), equalTo(user.getId())
            );
        }
    }

    @Test
    public void testListDatasets() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost/repositorytest/restcon/user");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(
                response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK)
        );
        UserDTO user = retrieveResourceFromResponse(response, UserDTO.class);
        request = new HttpGet("http://localhost/repositorytest/restcon/dataset");
        response = HttpClientBuilder.create().build().execute(request);
        assertThat(
                response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK)
        );
        DatasetDTO[] resource = retrieveResourceFromResponse(response, DatasetDTO[].class);
        //every dataset has name
        /*for (int i=0;i<resource.length;i++) {
            assertTrue(
                    resource[i].getName().length()>0
            );
        }*/
        request = new HttpGet("http://localhost/repositorytest2/restcon/dataset");
        response = HttpClientBuilder.create().build().execute(request);
        assertThat(
                response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK)
        );
        DatasetDTO[] resource2 = retrieveResourceFromResponse(response, DatasetDTO[].class);
        //returned dataset is different
        assertFalse(resource.length==resource2.length );
        //does not contain first dataset from demo user
        for (int i=0;i<resource2.length;i++) {
            assertFalse(
                    resource[0].getId()==resource2[i].getId()
            );
        }

    }

    @Test
    public void testcreateEmptyDatasets() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost/repositorytest/restcon/user");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(
                response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK)
        );
        UserDTO user = retrieveResourceFromResponse(response, UserDTO.class);
        HttpPost request2 = new HttpPost("http://localhost/repositorytest/restcon/dataset");
        request2.setEntity(new StringEntity("{\"info\":\"0b\",\"name\":\"Antidote\",\"projectId\":1}" ));

        response = HttpClientBuilder.create().build().execute(request2);
        assertThat(
                response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK)
        );

        //delete
        DatasetDTO resource = retrieveResourceFromResponse(response, DatasetDTO.class);
        HttpDelete request3 = new HttpDelete("http://localhost/repositorytest/restcon/dataset/"+resource.id);

    }

    @Test
    public void testcreateMetadataInDataset() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost/repositorytest/restcon/user");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(
                response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK)
        );
        UserDTO user = retrieveResourceFromResponse(response, UserDTO.class);
        HttpPost request2 = new HttpPost("http://localhost/repositorytest/restcon/dataset");
        request2.setEntity(new StringEntity("                \"{info\":\"0b\",\"name\":\"Antidote\",\"projectId\":1,\"metadata\":{\"title\":\"Strychnince 42 mg\",\"Data Type\":\"NMR FID\",\"Spectrometer/Dat type\":\"Varian Unix\"}\");\n"));

        response = HttpClientBuilder.create().build().execute(request2);
        assertThat(
                response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK)
        );

        //delete
        DatasetDTO resource = retrieveResourceFromResponse(response, DatasetDTO.class);
        HttpDelete request3 = new HttpDelete("http://localhost/repositorytest/restcon/dataset/"+resource.id);

    }

}
