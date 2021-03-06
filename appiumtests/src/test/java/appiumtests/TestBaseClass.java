package appiumtests;

import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import appiumtests.model.Project;
import appiumtests.model.Task;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TestBaseClass {
	public static final String myLoginToken = "5a9a60b3f88597a1996926de0e13a804c9d1954b";

	public static String myProjectName = "My New Test Project";
	public static String myTaskName = "My Task Test";

	public static Project myProject = null;
	public static Task myTask = null;

	public static AppiumDriver<MobileElement> driver;
	public static DesiredCapabilities cap;
	public static WebDriverWait wait;

	@BeforeClass
	public static void setUp() throws Exception {
		cap = new DesiredCapabilities();

		// Setup of my Android Device emulator connected to my Appium.
		cap.setCapability("deviceName", "sdk_gphone_x86");
		cap.setCapability("udid", "emulator-5554");
		cap.setCapability("platformName", "android");
		cap.setCapability("platformVersion", "11");
		cap.setCapability("appPackage", "com.todoist");
		cap.setCapability("appActivity", "com.todoist.activity.HomeActivity");

		URL url = new URL("http://localhost:9090/wd/hub");
		driver = new AppiumDriver<MobileElement>(url, cap);
		wait = new WebDriverWait(driver, 30);

		System.out.println("\nApplication started..\n");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		driver.quit();
		System.out.println("\nApplication exited.\n");
	}
}