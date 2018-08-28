import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class MortageTest {
    private String loanAmount = "200000";
    private String interestRate = "5";
    private String mortageTerm = "30";
    private String homeValue = "235000";
    private String annualTaxes = "2000";
    private String annualInsurance = "1865";
    private String pmi = "0.52";

    private String expectedPrincipalInterest = "$1,073.64";
    private String expectedLoanToValueRation = "85.11%";
    private String expectedMonthlyPayment = "$1,482";


    private WebDriver driver;

    @Before
    public void initialize() {
        //initialization of a browser
        String projectPath = System.getProperty("user.dir");
        System.setProperty("webdriver.gecko.driver", projectPath+"/webdriver/geckodriver 2");
        driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //navigate to the page
        driver.get("https://www.mortgageloan.com/calculator");
    }

    @Test
    public void mortageVerification() throws Exception{

        //Page 1
        WebElement amountInput = driver.findElement(By.name("calculator_widget[Amount]"));
        WebElement interestInput = driver.findElement(By.name("calculator_widget[Interest]"));
        WebElement lengthInput = driver.findElement(By.name("calculator_widget[Length]"));
        WebElement homeValueInput = driver.findElement(By.name("calculator_widget[HomeValue]"));
        WebElement nextButton = driver.findElement(By.xpath("//li[@class='next']/a"));

        //input the values on page 1
        amountInput.clear();
        amountInput.sendKeys(loanAmount);

        interestInput.clear();
        interestInput.sendKeys(interestRate);

        lengthInput.clear();
        lengthInput.sendKeys(mortageTerm);

        homeValueInput.clear();
        homeValueInput.sendKeys(homeValue);

        //click next
        nextButton.click();


        //Page 2
        WebElement propertyTaxesInput = driver.findElement(By.name("calculator_widget[PropertyTaxes]"));
        WebElement insuranceInput = driver.findElement(By.name("calculator_widget[Insurance]"));
        WebElement pmiInput = driver.findElement(By.name("calculator_widget[PMI]"));
        WebElement showResultsButton = driver.findElement(By.xpath("//li[@class='next finish']/a"));

        //input the values on page 2
        propertyTaxesInput.clear();
        propertyTaxesInput.sendKeys(annualTaxes);

        insuranceInput.clear();
        insuranceInput.sendKeys(annualInsurance);

        pmiInput.clear();
        pmiInput.sendKeys(pmi);

        //click on Show Results
        showResultsButton.click();

        TimeUnit.SECONDS.sleep(5);

        //result screen
        String monthlyPrincipalInterests = driver.findElement(By.xpath("//table[@class='table']//tr[1]/td")).getText();
        String loanToValueRation = driver.findElement(By.xpath("//table[@class='table']//tr[4]/td")).getText();
        String monthlyPayment = driver.findElement(By.xpath("//div[@class='cta-amount']")).getText();

        Assert.assertTrue("Expected monthly principal interest is '"+expectedPrincipalInterest+"', but found "+monthlyPrincipalInterests, expectedPrincipalInterest.equals(monthlyPrincipalInterests) );
        Assert.assertTrue("Expected loan To Value Ration is '"+expectedLoanToValueRation+"', but found "+loanToValueRation, expectedLoanToValueRation.equals(loanToValueRation) );
        Assert.assertTrue("Expected monthly payment is '"+expectedMonthlyPayment+"', but found "+monthlyPayment, expectedMonthlyPayment.equals(monthlyPayment) );

    }

    @After
    public void exit(){
        driver.quit();
    }


}