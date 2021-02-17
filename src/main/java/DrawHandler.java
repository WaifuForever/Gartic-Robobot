import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class DrawHandler {

   
    

    private int width = 1020;
    private int height = 608;

    final double max_BINARY_value = 255;
    final double threshold_value = 30;
    final int threshold_type = 0;

    final String googleLink = "https://www.google.com/imghp?hl=pt-BR&authuser=0&ogbl";
    Mat source;


    public void drawImage(String imageName, WebDriver driver) {
        try{
            find_image(imageName, driver);
            treatImage(imageName);
        }catch(Exception e){

        }
    }

    private void treatImage(String imageName) {
        // To load OpenCV core library C:\Users\Waifu\Documents\opencv\build\java\x64
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String input = "src\\main\\data\\images\\" + imageName + ".jpg";

        // To Read the image
        source = Imgcodecs.imread(input);

        // Creating the empty destination matrix
        Mat gray = new Mat();

        // Converting the image to gray scale and
        // saving it in the dst matrix
        Imgproc.cvtColor(source, gray, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(gray, source, threshold_value, max_BINARY_value, threshold_type);

        // Writing the image
        Imgcodecs.imwrite("src\\main\\data\\treated_images\\" + imageName + ".jpg", source.submat(new Rect(0, 0, width, height)));
        System.out.println("The image is successfully to Grayscale");
    }

    private void find_image(String imageName, WebDriver driver) throws InterruptedException {

        String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
        driver.findElement(By.linkText(googleLink)).sendKeys(selectLinkOpeninNewTab);

        driver.findElement(By.xpath("//*[@id='sbtc']/div/div[2]/input")).sendKeys(imageName + Keys.RETURN);
        String src = driver.findElement(By.xpath("//*[@id='islrg']/div[1]/div[1]/a[1]/div[1]/img")).getAttribute("src");
        Thread.sleep(200);
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.dir", "C:\\Users\\Waifu\\Documents\\Vs Projects\\Java\\Robobot\\src\\main\\data\\images");
        try {
            URL imageURL = new URL(src);
            BufferedImage saveImage = ImageIO.read(imageURL);
            ImageIO.write(saveImage, "jpg", new File(imageName + ".jpg"));  
           

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Thread.sleep(200);
        driver.findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.CONTROL, "w"));

    }

}
