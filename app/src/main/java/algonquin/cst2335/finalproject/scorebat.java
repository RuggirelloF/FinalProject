package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    //favs
    RecyclerView favRecyclerView;
    ArrayList<ScorebatModelClass> favsArrayList;

    //Shared prefrences
    public static final String SB_SHARED_PREFS = "sbWebPrefs";
    public static final String SBURL = "SBURL";
    public String lastWatchLink;

    FragmentContainerView fragView;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_fav:
                Toast.makeText(this, "You pressed Fav", Toast.LENGTH_SHORT).show();
                favRecyclerView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                fragView.setVisibility(View.GONE);
                break;
            case R.id.nav_home:
                Toast.makeText(this, "You pressed Home", Toast.LENGTH_SHORT).show();
                favRecyclerView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                fragView.setVisibility(View.GONE);
                break;
            case R.id.nav_about:
                Toast.makeText(this, "You pressed about", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.sbFragContainer, ScorebatAbout.class, null)
                        .commit();
                favRecyclerView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                fragView.setVisibility(View.VISIBLE);

                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.sb_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScorebatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.sbToolbar);
        recyclerView = findViewById(R.id.sb_recyclerView);
        favRecyclerView = findViewById(R.id.favRecyclerView);

        fragView = findViewById(R.id.sbFragContainer);
        favsArrayList = new ArrayList<>();
        scorebatArrayList = new ArrayList<>();
        getData();


        buildRecyclerView();
        buildFavsRecyclerView();

        SharedPreferences sbPrefs = getSharedPreferences(SB_SHARED_PREFS, Context.MODE_PRIVATE);
        lastWatchLink = sbPrefs.getString(SBURL,"");

        if(lastWatchLink.length() > 5){
            Uri uri = Uri.parse(lastWatchLink);
            this.startActivity(new Intent(Intent.ACTION_VIEW,uri));

            /*SharedPreferences*/ sbPrefs = getSharedPreferences(SB_SHARED_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sbPrefs.edit();
            editor = sbPrefs.edit();
            editor.putString(SBURL, "");
            editor.commit();
        }



    }
    private void buildFavsRecyclerView() {

        // initializing our adapter class.
        adapter = new ScorebatAdapter(favsArrayList, scorebat.this, favsArrayList);

        // adding layout manager
        // to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        favRecyclerView.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        favRecyclerView.setLayoutManager(manager);


        // setting adapter to
        // our recycler view.
        favRecyclerView.setAdapter(adapter);
    }

    private void buildRecyclerView() {

        // initializing our adapter class.
        adapter = new ScorebatAdapter(scorebatArrayList, scorebat.this,favsArrayList);

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

    //At the moment only searches throught the first 4 items to decrease load times on the images. (Change i back to solve this.)
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
                        String sbStreamUrl= responseObject.getString("url");

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

                        //Does not work causes the whole query to crash. I am unsure how to make this
                        //work I tried adding in the raw string from the fetch, then I tried to parse the
                        //string data to make it what it needs to be.
                        //JSONObject sbVideo = responseObject.getJSONObject("videos");
                        //String sbVideoEmbed = getVideoUrl(sbVideo.getString("embed"));


                        //v1
                        //scorebatArrayList.add(new ScorebatModelClass(sbTitle, sbTumbnail,sbDate));
                        scorebatArrayList.add(new ScorebatModelClass(sbTitle, sbTumbnail,sbDate, sbCompName, sbSide1Name, sbWatchLink1, sbSide2Name, sbWatchLink2, sbStreamUrl, ""));
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

    public String getVideoUrl(String videoEmbed){
        String url = videoEmbed;
        String[] urlSplit = url.split("src='");
        String[] urlSplit2 = urlSplit[1].split("'");
        return urlSplit2[0];
    }
}


