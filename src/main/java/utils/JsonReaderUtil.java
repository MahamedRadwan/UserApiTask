package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.Map;

public class JsonReaderUtil {
    private static final String FILE_PATH = "src/test/resources/users.json"; // Path to the JSON file

    public static List<Map<String, String>> readJsonData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<Map<String, String>>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON file", e);
        }
    }
}
