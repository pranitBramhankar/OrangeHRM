package TestUtilities;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader {

    private static JsonNode rootNode;

    static {

        try {

            ObjectMapper mapper = new ObjectMapper();

            rootNode = mapper.readTree(new File("src/test/resources/validationMessages.json"));

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to load JSON file",
                    e);
        }
    }

    /**
     * Example:
     * getValue("TC001.username")
     * getValue("Login.validUser.password")
     */
    public static String getValue(String path) {

        JsonNode node = getNode(path);

        return node.asText();
    }

    /**
     * Returns complete JsonNode
     */
    public static JsonNode getNode(String path) {

        String[] keys = path.split("\\.");

        JsonNode currentNode = rootNode;

        for (String key : keys) {

            currentNode = currentNode.get(key);

            if (currentNode == null) {

                throw new RuntimeException(
                        "Invalid JSON path : " + path);
            }
        }

        return currentNode;
    }

    public static int getIntValue(String path) {

        return getNode(path).asInt();
    }

    public static boolean getBooleanValue(String path) {

        return getNode(path).asBoolean();
    }

    public static double getDoubleValue(String path) {

        return getNode(path).asDouble();
    }
    /*
     * String username = JsonReader.getValue("Login.validUser.username");
     * 
     * */
}