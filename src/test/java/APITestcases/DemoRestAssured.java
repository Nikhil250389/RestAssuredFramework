package APITestcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import RestUtill.RestUtils;
import ReuseableMethods.reuseableMethods;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.sql.SQLException;

public class DemoRestAssured {
	
	String UserLoginId;
	String userEmail;
	String userName;
	String customerId;
	String userId;
	String username = RestUtils.getUserName();
	String updateUsername = RestUtils.getUserName();
	String email = RestUtils.getEmail();
	String updateEmail = RestUtils.getEmail();
	public static String configProperty = "./src/main/java/propertiesFiles/config.properties";
	public static RestUtill.Utilities utilities = new RestUtill.Utilities();
	String BaseURI = RestUtill.Utilities.getPropertyValue(configProperty, "baseURI");
	String Token = RestUtill.Utilities.getPropertyValue(configProperty, "Token");
	String createUserURI = RestUtill.Utilities.getPropertyValue(configProperty, "createUserURI");
	String viewCreateUser = RestUtill.Utilities.getPropertyValue(configProperty, "viewCreateUser");
	String updateUserURI = RestUtill.Utilities.getPropertyValue(configProperty, "updateUserURI");
	String deleteUserURI = RestUtill.Utilities.getPropertyValue(configProperty, "deleteUserURI");

	public static Logger log = LogManager.getLogger(DemoRestAssured.class.getName());
	SoftAssert softassert = new SoftAssert();
	


	@Test(priority = 1, groups= {"smoke","regression"})
	public void createUser() throws SQLException {
		log.info("Creating user for Interview Demo");
		String createUserResponce = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token)
				.body(PayLoads.RequestPayloads.createUserBody(username, email))
				.log().all().when().post(createUserURI).then().log().all().assertThat().statusCode(201)
				.extract().response().asString();

		JsonPath userResponce = reuseableMethods.rawToJson(createUserResponce);
		String meta = userResponce.get("meta");
		String userId = userResponce.get("data.id");
		String	userName = userResponce.get("data.name");
		String userEmail = userResponce.get("data.email");
		String	userGender = userResponce.get("data.gender");
		String	userStatus = userResponce.get("data.active");
		
		softassert.assertEquals(meta, null);
		softassert.assertEquals(userName, username);
		softassert.assertEquals(userEmail, email);
		softassert.assertEquals(userGender, "male");
		softassert.assertEquals(userStatus, "active");
		softassert.assertAll();
	}

	@Test(priority = 2, groups = { "smoke", "regression" })
	public void getCreateUser() throws SQLException {
		log.info("Verify by View above created User");
		String viewCreateUserData = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token)
				.log().all().when().get(viewCreateUser+userId).then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		JsonPath viewCreatedUserRes = reuseableMethods.rawToJson(viewCreateUserData);
		String meta = viewCreatedUserRes.get("meta");
		String viewUserId = viewCreatedUserRes.get("data.id");
		String	viewUserName = viewCreatedUserRes.get("data.name");
		String viewUserEmail = viewCreatedUserRes.get("data.email");
		String	userGender = viewCreatedUserRes.get("data.gender");
		String	userStatus = viewCreatedUserRes.get("data.active");
		
		softassert.assertEquals(meta, null);
		softassert.assertEquals(viewUserId, userId);
		softassert.assertEquals(viewUserName, username);
		softassert.assertEquals(viewUserEmail, email);
		softassert.assertEquals(userGender, "male");
		softassert.assertEquals(userStatus, "active");
		softassert.assertAll();
	}
	@Test(priority = 3, groups = { "smoke", "regression" })
	public void updateCreatedUser() throws SQLException {
		log.info("Verify by update created User ");
		String updateUserData = given().log().all().spec(reuseableMethods.setup())
				.header("Authorization", "Bearer" + " " + Token)
				.body(PayLoads.RequestPayloads.updateUserBody(updateUsername, updateEmail))
				.log().all().when().post(updateUserURI+userId).then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		JsonPath updateUserRes = reuseableMethods.rawToJson(updateUserData);
		String meta = updateUserRes.get("meta");
		String updateUserId = updateUserRes.get("data.id");
		String	updateUserName = updateUserRes.get("data.name");
		String updateUserEmail = updateUserRes.get("data.email");
		String	updateUserGender = updateUserRes.get("data.gender");
		String	updatedUserStatus = updateUserRes.get("data.active");
		
		softassert.assertEquals(meta, null);
		softassert.assertEquals(updateUserId, userId);
		softassert.assertEquals(updateUserName, updateUsername);
		softassert.assertEquals(updateUserEmail, updateEmail);
		softassert.assertEquals(updateUserGender, "male");
		softassert.assertEquals(updatedUserStatus, "active");
		softassert.assertAll();
	
	}
		@Test(priority = 4,groups = { "smoke", "regression" })
		public void deleteCreatedUser() throws SQLException {
			log.info("Verify by delete exixting User");
			String deleteData = given().log().all().spec(reuseableMethods.setup())
					.header("Authorization", "Bearer" + " " + Token)
					//.body(PayLoads.RequestPayloads.bindingAndRebinding(customerId))
					.log().all().when().delete(deleteUserURI+userId).then().log().all().assertThat().statusCode(204)
					.extract().response().asString();

				}
/*
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
		*/
}
