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

public class LoanDisburesment {
	ReuseableMethods.OracleConnection oc;
	String UserLoginId;
	String Token;
	String resCifNumber;
	String customerId;
	String bindingId;
	String creditLimitId;
	String partnerCode = RestUtils.partnerCode();
	String username = RestUtils.getUserName();
	String bankAccountNo = RestUtils.bankAccountNo();
	String mobileNumber = RestUtils.mobileNumber();
	String email = RestUtils.getEmail();
	String identityNumber = RestUtils.identityNumber();
	String cifNumber = RestUtils.cifNumber();
	String TrxId = RestUtils.TrxId();
	Integer creditLimitAmount;

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
	String loanDisbursementUri = RestUtill.Utilities.getPropertyValue(configProperty, "loanDisbursementUri");

	public static Logger log = LogManager.getLogger(LoanDisburesment.class.getName());
	SoftAssert softassert = new SoftAssert();

	@BeforeMethod(groups = { "smoke", "regression" })
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
		softassert.assertEquals(responseCode, "0000");
		softassert.assertEquals(responseMessage, "Successfully Registered Customer");
		softassert.assertEquals(resCifNumber, cifNumber);
		softassert.assertEquals(customerId, customerId);
		softassert.assertAll();
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
		softassert.assertEquals(responseCode, "0000");
		softassert.assertEquals(responseMessage, "Customer Binding Successfully");
		softassert.assertEquals(bindingId, bindingId);
		softassert.assertAll();

	}

	@Test(priority = 3)
	public void createCreditLimit() throws SQLException {
		log.info("Verify by create credit limit to User ");
		RestAssured.baseURI = baseURILoan;
		String creditLimitData = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
				.body(PayLoads.RequestPayloads.creditLimitPayload(customerId)).log().all().when().post(creditLimitURI)
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath bindingDataRes = reuseableMethods.rawToJson(creditLimitData);
		String responseCode = bindingDataRes.get("responseCode");
		String responseMessage = bindingDataRes.get("responseMessage");
		Boolean validationSuccess = bindingDataRes.get("validationSuccess");
		Boolean processingSuccess = bindingDataRes.get("processingSuccess");
		String crCifNumber = bindingDataRes.get("responseObject.cifNumber");
		String crCustomerId = bindingDataRes.get("responseObject.customerId");
		String crPkNumber = bindingDataRes.get("responseObject.pkNumber");
		creditLimitId = bindingDataRes.get("responseObject.creditLimitId");
		Integer creditLimitAmount = bindingDataRes.get("responseObject.creditLimitAmount");
		Integer creditLimitAvailable = bindingDataRes.get("responseObject.creditLimitAvailable");
		String creditLimitStatus = bindingDataRes.get("responseObject.creditLimitStatus");
		String validationErrors = bindingDataRes.get("validationErrors");
		String processingErrors = bindingDataRes.get("processingErrors");
		String securityKey = bindingDataRes.get("securityKey");
		String callbackURL = bindingDataRes.get("callbackURL");
		Boolean validRequest = bindingDataRes.get("validRequest");
		softassert.assertEquals(responseCode, "0000");
		softassert.assertEquals(responseMessage, "Success create limit");
		softassert.assertEquals(crCifNumber, resCifNumber);
		softassert.assertEquals(crCustomerId, customerId);
		softassert.assertEquals(crPkNumber, "TEST_PK_NUMBER");
		softassert.assertEquals(creditLimitStatus, "ACTIVE");
		softassert.assertAll();

	}

	@Test(priority = 4)
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
		softassert.assertEquals(responseCode, "0000");
		softassert.assertEquals(responseMessage, "Customer limit has been updated");
		softassert.assertEquals(upCifNumber, resCifNumber);
		softassert.assertEquals(upCustomerId, customerId);
		softassert.assertEquals(upPkNumber, "TEST_PK_NUMBER");
		softassert.assertEquals(creditLimitStatus, "ACTIVE");
		softassert.assertAll();

	}

	@Test(priority = 5, groups = { "smoke", "regression" })
	public void loanDirsbumentPositive() throws SQLException {
		log.info("Verify by perform loan disbursement for User ");
		RestAssured.baseURI = baseURILoan;
		String disrusbmentData = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token).header("userId", UserLoginId)
				.body(PayLoads.RequestPayloads.loanDirsbument(bindingId, TrxId)).log().all().when()
				.post(loanDisbursementUri).then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath disrusbmentDataRes = reuseableMethods.rawToJson(disrusbmentData);
		String responseCode = disrusbmentDataRes.get("responseCode");
		String responseMessage = disrusbmentDataRes.get("responseMessage");
		Boolean validationSuccess = disrusbmentDataRes.get("validationSuccess");
		Boolean processingSuccess = disrusbmentDataRes.get("processingSuccess");
		String loanPartnerTrxId = disrusbmentDataRes.get("responseObject.partnerTrxId");
		String loanAccountNumber = disrusbmentDataRes.get("responseObject.loanAccountNumber");
        String loanCreditLimitId = disrusbmentDataRes.get("responseObject.creditLimitId");
		Integer loanCreditLimitAmount = disrusbmentDataRes.get("responseObject.creditLimitAmount");
		Integer creditLimitAvailable = disrusbmentDataRes.get("responseObject.creditLimitAvailable");
		String creditLimitStatus = disrusbmentDataRes.get("responseObject.creditLimitStatus");
		String validationErrors = disrusbmentDataRes.get("validationErrors");
		String processingErrors = disrusbmentDataRes.get("processingErrors");
		String securityKey = disrusbmentDataRes.get("securityKey");
		String callbackURL = disrusbmentDataRes.get("callbackURL");
		Boolean validRequest = disrusbmentDataRes.get("validRequest");
		softassert.assertEquals(responseCode, "0000");
		softassert.assertEquals(responseMessage, "Disbursement is success");
		softassert.assertTrue(validationSuccess.equals(true));
		softassert.assertTrue(processingSuccess.equals(true));
		softassert.assertEquals(loanPartnerTrxId, TrxId);
		softassert.assertEquals(loanCreditLimitId, creditLimitId);
		softassert.assertEquals(creditLimitStatus, "ACTIVE");
		softassert.assertEquals(validationErrors, null);
		softassert.assertEquals(processingErrors, null);
		softassert.assertEquals(securityKey, "");
		softassert.assertEquals(callbackURL, null);
		softassert.assertTrue(validRequest.equals(true));
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