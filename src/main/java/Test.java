import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



public class Test {

	private static Proxy proxy;
	private static WebDriver driver; 
	private static Robobot robobot;
	
	
	private static String url = "https://gartic.com.br/";
	
	public static void main (String[]args) throws InterruptedException {

		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Waifu\\Documents\\Drivers\\Selenium\\geckodriver.exe");
		
	
        driver = new FirefoxDriver();
		
		driver.get(url);
		
		robobot = new Robobot(driver);

	
		
		if(robobot.joinRoom()){
			while(true) {

				System.out.println("Trying win.");
				robobot.tryWin();
				driver.getCurrentUrl();
			
				System.out.println("Reacting chat.");
				robobot.reactChat();
			}
		} else{
			System.out.println("End Program.");
			driver.quit();
		}
		

		




	}





}




