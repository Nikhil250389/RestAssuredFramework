package APITestcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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

public class BorrowerUser {
	ReuseableMethods.OracleConnection oc;
	String UserLoginId;
	String Token;
	String resCifNumber;
	String customerId;
	String username = RestUtils.getFirstName();
	String bankAccountNo = RestUtils.bankAccountNo();
	String mobileNumber = RestUtils.mobileNumber();
	String email = RestUtils.getEmail();
	String identityNumber = RestUtils.identityNumber();
	String cifNumber = RestUtils.cifNumber();
	public static String configProperty = "./src/main/java/propertiesFiles/config.properties";
	public static RestUtill.Utilities utilities = new RestUtill.Utilities();
	String BaseURI = RestUtill.Utilities.getPropertyValue(configProperty, "baseURI");
	String checkerLoginURI = RestUtill.Utilities.getPropertyValue(configProperty, "checkerLoginURI");
	String createBorrowerUserURI = RestUtill.Utilities.getPropertyValue(configProperty, "createBorrowerUserURI");
	String UserId = RestUtill.Utilities.getPropertyValue(configProperty, "userId");
	String updateBorrowerUser = RestUtill.Utilities.getPropertyValue(configProperty, "updateBorrowerUser");
	String getLenderUserDetails = RestUtill.Utilities.getPropertyValue(configProperty, "getLenderUserDetails");

	public static Logger log = LogManager.getLogger(BorrowerUser.class.getName());
	SoftAssert softassert = new SoftAssert();

	@BeforeMethod(groups = { "smoke", "regression" })
	public void userLogin() throws SQLException {
		RestAssured.baseURI = BaseURI;
		String response = given().log().all().spec(reuseableMethods.setup()).header("userId", UserId)
				.body(PayLoads.RequestPayloads.userLoginChecker()).log().all().when().post(checkerLoginURI).then().log()
				.all().assertThat().statusCode(200).body("responseObject.userDisplayName", equalTo("AhmadIrfan"))
				.extract().response().asString();

		JsonPath js = reuseableMethods.rawToJson(response);
		UserLoginId = js.get("responseObject.id");
		Token = js.getString("responseObject.loginToken");
		oc = new ReuseableMethods.OracleConnection();
		oc.setUpConnection();
	}

