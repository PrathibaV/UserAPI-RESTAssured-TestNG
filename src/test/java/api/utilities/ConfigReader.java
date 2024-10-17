package api.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;

    // Initialize properties
    public static Properties initProp() {
        if (prop == null) {
            prop = new Properties();
            try (FileInputStream inputProp = new FileInputStream("src/test/resources/propertyFile.properties")) {
                prop.load(inputProp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }
}