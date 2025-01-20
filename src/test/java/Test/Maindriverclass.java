package Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

public class Maindriverclass {

	private static org.openqa.selenium.By findFirstRow;
	private static final By By = findFirstRow;
	public static WebDriver driver;
    public static String language = "en_US";  //{"Español"},{"en_US"},{"pt_BR"},{"it_IT"} 
    
    @BeforeClass
    public void setupLanguage() {
        System.out.println("Running tests in language: " + language);
        // Set language for test classes
        System.setProperty("test.language", language);
    }
    
    public static void main(String[] args) throws InterruptedException {
        // Your main logic here
        System.out.println("Starting main method...");
        loginTest loginTest = new loginTest(language);
        loginTest.setup();
        loginTest.loginuser(language);
        // Add a sleep if necessary
        Thread.sleep(2000);
        
        createUserTest createUserTest = new createUserTest(language);
        createUserTest.testCreateUser();
        Thread.sleep(2000);
        
        createUserTest.editUser(language, By);
        Thread.sleep(2000);
        
        createUserTest.viewUser();
        Thread.sleep(3000);
        
  //      createUserTest.deleteUser(language);
 //       Thread.sleep(2000);
       
       createUserTest.testcaseOfFilter();
       Thread.sleep(3000);
     
       createUserTest.searchUser1();
       Thread.sleep(3000);
     
       createUserTest.pagination();
       Thread.sleep(3000);
     
       createUserTest.testAlreadyExistUser();
       Thread.sleep(3000);
      
        System.out.println("Main method execution complete.");
    }
         	
    }
