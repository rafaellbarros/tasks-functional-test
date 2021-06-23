package br.com.rafaellbarros.tasks.functional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TaskTest {


    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/home/rafael/env/tools/drivers/chromedriver");
    }

    public WebDriver acessarAplicacao() {
        final WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://localhost:8001/tasks");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() {
        final WebDriver driver = acessarAplicacao();

        try {
            // clicar em ADd Todo
            driver.findElement(By.id("addTodo")).click();

            //  escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via selenium");

            // escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("23/06/2021");

            // clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            // validar mensagem de sucesso
            final String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Success!", message);
        } finally {
            driver.quit();
        }

    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() {
        final WebDriver driver = acessarAplicacao();

        try {
            // clicar em ADd Todo
            driver.findElement(By.id("addTodo")).click();

            // escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("23/06/2021");

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
    public void naoDeveSalvarTarefaSemData() {
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
    public void naoDeveSalvarTarefaComDataPassada() {
        final WebDriver driver = acessarAplicacao();

        try {
            // clicar em ADd Todo
            driver.findElement(By.id("addTodo")).click();

            //  escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via selenium");

            // escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("23/06/2010");

            // clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            // validar mensagem de sucesso
            final String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Due date must not be in past", message);
        } finally {
            driver.quit();
        }

    }

}
