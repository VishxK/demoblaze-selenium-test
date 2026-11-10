package lk.jiat.demoblaze;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected static final String BASE_URL = "https://www.demoblaze.com/";
    protected static final int EXPLICIT_WAIT_TIMEOUT = 10;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new EdgeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT));

        driver.get(BASE_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void captureScreenshot(String fileName) {
        try {
            Thread.sleep(500);

            File targetFolder = new File("src/test/resources/screenshots");
            if (!targetFolder.exists()) {
                targetFolder.mkdirs();
            }

            Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = new Robot().createScreenCapture(screenSize);

            File destFile = new File(targetFolder, fileName + ".png");
            ImageIO.write(screenFullImage, "png", destFile);
            System.out.println("Robot Screenshot saved with active alert: " + destFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Failed to capture screen with Robot: " + e.getMessage());
        }
    }

}