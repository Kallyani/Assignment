package com.ays.assignment.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ays.assignment.R;
import com.ays.assignment.adapter.CardAdapter;
import com.ays.assignment.constants.API;
import com.ays.assignment.model.Canada;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CanadaActivity extends AppCompatActivity {


    private ProgressDialog pDialog;
    private List<Canada> listCanada;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_canada);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listCanada = new ArrayList<>();

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //fetchTimelineAsync(0);
                getCanadaData();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        getCanadaData();

    }

    /**
     * This method will get data from the web API
     */
    private void getCanadaData() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest canadaReq = new JsonObjectRequest(API.ServerUrl.URL_CANADA, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response:", response.toString());
                        hidePDialog();

                        //calling method to parse json array
                        parseData(response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                hidePDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(canadaReq);
        //AppController.getInstance().addToRequestQueue(canadaReq);
    }

    /**
     * This method will parse json data
     */
    private void parseData(JSONObject response) {

        listCanada.clear();
        if (adapter != null)
            adapter.notifyDataSetChanged();

        try {
            String appTitle = response.getString("title");

            android.support.v7.app.ActionBar ab = getSupportActionBar();
            ab.setTitle(appTitle);

            JSONArray jArrRow = response.getJSONArray("rows");

            // Parsing json
            for (int i = 0; i < jArrRow.length(); i++) {

                JSONObject obj = jArrRow.getJSONObject(i);
                Canada canada = new Canada();

                if (obj.isNull(API.TITLE))
                    canada.setTitle(" ");
                else
                    canada.setTitle(obj.getString(API.TITLE));


                if (!obj.isNull(API.IMAGE_URL))
                    canada.setThumbnailUrl(obj.getString(API.IMAGE_URL));

                if (obj.isNull(API.DESC))
                    canada.setDescription(" ");
                else
                    canada.setDescription(obj.getString(API.DESC));

                if (!(obj.isNull(API.TITLE) && obj.isNull(API.DESC) && obj.isNull(API.IMAGE_URL)))
                    listCanada.add(canada);
            }
        } catch (JSONException e) {
            Log.e("CanadaActivity ", "Error" + e);
        }

        //Finally initializing our adapter
        adapter = new CardAdapter(listCanada, CanadaActivity.this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}