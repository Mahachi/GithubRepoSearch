package com.ngonimahachi.githubreposearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText mSearchBoxEditText;
    private TextView mUrlDisplayTextView;
    private Button mSearchResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = findViewById(R.id.et_search_box);
        mUrlDisplayTextView = findViewById(R.id.tv_url_display);
        mSearchResultsTextView = findViewById(R.id.tv_github_search_results_json);
    }

    // Created a method called makeGithubSearchQuery
    // Within this method,i have built the URL with the text from the EditText and set the built URL to the TextView
    /**
     * This method retrieves the search text from the EditText, constructs
     * the URL (using {@link NetworkUtils}) for the github repository you'd like to find, displays
     * that URL in a TextView, and finally fires off an AsyncTask to perform the GET request using
     * our (not yet created) {@link GithubQueryTask}
     */
    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int itemThatWasClickedId = item.getItemId();
            if (itemThatWasClickedId == R.id.action_search) {
                //  Remove the Toast message when the search menu item is clicked
                // Call makeGithubSearchQuery when the search menu item is clicked
                makeGithubSearchQuery();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
 }