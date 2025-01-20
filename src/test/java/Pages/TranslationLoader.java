package Pages;


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




//correct
//package Pagess;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
//public class TranslationLoader {
//
//  public Map<String, Object> translations;
//
//  public TranslationLoader(String filePath) {
//      try {
//          // Create an ObjectMapper instance
//          ObjectMapper objectMapper = new ObjectMapper();
//
//          // Read the JSON file into a Map
//          translations = objectMapper.readValue(new File(filePath), Map.class);
//      } catch (IOException e) {
//          e.printStackTrace();
//          throw new RuntimeException("Failed to load translation JSON.");
//      }
//  }
//
//  public Map<String, Object> getTranslations() {
//      return translations;
//  }
//
//  public Map<String, Object> getLanguageTranslations(String languageCode) {
//      return (Map<String, Object>) translations.get(languageCode);
//  }
//
//  public static void main(String[] args) {
//      // Load the JSON file
//      TranslationLoader loader = new TranslationLoader("./translation.json");
//
//      // Get all translations
//      Map<String, Object> allTranslations = loader.getTranslations();
//      System.out.println("All Translations: " + allTranslations);
//
//      // Get translations for a specific language
//      Map<String, Object> spanishTranslations = loader.getLanguageTranslations("es_ES");
//      System.out.println("Spanish Translations: " + spanishTranslations);
//
//      // Access a specific key in the Spanish translations
//      Map<String, Object> loginTranslations = (Map<String, Object>) spanishTranslations.get("login");
//      System.out.println("Login Title (Spanish): " + loginTranslations.get("title"));
//  }
//}
