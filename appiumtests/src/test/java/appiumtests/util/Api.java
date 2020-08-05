package appiumtests.util;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonSyntaxException;

import appiumtests.TestBaseClass;
import appiumtests.model.Task;

public class Api {
	public static final String baseProjectUrl = "https://api.todoist.com/rest/v1/projects";
	
	public static final String baseTaskUrl = "https://api.todoist.com/rest/v1/tasks";
	
	public static void createProject() throws IOException {
		URL url = new URL(baseProjectUrl);
		
		// Setup parameters needed
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("token", TestBaseClass.myLoginToken);
		params.put("name", TestBaseClass.myProjectName);

		byte[] postDataBytes = TestUtils.toPostDataBytes(params);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes));
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		// Convert response to Project object
		TestBaseClass.myProject = TestUtils.toProject(conn.getInputStream());
		
		conn.disconnect();
	}

	public static boolean createTask() throws JsonSyntaxException, IOException {
		
		URL url = new URL(baseTaskUrl + "?token=" + TestBaseClass.myLoginToken);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		if (conn.getResponseCode() != 200) {
			assertTrue("Unable to create new task due to error", false);
		}

		List<Task> taskList = TestUtils.toTaskList(conn.getInputStream());

		conn.disconnect();

		TestUtils.printlnWithTab("Searching for \"" + TestBaseClass.myTaskName + "\" under Project Id: " + TestBaseClass.myProject.getId() + "..");

		for (Task task : taskList) {

			// if either is null, then proceed with the next Task
			if (task.getProjectId() == null || task.getContent() == null) {
				continue;
			}

			TestUtils.printlnWithTab("Is this what we're looking for? Project Id: "+ task.getProjectId() + ", Task name: " + task.getContent());

			if (task.getProjectId().equals(TestBaseClass.myProject.getId())
					&& task.getContent().equals(TestBaseClass.myTaskName)) {
						
				TestUtils.printlnWithTab("Task \"" + TestBaseClass.myTaskName + "\" found!");
				
				TestBaseClass.myTask = task;
				
				// Task found
				return true;
			}
		}
		
		return false;
	}

	public static boolean reopenTask() throws InterruptedException {
		
		TestUtils.printlnWithTab("Re-opening task \"" + TestBaseClass.myTask.getContent() + "\" with taskId=" + TestBaseClass.myTask.getId());

		Thread.sleep(5000);

		String sUrl = baseTaskUrl + "/" + TestBaseClass.myTask.getId() + "/reopen?token=" + TestBaseClass.myLoginToken;

		TestUtils.printlnWithTab("sUrl = " + sUrl);
		
		try {
			URL url = new URL(sUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");

			TestUtils.printlnWithTab("response code = " + conn.getResponseCode());
			
			if (conn.getResponseCode() != 204) {
				assertTrue("Unable to re-open task \"" + TestBaseClass.myTask.getContent() + "\" due to error", false);
			}
			
			return true;

		} catch (Exception e) {
			
			//In POSTMAN reopen task will return 204 response code.
			e.printStackTrace();
		}
		
		return false;
	}
}
