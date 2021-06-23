package br.com.rafaellbarros.tasks.prod;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HelthCheckIT {

    // TODO: config to get to properties
    private static final String URL_BASE = "http://192.168.1.7";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/home/rafael/env/tools/drivers/chromedriver");
    }

    @Test
    public void healthCheck() throws MalformedURLException {
        final DesiredCapabilities cap = DesiredCapabilities.chrome();
        final WebDriver driver =new RemoteWebDriver(new URL(URL_BASE + ":4444/wd/hub"), cap);

        try {
            driver.navigate().to(URL_BASE + ":9999/tasks");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            final String version = driver.findElement(By.id("version")).getText();
            Assert.assertTrue(version.startsWith("build"));
        } finally {
            driver.quit();
        }
    }
}
