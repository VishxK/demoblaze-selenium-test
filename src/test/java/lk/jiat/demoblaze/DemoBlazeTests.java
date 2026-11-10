package lk.jiat.demoblaze;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class DemoBlazeTests extends BaseTest {

    private static final String TEST_NAME = "Test Student";
    private static final String TEST_COUNTRY = "Sri Lanka";
    private static final String TEST_CITY = "Colombo";
    private static final String TEST_CARD = "4111111111111111";
    private static final String TEST_MONTH = "12";
    private static final String TEST_YEAR = "2027";

    /**
     * TC01: Home Page Smoke Test
     * Verify a non-empty title and displayed PRODUCT STORE heading.
     */
    @Test(priority = 1)
    public void testHomePageSmoke() {
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Page title is null.");
        Assert.assertFalse(title.trim().isEmpty(), "Page title is empty.");

        WebElement brandHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nava"))
        );

        Assert.assertTrue(brandHeading.isDisplayed(), "PRODUCT STORE brand heading is not displayed.");
        Assert.assertTrue(brandHeading.getText().contains("PRODUCT STORE"), "Brand text mismatch.");

        captureScreenshot("TC01_HomePage");
    }

    /**
     * TC02: Product Selection
     * Open Phones, select Samsung galaxy s6, verify its heading, and print its price.
     */
    @Test(priority = 2)
    public void testProductSelection() {
        WebElement phonesCategory = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Phones')]"))
        );
        phonesCategory.click();

        WebElement samsungLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Samsung galaxy s6"))
        );
        samsungLink.click();

        WebElement productTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='name']"))
        );
        Assert.assertEquals(productTitle.getText(), "Samsung galaxy s6", "Product title does not match.");

        WebElement productPrice = driver.findElement(By.xpath("//h3[@class='price-container']"));
        String priceText = productPrice.getText();
        Assert.assertTrue(priceText.contains("$360"), "Product price mismatch.");

        System.out.println("Selected Product Price: " + priceText);

        captureScreenshot("TC02_Product_Selection");

    }

    /**
     * TC03: Add to Cart
     * Add Samsung galaxy s6, wait for the JavaScript alert, print its text, and accept it.
     */
    @Test(priority = 3)
    public void testAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Samsung galaxy s6"))).click();

        WebElement addToCartBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']"))
        );
        addToCartBtn.click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        System.out.println("JavaScript Alert Text: " + alertText);
        captureScreenshot("TC03_Add_To_Cart");


        Assert.assertEquals(alertText, "Product added", "Alert text mismatch.");
        alert.accept();

    }

    /**
     * TC04: Cart Management
     * Add Samsung galaxy s6 and Nokia lumia 1520, count and print cart rows,
     * remove Nokia, verify one row remains, verify Samsung remains, and print total.
     */
    @Test(priority = 4)
    public void testCartManagement() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Samsung galaxy s6"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']"))).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();

        driver.findElement(By.id("nava")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Nokia lumia 1520"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']"))).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();

        driver.findElement(By.id("cartur")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@id='tbodyid']/tr")));

        List<WebElement> cartRows = driver.findElements(By.xpath("//tbody[@id='tbodyid']/tr"));
        System.out.println("Initial Cart Row Count: " + cartRows.size());
        Assert.assertEquals(cartRows.size(), 2, "Cart row count should be 2.");

        List<WebElement> productTitles = driver.findElements(By.xpath("//tbody[@id='tbodyid']/tr/td[2]"));
        List<WebElement> productPrices = driver.findElements(By.xpath("//tbody[@id='tbodyid']/tr/td[3]"));
        for (int i = 0; i < productTitles.size(); i++) {
            System.out.println("Cart Item: " + productTitles.get(i).getText() + " | Price: " + productPrices.get(i).getText());
        }

        captureScreenshot("TC04_Cart_Initial_Two_Items");

        WebElement deleteNokiaBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//td[text()='Nokia lumia 1520']/following-sibling::td/a[text()='Delete']"))
        );
        deleteNokiaBtn.click();

        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//tbody[@id='tbodyid']/tr"), 1));

        List<WebElement> remainingRows = driver.findElements(By.xpath("//tbody[@id='tbodyid']/tr"));
        Assert.assertEquals(remainingRows.size(), 1, "Cart row count after deletion should be 1.");

        WebElement remainingProduct = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@id='tbodyid']/tr/td[2]"))
        );
        Assert.assertEquals(remainingProduct.getText(), "Samsung galaxy s6", "Remaining product is incorrect.");

        WebElement totalElement = driver.findElement(By.id("totalp"));
        System.out.println("Final Cart Total: $" + totalElement.getText());

        captureScreenshot("TC04_Cart_After_Deletion");
    }

    /**
     * TC05: Checkout Validation
     * Verify invalid result for missing details, then submit using fictitious data and verify purchase-success message.
     */
    @Test(priority = 5)
    public void testCheckoutValidation() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Samsung galaxy s6"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']"))).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();

        driver.findElement(By.id("cartur")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@id='tbodyid']/tr")));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModalLabel")));

        driver.findElement(By.xpath("//button[text()='Purchase']")).click();

        Alert invalidAlert = wait.until(ExpectedConditions.alertIsPresent());
        String validationText = invalidAlert.getText();
        System.out.println("Validation Alert Text: " + validationText);
        Assert.assertTrue(validationText.contains("Please fill out Name and Creditcard."), "Invalid checkout alert mismatch.");
        invalidAlert.accept();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(TEST_NAME);
        driver.findElement(By.id("country")).sendKeys(TEST_COUNTRY);
        driver.findElement(By.id("city")).sendKeys(TEST_CITY);
        driver.findElement(By.id("card")).sendKeys(TEST_CARD);
        driver.findElement(By.id("month")).sendKeys(TEST_MONTH);
        driver.findElement(By.id("year")).sendKeys(TEST_YEAR);

        driver.findElement(By.xpath("//button[text()='Purchase']")).click();

        WebElement successHeader = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Thank you for your purchase!']"))
        );
        Assert.assertTrue(successHeader.isDisplayed(), "Purchase success header was not displayed.");

        WebElement confirmDetails = driver.findElement(By.xpath("//p[contains(@class,'text-muted')]"));
        Assert.assertTrue(confirmDetails.getText().contains(TEST_NAME), "Confirmation modal does not contain buyer name.");

        captureScreenshot("TC05_Purchase_Success_Modal");

        driver.findElement(By.xpath("//button[contains(@class,'confirm')]")).click();
    }
}