package api.endpoints;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Properties;

import api.payloads.UserPojo;
import api.utilities.ConfigReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class UserRequests {
	private static Properties properties = ConfigReader.initProp();
	public static String baseURL = properties.getProperty("baseURL");
	
	public static Response createUser(UserPojo payload) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.contentType(ContentType.JSON)
			.body(payload)
		.when()
			.post(baseURL + properties.getProperty("postEndpoint"));
		return response;
	}
	
	public static Response createUserWithoutAuth(UserPojo payload) {
		Response response = given()
			.contentType(ContentType.JSON)
			.body(payload)
		.when()
			.post(baseURL + properties.getProperty("postEndpoint"));
		return response;
	}
	
	public static Response createUserWithInvalidBaseURL(UserPojo payload) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.contentType(ContentType.JSON)
			.body(payload)
		.when()
			.post(properties.getProperty("invalidBaseURL") + properties.getProperty("postEndpoint"));
		return response;
	}
	
	public static Response createUserWithInvalidEndpoint(UserPojo payload) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.contentType(ContentType.JSON)
			.body(payload)
		.when()
			.post(baseURL + properties.getProperty("invalidEndpoint"));
		return response;
	}
	
	//Requests for GET user by first name
	public static Response readUserbyFirstName(String userFirstName) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.pathParam("userFirstName", userFirstName )
		.when()
			.get(baseURL + properties.getProperty("getByFirstnameEndpoint") + "{userFirstName}");
		return response;
	}
	
	public static Response readUserbyFirstNameWithoutAuth(String userFirstName) {
		Response response = given()
			.pathParam("userFirstName", userFirstName )
		.when()
			.get(baseURL + properties.getProperty("getByFirstnameEndpoint") + "{userFirstName}");
		return response;
	}
	
	public static Response readUserbyFirstNameWithInvalidBaseURL(String userFirstName) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.pathParam("userFirstName", userFirstName )
		.when()
			.get(properties.getProperty("invalidBaseURL") + properties.getProperty("getByFirstnameEndpoint") + "{userFirstName}");
		return response;
	}
	
	public static Response readUserbyFirstNameWithInvalidEndpoint(String userFirstName) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.pathParam("userFirstName", userFirstName )
		.when()
			.get(baseURL + properties.getProperty("invalidEndpoint") + "{userFirstName}");
		return response;
	}
	
	//Requests for GET user by ID
	public static Response readUserbyID(int userID) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.pathParam("userID", userID )
		.when()
			.get(baseURL + properties.getProperty("getByIdEndpoint") +"{userID}");
		return response;
	}
	
	public static Response readUserbyIDWithoutAuth(int userID) {
		Response response = given()
			.pathParam("userID", userID )
		.when()
			.get(baseURL + properties.getProperty("getByIdEndpoint") +"{userID}");
		return response;
	}
	
	public static Response readUserbyIDWithInvalidBaseURL(int userID) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.pathParam("userID", userID )
		.when()
			.get(properties.getProperty("invalidBaseURL") + properties.getProperty("getByIdEndpoint") +"{userID}");
		return response;
	}
	
	public static Response readUserbyIDWithoutInvalidEndpoint(int userID) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.pathParam("userID", userID )
		.when()
			.get(baseURL + properties.getProperty("invalidEndpoint") +"{userID}");
		return response;
	}
	
	//Requests for GET all users
	public static Response readAllUser() {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.when()
			.get(baseURL + properties.getProperty("getAllEndpoint"));
		return response;
	}	
	
	public static Response readAllUserWithoutAuth() {
		Response response = given()
			.when()
			.get(baseURL + properties.getProperty("getAllEndpoint"));
		return response;
	}
	
	public static Response readAllUserWithInvalidBaseURL() {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.when()
			.get(properties.getProperty("invalidBaseURL") + properties.getProperty("getAllEndpoint"));
		return response;
	}
	
	public static Response readAllUserWithInvalidEndpoint() {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.when()
			.get(baseURL + properties.getProperty("invalidEndpoint"));
		return response;
	}	
	
	
	public static Response updateUser(int userID, UserPojo payload) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.contentType(ContentType.JSON)
			.pathParam("userID", userID)
			.body(payload)
		.when()
			.put(baseURL + properties.getProperty("putEndpoint") + "{userID}");
		return response;
	}
	
	public static Response updateUserWithoutAuth(int userID, UserPojo payload) {
		Response response = given()
			.contentType(ContentType.JSON)
			.pathParam("userID", userID)
			.body(payload)
		.when()
			.put(baseURL + properties.getProperty("putEndpoint") + "{userID}");
		return response;
	}
	
	public static Response updateUserWithInvalidBaseURL(int userID, UserPojo payload) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.contentType(ContentType.JSON)
			.pathParam("userID", userID)
			.body(payload)
		.when()
			.put(properties.getProperty("invalidBaseURL") + properties.getProperty("putEndpoint") + "{userID}");
		return response;
	}
	
	public static Response updateUserWithInvalidEndpoint(int userID, UserPojo payload) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.contentType(ContentType.JSON)
			.pathParam("userID", userID)
			.body(payload)
		.when()
			.put(baseURL + properties.getProperty("invalidEndpoint") + "{userID}");
		return response;
	}
	
	public static Response deleteUserbyFirstName(String userFirstName) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.pathParam("userFirstName", userFirstName )
		.when()
			.delete(baseURL + properties.getProperty("deleteByFirstnameEndpoint") + "{userFirstName}");
		return response;
	}
	
	public static Response deleteUserbyFirstNameWithoutAuth(String userFirstName) {
		Response response = given()
			.pathParam("userFirstName", userFirstName )
		.when()
			.delete(baseURL + properties.getProperty("deleteByFirstnameEndpoint") + "{userFirstName}");
		return response;
	}
	
	public static Response deleteUserbyFirstNameWithInvalidBaseURL(String userFirstName) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.pathParam("userFirstName", userFirstName )
		.when()
			.delete(properties.getProperty("invalidBaseURL") + properties.getProperty("deleteByFirstnameEndpoint") + "{userFirstName}");
		return response;
	}
	
	public static Response deleteUserbyFirstNameWithInvalidEndpoint(String userFirstName) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.pathParam("userFirstName", userFirstName )
		.when()
			.delete(baseURL + properties.getProperty("invalidEndpoint") + "{userFirstName}");
		return response;
	}
	
	public static Response deleteUserbyID(int userID) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.pathParam("userID", userID )
		.when()
			.delete(baseURL + properties.getProperty("deleteByIdEndpoint") + "{userID}");
		return response;
	}
	
	public static Response deleteUserbyIDWithoutAuth(int userID) {
		Response response = given()
			.pathParam("userID", userID )
		.when()
			.delete(baseURL + properties.getProperty("deleteByIdEndpoint") + "{userID}");
		return response;
	}
	
	public static Response deleteUserbyIDWithInvalidBaseURL(int userID) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.pathParam("userID", userID )
		.when()
			.delete(properties.getProperty("invalidBaseURL") + properties.getProperty("deleteByIdEndpoint") + "{userID}");
		return response;
	}
	
	public static Response deleteUserbyIDWithInvalidEndpoint(int userID) {
		Response response = given().auth().basic(properties.getProperty("username"),properties.getProperty("password"))
			.pathParam("userID", userID )
		.when()
			.delete(baseURL + properties.getProperty("invalidEndpoint") + "{userID}");
		return response;
	}
}
