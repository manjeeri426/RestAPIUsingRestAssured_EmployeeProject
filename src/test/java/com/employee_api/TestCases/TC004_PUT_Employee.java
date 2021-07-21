package com.employee_api.TestCases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employee_api.Base.TestBase;
import com.employee_api.Utillities.RestUtils;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC004_PUT_Employee extends TestBase {

	String empname=RestUtils.empName();
	String empsalary=RestUtils.empSalary();
	String empage=RestUtils.empAge();
	
	RequestSpecification httprequest;
	Response response;
	
	@BeforeClass
	void Post_Employee_data() throws InterruptedException
	{
		logger.info("Started Test Case 003");
		
		RestAssured.baseURI="http://dummy.restapiexample.com/api/v1";
		
		httprequest=RestAssured.given();
		
		JSONObject jsondata=new JSONObject();
		
		jsondata.put("name",empname);
		jsondata.put("salary",empsalary);
		jsondata.put("age",empage);
		
		//httprequest.header("Content-Type","application/JSON");
		httprequest.header("Content-Type", "application/json");

		
		httprequest.body(jsondata.toJSONString());
		
		response=httprequest.request(Method.PUT,"/update/"+empId);
		
		String responsebody=response.getBody().asString();
		
		System.out.println("Response Body is" + responsebody);
		
		Thread.sleep(5000);
		
	}
	
	@Test
	void checkStatusCode()
	{
		logger.info("Checking status code");
		System.out.println("Check stats code" + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(),200);
	}
	
    @Test
	void checkStatusLine()
	{
	logger.info("Checking Status line");
	String statusline=response.getStatusLine();
	
	System.out.println("Status line is"+ statusline);
	Assert.assertEquals(statusline,"HTTP/1.1 200 OK");
	
	}
    
    @Test
    void checkResponseBody()
    {
    	logger.info("Checking Response Body");
    	String responsebody=response.getBody().asString();
    	Assert.assertTrue(responsebody.contains(empname));
    	Assert.assertTrue(responsebody.contains(empsalary));
    	Assert.assertTrue(responsebody.contains(empage));
    }
    @Test
	void checkResponseTime()
	{
	logger.info("Checking response time");
	
	long responsetime=response.getTime();
	System.out.println("response time is"+ responsetime);
	
	if(responsetime>2000)
	      logger.warn("Response time is greater than 2000");
	
	
	Assert.assertTrue(responsetime<10000);
	}
	
	
	@Test
	void contentType()
	{
	
	logger.info("Checking Content Type");
	String contentype=response.header("Content-Type");

	System.out.println("Content Type is"+ contentype);
	Assert.assertEquals(contentype,"application/json");
    }
	
	@Test
	void checkServerType()
	{
		logger.info("Checking server type");
		String server=response.getHeader("server");
		System.out.println("Server Type is"+ server);

		Assert.assertEquals(server,"cloudflare");
	}
	
	@Test
	void checkContentEncoding()
	{
		logger.info("Checking content encoding");
		
		String contentencoding=response.header("Content-Encoding");
		
		System.out.println("Content encoding is"+ contentencoding );
		Assert.assertEquals(contentencoding,"gzip");
	}
	
	@Test
	void checkContentLength()
	{
		logger.info("Checking content length");
		String contentlength=response.header("Content-Length");
		int contentlen=Integer.parseInt(contentlength);
		System.out.println("Content-length is"+ contentlen );
		if(contentlen<100)
			logger.warn("content len is less than 100");
		Assert.assertTrue(contentlen>100);
	}
	
	
	  @AfterClass
	  void teardown()
	  {
		  logger.info("Test Cases finished");
	  }

	
}
