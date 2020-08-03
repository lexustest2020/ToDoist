package appiumtests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ToDoistTest1 {

	private String projectId = "0";
	private String myNewProjectName = "My New Test Project";
	private static AppiumDriver<MobileElement> driver;
	private static DesiredCapabilities cap;
	private static WebDriverWait wait;

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
		wait = new WebDriverWait(driver, 10);

		System.out.println("Application started..");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		 driver.quit();
		 System.out.println("Application exited.");
	}

	public class Project {
		private String id;
		private String parentId;
		private String order;
		private String syncId;
		private String name;
		private String color;
		private boolean shared;
		private boolean favorite;
		private boolean inboxProject;
		private boolean teamInbox;
		private String commentCount;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

		public String getOrder() {
			return order;
		}

		public void setOrder(String order) {
			this.order = order;
		}

		public String getSyncId() {
			return syncId;
		}

		public void setSyncId(String syncId) {
			this.syncId = syncId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public boolean isShared() {
			return shared;
		}

		public void setShared(boolean shared) {
			this.shared = shared;
		}

		public boolean isFavorite() {
			return favorite;
		}

		public void setFavorite(boolean favorite) {
			this.favorite = favorite;
		}

		public boolean isInboxProject() {
			return inboxProject;
		}

		public void setInboxProject(boolean inboxProject) {
			this.inboxProject = inboxProject;
		}

		public boolean isTeamInbox() {
			return teamInbox;
		}

		public void setTeamInbox(boolean teamInbox) {
			this.teamInbox = teamInbox;
		}

		public String getCommentCount() {
			return commentCount;
		}

		public void setCommentCount(String commentCount) {
			this.commentCount = commentCount;
		}
	}

	public static String toString(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));
		String output;
		StringBuilder strBuilder = new StringBuilder();

		while ((output = br.readLine()) != null) {
			strBuilder.append(output);
		}

		return strBuilder.toString();
	}

	public static Project toProject(InputStream inputStream) throws JsonSyntaxException, IOException {
		Type type = new TypeToken<Project>() {
		}.getType();
		return new Gson().fromJson(toString(inputStream), type);

	}

	public static List<Project> toProjectList(InputStream inputStream) throws JsonSyntaxException, IOException {
		Type listType = new TypeToken<List<Project>>() {
		}.getType();
		return new Gson().fromJson(toString(inputStream), listType);
	}

	public static byte[] toPostDataBytes(Map<String, Object> params) throws UnsupportedEncodingException {
		StringBuilder postData = new StringBuilder();

		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0) {
				postData.append('&');
			}

			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}

		return postData.toString().getBytes("UTF-8");
	}

	public static String getBaseUrl() {
		return "https://api.todoist.com/rest/v1/projects";
	}

	public static String getLoginToken() {
		return "5a9a60b3f88597a1996926de0e13a804c9d1954b";
	}

	@Test
	public void test1CreateNewProjectViaAPIShouldReturnTrueIfProjectIsCreated() throws Exception {

		URL url = new URL(getBaseUrl());
		// Setup parameters needed
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("token", getLoginToken());
		params.put("name", myNewProjectName);

		byte[] postDataBytes = toPostDataBytes(params);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Lenght", String.valueOf(postDataBytes));
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		// Convert response to Project Model
		Project project = toProject(conn.getInputStream());

		conn.disconnect();

		// Project should not benull
		assertTrue(project != null);
	}

	@Test
	public void test2ShouldReturnTrueIfProjectIsCreated() throws InterruptedException {
		System.out.println("Clicking \"Continue with Google\"..");

		// Clicking google for login
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.todoist:id/btn_google"))).click();

		System.out.println("selecting \"todoisttest2020@gmail.com\" account..");

		// Select todoist account
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//android.widget.TextView[contains(@text, 'todoisttest2020@gmail.com')]"))).click();

		System.out.println("Clicking main menu..");

		// Clicking toolbar
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//android.widget.ImageButton[contains(@content-desc, 'Change the current view')]"))).click();

		boolean isNotClickable = true;
		WebElement projExpandCollapseElementBtn = null;

		while (isNotClickable) {
			try {
				projExpandCollapseElementBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//android.widget.ImageView[contains(@content-desc, 'Expand/collapse')]")));

				System.out.println("Is expand/collapse button visible? "+ projExpandCollapseElementBtn.isDisplayed());
			
				if (projExpandCollapseElementBtn.isDisplayed()) {
					System.out.println("Click Project's mmenu expand/collapse element button..");
					
					projExpandCollapseElementBtn.click();
					
					isNotClickable = false;
				}
				
			} catch (Exception e) {
				System.out.println("Was not able to locate expand/collapse button.");
				
				System.out.println("Re-clicking main menu..");
				
				wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//android.widget.ImageButton[contains(@content-desc, 'Change the current view')]"))).click();
			}
		}
		
		// Search for myNewProjectName if exists in my Todoist
		boolean isExists = isMyProjectExistsInTodoistUI(myNewProjectName);

		System.out.println("isExists: " + isExists);

		assertTrue("\"" + myNewProjectName + "\" does not exists or created.", isExists);
	}

	public boolean isMyProjectExistsInTodoistUI(String myProjectNameToSearch) throws InterruptedException {

		// Let's wait for 10 seconds allowing the emulator to finish loading the UI
		Thread.sleep(3000);

		List<MobileElement> textViewElements = driver.findElements(By.className("android.widget.TextView"));

		// Search for my projects under main menu Projects
		for (MobileElement elements : textViewElements) {

			// Retrieve text value property
			String textValue = elements.getAttribute("text");

			// If null, skip and proceed to the next TextView
			if (textValue == null) {
				continue;
			}

			System.out.println("Project item name: " + textValue);
		
			// Is the text value is equals to the myProjectNameToSearch value?
			if (textValue.equals(myProjectNameToSearch)) {
				System.out.println("\"" + myProjectNameToSearch + "\" found!");
				
				// project item found!
				return true;
			}
		}
		
		return false;
	}

	/// 2. Test “Reopen Task”
	// Login into mobile application and Verify on mobile that project is created
	// Login Via Token Authorization
	// Clicking Menu at ToDoist Hybrid Application: xpath
	/// //android.widget.ImageButton[@content-desc = 'Change the current view']
	// Clicking Arrow down in Projects Menu xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[4]/android.widget.ImageView[1]
	// Clicking "My New ToDoist Test Project" xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[6]/android.widget.TextView[1]
	// Tap + button to Add task xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.ImageButton[1]
	// Input field enter "MyTaskTest" xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.EditText[1]
	// Click Arrow ">" button to Save xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.ImageView[5]
	// Click Arrow ">" button to exit xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.ImageView[5]

	// Execute Get Active Task via API GET:
	// https://api.todoist.com/rest/v1/tasks?token=5a9a60b3f88597a1996926de0e13a804c9d1954b
	// Reopen test task via API. POST:
	// "https://api.todoist.com/rest/v1/tasks/1234/reopen"

	/// Execute Testing ViaMobile to verify test task.
	/// Login into mobile application and Verify on mobile that project is created
	// Login Via Token Authorization
	// Clicking Menu at ToDoist Hybrid Application: xpath
	/// //android.widget.ImageButton[@content-desc = 'Change the current view']
	// Clicking Arrow down in Projects Menu xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[4]/android.widget.ImageView[1]
	// Clicking "My New ToDoist Test Project" xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[6]/android.widget.TextView[1]
	// Verify "MyTaskTest" xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.TextView[1]
	// Check "MyTaskTest" xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.ScrollView[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.widget.EditText[1]
	//
}
