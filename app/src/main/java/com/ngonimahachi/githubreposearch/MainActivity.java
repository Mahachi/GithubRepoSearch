package com.ngonimahachi.githubreposearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText mSearchBoxEditText;
    private TextView mUrlDisplayTextView;
    private Button mSearchResultsTextView;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = findViewById(R.id.et_search_box);
        mUrlDisplayTextView = findViewById(R.id.tv_url_display);
        mSearchResultsTextView = findViewById(R.id.tv_github_search_results_json);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
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
        //  Call getResponseFromHttpUrl and display the results in mSearchResultsTextView
        //  Surround the call to getResponseFromHttpUrl with a try / catch block to catch an IO Exception
        String githubSearchResults = null;
        try {
            githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubSearchUrl);
            mSearchResultsTextView.setText(githubSearchResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //  Created a method called showJsonDataView to show the data and hide the error
    /**
     * This method will make the View for the JSON data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showJsonDataView() {
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

     //Created a method called showErrorMessage to show the error and hide the data
    /**
     * This method will make the error message visible and hide the JSON
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        // First, hide the currently visible data
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
        // Then, show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

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

    //Created a class called GithubQueryTask that extends AsyncTask<URL, Void, String>
    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

         //  Overrided the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
         @Override
         protected String doInBackground(URL... params) {
             URL searchUrl = params[0];
             String githubSearchResults = null;
             try {
                 githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
             } catch (IOException e) {
                 e.printStackTrace();
             }
             return githubSearchResults;
         }

         //  Override onPostExecute to display the results in the TextView
         @Override
         protected void onPostExecute(String githubSearchResults) {
             mLoadingIndicator.setVisibility(View.INVISIBLE);
             if (githubSearchResults != null && !githubSearchResults.equals("")) {
                 showJsonDataView();
             }else{
                 showErrorMessage();
             }

         }
     }
}