package Utilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Utility for explicit waits using refreshed ExpectedConditions to reduce
 * StaleElementReferenceException occurrences.
 */
public class WaitHelper {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitHelper(WebDriver driver, long timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public WaitHelper(WebDriver driver) {
        this(driver, 15);
    }

    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(locator)));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
    }

    public WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public boolean waitForStaleness(WebElement element, long timeoutSeconds) {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return w.until(ExpectedConditions.stalenessOf(element));
    }

    public boolean waitForTitleContains(String text, long timeoutSeconds) {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return w.until(ExpectedConditions.titleContains(text));
    }
}
