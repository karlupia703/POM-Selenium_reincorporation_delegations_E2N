
package Test;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.github.javafaker.File;

//import Pages.MessageUtils;
import Pages.TranslationLoader;
import Pages.createUserPages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class createUserTest {
    private static org.openqa.selenium.By test;
	private static final By By = test;
	WebDriver driver;
    Faker faker = new Faker();
 //   private Map<String, ?> t;
    private Map<String, ?> translations;
    public String language;
    private Map<String, String> languageMap = new HashMap<>(); 
    
    Map<String, Map<String, String>> supportedTranslations = Map.of(
	        "en_US", Map.of(		        			                       
	                "firstNameMinError", "First name must be at least 3 characters long",	                
	                "lastNameMinError", "Last name must be at least 3 characters long",
	                "emailRequiredError", "Email is required",
	                "headquarterError", "Please select a headquarter",
	                "title", "Edit Reinstatement Responsible",	                	             	                               
	       	        "body", "Are you sure you want to save the changes?",
	       	        
	       	        "deletealerttitle", "Delete Reinstatement Responsible",
	                "deletebody", "Are you sure to delete the user"              	                
	        ),
	        "Español", Map.of(
	        		
	                "firstNameMinError", "El nombre debe tener al menos 3 caracteres",
	                "lastNameMinError", "El apellido debe tener al menos 3 caracteres",
	                "emailRequiredError", "El correo electrónico es requerido",
	                "headquarterError", "Por favor seleccione una sede",
	                "title", "Editar Responsable de Reincorporación",
	                "body", "¿Está seguro de guardar los cambios?",
	                "deletealerttitle", "Eliminar Responsable de Reincorporación",
	                "deletebody", "¿Está seguro de eliminar al usuario"
	        )
	    );
        public createUserTest(String language) {
    	this.language=language;
 //   	translations = (Map<String, Map<String, String>>) new TranslationLoader(language).getTranslations();
    }


    @Test(priority = 1, enabled = true)
    public void testCreateUser() throws InterruptedException {
        driver = DriverManager.getDriver();
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = generateRandomEmail(firstName, lastName);

        createUser(language,firstName, lastName, email);
    }
  
    void createUser(String targetLanguage, String firstName, String lastName, String email) throws InterruptedException {
        Pages.createUserPages user = new Pages.createUserPages(driver);
        Map<String, String> expectedTexts = supportedTranslations.get(targetLanguage);
 
        user.clickOnCreateButton();
        user.ClickOnInsideCreateButton();
        
        // Assertions for the specified language
        Assert.assertTrue(user.isFirstNameErrorText(expectedTexts.get("firstNameMinError")), "First name error text does not match for " + targetLanguage + ".");
        Assert.assertTrue(user.isLastNameErrorText(expectedTexts.get("lastNameMinError")), "Last name error text does not match for " + targetLanguage + ".");
        Assert.assertTrue(user.isEmailErrorText(expectedTexts.get("emailRequiredError")), "Email text does not match for " + targetLanguage + ".");
        Assert.assertTrue(user.isHeadquarterErrorText(expectedTexts.get("headquarterError")), "Headquarter text does not match for " + targetLanguage + ".");
        System.out.println("Assertions passed for language: " + targetLanguage);
         
        user.enterFirstName(firstName);
        user.enterLastName(lastName);
        user.enterEmail(email);
        user.selectheadquarter();
        user.clickOnSubmitBtn();
        Thread.sleep(1000);
        String successMessage = user.getSuccessMessage();
        System.out.println("Snackbar Text: " + successMessage);
        Thread.sleep(1000);
        System.out.println("User created successfully for language: " + targetLanguage);
    }
        private String generateRandomEmail(String firstName, String lastName) {
      return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
    }
    
    
      
    @Test(priority = 2, enabled = true)
    public void editUser(String targetLanguage, By By) throws InterruptedException {
    	driver = DriverManager.getDriver();
        Faker faker = new Faker();
        String updatedLastName = faker.name().lastName();
        Pages.createUserPages userPage = new Pages.createUserPages(driver);
        Map<String, String> expectedTexts = supportedTranslations.get(targetLanguage);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        userPage.findFirstRow();
        Thread.sleep(2000);
        userPage.clickOnLastNameField(updatedLastName);
        userPage.clickOnEditButton();        
        
        // Assertions for the specified language
        Assert.assertTrue(userPage.isCheckEditCancelAlertTitle(expectedTexts.get("title")), "Edit title does not match for " + targetLanguage + ".");
        
        Assert.assertTrue(userPage.isCheckEditCancelAlertContent(expectedTexts.get("body")), "body not match for " + targetLanguage + ".");
        System.out.println("Edit Assertions passed for language: " + targetLanguage);
        
        userPage.clickOnConfirmDialogBox();
                     
        String successmessage1 = userPage.getSnackbarSuccessMessage();
        System.out.println("Snackbar Text: " + successmessage1);
        Thread.sleep(1000); 
    }   

   
    @Test(priority = 3, enabled = true)
    public void viewUser() throws InterruptedException {
        Pages.createUserPages userPage2 = new Pages.createUserPages(driver);
        
        userPage2.clickOnViewIcon1();
        Thread.sleep(2000);
        
        userPage2.clickOnViewCrossIcon();
        Thread.sleep(2000);
        System.out.println("User view successfully");
    }
 
    
    @Test(priority = 4, enabled = true)
    public void deleteUser(String targetLanguage) throws InterruptedException {
    	driver = DriverManager.getDriver();
        createUserPages userPage3 = new createUserPages(driver);
        Map<String, String> expectedTexts = supportedTranslations.get(targetLanguage);

        // Clear previous notifications
        userPage3.clearPreviousNotifications();

        // Perform delete action
        userPage3.clickDeleteButton();
        Assert.assertTrue(userPage3.isCheckDeleteAlertTitle(expectedTexts.get("deletealerttitle")),"Delete alert title does not match for " + targetLanguage + ".");
        Thread.sleep(1000);
        Assert.assertTrue(userPage3.isCheckDeleteAlertContent(expectedTexts.get("deletebody")), "Body does not match for " + targetLanguage + ".");
        Thread.sleep(1000);
        System.out.println("Delete Assertions passed for language: " + targetLanguage);
        
        userPage3.confirmDeletion();

        // Wait for the correct notification message
        String successmessage3 = userPage3.getNotificationMessage2();
        System.out.println("Snackbar Text: " + successmessage3);
        Thread.sleep(1000);       
    }

        
    @Test(priority = 5, enabled = true)
    public void testcaseOfFilter() throws InterruptedException {
        // Initialize the page object
    	createUserPages userPage5 = new createUserPages(driver);

        // Open the filter dropdown and select multiple options
        userPage5.openFilterDropdown();
        Thread.sleep(1000);
        userPage5.selectOptionAO();
        Thread.sleep(1000);
        userPage5.selectOptionCL();
        Thread.sleep(1000);
        // Close the filter dropdown
        userPage5.closeFilterDropdown();
        Thread.sleep(1000);
        // Clear filters
        userPage5.clearFilters();
        Thread.sleep(1000);
        // Open the Status filter dropdown and select "Inactive" option
        userPage5.openStatusFilterDropdown();
        Thread.sleep(1000);
        userPage5.selectInactiveOption();
        Thread.sleep(1000);
        // Clear filters again
        userPage5.clearFilters();
        Thread.sleep(1000);
    }
 
    
    @Test(priority = 6, enabled = true)
    public void searchUser1() throws InterruptedException {
        // Initialize the page object
        createUserPages userPage = new createUserPages(driver);

        // Locate the first row in the table
        WebElement firstRow = userPage.getFirstRow();

        // Extract UUID from the first row
        String uuid = userPage.extractUuidFromRow(firstRow);

        // Get the user name using the extracted UUID
        String userName = userPage.getUserName(firstRow, uuid);
        System.out.println("Extracted userName: " + userName);
        userPage.searchForUserName(userName);
        userPage.clearSearch();
    }
 
    
    @Test(priority = 7, enabled = true)
    public void pagination() throws InterruptedException {
        // Initialize the page object
        createUserPages userPage = new createUserPages(driver);

        // Check if the right pagination arrow is available and enabled
        if (userPage.isRightArrowAvailable()) {
            if (userPage.isRightArrowEnabled()) {
                System.out.println("Right pagination arrow is enabled.");
                userPage.clickRightArrow(); // Click the right arrow
                Thread.sleep(2000); // Wait after clicking
            } else {
                System.out.println("Right pagination arrow is disabled.");
            }
        } else {
            System.out.println("Right pagination arrow is not available.");
        }

        // Check if the left pagination arrow is available and enabled
        if (userPage.isLeftArrowAvailable()) {
            if (userPage.isLeftArrowEnabled()) {
                System.out.println("Left pagination arrow is enabled.");
                userPage.clickLeftArrow(); // Click the left arrow
                Thread.sleep(2000); // Wait after clicking
            } else {
                System.out.println("Left pagination arrow is disabled.");
            }
        } else {
            System.out.println("Left pagination arrow is not available.");
        }
    }
  
    
    @Test(priority = 8, enabled = true)
    public void testAlreadyExistUser() throws InterruptedException {
    	driver = DriverManager.getDriver();
        createUserPages userPage5 = new createUserPages(driver);

        // Generate unique user data
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress(firstName + lastName);

        // Create the first user
        userPage5.clickCreateButton();
        userPage5.fillUserDetails(firstName, lastName, email);
        userPage5.selectheadquarter1();
        userPage5.toggleStatus();
        userPage5.clickSubmitButton();
        
        // Wait for the correct notification message
        String successmessage3 = userPage5.getCreateUserSuccessMessage();
        System.out.println("Snackbar Text: " + successmessage3);
        Thread.sleep(1000);
        
        // Attempt to create the same user again
        userPage5.clickCreateButton();
        userPage5.fillUserDetails(firstName, lastName, email);
        userPage5.selectheadquarter1();
        userPage5.toggleStatus();
        userPage5.clickSubmitButton();
        
        String successmessage4 = userPage5.getEmailSnackSuccessMessage();
        System.out.println("Snackbar Text: " + successmessage4);
        Thread.sleep(1000);        

        // Handle duplicate user dialog
        userPage5.clickCancelButton();
        userPage5.clickYesButton();       
    }

}
    