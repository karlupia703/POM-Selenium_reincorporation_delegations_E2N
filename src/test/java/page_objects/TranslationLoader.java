package page_objects;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Map;

public class TranslationLoader {
  private Map<String, ?> translations;

  public TranslationLoader(String language) {
      try {            // Adjust file path to include language or generic name
          String fileName = "translation.json"; // Adjust if needed
          InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

          if (inputStream == null) {
              throw new RuntimeException("Translation file not found: " + fileName);
          }

         // Load and parse JSON
         ObjectMapper mapper = new ObjectMapper();
         translations = mapper.readValue(inputStream, Map.class);
     } catch (Exception e) {
         throw new RuntimeException("Failed to load translation JSON.", e);
      }
  }
 public Map<String, ?> getTranslations() {
      return translations;
  }
}
