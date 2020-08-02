package appiumtests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
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

import appiumtests.ToDoistTest2.Project;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ToDoistTest2 {
	private String myNewProjectName = "My New Test Project";
	private String myProjectTaskName = "My Task Test";
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
		wait = new WebDriverWait(driver, 30);

		System.out.println("Application started..");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		// driver.quit();
		// System.out.println("Application exited.");
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

	public int projectId = 0;

	public static String getBaseUrl() {
		return "https://api.todoist.com/rest/v1/projects";
	}

	public static String getLoginToken() {
		return "5a9a60b3f88597a1996926de0e13a804c9d1954b";
	}

	@Test
	public void test1OpenMobileApplication() {
		System.out.println("Clicking \"Continue with Google\"..");

		// Clicking google for login
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.todoist:id/btn_google"))).click();

		System.out.println("selecting \"todoisttest2020@gmail.com\" account..");

		// Select ToDoist account
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//android.widget.TextView[contains(@text, 'todoisttest2020@gmail.com')]"))).click();

	}

	@Test
	public void test2OpenTestProject() throws InterruptedException {
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

				System.out.println("Is expand/collapse button visible? " + projExpandCollapseElementBtn.isDisplayed());

				if (projExpandCollapseElementBtn.isDisplayed()) {
					System.out.println("Click Project's mmenu expand/collapse element button..");

					projExpandCollapseElementBtn.click();

					isNotClickable = false;
				}

			} catch (Exception e) {
				System.out.println("Was not able to locate expand/collapse button.");

				System.out.println("Re-clicking main menu..");

				wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//android.widget.ImageButton[contains(@content-desc, 'Change the current view')]")))
						.click();
			}
		}

		// Opening project in Todoist UI
		boolean status = openMyProjectInTodoistUI(myNewProjectName);

		System.out.println("status: " + status);

		assertTrue("\"" + myNewProjectName + "\" Not Opened Successfully.", status);
	}

	public boolean openMyProjectInTodoistUI(String myProjectNameToSearch) throws InterruptedException {

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

				elements.click();

				return true;
			}
		}
		return false;
	}

	@Test
	public void test3CreateTestTask() {
		MobileElement createTaskBtn = driver.findElementById("com.todoist:id/fab");
		createTaskBtn.click();

		MobileElement taskNameFld = driver.findElementById("android:id/message");
		 taskNameFld.setValue(myProjectTaskName);
		
		 MobileElement addTaskBtn = driver.findElementById("android:id/button1");
			addTaskBtn.click();	
		 
		//To Do: To store Task ID to Private Variable to use API later.
	}

}
