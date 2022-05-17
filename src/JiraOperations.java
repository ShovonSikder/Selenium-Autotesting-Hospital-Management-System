import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class JiraOperations {
	WebDriver driver;
	JiraOperations(WebDriver driver){
		this.driver=driver;
	}
	
	void openJira() throws InterruptedException {
		driver.get("https://zahid72.atlassian.net/jira/software/projects/HMS/boards/1/backlog");
		
		
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("labmanual74@gmail.com"); //email
		driver.findElement(By.xpath("//*[@id=\"login-submit\"]/span")).click();   //login
		Thread.sleep(500);
		driver.findElement(By.id("password")).sendKeys("jirapassword"); //password
		driver.findElement(By.xpath("//*[@id=\"login-submit\"]/span")).click(); // login
		
		
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"createGlobalItem\"]/span")).click();  //create issue button
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"summary-field\"]")).sendKeys("This is a test summary"); //summary
		driver.findElement(By.xpath("//*[@id=\"description-container\"]/div/div/div/div/div/div/div/div[2]/div/div[2]/div/div[2]/p")).sendKeys("This is a test description"); //description
		
		driver.findElement(By.xpath("//*[@id=\"jira\"]/div[15]/div[2]/div/div[3]/div[2]/div/div/footer/button/span")).click();  // create submit
		//*[@id="issue-create.ui.modal.create-form.type-picker.issue-type-select"]/div/div[1]        //dropdown
		   
		
		
		//driver.findElement(By.id("Value")).sendKeys(Keys.ENTER);
		
	}
	
	
}
