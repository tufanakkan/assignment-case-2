package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\src\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        boolean result = firstCheckpoint(driver);
        if(result)
            System.out.println("1st checkpoint reached");
        else{
            System.out.println("1st checkpoint failed");
        }

        result = secondCheckpoint(driver);
        if(result)
            System.out.println("2nd checkpoint reached");
        else{
            System.out.println("2nd checkpoint failed");
        }

        result = thirdCheckpoint(driver);
        if(result)
            System.out.println("3rd checkpoint reached");
        else{
            System.out.println("3rd checkpoint failed");
        }

        result = fourthCheckpoint(driver);
        if(result)
            System.out.println("4th checkpoint reached");
        else{
            System.out.println("4th checkpoint failed");
        }

        result = fifthCheckpoint(driver);
        if(result)
            System.out.println("5th checkpoint reached");
        else{
            System.out.println("5th checkpoint failed");
        }
    }


    public static void hover(WebDriver driver, WebElement element){

        Actions action = new Actions(driver);
        action.moveToElement(element).perform();

    }

    public static boolean firstCheckpoint (WebDriver driver){
        driver.get("https://rapsodo.com/");
        WebElement element = driver.findElement(By.xpath("//*[@id=\"menu-item-dropdown-529\"]"));
        hover(driver, element);
        driver.findElement(By.xpath("//*[@id=\"menu-item-6757\"]/a")).click();

        URL url = null;
        try {
            url = new URL(driver.getCurrentUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String er = "category=diamond-sports";

        boolean result = er.equals(url.getQuery());

        String text = driver.findElement(By.xpath("//*[@id=\"content\"]/header/nav/div/div/div[7]/a")).getText();
        er = "0 items - $0.00";

        result = result & er.equals(text);


        return result;
    }

    public static boolean secondCheckpoint (WebDriver driver){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,200)");

        driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/div/div[3]/a")).click();

        URL url = null;
        try {
            url = new URL(driver.getCurrentUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String er = "/product/rapsodo-hitting-monitor/";
        boolean result = er.equals(url.getPath());

        er = "Hitting 2.0 - Rapsodo";
        result = result & er.equals(driver.getTitle());

        er = "RBB01H";
        result =  result & er.equals(driver.findElement(By.className("sku")).getText());

        result = result & driver.findElement(
                By.xpath("//*[@id=\"product-386\"]/div/div[2]/div/form/div/div[2]/div/div[3]/button")).isEnabled();

        return result;

    }

    public static boolean thirdCheckpoint (WebDriver driver) {

        Select dropPlan = new Select(driver.findElement(By.id("stripe_plan_id")));
        dropPlan.selectByVisibleText("No Plan");
        String er = "$4,000.00";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,200)");

        boolean result = er.equals(driver.findElement
                (By.xpath("//*[@id=\"product-386\"]/div/div[2]/div/form/div/div[1]/div[2]/span/span")).getText());


        result = result & driver.findElement
                (By.xpath("//*[@id=\"product-386\"]/div/div[2]/div/form/div/div[2]/div/div[3]/button")).isEnabled();

        return result;
    }

    public static boolean fourthCheckpoint (WebDriver driver) {
        String er = "/cart/";
        boolean result = true;
        if(driver.findElement
                (By.xpath("//*[@id=\"product-386\"]/div/div[2]/div/form/div/div[2]/div/div[3]/button")).isEnabled()) {

            driver.findElement
                    (By.xpath("//*[@id=\"product-386\"]/div/div[2]/div/form/div/div[2]/div/div[3]/button")).click();

            URL url = null;
            try {
                url = new URL(driver.getCurrentUrl());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            result = er.equals(url.getPath());

            er = "1 item - $4,000.00";
            result = result & er.equals(driver.findElement(By.
                    xpath("//*[@id=\"content\"]/header/nav/div/div/div[7]/a"))
                    .getText());

        }
        return result;
    }

    public static boolean fifthCheckpoint (WebDriver driver) {
        String random = "45624862";
        WebElement coupon_field = driver.findElement(By.name("coupon_code"));
        WebElement button = driver
                .findElement(By.xpath("//*[@id=\"post-368\"]/div/div/form/div[2]/table/tbody/tr[2]/td/div/div/button"));
        coupon_field.sendKeys(random);
        button.click();

        String er = "Coupon \"" + random + "\" does not exist!";
        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"post-368\"]/div/div/div[1]/div"));
        boolean result = er.equals(errorMessage.getText());

        return result;
    }

}
