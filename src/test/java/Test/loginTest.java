package Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.By;



import Pages.loginPages;

public class loginTest {
    WebDriver driver;
    loginPages page;
    public String language;
	private Map<String, String> languageMap = new HashMap<>(); 
	
	@BeforeTest
    public void setup() {
        driver = DriverManager.getDriver(); // Use shared driver
        driver.get("http://localhost:3000/"); //reinstatement-responsibles
        page = new loginPages(driver); // Initialize the page object
    }
	
	Map<String, Map<String, String>> translations = Map.of(
	        "en_US", Map.of(
	            "title", "Reincorporation Delegations",
	            "button", "Sign in with Google",
	            "accessWithGoogle", "Access to reincorporation delegations"
	        ),
	        "Español", Map.of(
	            "title", "Responsables de Reincorporación",
	            "button", "Accede con Google", //
	            "accessWithGoogle", "Acceso a delegaciones de reincorporación"
	        )
	    );

    public loginTest(String language) {
        this.language = language;
    }
   
//    @BeforeTest
//    public void setup() {
//        driver = DriverManager.getDriver(); // Use shared driver
//        driver.get("http://localhost:3000/reinstatement-responsibles");
//        page = new loginPages(driver); // Initialize the page object
//    }
   
  @DataProvider(name = "languageProvider")
  public Object[][] languageProvider() {
      return new Object[][] {
       {"en_US"}, 
  //     {"pt_BR"}, 
  //     {"en_US"}
  //     {"es_ES"}
  //     {"Español"}
      };
  }
  
  @Test(priority = 1, enabled = true, dataProvider = "languageProvider")
  public void loginuser(String targetLanguage) throws InterruptedException {
      // Fetch the translations for the target language
      Map<String, String> expectedTexts = translations.get(targetLanguage);

      String displayedLanguage = languageMap.getOrDefault(targetLanguage, targetLanguage);
      String currentLanguage = page.getSelectedLanguage(); // Get the current language displayed
      System.out.println("Current language is: " + currentLanguage); // Debug

      // Normalize the language string
      String normalizedLanguage = currentLanguage.replaceAll("\\(.*?\\)", "").trim();
      System.out.println("Normalized language: " + normalizedLanguage); // Debug

      // Check if the current language matches the target language
      if (normalizedLanguage.equalsIgnoreCase(targetLanguage)) { 
          System.out.println("Condition met: Current language is " + targetLanguage + ". Executing login flow.");
          
          // Assertions
          if (targetLanguage.equals("Español")) {
              Assert.assertTrue(page.isTitleCorrect(expectedTexts.get("title")), "Title text does not match for Español.");
              Assert.assertTrue(page.isGoogleButtonCorrect(expectedTexts.get("button")), "Google button text does not match for Español.");
              Assert.assertTrue(page.isAccessWithGoogleTextCorrect(expectedTexts.get("accessWithGoogle")),"Access with Google text does not match for Español.");
              System.out.println("Inicio de sesión exitosa");
          } 

          page.clickOnGoogleField();
          handleGoogleLogin();
      } else {
          System.out.println("Current language is not " + displayedLanguage + ". Switching to target language.");
          page.clickonLanguageField();
          page.selectLanguage(targetLanguage); // Select the target language
          Thread.sleep(2000); // Wait for UI to reflect the change
          
          String updatedLanguage = page.getSelectedLanguage(); // Fetch updated language
          System.out.println("Language after selection: " + updatedLanguage); // Debug
          
          // Check for specific language assertions after switching
          if (targetLanguage.equals("en_US")) {
              Assert.assertTrue(page.isTitleCorrect(expectedTexts.get("title")), "Title text does not match for en_US after switching.");
              Assert.assertTrue(page.isGoogleButtonCorrect(expectedTexts.get("button")), "Google button text does not match for en_US after switching.");
              Assert.assertTrue(page.isAccessWithGoogleTextCorrect(expectedTexts.get("accessWithGoogle")), "Access with Google text does not match for en_US after switching.");
              System.out.println("login Successful");
          }
          
          page.clickOnGoogleField();
          handleGoogleLogin();
      }
  }

  // Helper method to handle Google login
  public void handleGoogleLogin() throws InterruptedException {
      String originalWindow = driver.getWindowHandle(); // Store the current window

      // Wait for the new window to open
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increase timeout
      wait.until(driver -> driver.getWindowHandles().size() > 1);

      // Switch to the new window
      Set<String> allWindows = driver.getWindowHandles();
      for (String window : allWindows) {
          if (!window.equals(originalWindow)) {
              driver.switchTo().window(window);
              break;
          }
      }

   // Perform Google login actions
    Thread.sleep(3000);
    page.enterEmailField("zenmonk-developer-zenmonk@zenmonk.tech"); //zenmonk-developer-zenmonk@zenmonk.tech
    Thread.sleep(2000);
    page.clickOnEmailNextBtn();
    Thread.sleep(3000);
    page.enterPasswordField("a2fiEz5Sx2LzRiJ"); //a2fiEz5Sx2LzRiJ
    Thread.sleep(2000);
    page.clickOnPasswordNextBtn();
    Thread.sleep(3000);
    
     //Switch back to the original window
      driver.switchTo().window(originalWindow);
      Thread.sleep(3000);
  }
} 
    