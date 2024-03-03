package edu.uci.swe264p.retrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

// [Reference] https://developer.android.com/develop/ui/views/layout/recyclerview#java
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

	// Field
	private List<Movie> mData;

	// Constructor
	MovieListAdapter(List<Movie> data) {
		this.mData = data;
	}

	/* 
	 * 1. Add a RecyclerView widget to the layout file of the Activity (activity_movie_list.xml) (-> provided by TA) 
	 * 2. Create an additional layout file (movie_row.xml) to display single item in the list (-> provided by TA)
	 * 3. Create an Adapter class (MovieListAdapter.java) that extends RecyclerView.Adapter to feed your data to the list. In the Adapter class, we need to:
	 * 	- Declare a ViewHolder class that extends RecyclerView.ViewHolder, responsible for displaying single item with a view.
	 *  - Overwrite three built-in methods:
	 *  	- onCreateViewHolder() (for creating a new ViewHolder with its corresponding layout).
	 *  	- onBindViewHolder() (for binding the data of single item to a view).
	 *  	- getItemCount() (for reporting the size of your dataset).
	*/
	public class ViewHolder extends RecyclerView.ViewHolder {
		ImageView ivMovie;
		TextView tvTitle;
		TextView tvReleaseDate;
		TextView tvVote;
		TextView tvOverview;

		ViewHolder(View itemView) {
            super(itemView);
			// Define click listener for the ViewHolder's View

            this.ivMovie = itemView.findViewById(R.id.ivMovie);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            this.tvVote = itemView.findViewById(R.id.tvVote);
            this.tvOverview = itemView.findViewById(R.id.tvOverview);
        }
	}


	// Create new views (invoked by the layout manager)
	@Override
	public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// Create a new view, which defines the UI of the list item
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
		return new ViewHolder(view);
	}


	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// Get element from your dataset at this position and replace the contents of the view with that element
		Movie movie = this.mData.get(position);
		// URL for image retrieval: https://image.tmdb.org/t/p/w500/POSTER_PATH
		// P.S. The first Character of POSTER_PATH is '/'
		// https://square.github.io/picasso/
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(holder.ivMovie);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvReleaseDate.setText(movie.getReleaseDate());
        holder.tvVote.setText(movie.getVoteAverage().toString());
        holder.tvOverview.setText(movie.getOverview());
	}


	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return this.mData.size();
	}
}
