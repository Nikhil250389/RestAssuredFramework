package APITestcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import com.Utilities.OracleConnection;

//import com.Utilities.Utilities;

//import com.Utilities.Utilities;

import RestUtill.RestUtils;
import ReuseableMethods.reuseableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.sql.SQLException;

public class UpdateCreditLimit {
	ReuseableMethods.OracleConnection oc;
	String UserLoginId;
	String Token;
	String resCifNumber;
	String customerId;
	String bindingId;
	String partnerCode = RestUtils.partnerCode();
	String username = RestUtils.getUserName();
	String bankAccountNo = RestUtils.bankAccountNo();
	String mobileNumber = RestUtils.mobileNumber();
	String email = RestUtils.getEmail();
	String identityNumber = RestUtils.identityNumber();
	String cifNumber = RestUtils.cifNumber();
	public static String configProperty = "./src/main/java/propertiesFiles/config.properties";
	public static RestUtill.Utilities utilities = new RestUtill.Utilities();
	String BaseURI = RestUtill.Utilities.getPropertyValue(configProperty, "baseURI");
	String baseURILoan = RestUtill.Utilities.getPropertyValue(configProperty, "baseURILoan");
	String checkerLoginURI = RestUtill.Utilities.getPropertyValue(configProperty, "checkerLoginURI");
	String createBorrowerUserURI = RestUtill.Utilities.getPropertyValue(configProperty, "createBorrowerUserURI");
	String UserId = RestUtill.Utilities.getPropertyValue(configProperty, "userId");
	String creditLimitURI = RestUtill.Utilities.getPropertyValue(configProperty, "creditLimitURI");
	String bindingOrReBindingURI = RestUtill.Utilities.getPropertyValue(configProperty, "bindingOrReBindingURI");
	String updateCreditLimitURI = RestUtill.Utilities.getPropertyValue(configProperty, "updateCreditLimitURI");
	
	public static Logger log = LogManager.getLogger(UpdateCreditLimit.class.getName());

	@BeforeMethod()
	public void userLogin() {
		RestAssured.baseURI = BaseURI;
		String response = given().log().all().spec(reuseableMethods.setup()).header("userId", UserId)
				.body(PayLoads.RequestPayloads.userLoginChecker()).log().all().when().post(checkerLoginURI).then().log()
				.all().assertThat().statusCode(200).body("responseObject.userDisplayName", equalTo("AhmadIrfan"))
				.extract().response().asString();

		JsonPath js = reuseableMethods.rawToJson(response);
		UserLoginId = js.get("responseObject.id");
		Token = js.getString("responseObject.loginToken");

	}

	@Test(priority = 1)
	public void createBorrowerUser() throws SQLException {
		log.info("Creating Borrower user with checker");
		String borrowerUser = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
				.body(PayLoads.RequestPayloads.createBorrowerBody(username, email, mobileNumber, identityNumber,
						cifNumber))
				.log().all().when().post(createBorrowerUserURI).then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		JsonPath createBorrowerRes = reuseableMethods.rawToJson(borrowerUser);
		String responseCode = createBorrowerRes.get("responseCode");
		String responseMessage = createBorrowerRes.get("responseMessage");
		resCifNumber = createBorrowerRes.get("responseObject.cifNumber");
		customerId = createBorrowerRes.get("responseObject.customerId");
		Assert.assertEquals(responseCode, "0000");
		Assert.assertEquals(responseMessage, "Successfully Registered Customer");
		Assert.assertEquals(resCifNumber, cifNumber);
		Assert.assertEquals(customerId, customerId);
	}

	@Test(priority = 2)
	public void bindingUserWithValidPartner() throws SQLException {
		log.info("Verify by Binding User With Valid Partner");
		String bindingData = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
				.body(PayLoads.RequestPayloads.bindingAndRebinding(customerId)).log().all().when()
				.post(bindingOrReBindingURI).then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath bindingDataRes = reuseableMethods.rawToJson(bindingData);
		String responseCode = bindingDataRes.get("responseCode");
		String responseMessage = bindingDataRes.get("responseMessage");
		bindingId = bindingDataRes.get("responseObject.bindingId");
		Assert.assertEquals(responseCode, "0000");
		Assert.assertEquals(responseMessage, "Customer Binding Successfully");
		Assert.assertEquals(bindingId, bindingId);

	}

