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

public class BindingAndRebinding {
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
	String checkerLoginURI = RestUtill.Utilities.getPropertyValue(configProperty, "checkerLoginURI");
	String createBorrowerUserURI = RestUtill.Utilities.getPropertyValue(configProperty, "createBorrowerUserURI");
	String UserId = RestUtill.Utilities.getPropertyValue(configProperty, "userId");
	String updateBorrowerUser = RestUtill.Utilities.getPropertyValue(configProperty, "updateBorrowerUser");
	String bindingOrReBindingURI = RestUtill.Utilities.getPropertyValue(configProperty, "bindingOrReBindingURI");

	public static Logger log = LogManager.getLogger(BindingAndRebinding.class.getName());
	SoftAssert softassert = new SoftAssert();
	@BeforeMethod(groups= {"smoke","regression"})
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

	@Test(priority = 1, groups= {"smoke","regression"})
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

	@Test(priority = 2, groups = { "smoke", "regression" })
	public void bindingUserWithValidPartner() throws SQLException {
		log.info("Verify by Binding User With Valid Partner");
		String bindingData = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
				.body(PayLoads.RequestPayloads.bindingAndRebinding(customerId))
				.log().all().when().post(bindingOrReBindingURI).then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		JsonPath bindingDataRes = reuseableMethods.rawToJson(bindingData);
		String responseCode = bindingDataRes.get("responseCode");
		String responseMessage = bindingDataRes.get("responseMessage");
		Boolean validationSuccess = bindingDataRes.get("validationSuccess");
		Boolean processingSuccess = bindingDataRes.get("processingSuccess");
		bindingId = bindingDataRes.get("responseObject.bindingId");
		String validationErrors = bindingDataRes.get("validationErrors");
		String processingErrors = bindingDataRes.get("processingErrors");
		String securityKey = bindingDataRes.get("securityKey");
		String callbackURL = bindingDataRes.get("callbackURL");
		Boolean validRequest = bindingDataRes.get("validRequest");
		softassert.assertEquals(responseCode, "0000");
		softassert.assertEquals(responseMessage, "Customer Binding Successfully");
		// Assert.assertEquals(validationSuccess, "true");
		// Assert.assertEquals(processingSuccess, "true");
		softassert.assertEquals(bindingId, bindingId);
		softassert.assertEquals(validationErrors, null);
		softassert.assertEquals(processingErrors, null);
		softassert.assertEquals(securityKey, "");
		softassert.assertEquals(callbackURL, null);
		// Assert.assertEquals(validRequest, "true");
		softassert.assertAll();
	}
	@Test(priority = 3, groups = { "smoke", "regression" })
	public void bindingUserWithInValidPartner() throws SQLException {
		log.info("Verify by Binding User With InValid Partner");
		String InvalidbindingData = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
				.body(PayLoads.RequestPayloads.invalidbinding(customerId,partnerCode))
				.log().all().when().post(bindingOrReBindingURI).then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		JsonPath InvalidBindingDataRes = reuseableMethods.rawToJson(InvalidbindingData);
		String responseCode = InvalidBindingDataRes.get("responseCode");
		String responseMessage = InvalidBindingDataRes.get("responseMessage");
		Boolean validationSuccess = InvalidBindingDataRes.get("validationSuccess");
		Boolean processingSuccess = InvalidBindingDataRes.get("processingSuccess");
		String invalidResMessage = InvalidBindingDataRes.get("responseObject");
		String validationErrors = InvalidBindingDataRes.get("validationErrors");
		String processingErrors = InvalidBindingDataRes.get("processingErrors");
		String securityKey = InvalidBindingDataRes.get("securityKey");
		String callbackURL = InvalidBindingDataRes.get("callbackURL");
		Boolean validRequest = InvalidBindingDataRes.get("validRequest");
		softassert.assertEquals(responseCode, "2007");
		softassert.assertEquals(responseMessage, "Partner is not Registred");
		// Assert.assertEquals(validationSuccess, "true");
		// Assert.assertEquals(processingSuccess, "true");
		softassert.assertEquals(invalidResMessage, "Partner is not Registred");
		softassert.assertEquals(validationErrors, null);
		softassert.assertEquals(processingErrors, null);
		softassert.assertEquals(securityKey, "");
		softassert.assertEquals(callbackURL, null);
		softassert.assertAll();
	}
		@Test(priority = 4,groups = { "smoke", "regression" })
		public void rebundingWithSameUser() throws SQLException {
			log.info("Verify by ReBinding with exixting User With Valid Partner");
			String DuplicateBindingData = given().log().all().spec(reuseableMethods.setup())
					.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
					.body(PayLoads.RequestPayloads.bindingAndRebinding(customerId))
					.log().all().when().post(bindingOrReBindingURI).then().log().all().assertThat().statusCode(200)
					.extract().response().asString();

			JsonPath duplicateBindingDataRes = reuseableMethods.rawToJson(DuplicateBindingData);
			String responseCode = duplicateBindingDataRes.get("responseCode");
			String responseMessage = duplicateBindingDataRes.get("responseMessage");
			Boolean validationSuccess = duplicateBindingDataRes.get("validationSuccess");
			Boolean processingSuccess = duplicateBindingDataRes.get("processingSuccess");
			String DuplicateBindingMessage = duplicateBindingDataRes.get("responseObject");
			String validationErrors = duplicateBindingDataRes.get("validationErrors");
			String processingErrors = duplicateBindingDataRes.get("processingErrors");
			String securityKey = duplicateBindingDataRes.get("securityKey");
			String callbackURL = duplicateBindingDataRes.get("callbackURL");
			Boolean validRequest = duplicateBindingDataRes.get("validRequest");
			softassert.assertEquals(responseCode, "2009");
			softassert.assertEquals(responseMessage, "There is already an active Binding exists");
			// Assert.assertEquals(validationSuccess, "true");
			// Assert.assertEquals(processingSuccess, "true");
			softassert.assertEquals(DuplicateBindingMessage, "There is already an active Binding exists");
			softassert.assertEquals(validationErrors, null);
			softassert.assertEquals(processingErrors, null);
			softassert.assertEquals(securityKey, "");
			softassert.assertEquals(callbackURL, null);
			// Assert.assertEquals(validRequest, "true");
			softassert.assertAll();
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
