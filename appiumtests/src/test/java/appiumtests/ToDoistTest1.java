package appiumtests;

import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import appiumtests.model.Project;
import appiumtests.util.TestUtils;
import io.appium.java_client.MobileElement;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ToDoistTest1 extends TestBaseClass {

	@Test
	public void test1CreateNewProjectViaAPIShouldReturnTrueIfProjectIsCreated() throws Exception {
		System.out.println("Creating New project ToDoist using API method...");

		URL url = new URL(getBaseUrl());
		// Setup parameters needed
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("token", getLoginToken());
		params.put("name", myNewProjectName);

		byte[] postDataBytes = TestUtils.toPostDataBytes(params);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Lenght", String.valueOf(postDataBytes));
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		// Convert response to Project Model
		Project project = TestUtils.toProject(conn.getInputStream());

		conn.disconnect();

		// Project should not benull
		assertTrue(project != null);

		// store project id to use later
		TestBaseClass.myProjectId = project.getId();
	}

	@Test
	public void test2ShouldReturnTrueIfProjectIsCreated() throws InterruptedException {
		System.out.println("Starting Mobile logIn Application..");
		System.out.println("Clicking \"Continue with Google\" button..");
		// Clicking Continue with Google for login
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.todoist:id/btn_google"))).click();

		System.out.println("Selecting \"todoisttest2020@gmail.com\" account..");
		// Select todoist account
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//android.widget.TextView[contains(@text, 'todoisttest2020@gmail.com')]"))).click();

		System.out.println("Clicking side button Main menu..");
		// Clicking toolbar
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
				System.out.println("Re-clicking side button Main menu..");
				wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//android.widget.ImageButton[contains(@content-desc, 'Change the current view')]")))
						.click();
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

			System.out.println("Project name: " + textValue);
			// Is the text value is equals to the myProjectNameToSearch value?
			if (textValue.equals(myProjectNameToSearch)) {
				System.out.println("\"" + myProjectNameToSearch + "\" is found!");

				// project item found!
				return true;
			}
		}

		return false;
	}
}
