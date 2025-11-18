package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger;
	public Properties prop;
	
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException {
		
		FileReader file = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");
		
		prop = new Properties();
		prop.load(file);
		
		logger = LogManager.getLogger(this.getClass());
		
		if(prop.getProperty("execution_env").equals("remote")) {
//			String huburl= "http://192.168.0.103:4444/wd/hub";
			String huburl= "http://localhost:4444/wd/hub/";
			
			DesiredCapabilities cap = new DesiredCapabilities();
			if(os.equalsIgnoreCase("windows")) {
				cap.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("mac")) {
				cap.setPlatform(Platform.MAC);
			}
			else if(os.equalsIgnoreCase("linux")) {
				cap.setPlatform(Platform.LINUX);
			}
			else {
				System.out.println("No Matching OS");
				return;
			}
			
			switch(br.toLowerCase()) {
				case "chrome": cap.setBrowserName("chrome"); break;
				case "edge":cap.setBrowserName("MicrosoftEdge");break;
				case "firefox":cap.setBrowserName("firefox");break;
				default: System.out.println("No matching browser");return;
			}
			
			
			
			driver = new RemoteWebDriver(new URL(huburl),cap);
			
		
		}
		
		if(prop.getProperty("execution_env").equals("local")) {
			switch(br.toLowerCase()) {
				case "chrome": driver = new ChromeDriver();break;
				case "edge": driver = new EdgeDriver(); break;
				case "firefox": driver = new FirefoxDriver();break;
				default: System.out.println("Invalid Browse"); return;
			}
		}
		
		
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get(prop.getProperty("appURL"));
		
	}
	
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown() {
		driver.close();
	}
	
	public String randomString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
		
	}
	
	public String randomNumber() {
		String generatedNumber = RandomStringUtils.randomNumeric(5);
		return generatedNumber;
	}
	
	public String randomAlphanumeric() {
		String generatedString = RandomStringUtils.randomAlphabetic(4);
		String generatedNumber = RandomStringUtils.randomNumeric(5);
		return generatedString + "@" + generatedNumber;
	}
	
	public String captureScreen(String tname) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		String timeStamp = df.format(dt);
		
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp+".png";
		File target = new File(targetFilePath);
		src.renameTo(target);
		return targetFilePath;
		
	}

}
