package testCases;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{
	
	
	@Test(groups={"Regression", "Master"})
	public void verify_account_registration() {
		logger.info("**** Starting TC001_AccountRegistrationTest ****");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on my Account link");
			hp.clickRegister();
			logger.info("Clicked on my Register link");
			
			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
			logger.info("Providing Customer details");
			regpage.setFirstName(randomString().toUpperCase());
			regpage.setLastName(randomString().toUpperCase());
			regpage.setEmail(randomString() +  "@gmail.com");
			regpage.setTelephone(randomNumber());
			
			String pwd = randomAlphanumeric();
			regpage.setPassword(pwd);
			regpage.setConfirmPassword(pwd);
			regpage.setPrivacyPolicy();
			regpage.clickContinue();
			
			logger.info("Validating expected message");
			String confMsg = regpage.getConfirmationMsg();
			if(confMsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			}
			else {
				logger.error("Test Failed");
				logger.debug("Debug logs");
				Assert.assertTrue(false);
			}
		}
		catch (Exception e) {
			
			Assert.fail();
		}
		logger.info("**** Finished TC001_AccountRegistrationTest ****");
	}
	
	

}