	@Test(priority = 3)
	public void createCreditLimit() throws SQLException {
		log.info("Verify by create credit limit to User ");
		RestAssured.baseURI = baseURILoan;
		String creditLimitData = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
				.body(PayLoads.RequestPayloads.creditLimitPayload(customerId)).log().all().when()
				.post(creditLimitURI).then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath bindingDataRes = reuseableMethods.rawToJson(creditLimitData);
		String responseCode = bindingDataRes.get("responseCode");
		String responseMessage = bindingDataRes.get("responseMessage");
		Boolean validationSuccess = bindingDataRes.get("validationSuccess");
		Boolean processingSuccess = bindingDataRes.get("processingSuccess");
		String crCifNumber = bindingDataRes.get("responseObject.cifNumber");
		String crCustomerId = bindingDataRes.get("responseObject.customerId");
		String crPkNumber = bindingDataRes.get("responseObject.pkNumber");
		String creditLimitId = bindingDataRes.get("responseObject.creditLimitId");
		Integer creditLimitAmount = bindingDataRes.get("responseObject.creditLimitAmount");
		Integer creditLimitAvailable = bindingDataRes.get("responseObject.creditLimitAvailable");
		String creditLimitStatus = bindingDataRes.get("responseObject.creditLimitStatus");
		String validationErrors = bindingDataRes.get("validationErrors");
		String processingErrors = bindingDataRes.get("processingErrors");
		String securityKey = bindingDataRes.get("securityKey");
		String callbackURL = bindingDataRes.get("callbackURL");
		Boolean validRequest = bindingDataRes.get("validRequest");
		Assert.assertEquals(responseCode, "0000");
		Assert.assertEquals(responseMessage, "Success create limit");
		// Assert.assertEquals(validationSuccess, "true");
		// Assert.assertEquals(processingSuccess, "true");
		Assert.assertEquals(crCifNumber, resCifNumber);
		Assert.assertEquals(crCustomerId, customerId);
		Assert.assertEquals(crPkNumber, "TEST_PK_NUMBER");
		Assert.assertEquals(creditLimitStatus, "ACTIVE");
		Assert.assertEquals(validationErrors, null);
		Assert.assertEquals(processingErrors, null);
		Assert.assertEquals(securityKey, "");
		Assert.assertEquals(callbackURL, null);
		// Assert.assertEquals(validRequest, "true");

	}
	@Test(priority = 4)
	public void createCreditLimitForSameUser() throws SQLException {
		log.info("Verify by create credit limit to User multiple time ");
		RestAssured.baseURI = baseURILoan;
		String multipleCreditLimitData = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
				.body(PayLoads.RequestPayloads.creditLimitPayload(customerId)).log().all().when()
				.post(creditLimitURI).then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath multipleCreditLimit = reuseableMethods.rawToJson(multipleCreditLimitData);
		String responseCode = multipleCreditLimit.get("responseCode");
		String responseMessage = multipleCreditLimit.get("responseMessage");
		Boolean validationSuccess = multipleCreditLimit.get("validationSuccess");
		Boolean processingSuccess = multipleCreditLimit.get("processingSuccess");
		String responceObject = multipleCreditLimit.get("responseObject");
		String validationErrors = multipleCreditLimit.get("validationErrors");
		String processingErrors = multipleCreditLimit.get("processingErrors");
		String securityKey = multipleCreditLimit.get("securityKey");
		String callbackURL = multipleCreditLimit.get("callbackURL");
		Boolean validRequest = multipleCreditLimit.get("validRequest");
		Assert.assertEquals(responseCode, "3001");
		Assert.assertEquals(responseMessage, "Credit limit already created of given user");
		// Assert.assertEquals(validationSuccess, "true");
		// Assert.assertEquals(processingSuccess, "true");
		Assert.assertEquals(responceObject, null);
		Assert.assertEquals(validationErrors, null);
		Assert.assertEquals(processingErrors, null);
		Assert.assertEquals(securityKey, "");
		Assert.assertEquals(callbackURL, null);
		// Assert.assertEquals(validRequest, "true");

	}
	
	@Test(priority = 5)
	public void UpdateCreditLimitForUser() throws SQLException {
		log.info("Verify by Update credit limit of User ");
		RestAssured.baseURI = baseURILoan;
		String updateCreditLimitData = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
				.body(PayLoads.RequestPayloads.updateCreditLimitPayload(customerId)).log().all().when()
				.post(updateCreditLimitURI).then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath updateCreditLimit = reuseableMethods.rawToJson(updateCreditLimitData);
		String responseCode = updateCreditLimit.get("responseCode");
		String responseMessage = updateCreditLimit.get("responseMessage");
		Boolean validationSuccess = updateCreditLimit.get("validationSuccess");
		Boolean processingSuccess = updateCreditLimit.get("processingSuccess");
		String upCifNumber = updateCreditLimit.get("responseObject.cifNumber");
		String upCustomerId = updateCreditLimit.get("responseObject.customerId");
		String upPkNumber = updateCreditLimit.get("responseObject.pkNumber");
		String creditLimitId = updateCreditLimit.get("responseObject.creditLimitId");
		Integer creditLimitAmount = updateCreditLimit.get("responseObject.creditLimitAmount");
		Integer creditLimitAvailable = updateCreditLimit.get("responseObject.creditLimitAvailable");
		String creditLimitStatus = updateCreditLimit.get("responseObject.creditLimitStatus");
		String validationErrors = updateCreditLimit.get("validationErrors");
		String processingErrors = updateCreditLimit.get("processingErrors");
		String securityKey = updateCreditLimit.get("securityKey");
		String callbackURL = updateCreditLimit.get("callbackURL");
		Boolean validRequest = updateCreditLimit.get("validRequest");
		Assert.assertEquals(responseCode, "0000");
		Assert.assertEquals(responseMessage, "Customer limit has been updated");
		// Assert.assertEquals(validationSuccess, "true");
		// Assert.assertEquals(processingSuccess, "true");
		Assert.assertEquals(upCifNumber, resCifNumber);
		Assert.assertEquals(upCustomerId, customerId);
		Assert.assertEquals(upPkNumber, "TEST_PK_NUMBER");
		Assert.assertEquals(creditLimitStatus, "ACTIVE");
		Assert.assertEquals(validationErrors, null);
		Assert.assertEquals(processingErrors, null);
		Assert.assertEquals(securityKey, "");
		Assert.assertEquals(callbackURL, null);
		// Assert.assertEquals(validRequest, "true");


	}
	@AfterClass(groups = { "smoke", "regression" })
	public void cleanUp() throws SQLException {
		oc = new ReuseableMethods.OracleConnection();
		oc.setUpConnection();
		log.info("Deleting Above borrower user from Borrower Table");
		System.out.println("Customer Id of tested borrouer user is" + ">>>" + customerId);
		oc.cleanUp("BORROWERUSER", "CUSTOMERID", customerId);
		System.out.println("Mobile NO of tested borrouer user is" + ">>> " + mobileNumber);
		log.info("Deleting Above borrower user from Basic User Table");
        oc.cleanUp("BASICUSER", "MOBILENUMBER", mobileNumber);
	}
}
