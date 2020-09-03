import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Automation {
    WebDriver driver;
    @Before
    public void setUp(){

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


    }
//    @After
//    public void tearDown() {
//        driver.quit();
//    }


    @Test
    public void loginSuccess() {
        driver.get("http://localhost:7080/login");

        WebElement userName = driver.findElement(By.id("username"));
        userName.sendKeys("tomsmith");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("SuperSecretPassword!");
        WebElement loginButton = driver.findElement(By.xpath("//i"));
        loginButton.click();

    }

    @Test
    public void loginFailure(){
        driver.get("http://localhost:7080/login");
        WebElement userName = driver.findElement(By.id("username"));
        userName.sendKeys("tomsmith");

        WebElement password = driver.findElement(By.id("p"));
        password.sendKeys("SuperSecretPassword!");
        WebElement loginButton = driver.findElement(By.xpath("//i"));
        loginButton.click();
    }

    @Test
    public void checkBox(){
        driver.get("http://localhost:7080/checkboxes ");
        WebElement checkBox = driver.findElement(By.xpath("//input[@type=\"checkbox\"][1]"));
        checkBox.click();
        checkBox.click();
    }

    @Test
    public void contextMenu(){
        driver.get("http://localhost:7080/context_menu");
        Actions actions = new Actions(driver);
        WebElement box = driver.findElement(By.id("hot-spot"));

        actions.contextClick(box).perform();
    }
    @Test
    public void dragAndDrop(){
        driver.get(" http://localhost:7080/drag_and_drop");
        Actions actions = new Actions(driver);

        WebElement A = driver.findElement(By.id("column-a"));
        WebElement B = driver.findElement(By.id("column-b"));
        actions.dragAndDrop(A,B).build().perform();

    }

    @Test
    public void dropdown(){
        driver.get("http://localhost:7080/dropdown ");
        driver.findElement(By.id("dropdown"));
        Select multipleSelectDropdown = new Select(driver.findElement(By.id("dropdown")));
        List<WebElement> options = multipleSelectDropdown.getOptions();
        for(WebElement option: options){
            option.click();
        }
    }

    @Test
    public void DynamicContent(){
        driver.get("http://localhost:7080/dynamic_content");
        WebElement staticAppend = driver.findElement(By.linkText("click here"));
        staticAppend.click();

    }

    @Test
    public void DynamicControls(){
        driver.get("http://localhost:7080/dynamic_controls");
        WebElement dynamicControlButton = driver.findElement(By.xpath("//button[@onclick=\"swapCheckbox()\"]"));
        dynamicControlButton.click();

        WebElement text = driver.findElement(By.id("message"));
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOf(text));
        Assert.assertEquals(text.getText(),"It's gone!");

    }

    @Test
    public void DynamicLoading(){
        driver.get("http://localhost:7080/dynamic_loading/2");
        WebElement startButton = driver.findElement(By.xpath("//div[@id=\"start\"]/button"));
        startButton.click();

        WebElement text = driver.findElement(By.xpath("//div[@id=\"finish\"]/h4"));
        WebDriverWait wait = new WebDriverWait(driver,15);
        wait.until(ExpectedConditions.visibilityOf(text));
        Assert.assertEquals(text.getText(),"Hello World!");

    }


    @Test
    public void FloatingMenu(){
        driver.get("http://localhost:7080/floating_menu");
        driver.findElement(By.linkText("Home")).click();
        String actualHomeUrl = driver.getCurrentUrl();
        String expectedUrl = "http://localhost:7080/floating_menu#home";

        driver.findElement(By.linkText("News")).click();
        String actualNewsUrl = driver.getCurrentUrl();
        String expectedNewsUrl = "http://localhost:7080/floating_menu#news";

        driver.findElement(By.linkText("Contact")).click();
        String actualContactUrl = driver.getCurrentUrl();
        String expectedContactUrl = "http://localhost:7080/floating_menu#contact";

        driver.findElement(By.linkText("About")).click();
        String actualAboutUrl = driver.getCurrentUrl();
        String expectedAboutUrl = "http://localhost:7080/floating_menu#about";

        Assert.assertEquals(actualHomeUrl,expectedUrl);
        Assert.assertEquals(actualAboutUrl,expectedAboutUrl);
        Assert.assertEquals(actualContactUrl,expectedContactUrl);
        Assert.assertEquals(actualNewsUrl,expectedNewsUrl);


    }

    @Test
    public void Iframe(){
        driver.get("http://localhost:7080/iframe");
        driver.switchTo().frame("mce_0_ifr");
        String actualText = driver.findElement(By.xpath("//p[.='Your content goes here.']")).getText();
        String expectedText = "Your content goes here.";
        Assert.assertEquals(actualText,expectedText);


    }

    @Test
    public void Hover(){

        driver.get("http://localhost:7080/hovers");
        Actions actions = new Actions(driver);
        WebElement image1 = driver.findElement(By.xpath("//div[@class=\"figure\"][1]"));
        actions.moveToElement(image1).perform();

    }

    @Test
    public void JavaScriptAlerts(){
        driver.get("http://localhost:7080/javascript_alerts");
        WebElement clickAlert = driver.findElement(By.xpath("//button[@onclick=\"jsAlert()\"]"));
        clickAlert.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        String  actualSuccessMessage = driver.findElement(By.xpath("//p[.='You successfuly clicked an alert']")).getText();
        String expectedMessage = "You successfuly clicked an alert";
        Assert.assertEquals(actualSuccessMessage,expectedMessage);


    }

    @Test
    public void JavaScriptError(){
        driver.get("http://localhost:7080/javascript_error");
        String actualText = driver.findElement(By.xpath("//p")).getText();
        String expectedText = "This page has a JavaScript error in the onload event. This is often a problem to using normal Javascript injection techniques.";
        Assert.assertEquals(actualText,expectedText);

    }

    @Test
    public void OpeninNewTab(){
        driver.get("http://localhost:7080/windows");
        String mainHandle = driver.getWindowHandle();

        WebElement clickHereLink =  driver.findElement(By.linkText("Click Here"));
        clickHereLink.click();
        for(String handle : driver.getWindowHandles()){
            if(!handle.equals(mainHandle)){
                driver.switchTo().window(handle);
            }
        }

        String expectedUrl = "http://localhost:7080/windows/new";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl, actualUrl);

    }
}
}
