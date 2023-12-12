package com.example.models.browsers;

import com.example.interfaces.IBrowser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromiumBrowser implements IBrowser {
    @Override
    public  void closeTab() {
        System.out.println(8);
        WebDriver driver = new ChromeDriver(); // Используем ChromeDriver, так как Brave основан на Chromium
        System.out.println(999);
        try {
            System.out.println(9);
            driver.findElement(By.cssSelector("body")).sendKeys("\uE00C"); // Ctrl + W
            System.out.println(99);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        finally {
            driver.quit();
        }
    }

    @Override
    public void closeBrowser() {

    }
}
