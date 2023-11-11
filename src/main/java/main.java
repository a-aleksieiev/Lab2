import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class main {
    public ChromeDriver chromeDriver;
    String baseUrl = "https://ek.ua/ua/";

    @BeforeClass(alwaysRun = true)
    public void setUp()
    {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
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
    public void findCatalog()
    {
        WebElement catalog = chromeDriver.findElement(By.xpath("//div[@id=\"mui_user_login_row\"]"));
        catalog.click();
    }
    @Test
    public void enterSearch() {
        WebElement searchInput = chromeDriver.findElement(By.id("ek-search-form"));
        if (searchInput != null) {
            if (searchInput.isDisplayed()) {
                searchInput.click();
                WebElement searchText = chromeDriver.findElement(By.xpath("//input[@id=\"ek-search\"]"));
                searchText.sendKeys("iPhone 12 pro");
                WebElement searchButton = chromeDriver.findElement(By.xpath("//div[@class='header_search_btn-submit']"));
                searchButton.click();
            }
        } else {
            System.out.println("Search field is not present on the page.");
        }
    }

        public void SetProperty ()
        {
            System.setProperty("webdriver.chrome.driver", "D:\\chromeDriver\\chromedriver.exe");
            chromeDriver = new ChromeDriver();
        }

        public void main (String[]args){
            SetProperty();
        }

}

