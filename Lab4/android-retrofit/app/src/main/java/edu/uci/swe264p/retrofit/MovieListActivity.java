package edu.uci.swe264p.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
		/*
		 List<String> programs = new ArrayList<String>(
                Arrays.asList(
                        "Computer Science — Ph.D.",
                        "Computer Science — M.S.",
                        "Informatics — Ph.D.",
                        "Informatics — M.S.",
                        "Networked Systems — Ph.D.",
                        "Networked Systems — Ph.D.",
                        "Software Engineering — Ph.D.",
                        "Software Engineering — M.S.",
                        "Statistics — Ph.D.",
                        "Statistics — M.S.",
                        "Master of Computer Science (MCS)",
                        "Master of Software Engineering (MSWE)",
                        "Master of Human-Computer Interaction and Design (MHCID)",
                        "Master of Embedded & Cyber-physical Systems (MECPS)"
                )
        );

        recyclerView = findViewById(R.id.rvPrograms);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ProgramListAdapter(programs));
		*/
    }
}
