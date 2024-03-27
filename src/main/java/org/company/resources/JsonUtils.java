package org.company.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class JsonUtils {

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(filePath), "UTF-8");

        // convert json string to java object
        ObjectMapper objectMapper = new ObjectMapper();

        // returns list of hashmaps (map, map1)
        return objectMapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
    }
}
