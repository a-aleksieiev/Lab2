import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class main {
    WebDriver chromeDriver = new ChromeDriver();
    String baseUrl = "https://tv.kyivstar.ua";

    @BeforeClass(alwaysRun = true)
    public void setUp()
    {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-fullscreen");
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(15));
        this.chromeDriver = new ChromeDriver(chromeOptions);
    }
    @BeforeMethod
    public void preconditions()
    {
        chromeDriver.get(baseUrl);
    }
    @AfterClass(alwaysRun = true)
    public void tearDown()
    {
        chromeDriver.quit();
    }
    @Test
    public void testHeader()
    {
        WebElement header = chromeDriver.findElement(By.id("header"));
        Assert.assertNotNull(header);
    }

    public void main(String[] args) {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "D:\\chromeDriver\\chromedriver.exe");
        chromeDriver.get(baseUrl);
    }

}
