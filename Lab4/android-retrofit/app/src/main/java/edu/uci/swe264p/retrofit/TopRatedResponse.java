package edu.uci.swe264p.retrofit;
import com.google.gson.annotations.SerializedName;
import java.util.List;

// Implement this class as the TA did in the Movie.java.
public class TopRatedResponse {
	// Field
	@SerializedName("results")
	private List<Movie> results;

	// Constructor
	public TopRatedResponse(List<Movie> results) {
		this.results = results;
	}

	// Getter
	public List<Movie> getResults(){ 
		return results; 
	}
}
