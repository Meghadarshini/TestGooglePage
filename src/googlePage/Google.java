package googlePage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Google {
	public WebDriver driver;
	Wait<WebDriver> wait;	
	SoftAssert softAssert = new SoftAssert();
	String searchString = "game";
	int length = searchString.length();
	
	@Test(groups = { "loadPage" })
	public void loadGooglePage()
	{
		driver.get("https://www.google.com");
	}
	
	@Test(groups = { "search" }, dependsOnMethods = { "loadGooglePage" })
	public void searchText()
	{
		driver.findElement(By.id("lst-ib")).sendKeys(searchString);
		driver.findElement(By.name("btnG")).click();
	}

	@Test(groups = { "validateResult" }, dependsOnMethods = { "searchText" } )
	public void validateSearchResults()
	{
		WebElement result = driver.findElement(By.id("rso"));
		List<WebElement> resultList = result.findElements(By.className("srg"));
		for(WebElement resultVal : resultList)
		{
			List<WebElement> resVal = resultVal.findElements(By.className("g"));
			for(WebElement resultValue : resVal)
			{
				String resultString = resultValue.findElement(By.className("r")).getText();
				String[] resultWords = resultString.split(" ");
				int found = 0;
				for(String str : resultWords)
				{
					if(str.length() == length)
					{
						if(str.matches(searchString))
						{
							found = 1;
							break;
						}
						else if(str.toUpperCase().matches(searchString))
						{
							found = 1; 
							break;
						}
						else
						{
							for(int i = 0; i < length; i++)
							{
								String subString = "";
								if(searchString.charAt(i) != str.charAt(i))
								{
									subString += searchString.charAt(i);
									subString = subString.toUpperCase();
									char subStringCharacter = subString.charAt(i);
									if(subStringCharacter != str.charAt(i))
									{
										found = 0;
										break;
									}	
									else
									{
										found = 1;
									}
									
								}
								if(found == 0)
								{
									break;
								}
								
							}
							
						}
					}
					
					if(str.length() > length)
					{
						String customizedString = str.substring(0, length);
						if(customizedString.matches(searchString))
						{
							found = 1;
							break;
						}
						if(searchString.toUpperCase().matches(customizedString))
						{
							found = 1;
							break;
						}
						else
						{
							for(int i = 0; i < length; i++)
							{
								String subString = "";
								if(searchString.charAt(i) != customizedString.charAt(i))
								{
									subString += searchString.charAt(i);
									subString = subString.toUpperCase();
									char subStringCharacter = subString.charAt(i);
									if(subStringCharacter != customizedString.charAt(i)) 
									{
										found = 0;
										break;
									}
									else
									{
										found = 1;
									}
									
								}
								if(found == 0)
								{
									break;
								}
							}
							
						}
						
						
					}
					
					if(str.length() < length)
					{
						found = 0;
					}
						
				}
				
				if(found == 0)
				{
					System.out.println(resultString+" is an Incorrect search result is displayed");
				}
				else
				{
					System.out.println(resultString+" is a correct search result");
				}				
				
			}			
		}		
	}
	
	
	
	
	@BeforeClass(alwaysRun = true)
	public void beforeMethod()
	{
		driver = new FirefoxDriver();
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterMethod()
	{
		driver.quit();
	}

}
