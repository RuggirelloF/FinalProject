package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import algonquin.cst2335.finalproject.databinding.ActivityScorebatBinding;

@SuppressWarnings("ALL")
public class scorebat extends AppCompatActivity {
    //Volley
    private TextView textViewResults;
    private RequestQueue queue;

    //New
    RecyclerView recyclerView;
    ScorebatAdapter adapter;
    ArrayList<ScorebatModelClass> scorebatArrayList;
    String url = "https://www.scorebat.com/video-api/v1/";


    ActivityScorebatBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScorebatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.sb_recyclerView);

        scorebatArrayList = new ArrayList<>();
        getData();

        buildRecyclerView();

    }

    private void buildRecyclerView() {

        // initializing our adapter class.
        adapter = new ScorebatAdapter(scorebatArrayList, scorebat.this);

        // adding layout manager
        // to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        recyclerView.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        recyclerView.setAdapter(adapter);
    }


    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(scorebat.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new  Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response){
                recyclerView.setVisibility(View.VISIBLE);
                for (int i = 0; i < response.length(); i++){
                    try {
                        //Top level object
                        JSONObject responseObject = response.getJSONObject(i);
                        String sbTitle = responseObject.getString("title");
                        String sbDate = responseObject.getString("date");
                        String sbTumbnail = responseObject.getString("thumbnail");

                        //Competition - inner array
                        JSONObject sbComp = responseObject.getJSONObject("competition");
                        String sbCompName = sbComp.getString("name");

                        //Side1 - inner array
                        JSONObject sbSide1 = responseObject.getJSONObject("side1");
                        String sbSide1Name = sbSide1.getString("name");
                        String sbWatchLink1 = sbSide1.getString("url");

                        //Side2 - inner array
                        JSONObject sbSide2 = responseObject.getJSONObject("side2");
                        String sbSide2Name = sbSide2.getString("name");
                        String sbWatchLink2 = sbSide2.getString("url");

                        //v1
                        //scorebatArrayList.add(new ScorebatModelClass(sbTitle, sbTumbnail,sbDate));
                        scorebatArrayList.add(new ScorebatModelClass(sbTitle, sbTumbnail,sbDate, sbCompName, sbSide1Name, sbWatchLink1, sbSide2Name, sbWatchLink2));
                        buildRecyclerView();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError ve){
                Toast.makeText(scorebat.this,"Failed to get data", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }
}


