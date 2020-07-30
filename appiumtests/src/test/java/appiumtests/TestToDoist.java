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
		
		//Setup of my Android Device emulator connected to my Appium.
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
		
		driver.quit();
	}

///Defining and Declaring xpath Support the Functions for Test Scripting ToDoist application.
	
	///1. Create test project via API.
	
	//Execute Create Project via API				POST: https://api.todoist.com/rest/v1/projects?token=5a9a60b3f88597a1996926de0e13a804c9d1954b&name=My New ToDoist Test Project	
	
	///Login into mobile application and Verify on mobile that project is created
	//Login Via Token Authorization
	//Clicking ToDoist Hybrid application:         	xpath //android.widget.TextView[@content-desc = 'Todoist']
	//Clicking Menu at ToDoist Hybrid Application: 	xpath //android.widget.ImageButton[@content-desc = 'Change the current view']
	//Clicking Arrow down in Projects Menu         	xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[4]/android.widget.ImageView[1]
	//Verify Created Project						xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[6]
	
	///LogOut ToDoist Application
	//Clicking Menu at ToDoist Hybrid Application: 	xpath //android.widget.ImageButton[@content-desc = 'Change the current view']
	//Clicking Settings								xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[7]
	//Clicking LogOut Settings						xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.ViewGroup[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.ListView[1]/android.widget.LinearLayout[9]
	//Clicking LogOut Condition "Yes"				xpath ////android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.appcompat.widget.LinearLayoutCompat[1]/android.widget.ScrollView[1]/android.widget.LinearLayout[1]/android.widget.Button[2]
	
	///2. Test “Reopen Task”
	//Login into mobile application and Verify on mobile that project is created
	//Login Via Token Authorization
	//Clicking Menu at ToDoist Hybrid Application: 	xpath //android.widget.ImageButton[@content-desc = 'Change the current view']
	//Clicking Arrow down in Projects Menu         	xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[4]/android.widget.ImageView[1]
	//Clicking "My New ToDoist Test Project"		xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[6]/android.widget.TextView[1]
	//Tap + button to Add task						xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.ImageButton[1]
	//Input field enter "MyTaskTest"				xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.EditText[1]
	//Click Arrow ">" button to Save				xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.ImageView[5]
	//Click Arrow ">" button to exit				xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.ImageView[5]

	//Execute Get Active Task via API 				GET: https://api.todoist.com/rest/v1/tasks?token=5a9a60b3f88597a1996926de0e13a804c9d1954b
	//Reopen test task via API.						POST: "https://api.todoist.com/rest/v1/tasks/1234/reopen"
	
	///Execute Testing ViaMobile to verify test task.
	///Login into mobile application and Verify on mobile that project is created
	//Login Via Token Authorization
	//Clicking Menu at ToDoist Hybrid Application: 	xpath //android.widget.ImageButton[@content-desc = 'Change the current view']
	//Clicking Arrow down in Projects Menu         	xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[4]/android.widget.ImageView[1]
	//Clicking "My New ToDoist Test Project"		xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[6]/android.widget.TextView[1]
	//Verify "MyTaskTest"							xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.TextView[1]
	//Check "MyTaskTest"							xpath //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.ScrollView[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.EditText[1]
    //
}
