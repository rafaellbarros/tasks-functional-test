package br.com.rafaellbarros.tasks.functional;

import br.com.rafaellbarros.tasks.utils.DataUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TaskTest {

    // TODO: config to get to properties
    private static final String URL_BASE = "http://192.168.1.7";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/home/rafael/env/tools/drivers/chromedriver");
    }

    public WebDriver acessarAplicacao() throws MalformedURLException {
        final DesiredCapabilities cap = DesiredCapabilities.chrome();
        final WebDriver driver =new RemoteWebDriver(new URL(URL_BASE + ":4444/wd/hub"), cap);
        driver.navigate().to(URL_BASE + ":8001/tasks");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException {
        final WebDriver driver = acessarAplicacao();

        try {

            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Teste via selenium");
            final String dataAtual = DataUtil.convertLocalDateToDateStringBR(LocalDate.now());
            driver.findElement(By.id("dueDate")).sendKeys(dataAtual);
            driver.findElement(By.id("saveButton")).click();
            final String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);

        } finally {
            driver.quit();
        }

    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
        final WebDriver driver = acessarAplicacao();

        try {
            // clicar em ADd Todo
            driver.findElement(By.id("addTodo")).click();

            // escrever a data
            final String dataAtual = DataUtil.convertLocalDateToDateStringBR(LocalDate.now());
            driver.findElement(By.id("dueDate")).sendKeys(dataAtual);

            // clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            // validar mensagem de sucesso
            final String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Fill the task description", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
        final WebDriver driver = acessarAplicacao();

        try {
            // clicar em ADd Todo
            driver.findElement(By.id("addTodo")).click();

            //  escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via selenium");

            // clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            // validar mensagem de sucesso
            final String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Fill the due date", message);
        } finally {
            driver.quit();
        }

    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
        final WebDriver driver = acessarAplicacao();

        try {
            // clicar em ADd Todo
            driver.findElement(By.id("addTodo")).click();

            //  escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via selenium");

            // escrever a data
            final String dataOntem = DataUtil.convertLocalDateToDateStringBR(LocalDate.now().minusDays(1));
            driver.findElement(By.id("dueDate")).sendKeys(dataOntem);

            // clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            // validar mensagem de sucesso
            final String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Due date must not be in past", message);
        } finally {
            driver.quit();
        }

    }

    @Test
    public void deveRemoverTarefaComSucesso() throws MalformedURLException {
        final WebDriver driver = acessarAplicacao();

        try {

            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Teste via selenium");
            final String dataAtual = DataUtil.convertLocalDateToDateStringBR(LocalDate.now());
            driver.findElement(By.id("dueDate")).sendKeys(dataAtual);
            driver.findElement(By.id("saveButton")).click();
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);

            driver.findElement(By.xpath("//a[@class='btn btn-outline-danger btn-sm']")).click();
            message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);

        } finally {
            driver.quit();
        }

    }

}
