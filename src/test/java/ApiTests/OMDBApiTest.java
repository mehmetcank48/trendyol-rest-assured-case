package ApiTests;
// Java imports
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
// Rest Assured imports
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import Classes.OMDBMovie;
import environments.TestEnvironment;

public class OMDBApiTest {
	// Request Spec
	private static RequestSpecification reqSpec;
	// Test environment
	TestEnvironment testEnv = new TestEnvironment();
	// OMDBMovie list
	List<OMDBMovie> movies = new ArrayList<OMDBMovie>();

	String imdbId;

	// Create api settings
	@BeforeClass
	public void CreateApiSettings() {
		RestAssured.baseURI = testEnv.API_BASE_URL;
		reqSpec = new RequestSpecBuilder().setBaseUri(testEnv.API_BASE_URL).build().param("apikey", testEnv.API_KEY);
	}

	public String getMovieImdbId() {
		// first search operation
		Response response = given().spec(reqSpec).param("s", testEnv.SEARCH_MOVIE_TITLE).get();
		JsonPath jsonPath = response.jsonPath();
		
		movies = jsonPath.getList("Search", OMDBMovie.class);  // Movie list in search results
		// find the id of the desired movie
		for (OMDBMovie movie : movies) {
			if (movie.Title.equals(testEnv.EXPECTED_SEARCH_MOVIE_TITLE)) {
				return movie.imdbID;
			}
		}
		return null;
	}

	@Test
	public void checkMovie() {
		imdbId = getMovieImdbId();
		given()
		.spec(reqSpec)
		.param("i", imdbId)
		.when()
		.get()
		.then()
		.statusCode(HttpStatus.SC_OK)
		.body("Title", not(equalTo("")))
		.body("Released", not(equalTo("")))
		.body("Year", not(equalTo("")));
	}
}
