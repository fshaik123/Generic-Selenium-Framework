package org.company.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils extends JsonUtils {

    public Properties readPropertiesFile(String filePath) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(filePath);
        prop.load(fis);
        return prop;
    }
}
