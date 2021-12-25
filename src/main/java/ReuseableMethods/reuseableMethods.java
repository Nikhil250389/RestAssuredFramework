package ReuseableMethods;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;
import org.testng.annotations.BeforeClass;

import io.restassured.path.json.JsonPath;
public class reuseableMethods {

public	static RequestSpecification requestSpec;
	static ResponseSpecification responseSpec;

	public static String configProperty = "./src/main/java/propertiesFiles/config.properties";
	public static RestUtill.Utilities utilities = new RestUtill.Utilities();
//	String BaseURI = RestUtill.Utilities.getPropertyValue(configProperty, "baseURI");
	static String ContentType = RestUtill.Utilities.getPropertyValue(configProperty, "Content-Type");
	static String TenantCode = RestUtill.Utilities.getPropertyValue(configProperty, "tenantCode");
//	static String UserId = RestUtill.Utilities.getPropertyValue(configProperty, "userId");
	static String locale = RestUtill.Utilities.getPropertyValue(configProperty, "locale");
	static	String requestId = RestUtill.Utilities.getPropertyValue(configProperty, "requestId");
	public static Logger log = LogManager.getLogger(reuseableMethods.class.getName());

//	@BeforeClass
	public static RequestSpecification setup()
	{
		RequestSpecBuilder req_Builder=new RequestSpecBuilder();
			
		//req_Builder.addParam("myparam", "paramValue1");
		req_Builder.addHeader("tenantCode", TenantCode);
	//	req_Builder.addHeader("userId", UserId);
		req_Builder.addHeader("locale", locale);
		req_Builder.addHeader("requestId", requestId);
	//	req_Builder.setBaseUri(BaseURI);
		req_Builder.setContentType(ContentType);
		requestSpec=req_Builder.build();
		
		ResponseSpecBuilder res_Builder=new ResponseSpecBuilder();
		res_Builder.expectStatusCode(200);
		res_Builder.expectStatusLine("HTTP/1.1 200 OK");
		res_Builder.expectHeader("Content-Type","application/json; charset=utf-8");
		res_Builder.expectHeader("Content-Encoding","gzip");
		responseSpec=res_Builder.build();
		return requestSpec;
		
	}
	
	public static JsonPath rawToJson(String response) {
		
		JsonPath js= new JsonPath(response);
		return js;
		
		
		
	}
	
}
