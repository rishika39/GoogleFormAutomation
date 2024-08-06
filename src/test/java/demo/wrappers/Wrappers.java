package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class Wrappers {
    private WebDriver driver;
    private WebDriverWait wait;

    public Wrappers(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

    public void waitForPageToLoad() {
        wait.until(driver -> ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public void sendKeys(By locator, String keysToSend) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(keysToSend);
    }

    public void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    public void selectRadioButton(Map<String, By> radioButtons, String value) {
        By locator = radioButtons.get(value);
        if (locator != null) {
            clickElement(locator);
        } else {
            System.out.println("Radio button not found for value: " + value);
        }
    }

    public void selectCheckboxes(Map<String, By> checkboxes, List<String> values) {
        for (String value : values) {
            By locator = checkboxes.get(value);
            if (locator != null) {
                WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(locator));
                if (!checkbox.isSelected()) {
                    checkbox.click();
                }
            } else {
                System.out.println("Checkbox not found for value: " + value);
            }
        }
    }

    public void openDropdown(By dropdownLocator) {
        clickElement(dropdownLocator);
    }

    public void selectDropdownOption(By optionLocator) {
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();
    }

    public String getText(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }
}
