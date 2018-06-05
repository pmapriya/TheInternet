/**
 * 
 */
package com.qa.Testcase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
/**
 * @author Priyanka
 * 
 * Test Cases to verify functionality of The Internet application
 *
 */
public class TestCase 
{
	WebDriver driver;
	
	@BeforeMethod
	public void openBrowser()
	{
		
		driver=new FirefoxDriver();
		driver.get("http://the-internet.herokuapp.com/challenging_dom");
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
	}
	
	//@Test(priority=1)
	public void Verify_Title()
	{
		String Actual_title=driver.getTitle();
		
		String Expected_title="The Internet";
		
		Assert.assertEquals(Actual_title, Expected_title);
		
	}
	
	//@Test(priority=2)
	public void moveToGit()
	{
		driver.findElement(By.xpath("//a[@href='https://github.com/tourdedave/the-internet']/img")).click();
		
		String gitTitle=driver.getTitle();
		
		System.out.println("GitHub title is :--"+gitTitle);
		
		Assert.assertTrue(gitTitle.contains("GitHub"));
		
	}
	
	//@Test(priority=3)
	public void gitToInternet() throws Exception
	{
		driver.findElement(By.tagName("img")).click();
		
		Thread.sleep(3000);
		
		driver.navigate().back();
		
		String internetPageTitle=driver.getTitle();
		String Expected_title="The Internet";
		
		Assert.assertEquals(internetPageTitle, Expected_title);
	}
	
	//@Test(priority=4)
	public void listButton()
	{
		List<WebElement> buttons=driver.findElements(By.xpath("//a[contains(@id,'12416b6ecc4a')]"));
		
		System.out.println("Button name are :---");
		for(WebElement ele:buttons)
		{
			String buttonlabel=ele.getAttribute("innerHTML");
			
			System.out.println(buttonlabel);
		}
	}
	
	//@Test(priority=5)
	public void printbutton()
	{
		List<String> buttonName_actual=new ArrayList<String>();
		buttonName_actual.add("foo");
		buttonName_actual.add("bar");
		buttonName_actual.add("baz");
		buttonName_actual.add("qux");
		
		List<WebElement> buttons=driver.findElements(By.xpath("//a[contains(@id,'12416b6ecc4a')]"));
		
		
		int cnt=0;
		
		for(String bname:buttonName_actual) 
		{
			boolean found=false;
			for(WebElement Aname:buttons)
			{
				if(bname.equals(Aname.getText()))
				{
					//System.out.println(Aname.getText());
					found=true;
					cnt=cnt+1;
					break;
				}
			}
			
		}
		System.out.println(cnt+"matches no button "+buttons.size()+" test case passed");
		
		Assert.assertEquals(cnt, buttons.size());	
	}
	
	//@Test(priority=6)
	public void noOfLink()
	{
		List<WebElement> link =driver.findElements(By.tagName("a"));
		System.out.println("Total no, of links are :--"+link.size());
		
		Assert.assertEquals(link.size(),25);
	}
	
	//@Test(priority=7)
	public void tableFields()
	{
		List<String> field_actual=new ArrayList<String>();
		field_actual.add("Lorem");
		field_actual.add("Ipsum");
		field_actual.add("Dolor");
		field_actual.add("Sit");
		field_actual.add("Amet");
		field_actual.add("Diceret");
		field_actual.add("Action");
		
		List<WebElement> field=driver.findElements(By.xpath("//div[@id='content']/div/div/div/div[2]/table/thead/tr/th"));
		
		System.out.println("List of fields in table :--");
		for(WebElement ele:field)
		{
			System.out.println(ele.getAttribute("innerHTML"));
		}
		int cnt=0;
		for(String fname:field_actual) 
		{
			boolean found=false;
			for(WebElement name:field)
			{
				if(fname.equals(name.getText()))
				{
					found=true;
					cnt=cnt+1;
					break;
				}
			}
			
		}
		
		
		Assert.assertEquals(cnt, field.size());
		
	}
	@Test(priority=8)
	public void activeLinks() throws Exception
	{
		
		HttpURLConnection huc;
		List<WebElement> link =driver.findElements(By.tagName("a"));
		
		List<WebElement> activeFinal=new ArrayList<WebElement>();
		List<WebElement> active_links=new ArrayList<WebElement>();
		List<WebElement> broken_links=new ArrayList<WebElement>();
		
		for(int i=0;i<link.size();i++)
		{
			if(link.get(i).getAttribute("href") !=null &&  ! link.get(i).getAttribute("href").isEmpty())
			{
				active_links.add(link.get(i));
			}
			else
			{
				broken_links.add(link.get(i));
			}
		}
		
		System.out.println("Number of non empty are :---"+active_links.size());
	
		
		for(int j=0;j<active_links.size();j++)
		{
			huc=(HttpURLConnection) new URL(active_links.get(j).getAttribute("href")).openConnection();
			
			huc.setRequestMethod("HEAD");
			huc.connect();
			int code=huc.getResponseCode();
			
			if(code>=400)
			{
				broken_links.add(active_links.get(j));
			}
			else
			{
				activeFinal.add(active_links.get(j));
			}
			
			huc.disconnect();
		}
		
		System.out.println("No of active links:---"+activeFinal.size());
		
	}
	
	@AfterMethod
	public void closeBrowser()
	{
		driver.quit();
	}
	

}
