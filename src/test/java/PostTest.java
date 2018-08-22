import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostTest {

    private AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();

    @Test
    @Tag("syntax")
    @DisplayName("should work when required parameter 'expr' is present")
    void testCorrectExpression() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode requestNode = mapper.createObjectNode();
        requestNode.put("expr", "");
        Response response = asyncHttpClient.preparePost("http://api.mathjs.org/v4/").setBody(requestNode.toString())
                .execute().get();
        JsonNode responseNode = mapper.readTree(response.getResponseBody());
        String errorStatus = responseNode.get("error").textValue();
        assertEquals(errorStatus, null);
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @Tag("syntax")
    @DisplayName("shouldn't work when required parameter 'expr' is absent")
    void testWrongExpression() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode requestNode = mapper.createObjectNode();
        requestNode.put("error", "");
        Response response = asyncHttpClient.preparePost("http://api.mathjs.org/v4/").setBody(requestNode.toString())
                .execute().get();
        assertEquals(response.getStatusCode(), 400);
    }

    @Test
    @Tag("functionality")
    @DisplayName("should make different basic mathematics operations")
    void testBasicFunctionality() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode requestNode = mapper.createObjectNode();
        ArrayNode tasksNode = mapper.createArrayNode();
        tasksNode.add("7+5");
        tasksNode.add("18-7");
        tasksNode.add("8*8");
        tasksNode.add("72/9");
        tasksNode.add("65/6");
        requestNode.putPOJO("expr", tasksNode);
        requestNode.put("precision", 4);
        Response response = asyncHttpClient.preparePost("http://api.mathjs.org/v4/").setBody(requestNode.toString())
                .execute().get();
        JsonNode responseNode = mapper.readTree(response.getResponseBody());
        JsonNode result = responseNode.get("result");
        assertAll(() -> assertEquals(result.get(0).asInt(), 12),
                  () -> assertEquals(result.get(1).asInt(), 11),
                  () -> assertEquals(result.get(2).asInt(), 64),
                  () -> assertEquals(result.get(3).asInt(), 8),
                  () -> assertEquals(result.get(4).asDouble(), 10.83));
        String errorStatus = responseNode.get("error").textValue();
        assertEquals(errorStatus, null);
    }
}