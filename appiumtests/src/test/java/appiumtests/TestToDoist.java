package appiumtests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TestToDoist {
	
	static AppiumDriver<MobileElement> driver;
	
	public static void main(String[] args) {
		try {
			testLaunchToDoist();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void testLaunchToDoist() throws MalformedURLException {
		DesiredCapabilities cp = new DesiredCapabilities();
		
		cp.setCapability("deviceName", "sdk_gphone_x86");
		cp.setCapability("udid", "emulator-5554");
		cp.setCapability("platformName", "android");
		cp.setCapability("platformVersion", "11");
		cp.setCapability("appPackage", "com.todoist");
		cp.setCapability("appActivity", "com.todoist.activity.HomeActivity");
		cp.setCapability("", "");
		
		URL url = new URL("http://localhost:9090/wd/hub");
		driver = new AppiumDriver<MobileElement>(url, cp);
		
		System.out.println("Application Started..");
	}
}
