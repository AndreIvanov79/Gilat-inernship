package website.main;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.GeckoDriverService;

import com.github.javafaker.Faker;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import website.main.mail.MailSender;

public class FormPopulationTest {

	@Mock
	private MailSender mailSenderMock = Mockito.mock(MailSender.class);

	@Mock
	private Message message = Mockito.mock(Message.class);

	@Test
	public void testFormSubmission() throws InterruptedException {

		Faker faker = new Faker();

		System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
		FirefoxDriverService service = new GeckoDriverService.Builder().withLogOutput(System.out).build();
		WebDriver driver = new FirefoxDriver(service);
//		
//		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
//		WebDriver driver = new ChromeDriver();

		Logger.getLogger("org.openqa.selenium").setLevel(Level.ALL);
		driver.manage().window().setSize(new Dimension(1200, 800));

		// Open the website
		driver.get("http://localhost:8080/MainWebsite");

		Thread.sleep(2000);

		// Locate form elements by their IDs and fill in the values
		WebElement registerButton = driver.findElement(By.linkText("Register"));
		registerButton.click();
		Thread.sleep(2000);

		WebElement fullNameInput = driver.findElement(By.id("fullName"));
		fullNameInput.sendKeys(faker.name().fullName());

		WebElement emailInput = driver.findElement(By.id("email"));
		emailInput.sendKeys(faker.internet().emailAddress());

		WebElement phoneInput = driver.findElement(By.id("phone"));
		phoneInput.sendKeys(faker.phoneNumber().phoneNumber());

		String testPassword = faker.internet().password();

		WebElement passwordInput = driver.findElement(By.id("password"));
		passwordInput.sendKeys(testPassword);

		WebElement confirmPasswordInput = driver.findElement(By.id("confirmPassword"));
		confirmPasswordInput.sendKeys(testPassword);

		WebElement nicknameInput = driver.findElement(By.id("nickname"));
		nicknameInput.sendKeys(faker.name().username());

//		driver.findElement(By.xpath("//div/form[2]")).click();   
		Thread.sleep(1000);

		WebElement submitButton = driver.findElement(By.xpath("//form[2]//button[@type='submit']"));
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
		submitButton.click();

		Thread.sleep(2000);
//		configureWireMockStubs();
		URL url;
		int responseCode = 0;
		try {
			url = new URL("http://localhost:8080/MainWebsite/signup-action");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (responseCode == 200) {
			try {
				driver.navigate().to(new URL("http://localhost:8080/MainWebsite"));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Mock the email sending service
		Mockito.when(mailSenderMock.sendMessage(Mockito.any())).thenReturn("Message sent successfully.");

		// Call the email sending service

		try {
			message = mailSenderMock.prepareMessage("john.doe@example.com", "testPassword",
					"This is the test message.");
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String returnedConfirmation = mailSenderMock.sendMessage(message);

		Assert.assertEquals("Returned messages are not equals.", "Message sent successfully.", returnedConfirmation);

		// Close the browser
		driver.quit();
	}

}
