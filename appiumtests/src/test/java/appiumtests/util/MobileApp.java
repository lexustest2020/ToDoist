package appiumtests.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import appiumtests.TestBaseClass;
import io.appium.java_client.MobileElement;

public class MobileApp {
	
	public static void loginUsingGoogleAccount() {
		
		TestUtils.printlnWithTab("Clicking \"Continue with Google\" button..");
		
		// Clicking Continue with Google for login
		TestBaseClass.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.todoist:id/btn_google"))).click();

		TestUtils.printlnWithTab("Selecting \"todoisttest2020@gmail.com\" account..");
		
		// Select todoist account
		TestBaseClass.wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//android.widget.TextView[contains(@text, 'todoisttest2020@gmail.com')]"))).click();
	}

	public static void clickMainMenuThenClickProjectsExpandCollapseButton() {
		
		TestUtils.printlnWithTab("Clicking upper left most menu icon");
		
		TestBaseClass.wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//android.widget.ImageButton[contains(@content-desc, 'Change the current view')]"))).click();

		boolean isNotClickable = true;
		
		WebElement projExpandCollapseElementBtn = null;

		while (isNotClickable) {
			try {
				projExpandCollapseElementBtn = TestBaseClass.wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//android.widget.ImageView[contains(@content-desc, 'Expand/collapse')]")));

				if (projExpandCollapseElementBtn.isDisplayed()) {
					
					TestUtils.printlnWithTab("Project's expand/collapse button found! Clicking the button..");
					
					// click the button in the UI
					projExpandCollapseElementBtn.click();

					isNotClickable = false;
				
				} else {
					TestUtils.printlnWithTab("Project's expand/collapse still not visibile..");
				}

			} catch (Exception e) {
				TestUtils.printlnWithTab("Re-clicking upper left most menu icon..");
				
				TestBaseClass.wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//android.widget.ImageButton[contains(@content-desc, 'Change the current view')]")))
						.click();
			}
		}
	}

	public static boolean isMyProjectVisible() throws InterruptedException {
		return isTextVisible(TestBaseClass.myProjectName, "project \"" + TestBaseClass.myProjectName + "\"");
	}

	public static boolean searchForMyProjectThenClickToOpenTheProject() throws InterruptedException {

		// Let's wait for 10 seconds allowing the emulator to finish loading the UI
		Thread.sleep(3000);

		TestUtils.printlnWithTab("Searching for my project \"" + TestBaseClass.myProjectName + "\" in the mobile app..");
		
		List<MobileElement> textViewElements = TestBaseClass.driver.findElements(By.className("android.widget.TextView"));

		// Search for my projects under main menu Projects
		for (MobileElement elements : textViewElements) {

			// Retrieve text value property
			String textValue = elements.getAttribute("text");

			// If null, skip and proceed to the next TextView
			if (textValue == null) {
				continue;
			}

			TestUtils.printlnWithTab("Is this what we are looking for? " + textValue);

			// Is the text value is equals to the myProjectNameToSearch value?
			if (textValue.equals(TestBaseClass.myProjectName)) {
				
				TestUtils.printlnWithTab("Project \"" + TestBaseClass.myProjectName + "\" found!");

				// click the project to open
				elements.click();

				return true;
			}
		}

		return false;
	}

	public static boolean searchForMyTaskThenClickCheckMarkToCompleteTheTask() throws InterruptedException {

		// Let's wait for 10 seconds allowing the emulator to finish loading the UI
		Thread.sleep(3000);

		TestUtils.printlnWithTab("Searching for my task \"" + TestBaseClass.myTaskName + "\" in the mobile app..");
		
		List<MobileElement> itemElements = TestBaseClass.driver.findElements(By.id("com.todoist:id/item"));

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

			TestUtils.printlnWithTab("Is this what we're looking for? " + textValue);

			// Is the text value is equals to the myProjectNameToSearch value?
			if (textValue.equals(TestBaseClass.myTaskName)) {
				
				TestUtils.printlnWithTab("Task \"" + TestBaseClass.myTaskName + "\" found!");

				MobileElement checkMarkElement = element.findElement(By.id("com.todoist:id/checkmark"));

				// If null, skip and proceed to the next TextView
				if (checkMarkElement == null) {
					continue;
				}

				TestUtils.printlnWithTab("Clicking check mark to complete task");
				
				checkMarkElement.click();

				return true;
			}
		}

		return false;
	}
	
	public static boolean isMyTaskVisible() throws InterruptedException {
		return isTextVisible(TestBaseClass.myTaskName, "task \"" + TestBaseClass.myTaskName + "\"");
	}

	private static boolean isTextVisible(String textToSearch, String searchForLabel) throws InterruptedException {

		// Let's wait for few seconds allowing the emulator to finish loading the UI
		Thread.sleep(15000);

		TestUtils.printlnWithTab("Searching for " + searchForLabel + " in the mobile app..");
		
		List<MobileElement> textViewElements = TestBaseClass.driver.findElements(By.className("android.widget.TextView"));

		// Search for my projects under main menu Projects
		for (MobileElement elements : textViewElements) {

			// Retrieve text value property
			String textValue = elements.getAttribute("text");

			// If null, skip and proceed to the next TextView
			if (textValue == null) {
				continue;
			}

			TestUtils.printlnWithTab("Is this what we are looking for? " + textValue);
			
			// Is the text value is equals to the myProjectNameToSearch value?
			if (textValue.equals(textToSearch)) {
				
				TestUtils.printlnWithTab("\"" + textToSearch + "\" found!");

				// project item found!
				return true;
			}
		}

		return false;
	}
}
