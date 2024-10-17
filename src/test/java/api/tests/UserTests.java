package api.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import api.endpoints.UserRequests;
import api.payloads.UserAddressPojo;
import api.payloads.UserPojo;
import api.utilities.ConfigReader;
import api.utilities.DataProviderUtil;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class UserTests {

	UserPojo userPojo;
	UserAddressPojo userAddress;
	private static Properties properties = ConfigReader.initProp();
	private static List<Integer> userIds = new ArrayList();
	private static List<String> userFirstNames = new ArrayList();
	String userFirstName;

	@Test(priority = 1, dataProvider = "PostData", dataProviderClass = DataProviderUtil.class)
	public void createUserTests(String scenario, String firstName, String lastName, String contactNumber,
			String emailID, String plotNumber, String street, String state, String country, String zipCode,
			String expectedStatusCodes, String expectedErrorMessage) {
		userPojo = new UserPojo();

		userPojo.setUser_first_name(firstName);
		userPojo.setUser_last_name(lastName);
		userPojo.setUser_contact_number(contactNumber);
		userPojo.setUser_email_id(emailID);
		userAddress = new UserAddressPojo();
		userAddress.setPlotNumber(plotNumber);
		userAddress.setStreet(street);
		userAddress.setState(state);
		userAddress.setCountry(country);
		userAddress.setZipCode(zipCode);
		userPojo.setUserAddress(userAddress);

		int expectedStatusCode = Integer.parseInt(expectedStatusCodes);
		if (scenario.equals("all fields") || scenario.equals("only mandatory fields")) {
			System.out.println("Create user with " + scenario + " scenario is tested");
			Response response = UserRequests.createUser(userPojo);
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
			int userID = response.jsonPath().get("user_id");
			userIds.add(userID);
			String userFirstName = response.jsonPath().get("user_first_name");
			userFirstNames.add(userFirstName);
			System.out.println("the user ids are: " + userIds);
			Assert.assertEquals(response.header("Content-Type"), "application/json");
			Assert.assertEquals(response.jsonPath().get("user_first_name"), userPojo.getUser_first_name());
			Assert.assertEquals(response.jsonPath().get("user_last_name"), userPojo.getUser_last_name());
			Assert.assertEquals(response.jsonPath().getString("user_contact_number"),
					userPojo.getUser_contact_number());
			Assert.assertEquals(response.jsonPath().get("user_email_id"), userPojo.getUser_email_id());
			Assert.assertEquals(response.jsonPath().get("userAddress.plotNumber"), userAddress.getPlotNumber());
			Assert.assertEquals(response.jsonPath().get("userAddress.street"), userAddress.getStreet());
			Assert.assertEquals(response.jsonPath().get("userAddress.state"), userAddress.getState());
			Assert.assertEquals(response.jsonPath().get("userAddress.country"), userAddress.getCountry());
			Assert.assertEquals(response.jsonPath().getString("userAddress.zipCode"), userAddress.getZipCode());
			response.then().assertThat()
					.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/postSchema.json"));
		} else if (scenario.equals("with invalid baseURL")) {
			System.out.println("Create user with " + scenario + " scenario is tested");
			Response response = UserRequests.createUserWithInvalidBaseURL(userPojo);
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with invalid endpoint")) {
			System.out.println("Create user with " + scenario + "  scenario is tested");
			Response response = UserRequests.createUserWithInvalidEndpoint(userPojo);
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("without authorization")) {
			System.out.println("Create user " + scenario + " scenario is tested");
			Response response = UserRequests.createUserWithoutAuth(userPojo);
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
		} else {
			System.out.println("Create user with " + scenario + " scenario is tested");
			Response response = UserRequests.createUser(userPojo);
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
			Assert.assertEquals(response.jsonPath().get("message"), expectedErrorMessage);
		}
	}

	@Test(priority = 2, dataProvider = "GetData", dataProviderClass = DataProviderUtil.class)
	public void getAllUsersTests(String scenario, String expectedStatusCodes, String expectedMessage) {
		int expectedStatusCode = Integer.parseInt(expectedStatusCodes);

		if (scenario.equals("without authorization")) {
			System.out.println("Get all users " + scenario + " scenario is tested");
			Response responseWithoutAuth = UserRequests.readAllUserWithoutAuth();
			Assert.assertEquals(responseWithoutAuth.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with invalid baseURL")) {
			System.out.println("Get all users " + scenario + " scenario is tested");
			Response responseInvalidBaseURL = UserRequests.readAllUserWithInvalidBaseURL();
			Assert.assertEquals(responseInvalidBaseURL.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with invalid endpoint")) {
			System.out.println("Get all users " + scenario + " scenario is tested");
			Response responseInvalidEndpoint = UserRequests.readAllUserWithInvalidEndpoint();
			Assert.assertEquals(responseInvalidEndpoint.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with valid request")) {
			System.out.println("Get all users " + scenario + " scenario is tested");
			Response response = UserRequests.readAllUser();
			// response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
			Assert.assertEquals(response.header("Content-Type"), "application/json");			
			response.then().assertThat()
					.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/getAllSchema.json"));
		}
	}

	@Test(priority = 3, dataProvider = "GetData", dataProviderClass = DataProviderUtil.class)
	public void getUserByIDTests(String scenario, String expectedStatusCodes, String expectedMessage) {
		int userID = userIds.get(0);
		int invalidUserId = Integer.parseInt(properties.getProperty("invalidUserID"));
		int expectedStatusCode = Integer.parseInt(expectedStatusCodes);

		if (scenario.equals("without authorization")) {
			System.out.println("Get user by ID " + scenario + " scenario is tested");
			Response responseWithoutAuth = UserRequests.readUserbyIDWithoutAuth(userID);
			Assert.assertEquals(responseWithoutAuth.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with invalid baseURL")) {
			System.out.println("Get user by ID " + scenario + " scenario is tested");
			Response responseInvalidBaseURL = UserRequests.readUserbyIDWithInvalidBaseURL(userID);
			Assert.assertEquals(responseInvalidBaseURL.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with invalid endpoint")) {
			System.out.println("Get user by ID " + scenario + " scenario is tested");
			Response responseInvalidEndpoint = UserRequests.readUserbyIDWithoutInvalidEndpoint(userID);
			Assert.assertEquals(responseInvalidEndpoint.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with non-existing userID")) {
			System.out.println("Get user by ID " + scenario + " scenario is tested");
			Response response = UserRequests.readUserbyID(invalidUserId);
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
			Assert.assertEquals(response.header("Content-Type"), "application/json");
			Assert.assertEquals(response.jsonPath().get("message"),
					(expectedMessage.replace("$", String.valueOf(invalidUserId))));
		} else if (scenario.equals("with valid request")) {
			System.out.println("Get user by ID " + scenario + " scenario is tested");
			Response response = UserRequests.readUserbyID(userID);
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
			Assert.assertEquals(response.header("Content-Type"), "application/json");			
		}
	}

	@Test(priority = 4, dataProvider = "GetData", dataProviderClass = DataProviderUtil.class)
	public void getUserByFirstNameTests(String scenario, String expectedStatusCodes, String expectedMessage) {
		String userFirstName = userFirstNames.get(1);

		String invalidUserFirstName = properties.getProperty("invalidUserFirstName");
		int expectedStatusCode = Integer.parseInt(expectedStatusCodes);
		if (scenario.equals("without authorization")) {
			System.out.println("Get user by first name " + scenario + " scenario is tested");
			Response responseWithoutAuth = UserRequests.readUserbyFirstNameWithoutAuth(userFirstName);
			Assert.assertEquals(responseWithoutAuth.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with invalid baseURL")) {
			System.out.println("Get user by first name " + scenario + " scenario is tested");
			Response responseInvalidBaseURL = UserRequests.readUserbyFirstNameWithInvalidBaseURL(userFirstName);
			Assert.assertEquals(responseInvalidBaseURL.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with invalid endpoint")) {
			System.out.println("Get user by first name " + scenario + " scenario is tested");
			Response responseInvalidEndpoint = UserRequests.readUserbyFirstNameWithInvalidEndpoint(userFirstName);
			Assert.assertEquals(responseInvalidEndpoint.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with non-existing userFirstName")) {
			System.out.println("Get user by first name " + scenario + " scenario is tested");
			Response response = UserRequests.readUserbyFirstName(invalidUserFirstName);
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
			Assert.assertEquals(response.header("Content-Type"), "application/json");
			Assert.assertEquals(response.jsonPath().get("message"),
					(expectedMessage.replace("$", invalidUserFirstName)));
		} else if (scenario.equals("with valid request")) {
			System.out.println("Get user by first name " + scenario + " scenario is tested");
			Response response = UserRequests.readUserbyFirstName(userFirstName);
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
			Assert.assertEquals(response.header("Content-Type"), "application/json");
		}
	}

	@Test(priority = 5, dataProvider = "PutData", dataProviderClass = DataProviderUtil.class)
	public void updateUserTests(String scenario, String firstName, String lastName, String contactNumber,
			String emailID, String plotNumber, String street, String state, String country, String zipCode,
			String expectedStatusCodes, String expectedErrorMessage) {
		userPojo = new UserPojo();

		userPojo.setUser_first_name(firstName);
		userPojo.setUser_last_name(lastName);
		userPojo.setUser_contact_number(contactNumber);
		userPojo.setUser_email_id(emailID);
		userAddress = new UserAddressPojo();
		userAddress.setPlotNumber(plotNumber);
		userAddress.setStreet(street);
		userAddress.setState(state);
		userAddress.setCountry(country);
		userAddress.setZipCode(zipCode);
		userPojo.setUserAddress(userAddress);

		int userID = userIds.get(0);

		int expectedStatusCode = Integer.parseInt(expectedStatusCodes);
		if (scenario.equals("all fields") || scenario.equals("only mandatory fields")) {
			System.out.println("Update user with " + scenario + " scenario is tested");
			Response response = UserRequests.updateUser(userID, userPojo);
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
			Assert.assertEquals(response.header("Content-Type"), "application/json");
			Assert.assertEquals(response.jsonPath().get("user_first_name"), userPojo.getUser_first_name());
			Assert.assertEquals(response.jsonPath().get("user_last_name"), userPojo.getUser_last_name());
			Assert.assertEquals(response.jsonPath().getString("user_contact_number"),
					userPojo.getUser_contact_number());
			Assert.assertEquals(response.jsonPath().get("user_email_id"), userPojo.getUser_email_id());
			Assert.assertEquals(response.jsonPath().get("userAddress.plotNumber"), userAddress.getPlotNumber());
			Assert.assertEquals(response.jsonPath().get("userAddress.street"), userAddress.getStreet());
			Assert.assertEquals(response.jsonPath().get("userAddress.state"), userAddress.getState());
			Assert.assertEquals(response.jsonPath().get("userAddress.country"), userAddress.getCountry());
			Assert.assertEquals(response.jsonPath().getString("userAddress.zipCode"), userAddress.getZipCode());
			response.then().assertThat()
					.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/postSchema.json"));
		} else if (scenario.equals("with invalid baseURL")) {
			System.out.println("Update user with " + scenario + " scenario is tested");
			Response response = UserRequests.updateUserWithInvalidBaseURL(userID, userPojo);
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with invalid endpoint")) {
			System.out.println("Update user with " + scenario + "  scenario is tested");
			Response response = UserRequests.updateUserWithInvalidEndpoint(userID, userPojo);
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("without authorization")) {
			System.out.println("Update user " + scenario + " scenario is tested");
			Response response = UserRequests.updateUserWithoutAuth(userID, userPojo);
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
		} else {
			System.out.println("Update user with " + scenario + " scenario is tested");
			Response response = UserRequests.updateUser(userID, userPojo);
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
			Assert.assertEquals(response.jsonPath().get("message"), expectedErrorMessage);
		}
	}

	@Test(priority = 7, dataProvider = "DeleteData", dataProviderClass = DataProviderUtil.class)
	public void deleteUserByIDTests(String scenario, String expectedStatusCodes, String expectedMessage) {

		int userID = userIds.get(0);
		int invalidUserId = Integer.parseInt(properties.getProperty("invalidUserID"));
		int expectedStatusCode = Integer.parseInt(expectedStatusCodes);

		if (scenario.equals("without authorization")) {
			System.out.println("Delete user by ID " + scenario + " scenario is tested");
			Response responseWithoutAuth = UserRequests.deleteUserbyIDWithoutAuth(userID);
			Assert.assertEquals(responseWithoutAuth.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with invalid baseURL")) {
			System.out.println("Delete user by ID " + scenario + " scenario is tested");
			Response responseInvalidBaseURL = UserRequests.deleteUserbyIDWithInvalidBaseURL(userID);
			Assert.assertEquals(responseInvalidBaseURL.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with invalid endpoint")) {
			System.out.println("Delete user by ID " + scenario + " scenario is tested");
			Response responseInvalidEndpoint = UserRequests.deleteUserbyIDWithInvalidEndpoint(userID);
			Assert.assertEquals(responseInvalidEndpoint.getStatusCode(), expectedStatusCode);
		} else if (scenario.equals("with non-existing userID")) {
			System.out.println("Delete user by ID " + scenario + " scenario is tested");
			Response response = UserRequests.deleteUserbyID(invalidUserId);
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
			Assert.assertEquals(response.header("Content-Type"), "application/json");
			Assert.assertEquals(response.jsonPath().get("message"),
					(expectedMessage.replace("$", String.valueOf(invalidUserId))));
		} else if (scenario.equals("with valid request")) {
			System.out.println("Delete user by ID " + scenario + " scenario is tested");
			Response response = UserRequests.deleteUserbyID(userID);
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
			Assert.assertEquals(response.header("Content-Type"), "application/json");
			Assert.assertEquals(response.jsonPath().get("message"), (expectedMessage));
		}
	}

	@Test(priority = 8, dataProvider = "DeleteData", dataProviderClass = DataProviderUtil.class)
	public void deleteUserByFirstNameTests(String scenario, String expectedStatusCodes, String expectedMessage) {
		String invalidUserFirstName = properties.getProperty("invalidUserFirstName");
		int expectedStatusCode = Integer.parseInt(expectedStatusCodes);
		for (int i = 1; i < userFirstNames.size(); i++) {
			String userFirstName = userFirstNames.get(i);

			if (scenario.equals("without authorization")) {
				System.out.println("Delete user by first name " + scenario + " scenario is tested");
				Response responseWithoutAuth = UserRequests.deleteUserbyFirstNameWithoutAuth(userFirstName);
				Assert.assertEquals(responseWithoutAuth.getStatusCode(), expectedStatusCode);
			} else if (scenario.equals("with invalid baseURL")) {
				System.out.println("Delete user by first name " + scenario + " scenario is tested");
				Response responseInvalidBaseURL = UserRequests.deleteUserbyFirstNameWithInvalidBaseURL(userFirstName);
				Assert.assertEquals(responseInvalidBaseURL.getStatusCode(), expectedStatusCode);
			} else if (scenario.equals("with invalid endpoint")) {
				System.out.println("Delete user by first name " + scenario + " scenario is tested");
				Response responseInvalidEndpoint = UserRequests.deleteUserbyFirstNameWithInvalidEndpoint(userFirstName);
				Assert.assertEquals(responseInvalidEndpoint.getStatusCode(), expectedStatusCode);
			} else if (scenario.equals("with non-existing userFirstName")) {
				System.out.println("Delete user by first name " + scenario + " scenario is tested");
				Response response = UserRequests.deleteUserbyFirstName(invalidUserFirstName);
				response.then().log().all();
				Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
				Assert.assertEquals(response.header("Content-Type"), "application/json");
				Assert.assertEquals(response.jsonPath().get("message"),
						(expectedMessage.replace("$", invalidUserFirstName)));
			} else if (scenario.equals("with valid request")) {
				System.out.println("Delete user by first name " + scenario + " scenario is tested");
				Response response = UserRequests.deleteUserbyFirstName(userFirstName);
				response.then().log().all();
				Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
				Assert.assertEquals(response.header("Content-Type"), "application/json");
				Assert.assertEquals(response.jsonPath().get("message"), (expectedMessage));
			}
		}
	}
}
