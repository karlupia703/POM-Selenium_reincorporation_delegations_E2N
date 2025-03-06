package page_objects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;


public class loginPages {
    WebDriver driver;

    // Constructor
    public loginPages(WebDriver driver) {
        this.driver = driver;
    }

    // Selectors
    By LanguageField = By.cssSelector("[data-test-id=\"login-language-select\"]");
    //  By outSide = By.tagName("body");
    By GoogleField = By.cssSelector("[data-test-id=\"login-button-text\"]");
    By signInWithGoogleText = By.cssSelector("[data-test-id=\"login-button-text\"]");
  	By Email = By.xpath("//input[@type='email']");
	By emailNextBtn = By.xpath("//span[text()='Next']");
    
    // Add By selectors for the available languages
    By selectedLanguage = By.cssSelector("[data-test-id=\"login-language-select\"]"); // Change this to the correct selector for language displayed text
    By LanguageOption = By.cssSelector("[data-value='en_US'], [data-value='pt_BR'], [data-value='it_IT']"); // Add other languages as required
    
	By Password = By.xpath("//input[@type='password']");
	By passwordNextBtn = By.xpath("//span[text()='Next']");

	//Assertion for login page 
    By Title = By.cssSelector("[data-test-id=\"login-app-title\"]");
    By GoogleButton = By.cssSelector("[data-test-id=\"login-button-text\"]");
    By AccessWithGoogleText = By.cssSelector("[data-test-id=\"login-app-title-sub-text\"]");
	
	
    // Method to click on the language field
    public void clickonLanguageField() {
        driver.findElement(LanguageField).click();
    }
   
    // Method to get the selected language text
//      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      public String getSelectedLanguage() {
      WebElement languageElement = driver.findElement(selectedLanguage);
      return languageElement.getText();
    }
      
    // Method to select language dynamically
     public void selectLanguage(String language) {
        By languageSelector = By.cssSelector("[data-value=\"" + language + "\"]");
        driver.findElement(languageSelector).click();
    }
    
    // Method to click on the Google sign-in button
    public void clickOnGoogleField() {
        driver.findElement(GoogleField).click();
    }
    
    // Method to enter email in the email field
    
    public void enterEmailField(String email1) {
      driver.findElement(Email).sendKeys(email1);
    }

   
     // Method to click on the email Next button
     public void clickOnEmailNextBtn() {
      driver.findElement(emailNextBtn).click();
    }

     // Method to enter password in the password field
     public void enterPasswordField(String password1) {
       WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
       WebElement passwordField = wait1.until(ExpectedConditions.elementToBeClickable(Password));
       passwordField.sendKeys(password1);
     }

    
    // Method to click on the password Next button
    public void clickOnPasswordNextBtn() {
      driver.findElement(passwordNextBtn).click();
    }
  
     //Method of Assertion for login page 
     public String getElementText(By selector) {
      return driver.findElement(selector).getText().trim();
     }
  
     public boolean isTitleCorrect(String expectedTitle) {
      return getElementText(Title).equals(expectedTitle);
     }
 
       public boolean isGoogleButtonCorrect(String expectedButton) {
          return getElementText(GoogleButton).equals(expectedButton);
       }

      public boolean isAccessWithGoogleTextCorrect(String expectedText) {
      return getElementText(AccessWithGoogleText).equals(expectedText);
  }
}

