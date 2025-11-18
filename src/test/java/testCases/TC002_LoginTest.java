package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups={"Sanity","Master"})
	public void verifyLogin() {
		logger.info("***** Staring TC002_LoginTest *****");
		
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
//			Thread.sleep(1000);
			hp.clickLogin();
			
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(prop.getProperty("email"));
			lp.setPassword(prop.getProperty("password"));
			lp.clickLogin();
			
			MyAccountPage map = new MyAccountPage(driver);
			Assert.assertTrue(map.isMyAccountPageExist());
		}
		catch (Exception e) {
			Assert.fail();
		}
		
		logger.info("***** Finished TC002_LoginTest *****");
	}
	
}
