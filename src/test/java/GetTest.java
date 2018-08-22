import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetTest {

    private AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();

    @Test
    @Tag("syntax")
    @DisplayName("should work when required parameter 'expr' is present")
    void testCorrectExpression() throws Exception {

        Response response = asyncHttpClient.prepareGet("http://api.mathjs.org/v4/?expr=2*2").execute().get();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @Tag("syntax")
    @DisplayName("shouldn't work when required parameter 'expr' is absent")
    void testWrongExpression() throws Exception {

        Response response = asyncHttpClient.prepareGet("http://api.mathjs.org/v4/").execute().get();
        assertEquals(response.getStatusCode(), 400);
    }

    @Test
    @Tag("functionality")
    @DisplayName("should sum two numbers")
    void testAddition() throws Exception {

        Response response = asyncHttpClient.prepareGet("http://api.mathjs.org/v4/?expr=2%2B2").execute().get();
        assertEquals(response.getResponseBody(), "4");
    }

    @Test
    @Tag("functionality")
    @DisplayName("should subtract two numbers")
    void testSubtraction() throws Exception {

        Response response = asyncHttpClient.prepareGet("http://api.mathjs.org/v4/?expr=12-5").execute().get();
        assertEquals(response.getResponseBody(), "7");
    }

    @Test
    @Tag("functionality")
    @DisplayName("should multiplication two numbers")
    void testMultiplication() throws Exception {

        Response response = asyncHttpClient.prepareGet("\thttp://api.mathjs.org/v4/?expr=5*5").execute().get();
        assertEquals(response.getResponseBody(), "25");
    }

    @Test
    @Tag("functionality")
    @DisplayName("should divide two numbers")
    void testDivision() throws Exception {

        Response response = asyncHttpClient.prepareGet("http://api.mathjs.org/v4/?expr=24%2F8").execute().get();
        assertEquals(response.getResponseBody(), "3");
    }

    @Test
    @Tag("functionality")
    @DisplayName("should divide and precision two numbers")
    void testPrecision() throws Exception {

        Response response = asyncHttpClient.prepareGet("\thttp://api.mathjs.org/v4/?expr=100%2F6&precision=4").execute().get();
        assertEquals(response.getResponseBody(), "16.67");
    }
}