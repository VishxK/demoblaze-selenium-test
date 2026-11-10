# DemoBlaze Selenium Automation

Automated UI tests for the [DemoBlaze Product Store](https://www.demoblaze.com/) using Selenium WebDriver, Java, Maven, and TestNG.

The suite covers the main shopping journey: loading the store, selecting a product, adding products to the cart, managing cart contents, and validating checkout behavior.

## Technology Stack

| Component | Version |
| --- | --- |
| Java | 17 or newer |
| Selenium WebDriver | 4.16.1 |
| TestNG | 7.9.0 |
| Maven | 3.8+ recommended |
| Browser | Microsoft Edge |
| WebDriver | `msedgedriver.exe` |

## Test Coverage

| Test case | Scenario | Main validations |
| --- | --- | --- |
| TC01 | Home page smoke test | Page title and `PRODUCT STORE` branding are displayed |
| TC02 | Product selection | Phones category, Samsung Galaxy S6 title, and price |
| TC03 | Add to cart | Product-added JavaScript alert |
| TC04 | Cart management | Two products, Nokia removal, remaining product, and cart total |
| TC05 | Checkout validation | Missing required fields and successful purchase confirmation |

Each test starts with a fresh browser session and navigates to the DemoBlaze home page. Screenshots are saved under `src/test/resources/screenshots/` during execution.

## Prerequisites

1. Install **JDK 17+** and configure `JAVA_HOME`.
2. Install **Maven 3.8+** and ensure `mvn` is available on `PATH`.
3. Install Microsoft Edge.
4. Ensure `msedgedriver.exe` is compatible with the installed Edge version and remains in the project root.
5. Ensure the machine has internet access; the tests run against the public DemoBlaze website.

Verify the Java and Maven installations:

```bash
java -version
mvn -version
```

## Project Structure

```text
demoblaze-selenium/
├── pom.xml
├── testing.xml
├── msedgedriver.exe
└── src/
    └── test/
        ├── java/lk/jiat/demoblaze/
        │   ├── BaseTest.java
        │   └── DemoBlazeTests.java
        └── resources/screenshots/
```

`BaseTest` owns WebDriver setup, explicit waits, browser cleanup, and screenshot capture. `DemoBlazeTests` contains the functional scenarios and assertions. `testing.xml` defines the TestNG suite.

## Running the Tests

### IntelliJ IDEA

1. Open the project as a Maven project.
2. Reload the Maven project and confirm that dependencies resolve.
3. Right-click `testing.xml`.
4. Select **Run 'testing.xml'**.

You can also run an individual test method or the complete `DemoBlazeTests` class from the IntelliJ test runner.

### Maven

From the project root, run:

```bash
mvn test
```

To run a specific test class:

```bash
mvn -Dtest=lk.jiat.demoblaze.DemoBlazeTests test
```

The TestNG suite file used by the IDE is `testing.xml`. If Surefire reports that
`testng.xml` cannot be found, update the suite file name in `pom.xml` to
`testing.xml` before running through Maven.

The browser is launched in a visible, maximized window. Do not minimize or cover the browser while tests are running because screenshots are captured from the desktop using Java AWT.

## Test Artifacts

Screenshots are written to:

```text
src/test/resources/screenshots/
```

Current examples include:

- [Home page](src/test/resources/screenshots/TC01_HomePage.png)
- [Product selection](src/test/resources/screenshots/TC02_Product_Selection.png)
- [Add-to-cart alert](src/test/resources/screenshots/TC03_Add_To_Cart.png)
- [Cart before deletion](src/test/resources/screenshots/TC04_Cart_Initial_Two_Items.png)
- [Cart after deletion](src/test/resources/screenshots/TC04_Cart_After_Deletion.png)
- [Purchase confirmation](src/test/resources/screenshots/TC05_Purchase_Success_Modal.png)

## Configuration Notes

- The base URL and explicit wait timeout are defined in `BaseTest`.
- Checkout data is fictitious test data and must not be replaced with real payment information.
- The test suite depends on the current DemoBlaze page structure, product names, prices, and alert text. Changes to the website may require locator or assertion updates.
- The tests are not isolated from external site state and should be treated as live-site UI tests rather than offline unit tests.

## Troubleshooting

**EdgeDriver cannot be started**

- Confirm that Microsoft Edge is installed.
- Confirm that `msedgedriver.exe` is in the project root.
- Download a driver version compatible with the installed Edge browser.
- Run the tests from the project root so the relative driver path can be resolved.

**Elements or alerts time out**

- Confirm that `https://www.demoblaze.com/` is reachable.
- Check the browser and test output for changes to the DemoBlaze UI.
- Avoid running multiple tests concurrently against the same browser profile or external site.

**Screenshots are missing or incomplete**

- Run the tests on a desktop session rather than a headless environment.
- Keep the browser visible and unobstructed while the suite runs.
- Confirm that the process can write to `src/test/resources/screenshots/`.

## License

This project is intended for educational and test automation purposes. DemoBlaze is an external website owned and operated by its respective provider.
