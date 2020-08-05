package appiumtests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import appiumtests.util.Api;
import appiumtests.util.MobileApp;
import appiumtests.util.TestUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ToDoistTest1 extends TestBaseClass {

	@Test
	public void test1CreateNewProjectViaAPIShouldReturnTrueIfProjectIsCreated() throws Exception {
		
		TestUtils.printTestTitle("Creating \"" + TestBaseClass.myProjectName + "\" project via API");

		// Given
		assertNotNull("Project name should not be null", TestBaseClass.myProjectName);

		// When
		Api.createProject();
		
		// Then
		assertTrue("Newly created project should not be null.", TestBaseClass.myProject != null);
		
		TestUtils.printEnd();
	}

	@Test
	public void test2LoginToMobileApplication() throws InterruptedException {
		
		TestUtils.printTestTitle("Login into mobile application");

		try {
			// When
			MobileApp.loginUsingGoogleAccount();
			
			// Then
			assertTrue("Successfully login to mobile application.", true);
			
		} catch (Exception e) {
			
			// If error detected, then failed to login in mobile application
			assertTrue("Unable to login to mobile application due to error", false);
			
			// For debugging and to know what the error is about, uncomment below
			// e.printStackTrace();
		}
		
		TestUtils.printEnd();
	}

	@Test
	public void test3VerifyProjectIfCreatedAndShouldReturnTrue() throws InterruptedException {
		
		TestUtils.printTestTitle("Verify on mobile that project is created");
		
		// Given
		assertNotNull("Project name should not be null", TestBaseClass.myProjectName);
		
		// When
		MobileApp.clickMainMenuThenClickProjectsExpandCollapseButton();
		
		// Then
		boolean isVisible = MobileApp.isMyProjectVisible();

		assertTrue("Project \"" + TestBaseClass.myProjectName + "\" is not visible in mobile app", isVisible);
		
		TestUtils.printEnd();
	}
}
