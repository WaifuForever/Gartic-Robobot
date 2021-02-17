import java.util.ArrayList;
import java.util.Collections;


import org.json.CDL;
import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Robobot {

	private static WebDriver driver;
	private static WebElement chat, answerChat;
	private static WebElement robotPosition;
	private static WebElement tela, dica;

	String lastid = "";
	String cache = "";
	String url = "";


	private String nick = "Robobot";
	private String lastReact = "";
	private String lastWord = "";
	private int points = 0;
	private boolean hit = false;	
	private boolean drawing = false;

	private Data data = new Data();
	private DrawHandler drawHandler = new DrawHandler();
	
	private String sala1 = "itemSala7455149";
	private String sala2 = "itemSala32758518";

	


	//*[@id="chat"]/div/div[1]/div[1]
	//*[@id="chat"]/div/div[1]/div[3]

	
	
	public Robobot(WebDriver driver) {
		Robobot.driver = driver;
	}

	public void draw() throws InterruptedException {

		if(drawing){
			lastWord = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[2]/div[2]/div[1]/div[3]/div[3]")).getText();
			driver.findElement(By.xpath("//*[@id='desenhar']")).click();
			drawHandler.drawImage(lastWord, driver);
			

		} else{
			driver.findElement(By.xpath("//*[@id='pular']")).click();
			chat.sendKeys("Ainda não estou pronto :/");
			Thread.sleep(200);
			chat.sendKeys(Keys.RETURN);
			Thread.sleep(200);
		}
		
	}

	public void reactChat() throws InterruptedException {
		tela = driver.findElement(By.id("desenho"));
		while(true){
			try{	
				String classTela = 	tela.getAttribute("class");		
				if(classTela.equalsIgnoreCase("vezOutro")){
					hit=false;
				} else if(classTela.equalsIgnoreCase("vez")){
					draw();
				}
				
				if(classTela.equalsIgnoreCase("") && !hit){
					chat.sendKeys("Começou!");
					
					chat.sendKeys(Keys.RETURN);
					Thread.sleep(500);
					break;
				}
				ArrayList<WebElement> lista = new ArrayList<WebElement> (driver.findElements(By.xpath("//*[@id=\"chat\"]/div/div[1]")));
		
				//while(!lista.getText().isEmpty()) {
				while(lista.size() > 0) {

					String fullchat = lista.get(lista.size() - 1).getText();
					//String txt = lista.getText();
					String comments[] = new String [lista.size() + 1];
					comments = fullchat.split("\n");

					String lastComment = comments[comments.length - 1];
				




					boolean matches = lastComment.startsWith("~Robobot") || 
							lastComment.equals("Mensagem bloqueada (Flood)") ||
							lastComment.equalsIgnoreCase(lastReact);

					if(!matches) {
						String nickname = lastComment.substring(0, lastComment.indexOf(' '));
						String content = lastComment.substring(lastComment.indexOf(' ') + 1);

						if(content.toLowerCase().matches("(.*)" + this.nick.toLowerCase() + "(.*)")) {
							chat.sendKeys("você falou " + this.nick.toLowerCase() + "?");
							chat.sendKeys(Keys.ENTER);
							
						}

						else if(content.toLowerCase().equalsIgnoreCase("entrou no jogo.")) {							
							chat.sendKeys("Bem-vindo " + nickname + " :)");
							Thread.sleep(200);
							chat.sendKeys(Keys.ENTER);
						}

						else if(content.toLowerCase().equalsIgnoreCase("saiu do jogo.")) {							
							chat.sendKeys("Adeus " + nickname + " :(");
							Thread.sleep(200);
							chat.sendKeys(Keys.ENTER);
						}
				
						
						System.out.println(lastComment);						
						lastReact = lastComment;
						Thread.sleep(500);
						System.out.println();
						break;
					}
					else {

						
						Thread.sleep(500);
						
						break;
					}


				}
				
			} catch (Exception e){				
				System.out.println("The drawing started");
				break;
			}
		}
		
	}
	
	public boolean joinRoom() throws InterruptedException{
	
		Thread.sleep(5000);
		System.out.println("Joining room");
		
	

		try{
			driver.findElement(By.xpath("/html/body/div[11]/div[2]/div[1]/div[2]/div[2]/button[1]")).click();
		} catch(Exception e){
			try{
				Thread.sleep(500);
				driver.findElement(By.xpath("/html/body/div[11]/div[2]/div[1]/div[2]/div[2]/button[1]")).click();
			} catch(Exception e2){

			}
		
		}

		driver.findElement(By.id("nick")).sendKeys(nick);
		
		
		//select room		
		driver.findElement(By.id("aba2")).click();
		while(true){
			try {
				driver.findElement(By.id(sala1)).click();
				break;
			}
			catch(Exception e) {
				try{
					driver.findElement(By.id(sala2)).click();
	
				} catch(Exception e2){
					System.out.println("could not be possible select a room.");
					return false;
					
				}
				
			}
		}
		//join room			
		try {
			driver.findElement(By.xpath("//*[@id=\"formJogar\"]/div[3]/input[4]")).click();
			
		}
		catch(Exception e) {
			Thread.sleep(1000);
			driver.switchTo().alert().accept();
			driver.findElement(By.id(sala1)).click();
			driver.findElement(By.xpath("//*[@id=\'formJogar\']/div[3]/input[4]")).click();
			
		}
		try{
			driver.findElement(By.xpath("//*[@id='janelaFecharLink']")).click();
		}
		catch(Exception e){

		}
		
		try {
			driver.findElement(By.xpath("//*[@id=\'popupBt1\']")).click();
		}
		catch(NoSuchElementException e) {
			Thread.sleep(3000);
			driver.findElement(By.xpath("//*[@id=\"popupBt1\"]")).click();
		}

		catch(ElementNotInteractableException e1) {
			Thread.sleep(3000);
			driver.findElement(By.xpath("//*[@id=\"popupBt1\"]")).click();
		}
		
		chat = driver.findElement(By.xpath("//*[@id=\"chat\"]/form/label/input"));
		answerChat = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[2]/div[2]/div[2]/form/label/input"));		
		robotPosition = driver.findElement(By.xpath("//div[@class='user proprio']"));
		tela = driver.findElement(By.id("desenho"));
		dica = driver.findElement(By.xpath("//*[@id='dica']"));

		//*[@id="chat"]/div/div[1]/div[1]
		//*[@id="chat"]/div/div[1]/div[3]

		answerChat.sendKeys("Tracking");
		answerChat.sendKeys(Keys.RETURN);

		//String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
		String scriptToExecute = "var network = performance.getEntries() || {}; return network;";
		String netData = ((JavascriptExecutor)driver).executeScript(scriptToExecute).toString();
		JSONArray result = CDL.toJSONArray(netData);
		System.out.println(netData);
		try {
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

		chat.sendKeys("Eu sou o " + this.nick);
		chat.sendKeys(Keys.RETURN);
		return true;
	
		
		
		
	}

	/*private static void sendGET() throws IOException {
		URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}

	}*/

	
	public void tryWin() throws InterruptedException {		

		if(!hit){
			ArrayList <String> words = data.get_words("lol");		
			ArrayList <WebElement> letters, rank;
			Collections.shuffle(words);
	
			for (int i=0; i< words.size() - 1; i++){
				try{
					
					if(dica.getAttribute("class").equalsIgnoreCase("abrir")){
						letters = new ArrayList<WebElement> (driver.findElements(By.xpath("//*[@id='dica']/div[1]/div/div/span")));
						
						
						if(words.get(i).length() == letters.size()){
							answerChat.sendKeys(words.get(i));
							
							Thread.sleep(1200);
							
							answerChat.sendKeys(Keys.RETURN);
						}

					} else {
						answerChat.sendKeys(words.get(i));
						Thread.sleep(1000);
						answerChat.sendKeys(Keys.RETURN);
					}
					
				} catch (Exception e){

				}
				String str = robotPosition.findElement(By.tagName("pre")).getText();
				
				int currentPoints = Integer.parseInt(str.substring(0, str.length() - 7));
//*[@id="usuarios"]/div[1]/div/div[18]/div[2]/pre
								
				if(currentPoints > points){
					hit = true;
					System.out.println("Acertou.");
					points = currentPoints;
					System.out.println(points + " pontos.");
					chat.sendKeys("O " + this.nick + " acertou :3");
					Thread.sleep(200);
					chat.sendKeys(Keys.RETURN);
					Thread.sleep(100);
					break;
				}				
											
				else if(!tela.getAttribute("class").equalsIgnoreCase("")){
					System.out.println(points + " pontos.");
					chat.sendKeys("O " + this.nick + " falhou ;-;");
					Thread.sleep(200);
					chat.sendKeys(Keys.RETURN);
					Thread.sleep(200);
					break;
				}
					
				
			}

		}
	
	

			
		
				
	}	
}
