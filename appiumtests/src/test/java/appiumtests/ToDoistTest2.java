package appiumtests;

import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import appiumtests.model.Task;
import appiumtests.util.TestUtils;
import io.appium.java_client.MobileElement;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ToDoistTest2 extends TestBaseClass {

	@Test
	public void test1OpenMobileApplication() {

		System.out.println("Clicking \"Continue with Google\" button..");
		// Clicking Continue with Google for login
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.todoist:id/btn_google"))).click();

		System.out.println("Selecting \"todoisttest2020@gmail.com\" account..");
		// Select ToDoist account
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//android.widget.TextView[contains(@text, 'todoisttest2020@gmail.com')]"))).click();
	}

	@Test
	public void test2OpenTestProject() throws InterruptedException {

		System.out.println("Clicking side button Main menu..");
		// Clicking Toolbar
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//android.widget.ImageButton[contains(@content-desc, 'Change the current view')]"))).click();

		boolean isNotClickable = true;
		WebElement projExpandCollapseElementBtn = null;

		while (isNotClickable) {
			try {
				projExpandCollapseElementBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//android.widget.ImageView[contains(@content-desc, 'Expand/collapse')]")));

				System.out.println("At Sub-Menu Project's, is expand/collapse button visible? "
						+ projExpandCollapseElementBtn.isDisplayed());
				if (projExpandCollapseElementBtn.isDisplayed()) {
					System.out.println("Click Project's Sub-Menu expand/collapse element button..");

					projExpandCollapseElementBtn.click();

					isNotClickable = false;
				}

			} catch (Exception e) {
				System.out.println("At sub Menu Projects, was not able to locate expand/collapse button.");

				System.out.println("Re-clicking side button Main menu...");
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
	public void test3CreateTestTask() throws InterruptedException {

		Thread.sleep(3000);

		MobileElement createTaskBtn = driver.findElementById("com.todoist:id/fab");
		createTaskBtn.click();

		Thread.sleep(3000);

		MobileElement taskNameFld = driver.findElementById("android:id/message");
		taskNameFld.setValue(myProjectTaskName);

		Thread.sleep(3000);

		MobileElement addTaskBtn = driver.findElementById("android:id/button1");
		addTaskBtn.click();

		Thread.sleep(3000);

		driver.navigate().back();

		Thread.sleep(3000);

	}

	@Test
	public void test4TestTaskAppearedviaAPI() throws InterruptedException {
		System.out.println("\nAPI: Verify that task created correctly.");

		try {
			URL url = new URL("https://api.todoist.com/rest/v1/tasks?token=" + getLoginToken());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			// conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			List<Task> taskList = TestUtils.toTaskList(conn.getInputStream());

			conn.disconnect();

			boolean isCreated = false;

			System.out.println("Searching \"" + TestBaseClass.myProjectTaskName + "\" with Project Id: "
					+ TestBaseClass.myProjectId);

			for (Task task : taskList) {

				// if either is null, then proceed with the next Task
				if (task.getProjectId() == null || task.getContent() == null) {
					continue;
				}

				System.out.println(task.getProjectId() + ", " + task.getContent());

				if (task.getProjectId().equals(TestBaseClass.myProjectId)
						&& task.getContent().equals(TestBaseClass.myProjectTaskName)) {
					isCreated = true;
					TestBaseClass.myProjectTaskId = task.getId();
					break;
				}
			}

			assertTrue("Task \"" + TestBaseClass.myProjectTaskName + "\" is not created", isCreated);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test5CompleteTestTask() throws InterruptedException {
		System.out.println("Complete Test Task");

		// Opening project in Todoist UI
		MobileElement checkMarkElement = searchTaskCheckMarkInTodoistUI(myProjectTaskName);

		boolean status = checkMarkElement != null;

		System.out.println("Is Task check mark found: " + status);

		if (status) {
			checkMarkElement.click();
		}

		assertTrue("\"" + myProjectTaskName + "\" Not Exist.", status);
	}

	public MobileElement searchTaskCheckMarkInTodoistUI(String taskName) throws InterruptedException {
		MobileElement returnElement = null;

		// Let's wait for 10 seconds allowing the emulator to finish loading the UI
		Thread.sleep(3000);

		List<MobileElement> itemElements = driver.findElements(By.id("com.todoist:id/item"));

		// Search for my projects under main menu Projects
		for (MobileElement element : itemElements) {

			MobileElement taskNameElement = element.findElement(By.id("com.todoist:id/text"));

			// If null, skip and proceed to the next TextView
			if (taskNameElement == null) {
				continue;
			}
			// Retrieve text value property
			String textValue = taskNameElement.getAttribute("text");

			// If null, skip and proceed to the next TextView
			if (textValue == null) {
				continue;
			}

			System.out.println("Task name: " + textValue);

			// Is the text value is equals to the myProjectNameToSearch value?
			if (textValue.equals(taskName)) {
				System.out.println("\"" + taskName + "\" found!");

				MobileElement checkMarkElement = element.findElement(By.id("com.todoist:id/checkmark"));

				// If null, skip and proceed to the next TextView
				if (checkMarkElement == null) {
					continue;
				}

				returnElement = checkMarkElement;

				break;
			}
		}

		return returnElement;
	}

	@Test
	public void test6ReopenTaskViaAPI() throws InterruptedException {
		System.out.println("\nRe-opening Task \"" + TestBaseClass.myProjectTaskName + "\" with taskId="
				+ TestBaseClass.myProjectTaskId);

		Thread.sleep(5000);

		boolean responseOk = false;
		String sUrl = "https://api.todoist.com/rest/v1/tasks/" + TestBaseClass.myProjectTaskId + "/reopen?token="
				+ getLoginToken();
		System.out.println("Url = " + sUrl);
		try {
			URL url = new URL(sUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			// In POSTMAN 204 is a correct
			if (conn.getResponseCode() == 204) {
				responseOk = true;
			}

			assertTrue("Task \"" + TestBaseClass.myProjectTaskName + "\" is not successfully re-opened", responseOk);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test7TestTaskAppearedViaMobile() throws InterruptedException {

		System.out.println("\nVerify that test task appears in your test project.");

		Thread.sleep(15000);

		// Opening project in Todoist UI
		MobileElement element = searchElementInTodoistUI(myProjectTaskName);

		System.out.println("TaskName isExist: " + (element != null));

		assertTrue("\"" + myProjectTaskName + "\" Not Opened Successfully.", (element != null));
	}

	public MobileElement searchElementInTodoistUI(String myProjectNameToSearch) throws InterruptedException {
		MobileElement returnElement = null;

		// Let's wait for 10 seconds allowing the emulator to finish loading the UI
		Thread.sleep(3000);

		List<MobileElement> textViewElements = driver.findElements(By.className("android.widget.TextView"));

		// Search for my projects under main menu Projects
		for (MobileElement element : textViewElements) {

			// Retrieve text value property
			String textValue = element.getAttribute("text");

			// If null, skip and proceed to the next TextView
			if (textValue == null) {
				continue;
			}

			System.out.println("Project item name: " + textValue);

			// Is the text value is equals to the myProjectNameToSearch value?
			if (textValue.equals(myProjectNameToSearch)) {
				System.out.println("\"" + myProjectNameToSearch + "\" found!");

				// element.click();
				returnElement = element;

			}
		}

		return returnElement;

	}

}