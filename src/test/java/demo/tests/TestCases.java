package demo.tests;

import java.time.Duration;
import java.util.*;
import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import demo.methods.WrapperMethods;


public class TestCases {
    static ChromeDriver driver;
    
    @BeforeSuite(enabled = true,alwaysRun = true)
    public static void OpenBrowser()
    {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public static void openURL(String url){
        driver.get(url);
    }

    public static void searchProduct(String productName) throws InterruptedException{
        try {
            WebElement searchbar = driver.findElement(By.xpath("//input[@placeholder='Search for Products, Brands and More']"));
            WebElement searchbutton= driver.findElement(By.xpath("//button[@class='_2iLD__']"));
            WrapperMethods.advanceSearch(productName, searchbar,searchbutton);
        } catch (Exception e) {
            // TODO: handle exception
            WebElement close = driver.findElement(By.xpath("//span[@class='_30XB9F']"));
            close.click();
            WebElement searchbutton= driver.findElement(By.xpath("//button[@class='_2iLD__']"));
            searchbutton.click();
        }
    }

    public static void sortByOptions(String sortBy) {
        try {
            List<WebElement> sort = driver.findElements(By.xpath("//div[contains(@class,'zg-M3Z')]"));
            for (WebElement webElement : sort) {
                if (webElement.getText().equals(sortBy)) {
                    webElement.click();
                    Thread.sleep(5000);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void countItemsWithRatingLessThanOrEqual(double rate){
        try {
            List<WebElement> ratings= driver.findElements(By.xpath("//span[@class='Y1HWO0']"));
            int count=0;
            for (WebElement webElement : ratings) {
                if (Double.parseDouble(webElement.getText())<=rate) {
                    count++;
                }
            }
            System.out.println("count of items with rating less than or equal to "+rate+" stars is "+count);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void TitlesOfTheDiscountItemsWithMoreThanSelectedDiscount(int discountValue){
        try {
            List<WebElement> discounts = driver.findElements(By.xpath("//div[@class='KzDlHZ']/following::div[9]//span[contains(text(),'%')]"));
            int count=0;
            for (WebElement webElement : discounts) {
                count++;
                String s1 = webElement.getText();
                String[] s2 = s1.split("%");
                if (Integer.parseInt(s2[0])>discountValue) {
                    WebElement products = driver.findElement(By.xpath("(//span[contains(text(),'%')]/parent::div/parent::div/parent::div/parent::div/parent::div/div[1]/div[1])["+String.valueOf(count)+"]"));
                    System.out.println(" Titles and discount % of items with more than "+discountValue+"% discount: "+products.getText());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void select_customer_ratings(String rateOption){
        try {
            WebElement rate = driver.findElement(By.xpath("//*[contains(text(),'"+rateOption+"')]/preceding-sibling::div[@class='XqNaEv']"));
            rate.click();
            Thread.sleep(3000);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void print_Title_and_imageURL_asPerHighestReviews(int items){
        try {
            List<WebElement> reviews = driver.findElements(By.xpath("//span[@class='Wphh3N']"));
            ArrayList<Integer> arrlist1= new ArrayList<Integer>();
            for (WebElement webElement : reviews) {
                String arr1=webElement.getText();
                arr1 = arr1.replaceAll("[^\\d]","");
                arrlist1.add(Integer.parseInt(arr1));
            }
            Collections.sort(arrlist1);
    
            for(int i=arrlist1.size()-1;i>arrlist1.size()-1-items;i--){
                String formattedNumber="";
                    DecimalFormat formatter=new DecimalFormat("#,###");
                    formattedNumber = formatter.format(arrlist1.get(i));
                    System.out.println(formattedNumber);
                WebElement elem = driver.findElement(By.xpath("//span[@class='Wphh3N' and contains(text(),'"+formattedNumber+"')]/parent::div/parent::div/a[2]"));
                WebElement img = driver.findElement(By.xpath("//span[@class='Wphh3N' and contains(text(),'"+formattedNumber+"')]/parent::div/parent::div/a[2]/preceding-sibling::a/div/child::div/div/img"));
                System.out.println("Title of the item with highest number of reviews: "+elem.getText());
                System.out.println("image URL of the item with highest number of reviews: "+img.getAttribute("src"));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }



    @Test(description = "Go to www.flipkart.com. Search Washing Machine. Sort by popularity and print the count of items with rating less than or equal to 4 stars.",enabled = true)
    public static void TestCase01(){
        try {
            openURL("https://www.flipkart.com/");
            searchProduct("Washing Machine");
            sortByOptions("Popularity");
            countItemsWithRatingLessThanOrEqual(4.1);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Test(description = "Search iPhone, print the Titles and discount % of items with more than 17% discount",enabled = true)
    public static void TestCase02(){
        try {
            openURL("https://www.flipkart.com/");
            searchProduct("iPhone");
            TitlesOfTheDiscountItemsWithMoreThanSelectedDiscount(17);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Test
    public static void TestCase03(){
        try {
            openURL("https://www.flipkart.com/");
            searchProduct("Coffee Mug");
            select_customer_ratings("4");
            print_Title_and_imageURL_asPerHighestReviews(5);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @AfterSuite(enabled = true)
    public static void CloseBrowser()
    {
        driver.quit();
    }
}