	@Test(priority = 1, groups = { "smoke", "regression" })
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
		Boolean validationSuccess = createBorrowerRes.get("validationSuccess");
		Boolean processingSuccess = createBorrowerRes.get("processingSuccess");
		resCifNumber = createBorrowerRes.get("responseObject.cifNumber");
		customerId = createBorrowerRes.get("responseObject.customerId");
		String validationErrors = createBorrowerRes.get("validationErrors");
		String processingErrors = createBorrowerRes.get("processingErrors");
		String securityKey = createBorrowerRes.get("securityKey");
		String callbackURL = createBorrowerRes.get("callbackURL");
		Boolean validRequest = createBorrowerRes.get("validRequest");
		softassert.assertEquals(responseCode, "0000");
		softassert.assertEquals(responseMessage, "Successfully Registered Customer");
		softassert.assertTrue(validationSuccess.equals(true));
		softassert.assertTrue(processingSuccess.equals(true));
		softassert.assertEquals(resCifNumber, cifNumber);
		softassert.assertEquals(customerId, customerId);
		softassert.assertEquals(validationErrors, null);
		softassert.assertEquals(processingErrors, null);
		softassert.assertEquals(securityKey, "");
		softassert.assertEquals(callbackURL, null);
		softassert.assertTrue(validRequest.equals(true));
		softassert.assertAll();
	}

	@Test(priority = 2, dependsOnMethods = "createBorrowerUser", groups = { "smoke", "regression" })
	public void createBorrowerWithDuplicateValues() throws SQLException {
		log.info("Creating Borrower user create Borrower With Duplicate User Name");
		String DuplicateUser = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
				.body(PayLoads.RequestPayloads.createBorrowerBody(username, email, mobileNumber, identityNumber,
						cifNumber))
				.log().all().when().post(createBorrowerUserURI).then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		JsonPath duplicateBorrowerRes = reuseableMethods.rawToJson(DuplicateUser);
		String responseCode = duplicateBorrowerRes.get("responseCode");
		String responseMessage = duplicateBorrowerRes.get("responseMessage");
		Boolean validationSuccess = duplicateBorrowerRes.get("validationSuccess");
		Boolean processingSuccess = duplicateBorrowerRes.get("processingSuccess");
		String responseObject = duplicateBorrowerRes.get("responseObject");
		String cifNumber = duplicateBorrowerRes.get("validationErrors.cifNumber");
		String identityNumber = duplicateBorrowerRes.get("validationErrors.identityNumber");
		String mobile = duplicateBorrowerRes.get("validationErrors.mobile");
		String email = duplicateBorrowerRes.get("validationErrors.email");
		String processingErrors = duplicateBorrowerRes.get("processingErrors");
		String securityKey = duplicateBorrowerRes.get("securityKey");
		String callbackURL = duplicateBorrowerRes.get("callbackURL");
		Boolean validRequest = duplicateBorrowerRes.get("validRequest");

		softassert.assertEquals(responseCode, "1010");
		softassert.assertEquals(responseMessage, "Invalid Request");
		softassert.assertTrue(processingSuccess.equals(false));
		softassert.assertTrue(validationSuccess.equals(false));
		softassert.assertEquals(responseObject, null);
		softassert.assertEquals(cifNumber, "CIF Number Already Exists");
		softassert.assertEquals(identityNumber, "Identity Number Already Exists");
		softassert.assertEquals(mobile, "Mobile Number Already Exists");
		softassert.assertEquals(email, "Email already exist");
		softassert.assertEquals(processingErrors, null);
		softassert.assertEquals(securityKey, "");
		softassert.assertEquals(callbackURL, null);
		softassert.assertTrue(validRequest.equals(true));
		softassert.assertAll();
	}

	@Test(priority = 3, dependsOnMethods = "createBorrowerUser", groups = { "smoke", "regression" })
	public void checkExistingBorrowerUser() throws SQLException {
		log.info("Check existing Borrower user  With Duplicate Cif Number");
		String existingUser = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
				.body(PayLoads.RequestPayloads.createBorrowerBody(username, email, mobileNumber, identityNumber,
						resCifNumber))
				.log().all().when().post(createBorrowerUserURI).then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		JsonPath duplicateCifNumberRes = reuseableMethods.rawToJson(existingUser);
		String responseCode = duplicateCifNumberRes.get("responseCode");
		String responseMessage = duplicateCifNumberRes.get("responseMessage");
		Boolean validationSuccess = duplicateCifNumberRes.get("validationSuccess");
		Boolean processingSuccess = duplicateCifNumberRes.get("processingSuccess");
		String responseObject = duplicateCifNumberRes.get("responseObject");
		String cifNumber = duplicateCifNumberRes.get("validationErrors.cifNumber");
		// String identityNumber =
		// duplicateCifNumberRes.get("validationErrors.identityNumber");
		// String mobile = duplicateCifNumberRes.get("validationErrors.mobile");
		// String email = duplicateCifNumberRes.get("validationErrors.email");
		String processingErrors = duplicateCifNumberRes.get("processingErrors");
		String securityKey = duplicateCifNumberRes.get("securityKey");
		String callbackURL = duplicateCifNumberRes.get("callbackURL");
		Boolean validRequest = duplicateCifNumberRes.get("validRequest");

		softassert.assertEquals(responseCode, "1010");
		softassert.assertEquals(responseMessage, "Invalid Request");
		softassert.assertTrue(processingSuccess.equals(false));
		softassert.assertTrue(validationSuccess.equals(false));
		softassert.assertEquals(responseObject, null);
		softassert.assertEquals(cifNumber, "CIF Number Already Exists");
		softassert.assertEquals(processingErrors, null);
		softassert.assertEquals(securityKey, "");
		softassert.assertEquals(callbackURL, null);
		softassert.assertTrue(validRequest.equals(true));
		softassert.assertAll();
	}

	@Test(priority = 4, dependsOnMethods = "createBorrowerUser", groups = { "smoke", "regression" })
	public void updatedBorrowerUser() throws SQLException {
		log.info("Check by update existing Borrower user ");
		String updateUser = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
				.body(PayLoads.RequestPayloads.updateBorrowerUser(customerId, email, mobileNumber, identityNumber))
				.log().all().when().put(updateBorrowerUser).then().log().all().assertThat().statusCode(200).extract()
				.response().asString();

		JsonPath UpdateUserRes = reuseableMethods.rawToJson(updateUser);
		String responseCode = UpdateUserRes.get("responseCode");
		String responseMessage = UpdateUserRes.get("responseMessage");
		Boolean validationSuccess = UpdateUserRes.get("validationSuccess");
		Boolean processingSuccess = UpdateUserRes.get("processingSuccess");
		String upCifNumber = UpdateUserRes.get("responseObject.cifNumber");
		String upCustomerId = UpdateUserRes.get("responseObject.customerId");
		String processingErrors = UpdateUserRes.get("processingErrors");
		String securityKey = UpdateUserRes.get("securityKey");
		String callbackURL = UpdateUserRes.get("callbackURL");
		Boolean validRequest = UpdateUserRes.get("validRequest");
		softassert.assertEquals(responseCode, "0000");
		softassert.assertEquals(responseMessage, "Successfully Registered Customer");
		softassert.assertTrue(processingSuccess.equals(true));
		softassert.assertTrue(validationSuccess.equals(true));
		softassert.assertEquals(upCifNumber, resCifNumber);
		softassert.assertEquals(processingErrors, null);
		softassert.assertEquals(securityKey, "");
		softassert.assertEquals(callbackURL, null);
		softassert.assertTrue(validRequest.equals(true));
		softassert.assertAll();

	}

	@AfterClass(groups = { "smoke", "regression" })
	public void cleanUp() throws SQLException {
	
		log.info("Deleting Above borrower user from Borrower Table");
		System.out.println("Customer Id of tested borrouer user is" + ">>>" + customerId);
		oc.cleanUp("BORROWERUSER", "CUSTOMERID", customerId);
		System.out.println("Mobile NO of tested borrouer user is" + ">>> " + mobileNumber);
		log.info("Deleting Above borrower user from Basic User Table");
        oc.cleanUp("BASICUSER", "MOBILENUMBER", mobileNumber);
	}
}
