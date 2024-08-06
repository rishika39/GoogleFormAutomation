package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Durations;

import java.time.Duration;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.time.LocalTime;
public class TestCases {
    WebDriver driver;
    private Wrappers wrappers;

    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Start test case 01");

        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform?pli=1");

        // Use wrappers to wait for the page to load
        wrappers.waitForPageToLoad();

        // Define XPaths for radio buttons, checkboxes, and dropdown options
        Map<String, By> radioButtons = new HashMap<>();
        radioButtons.put("Automation Testing", By.xpath("//*[@id=\"i13\"]/div[3]"));

        Map<String, By> checkboxes = new HashMap<>();
        checkboxes.put("Java", By.xpath("//*[@id=\"i30\"]"));
        checkboxes.put("Selenium", By.xpath("//div[@aria-label='Selenium']"));
        checkboxes.put("TestNG", By.xpath("//div[@aria-label='TestNG']"));

        By dropdownLocator = By.xpath("//div[@jsname=\"wQNmvb\"][1]");
        //By dropdownOptionsLocator = By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[2]/div[4]");

        // Fill in the first text box
        wrappers.sendKeys(By.xpath("//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[1]/input"), "Crio Learner");
      
        // Current epoch time
        String currentEpochTime = String.valueOf(System.currentTimeMillis() / 1000L);
        String textToEnter = "I want to be the best QA Engineer! " + currentEpochTime;
        wrappers.sendKeys(By.xpath("//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div[2]/textarea"), textToEnter);

        // Select the appropriate radio button
        wrappers.selectRadioButton(radioButtons, "Automation Testing");

        // Select checkboxes
        List<String> selectedCheckboxes = Arrays.asList("Java", "Selenium", "TestNG");
        wrappers.selectCheckboxes(checkboxes, selectedCheckboxes);

        // Open the dropdown and select an option
        wrappers.openDropdown(dropdownLocator);
       Thread.sleep(2000);
        wrappers.selectDropdownOption(By.xpath("(//div[@jsname=\"wQNmvb\"])[9]")); // Select Option1 from dropdown

        // Provide the current date minus 7 days in the date field
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        date.setTime(date.getTime() - 7L * 24 * 60 * 60 * 1000); // Subtract 7 days
        String dateMinus7Days = sdf.format(date);
        wrappers.sendKeys(By.xpath("//input[@type='date']"), dateMinus7Days);

        // Provide the time 07:30
       // wrappers.sendKeys(By.xpath("//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[7]/div/div/div[2]/div/div[1]/div[2]/div[1]/div/div[1]/input"), "07");
        //wrappers.sendKeys(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[7]/div/div/div[2]/div/div[3]/div/div[1]/div/div[1]/input"), "30");

        LocalTime currentTime = LocalTime.now();
        String currentHour = String.format("%02d", currentTime.getHour());
        String currentMinute = String.format("%02d", currentTime.getMinute());

        // Send the current time to the form fields
        wrappers.sendKeys(By.xpath("//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[7]/div/div/div[2]/div/div[1]/div[2]/div[1]/div/div[1]/input"), currentHour);
        wrappers.sendKeys(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[7]/div/div/div[2]/div/div[3]/div/div[1]/div/div[1]/input"), currentMinute);


        // Submit the form
        wrappers.clickElement(By.xpath("//span[contains(text(),'Submit')]"));

        // Wait for the success message and print it
        String successMessage = wrappers.getText(By.xpath("//div[text()='Thanks for your response, Automation Wizard!']"));
        System.out.println("Success Message: " + successMessage);
        System.out.println("End test case 01");
    }

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");
        // Setup ChromeDriver
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wrappers = new Wrappers(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
    }
}
