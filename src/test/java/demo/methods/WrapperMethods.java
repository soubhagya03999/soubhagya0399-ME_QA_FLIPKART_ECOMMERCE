package demo.methods;


import org.openqa.selenium.WebElement;

public class WrapperMethods {
    

    public static void advanceSearch(String keyword,WebElement searchbar,WebElement searchbutton) throws InterruptedException{
        searchbar.sendKeys(keyword);
        searchbutton.click();
        Thread.sleep(5000);
    }

}
