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

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TestToDoist {

	//@Before
	public void setUp() throws Exception {
		// insert here desired capabilities
	}

	//@After
	public void tearDown() throws Exception {
		// stops current session of test
		// driver.quit();
	}

	
	
	static AppiumDriver<MobileElement> driver;
	
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
	public static void main(String[] args) {
		try {
			//testLaunchToDoist();
			createNewProjectViaAPIShouldReturnTrueWhenProjectIsCreated();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void testLaunchToDoist() throws MalformedURLException {
		DesiredCapabilities cp = new DesiredCapabilities();

		// Setup of my Android Device emulator connected to my Appium.
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

	public class BaseClass {

	}

	public int projectId = 0;

	
	public static String getBaseUrl() {
		return "https://api.todoist.com/rest/v1/projects";
	}

	public static String getLoginToken() {
		return "5a9a60b3f88597a1996926de0e13a804c9d1954b";
	}

	@Test
	/// 1. Create test project via API.
	public static void createNewProjectViaAPIShouldReturnTrueWhenProjectIsCreated() throws Exception{
		
		URL url = new URL(getBaseUrl());
		//Setup parameters needed
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("token", getLoginToken());
		params.put("name", "My New Test Project");
		
		byte[] postDataBytes = toPostDataBytes(params);
		
		HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Lenght", String.valueOf(postDataBytes));
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);
		
		//Convert response to Project Model
		Project project = toProject(conn.getInputStream());
		
		conn.disconnect();
		
		//project should not null
		assertTrue(project != null);
				
	}

	/// Defining and Declaring xpath Support the Functions for Test Scripting
	/// ToDoist application.

	// Execute Create Project via API POST:	// https://api.todoist.com/rest/v1/projects?token=5a9a60b3f88597a1996926de0e13a804c9d1954b&name=My
	// New ToDoist Test Project

	/// Login into mobile application and Verify on mobile that project is created
	// Login Via Token Authorization
	// Clicking ToDoist Hybrid application: xpath
	/// //android.widget.TextView[@content-desc = 'Todoist']
	// Clicking Menu at ToDoist Hybrid Application: xpath
	/// //android.widget.ImageButton[@content-desc = 'Change the current view']
	// Clicking Arrow down in Projects Menu xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[4]/android.widget.ImageView[1]
	// Verify Created Project xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[6]

	/// LogOut ToDoist Application
	// Clicking Menu at ToDoist Hybrid Application: xpath
	/// //android.widget.ImageButton[@content-desc = 'Change the current view']
	// Clicking Settings xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.drawerlayout.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/androidx.recyclerview.widget.RecyclerView[1]/android.widget.RelativeLayout[7]
	// Clicking LogOut Settings xpath
	/// //android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.view.ViewGroup[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.ListView[1]/android.widget.LinearLayout[9]
	// Clicking LogOut Condition "Yes" xpath
	/// ////android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/androidx.appcompat.widget.LinearLayoutCompat[1]/android.widget.ScrollView[1]/android.widget.LinearLayout[1]/android.widget.Button[2]

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
