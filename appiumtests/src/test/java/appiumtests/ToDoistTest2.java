package appiumtests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import appiumtests.util.Api;
import appiumtests.util.MobileApp;
import appiumtests.util.TestUtils;
import io.appium.java_client.MobileElement;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ToDoistTest2 extends TestBaseClass {

	@Test
	public void test1OpenMobileApplication() {

		TestUtils.printTestTitle("Open mobile application");

		try {
			// When
			MobileApp.loginUsingGoogleAccount();
			
			// Then
			assertTrue("Successfully login to mobile application.", true);
			
		} catch (Exception e) {
			
			// If error detected, then failed to login in the mobile application
			assertTrue("Unable to login to mobile application due to error", false);
			
			// For debugging and to know what the error is about, uncomment below
			// e.printStackTrace();
		}
		
		TestUtils.printEnd();
	}

	@Test
	public void test2OpenTestProject() throws InterruptedException {
		
		TestUtils.printTestTitle("Open test project");

		// Given
		assertNotNull("Project name should not be null", TestBaseClass.myProjectName);

		// When
		MobileApp.clickMainMenuThenClickProjectsExpandCollapseButton();

		// Then
		boolean isProjectOpen = MobileApp.searchForMyProjectThenClickToOpenTheProject();

		assertTrue("Project \"" + TestBaseClass.myProjectName + "\" not successfully opened in mobile application", isProjectOpen);
		
		TestUtils.printEnd();
	}

	@Test
	public void test3CreateTestTask() {

		TestUtils.printTestTitle("Create test task");
	
		// Given
		assertNotNull("Task name should not be null", TestBaseClass.myTaskName);
		
		try {
			Thread.sleep(3000);

			// When
			MobileElement createTaskBtn = driver.findElementById("com.todoist:id/fab");
			createTaskBtn.click();

			Thread.sleep(3000);

			// And
			MobileElement taskNameFld = driver.findElementById("android:id/message");
			taskNameFld.setValue(myTaskName);

			Thread.sleep(3000);

			// And
			MobileElement addTaskBtn = driver.findElementById("android:id/button1");
			addTaskBtn.click();

			Thread.sleep(3000);

			// And
			driver.navigate().back();

			Thread.sleep(3000);
		
			// Then
			assertTrue("Successfully created new task \"" + TestBaseClass.myTaskName + " in mobile application.", true);
			
		} catch (Exception e) {
			
			// If error detected, then failed to create task in mobile application
			assertTrue("Unable to create task in mobile application due to error", false);
			
			// For debugging and to know what the error is about, uncomment below
			// e.printStackTrace();
		}
		
		TestUtils.printEnd();
	}

	@Test
	public void test4VerifyTaskViaAPI() throws InterruptedException {
		
		TestUtils.printTestTitle("API: Verify that task created correctly.");

		// Given
		assertNotNull("Task name should not be null", TestBaseClass.myTaskName);
		
		try {
			
			// When
			boolean isTaskCreated = Api.createTask();

			// Then
			assertTrue("Task \"" + TestBaseClass.myTaskName + "\" is not successfully created", isTaskCreated);

		} catch (Exception e) {
			
			// If error detected, then failed to create task in mobile application
			assertTrue("Unable to create task in mobile application due to error", false);
			
			// Uncomment below to see what is the error about
			// e.printStackTrace();
		}
		
		TestUtils.printEnd();
	}

	@Test
	public void test5CompleteTestTask() throws InterruptedException {
		
		TestUtils.printTestTitle("Complete Test Task");

		// Given
		assertNotNull("Task name should not be null", TestBaseClass.myTaskName);

		// When
		boolean isTaskCompleted = MobileApp.searchForMyTaskThenClickCheckMarkToCompleteTheTask();

		// Then
		assertTrue("Task \"" + TestBaseClass.myTaskName + "\" was not successfully completed.", isTaskCompleted);
		
		TestUtils.printEnd();
	}

	@Test
	public void test6ReopenTaskViaAPI() throws InterruptedException {
		
		TestUtils.printTestTitle("Reopen test task via API");
		
		// Given
		assertNotNull("Task name should not be null", TestBaseClass.myTaskName);

		// When
		boolean isTaskReopened = Api.reopenTask();

		// Then
		assertTrue("Task \"" + TestBaseClass.myTaskName + "\" was not successfully re-opened.", isTaskReopened);
	}

	@Test
	public void test7TestTaskAppearedViaMobile() throws InterruptedException {
		
		TestUtils.printTestTitle("Verify on mobile that project is created");
		
		// Given
		assertNotNull("Task name should not be null", TestBaseClass.myTaskName);
				
		// Then
		boolean isVisible = MobileApp.isMyTaskVisible();

		assertTrue("Task \"" + TestBaseClass.myTaskName + "\" is not visible in mobile app", isVisible);
		
		TestUtils.printEnd();
	}
}