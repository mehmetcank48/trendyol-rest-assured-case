package environments;

public class TestEnvironment {
	public final String API_KEY = "de33160d";
	public final String API_BASE_URL = "http://www.omdbapi.com/";
	public final String SEARCH_MOVIE_TITLE = "Harry Potter";
	public final String EXPECTED_SEARCH_MOVIE_TITLE = "Harry Potter and the Sorcerer's Stone";
	
	public String getApiKey() {
		return API_KEY;
	}
	
	public String getApiBaseUrl() {
		return API_BASE_URL;
	}
	
	public String getSeacrMovieTitle() {
		return SEARCH_MOVIE_TITLE;
	}
	
	public String getExpectedSearchMovieTitle() {
		return EXPECTED_SEARCH_MOVIE_TITLE;
	}
}
