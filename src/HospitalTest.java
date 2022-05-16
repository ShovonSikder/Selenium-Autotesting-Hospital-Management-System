import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.units.qual.s;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.Select;

public class HospitalTest {
	WebDriver driver;

	public HospitalTest(String entryURL) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", ".\\Chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(entryURL);
		//Thread.sleep(3000);
	}

	// simple check on buttons of home page
	void home() throws InterruptedException {

		driver.get(SiteUrls.home);

		try {
			File inputFile = new File("Result\\inputs\\home.txt");
			FileWriter outputFile = new FileWriter("Result\\outputs\\home.txt");

			if (inputFile == null || outputFile == null) {
				System.err.println("File reading error");
				return;
			}

			Scanner readFile = new Scanner(inputFile);

			System.out.println("Testing home...");

			while (readFile.hasNext()) {
				// read test cases from external sources (files)
				String[] test = readFile.nextLine().split(" ");
				String expectedString = test[2];
				
				
				// test on test case
				driver.findElement(By.xpath(test[1])).click();
				String originalString = driver.getCurrentUrl();

				// write output to external files
				if (expectedString.equals(originalString)) {

					outputFile.write(test[0] + " passed\n");
				} else {
					outputFile.write(
							test[0] + " failed expected: " + expectedString + " found: " + originalString + "\n");
					takeSnapShot(driver, "Result\\outputs\\homess\\ss" + test[0] + ".png");
				}
			}

			outputFile.close();
			readFile.close();

		} catch (Exception e) {
			System.err.println("Error:-> (-_-) " + e.getMessage());   
		} finally {
			System.out.println("Test finished. Check output files");
		}

	}

	// Register new patient
	void addNewPatient() {
		driver.get(SiteUrls.patientReg);

		try {

			File inputFile = new File("Result\\inputs\\new_patient.txt");
			FileWriter outputFile = new FileWriter("Result\\outputs\\new_patient.txt");

			if (inputFile == null || outputFile == null) {
				System.err.println("File reading error");
				return;
			}

			Scanner readFile = new Scanner(inputFile);

			System.out.println("Testing new patient reg...");

			while (readFile.hasNext()) {
				// read test cases from external sources (files)
				String[] test = readFile.nextLine().split(" ");
				String currentString = driver.findElement(By.cssSelector("input[name=pt_id")).getAttribute("value");
				System.out.println("current string"+currentString);
				// select fields
				WebElement firstName = driver.findElement(By.cssSelector("input[name=f_name]"));
				WebElement lastName = driver.findElement(By.cssSelector("input[name=l_name]"));
				WebElement addrs = driver.findElement(By.cssSelector("input[name=addrs]"));
				WebElement dob = driver.findElement(By.cssSelector("input[name=dob]"));
				WebElement phone = driver.findElement(By.cssSelector("input[name=phone]"));
				WebElement gmail = driver.findElement(By.cssSelector("input[name=gmail]"));
				WebElement password = driver.findElement(By.cssSelector("input[name=pass"));

				Select drpGender = new Select(driver.findElement(By.name("gen")));

				// test on test case
				if (!test[1].equals("null"))
					firstName.sendKeys(test[1]);
				if (!test[2].equals("null"))
					lastName.sendKeys(test[2]);
				if (!test[3].equals("null"))
					addrs.sendKeys(test[3]);
				if (!test[4].equals("null"))
					dob.sendKeys(test[4]);

				if (!test[5].equals("null"))
					drpGender.selectByVisibleText(test[5]);
				else {
					drpGender.selectByValue("Others");
				}

				if (!test[6].equals("null"))
					phone.sendKeys(test[6]);
				if (!test[7].equals("null"))
					gmail.sendKeys(test[7]);
				if (!test[8].equals("null"))
					password.sendKeys(test[8]);
				
				
				
				//validateModule
				VerifyData vd=new VerifyData(test);
				String vdmessage=vd.startVerify();
				//System.out.println(test[0]+" "+vdmessage);
				

				//takes snapshot
				takeSnapShot(driver, "Result\\outputs\\add_New_Patientss\\ss" + test[0] + ".png");
				driver.findElement(By.name("sbtn")).click();// submit form

				String expectedOutput = test[9];

				String originalString = driver.findElement(By.cssSelector("input[name=pt_id")).getAttribute("value");

				// write output to external files
				if (!currentString.equals(originalString)) {

					if (expectedOutput.equals("t")) {
						outputFile.write(test[0] + " passed\n");
						Operation operation=new Operation();
						operation.setLocation("Result\\outputs\\add_New_Patientss\\ss" + test[0] + ".png");						
					}
						
					else {
						outputFile.write(test[0] + " failed  | expected: should not submit  | found: form submitted,   |   details:"+vdmessage+"\n");
					}
				} else {
					if (expectedOutput.equals("t")) {
						outputFile.write(test[0] + " failed  | expected: should not submit  | found: form submitted,   |   details:"+vdmessage+"\n");
						
					} else {
						outputFile.write(test[0] + " passed\n");
						Operation operation=new Operation();
						operation.setLocation("Result\\outputs\\add_New_Patientss\\ss" + test[0] + ".png");
					}

				}

				clearInputField(firstName, lastName, addrs, dob, phone, password, gmail);

			}

			outputFile.close();
			readFile.close();

		} catch (Exception e) {
			System.err.println("Error:-> (-_-) " + e.getMessage());
			return;
		} finally {
			System.out.println("Test finished. Check output files");
		}
	}

	// test all the login modules
	void login(String moduleToLogin) {
		driver.get(SiteUrls.home);
		driver.findElement(By.xpath(moduleToLogin)).click();

		try {
			File inputFile = new File("Result\\inputs\\login.txt");
			FileWriter outputFile = new FileWriter("Result\\outputs\\" + driver.getTitle() + ".txt");

			if (inputFile == null || outputFile == null) {
				System.err.println("File reading error");
				return;
			}
			Scanner readFile = new Scanner(inputFile);

			System.out.println("Testing login " + driver.getTitle() + "...");

			while (readFile.hasNext()) {
				// read test cases from external sources (files)
				String[] test = readFile.nextLine().split(" ");
				String expectedString = test[3];

				boolean back;
				// decide expected result
				if (expectedString.equals("t")) {
					back = true;
					if (moduleToLogin.equals(SiteUrls.loginLinksMap.get("admin"))) {
						expectedString = SiteUrls.adminDashboard;

					} else if (moduleToLogin.equals(SiteUrls.loginLinksMap.get("doctor"))) {
						expectedString = SiteUrls.doctorDashboard;
					} else {
						expectedString = SiteUrls.patientDashboard;
					}

				} else {
					if (moduleToLogin.equals(SiteUrls.loginLinksMap.get("admin"))) {
						expectedString = SiteUrls.adminLogin;

					} else if (moduleToLogin.equals(SiteUrls.loginLinksMap.get("doctor"))) {
						expectedString = SiteUrls.doctorLogin;
					} else {
						expectedString = SiteUrls.patientLogin;
					}
				}

				// test on test case
				WebElement gmail = driver.findElement(By.cssSelector("input[name=gmail]"));
				WebElement pass = driver.findElement(By.cssSelector("input[name=pass]"));

				gmail.sendKeys(test[1]);
				pass.sendKeys(test[2]);

				driver.findElement(By.cssSelector("button[name=sbtn]")).click();
				String originalString = driver.getCurrentUrl();

				// write output to external files
				if (expectedString.equals(originalString)) {

					outputFile.write(test[0] + " passed\n");

				} else {
					outputFile.write(
							test[0] + " failed expected: " + expectedString + " found: " + originalString + "\n");
					takeSnapShot(driver, "Result\\outputs\\Loginss\\ss"+driver.getTitle()+ test[0] + ".png");
				}
				if (originalString.equals(SiteUrls.adminDashboard) || originalString.equals(SiteUrls.doctorDashboard)
						|| originalString.equals(SiteUrls.patientDashboard))
					driver.navigate().back();
				clearInputField(driver.findElement(By.cssSelector("input[name=gmail]")),
						driver.findElement(By.cssSelector("input[name=pass]")));

			}

			outputFile.close();
			readFile.close();

		} catch (Exception e) {
			System.err.println("Error:-> (-_-) " + e.getMessage());
		} finally {
			System.out.println("Test finished. Check output files");
		}
	}

	// test doctor modules
	void doctor() {

		logInTo(SiteUrls.doctorLogin, "anis@gmail.com", "1234");

		try {
			File inputFile = new File("Result\\inputs\\doctor_moduls.txt");
			FileWriter outputFile = new FileWriter("Result\\outputs\\doctor_moduls.txt");

			if (inputFile == null || outputFile == null) {
				System.err.println("File reading error");
				return;
			}

			System.out.println("Testing doctor mod...");

			// checking if the dash-board loaded
			if (driver.getCurrentUrl().equals(SiteUrls.doctorDashboard)) {
				outputFile.write("Succefully Logged in\n");
			} else {
				outputFile.write("Login error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\doctorss\\ss1.png");

			// loading appointment page
			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[2]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.doctorAppoint)) {
				outputFile.write("Succefully in appoint page\n");
			} else {
				outputFile.write("Not in appoint page Error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\doctorss\\ss2.png");

			try {
				driver.findElement(By.xpath("//*[@id=\"display\"]/div[1]/table/tbody/tr[2]/td[6]/a[1]")).click();
				if (driver.getCurrentUrl().equals(SiteUrls.doctorAppoint)) {
					outputFile.write("Succefully deleted first appointment\n");
				} else {
					outputFile.write("Error at url: " + driver.getCurrentUrl() + "\n");
				}
				takeSnapShot(driver, "Result\\outputs\\doctorss\\ss3.png");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

			// page to add new visit
			driver.findElement(By.xpath("//*[@id=\"display\"]/div[1]/a/button")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.docAddVisti)) {
				outputFile.write("Succefully opend add visit form\n");
			} else {
				outputFile.write("Didn't open visit form Error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\doctorss\\ss3.png");
			Scanner readFile = new Scanner(inputFile);

			// checking add visit form
			while (readFile.hasNext()) {

				// read test cases from external sources (files)
				String[] test = readFile.nextLine().split(" ");
				String expectedString = (test[4] == "t") ? SiteUrls.doctorAppoint : SiteUrls.docAddVisti;

				WebElement ptId = driver.findElement(By.cssSelector("input[name=ptid]"));
				WebElement suggestionElement = driver.findElement(By.cssSelector("input[name=sug"));

				Select drpDiss = new Select(driver.findElement(By.name("diss")));

				if (!test[1].equals("null"))
					ptId.sendKeys(test[1]);
				if (!test[2].equals("null"))
					drpDiss.selectByVisibleText(test[2]);
				else {
					drpDiss.selectByValue("None");
				}
				if (!test[3].equals("null"))
					suggestionElement.sendKeys(test[3]);

				driver.findElement(By.cssSelector("[name=sbtn]")).click();

				String originalString = driver.getCurrentUrl();
				if (expectedString.equals(originalString)) {
					outputFile.write(test[0] + " passed\n");
					

				} else {
					outputFile.write(
							test[0] + " failed expected: " + expectedString + " found: " + originalString + "\n");
					clearInputField(ptId, suggestionElement);
					takeSnapShot(driver, "Result\\outputs\\doctorss\\ss" + test[0] + ".png");
				}
				if (originalString.equals(SiteUrls.doctorAppoint))
					driver.findElement(By.xpath("//*[@id=\"display\"]/div[1]/a/button")).click();

			}

			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[3]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.docSarve)) {
				outputFile.write("Succefully opend serve page\n");
			} else {
				outputFile.write("Didn't open sarve Error at url: " + driver.getCurrentUrl() + "\n");
			}

			//log out
			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[4]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.home)) {
				outputFile.write("Succefully Logged out\n");
			} else {
				outputFile.write("Didn't logged out Error at url: " + driver.getCurrentUrl() + "\n");
			}

			outputFile.close();
			readFile.close();
			// inputFile.close();
		} catch (Exception e) {
			System.err.println("Error:-> (-_-) " + e.getMessage());
		} finally {
			System.out.println("Test finished. Check output files");
		}

	}

	// test patient modules
	void patient() {

		logInTo(SiteUrls.patientLogin, "shovon@gmail.com", "123456");

		try {

			FileWriter outputFile = new FileWriter("Result\\outputs\\patient_moduls.txt");

			System.out.println("Testing patient mod...");

			// checking if the dash-board loaded
			if (driver.getCurrentUrl().equals(SiteUrls.patientDashboard)) {
				outputFile.write("Succefully Logged in\n");
			} else {
				outputFile.write("Login error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\patientss\\ss1.png");
			// loading appointment page
			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[2]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.patientAppoint)) {
				outputFile.write("Succefully in appoint page\n");
			} else {
				outputFile.write("Not in appoint page Error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\patientss\\ss2.png");

			driver.findElement(By.cssSelector("[name=apdate]")).sendKeys("17/05/2022");
			driver.findElement(By.cssSelector("[name=apdoc]")).click();
			outputFile.write("Succefully appointment added\n");

			driver.findElement(By.xpath("//*[@id=\"display\"]/div[1]/table/tbody/tr[2]/td[5]/a")).click();
			outputFile.write("Succefully appointment deleted\n");

			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[3]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.patientVisit)) {
				outputFile.write("Succefully in visit page\n");
			} else {
				outputFile.write("Not in visit page Error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\patientss\\ss3.png");

			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[4]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.patientBills)) {
				outputFile.write("Succefully in bills page\n");
			} else {
				outputFile.write("Not in bills page Error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\patientss\\bill1.png");

			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[5]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.home)) {
				outputFile.write("Succefully logged out\n");
			} else {
				outputFile.write("Didn't logged out Error at url: " + driver.getCurrentUrl() + "\n");
			}

			outputFile.close();

		} catch (Exception e) {
			System.err.println("Error:-> (-_-) " + e.getMessage());
		} finally {
			System.out.println("Test finished. Check output files");
		}
	}

	// test admin modules
	void admin() {
		logInTo(SiteUrls.adminLogin, "shovon@gmail.com", "shovon123");

		try {
			FileWriter outputFile = new FileWriter("Result\\outputs\\admin_moduls.txt");

			System.out.println("Testing admin mod...");

			// checking if the dash-board loaded
			if (driver.getCurrentUrl().equals(SiteUrls.adminDashboard)) {
				outputFile.write("Succefully Logged in\n");
			} else {
				outputFile.write("Login error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\adminss\\s1.png");

			// doc list
			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[2]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.docList)) {
				outputFile.write("Succefully in doc list\n");
			} else {
				outputFile.write("Doc list loading error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\adminss\\docinfo.png");

			// adding new doctor
			driver.findElement(By.xpath("//*[@id=\"display\"]/div[1]/a/button")).click();
			addDoc();

			// admit info
			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[3]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.admit)) {
				outputFile.write("Succefully in admit info\n");
			} else {
				outputFile.write("admit info loading error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\adminss\\admit_info.png");

			// bill info
			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[4]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.billInfo)) {
				outputFile.write("Succefully in bill info\n");
			} else {
				outputFile.write("Bill info loading error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\adminss\\bill_info.png");
			// visit info
			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[5]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.visitInfo)) {
				outputFile.write("Succefully in visit info\n");
			} else {
				outputFile.write("visit info loading error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\adminss\\visit_info.png");

			// patient list
			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[6]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.patientList)) {
				outputFile.write("Succefully in patient list\n");
			} else {
				outputFile.write("Patient list loading error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\adminss\\patient_list.png");

			// room list
			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[7]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.rooms)) {
				outputFile.write("Succefully in room info\n");
			} else {
				outputFile.write("Room info loading error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\adminss\\room_info.png");

			// disease list
			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[8]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.disease)) {
				outputFile.write("Succefully in disease info\n");
			} else {
				outputFile.write("Diseae info loading error at url: " + driver.getCurrentUrl() + "\n");
			}
			takeSnapShot(driver, "Result\\outputs\\adminss\\disease_info.png");

			// Log out
			driver.findElement(By.xpath("//*[@id=\"menu\"]/a[9]/div")).click();
			if (driver.getCurrentUrl().equals(SiteUrls.home)) {
				outputFile.write("Succefully in Logge out\n");
			} else {
				outputFile.write("Log out error at url: " + driver.getCurrentUrl() + "\n");
			}

			outputFile.close();

		} catch (Exception e) {
			System.err.println("Error:-> (-_-) " + e.getMessage());
		} finally {
			System.out.println("Test finished. Check output files");
		}
	}

	// add new doctor
	void addDoc() {
		try {

			File inputFile = new File("Result\\inputs\\new_patient.txt");
			FileWriter outputFile = new FileWriter("Result\\outputs\\new_doctor.txt");

			if (inputFile == null || outputFile == null) {
				System.err.println("File reading error");
				return;
			}

			Scanner readFile = new Scanner(inputFile);

			System.out.println("Testing new doc reg...");

			while (readFile.hasNext()) {
				// read test cases from external sources (files)
				String[] test = readFile.nextLine().split(" ");

				// select fields
				WebElement firstName = driver.findElement(By.cssSelector("input[name=f_name]"));
				WebElement lastName = driver.findElement(By.cssSelector("input[name=l_name]"));
				WebElement addrs = driver.findElement(By.cssSelector("input[name=addrs]"));
				WebElement dob = driver.findElement(By.cssSelector("input[name=dob]"));
				WebElement phone = driver.findElement(By.cssSelector("input[name=phone]"));
				WebElement gmail = driver.findElement(By.cssSelector("input[name=gmail]"));
				WebElement password = driver.findElement(By.cssSelector("input[name=pass"));

				Select drpGender = new Select(driver.findElement(By.name("gen")));

				// test on test case
				if (!test[1].equals("null"))
					firstName.sendKeys(test[1]);
				if (!test[2].equals("null"))
					lastName.sendKeys(test[2]);
				if (!test[3].equals("null"))
					addrs.sendKeys(test[3]);
				if (!test[4].equals("null"))
					dob.sendKeys(test[4]);

				if (!test[5].equals("null"))
					drpGender.selectByVisibleText(test[5]);
				else {
					drpGender.selectByValue("Others");
				}

				if (!test[6].equals("null"))
					phone.sendKeys(test[6]);
				if (!test[7].equals("null"))
					gmail.sendKeys(test[7]);
				if (!test[8].equals("null"))
					password.sendKeys(test[8]);

				driver.findElement(By.name("sbtn")).click();// submit form

				String expectedOutput = test[9];

				String originalString = driver.getCurrentUrl();

				// write output to external files
				if (originalString.equals(SiteUrls.docList + "?")) {

					if (expectedOutput.equals("t")) {
						outputFile.write(test[0] + " passed\n");

					} else {
						outputFile.write(test[0] + " failed expected: not to submit found: form submitted\n");
						takeSnapShot(driver, "Result\\outputs\\add_New_docss\\ss" + test[0] + ".png");

					}

					driver.findElement(By.xpath("//*[@id=\"display\"]/div[1]/a/button")).click();
				} else {
					if (expectedOutput.equals("t")) {
						outputFile.write(test[0] + " failed expected: not to submit found: form submitted\n");
						takeSnapShot(driver, "Result\\outputs\\add_New_docss\\ss" + test[0] + ".png");
					} else {
						outputFile.write(test[0] + " passed\n");

					}

				}

				clearInputField(firstName, lastName, addrs, dob, phone, password, gmail);

			}

			outputFile.close();
			readFile.close();

		} catch (Exception e) {
			System.err.println("Error:-> (-_-) " + e.getMessage());
			return;
		} finally {
			System.out.println("Test finished. Check output files");
		}
	}
	


	// method to clear input fields
	private void clearInputField(WebElement... fields) {
		for (WebElement webElement : fields) {
			try {
				webElement.clear();
			} catch (Exception e) {
				//
			}
		}
	}

	// log in to specific
	private void logInTo(String url, String gmail, String pass) {
		driver.get(url);
		driver.findElement(By.cssSelector("input[name=gmail]")).sendKeys(gmail);
		driver.findElement(By.cssSelector("input[name=pass]")).sendKeys(pass);
		driver.findElement(By.cssSelector("button[name=sbtn]")).click();

	}

	private void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {

		// Take the screenshot
		File screenshot = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);

		// Copy the file to a location and use try catch block to handle exception
		try {
			FileUtils.copyFile(screenshot, new File(fileWithPath));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	
	//initiating Jira
	public void initiateJira() throws InterruptedException {
		JiraOperations jiraoperations=new JiraOperations(driver);
		jiraoperations.openJira();
	}
	
	

}
