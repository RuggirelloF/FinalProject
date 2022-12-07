package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PexelsActivity extends AppCompatActivity {

    SharedPreferences prefs;
    final String SHARED_PREF_NAME = "MyData";
    final String KEY_SEARCH = "search";

    RecyclerView pexelsRecyclerView;
    PexelsAdapter pexelsAdapter;
    ArrayList<PexelsModel> pexelsModelList;
    int pageNumber = 1;

    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    String url ="https://api.pexels.com/v1/curated/?page=" + pageNumber + "&per_page=80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pexels_activity);

        pexelsRecyclerView = findViewById(R.id.pexelsRecyclerView);
        pexelsModelList = new ArrayList<>();

        pexelsAdapter = new PexelsAdapter(this, pexelsModelList);
        pexelsRecyclerView.setAdapter(pexelsAdapter);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        pexelsRecyclerView.setLayoutManager(gridLayoutManager);

        pexelsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = gridLayoutManager.getChildCount();
                totalItems = gridLayoutManager.getItemCount();
                scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems)){
                    isScrolling = false;
                    parseJSON();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent fromPrevious = getIntent();
        String searchParam = fromPrevious.getStringExtra("SearchParam");
        url = "https://api.pexels.com/v1/search/?page=" + pageNumber + "&per_page=80&query=" + searchParam;
        pexelsModelList.clear();
        parseJSON();
    }

    public void parseJSON(){

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("photos");
                            int length = jsonArray.length();
                            for (int i = 0; i < length; i++){
                                JSONObject photos = jsonArray.getJSONObject(i);

                                String pexelsImageUrl = photos.getString("url");
                                String pexelsCreatorName = photos.getString("photographer");
                                String pexelsDescription = photos.getString("alt");

                                JSONObject objectImages = photos.getJSONObject("src");

                                String pexelsOriginalUrl = objectImages.getString("original");
                                String pexelsMediumUrl = objectImages.getString("medium");

                                PexelsModel pexelsModel = new PexelsModel(pexelsImageUrl, pexelsCreatorName, pexelsDescription, pexelsOriginalUrl, pexelsMediumUrl);
                                pexelsModelList.add(pexelsModel);
                            }
                            pexelsAdapter.notifyDataSetChanged();
                            pageNumber++;
                        } catch (JSONException e) {}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","563492ad6f917000010000019c98790bba1145e8ad942c3914537b55");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pexels_main, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String language = Locale.getDefault().getLanguage();

        if(item.getItemId() == R.id.pexelsNavHelp){

            if(language == "es"){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("¡Bienvenido a la Aplicación Pexels!");
                alert.setMessage(
                        "No te preocupes, estoy acá para ayudarte\n\n" +
                                "■ Seleccionar la casa te lleva al menú de incio\n" +
                                "■ Seleccionar la lupa te lleva al menú de busqueda\n" +
                                "■ Seleccionar el corazón te lleva al menú de favoritos\n" +
                                "■ Seleccionar el signo de interrogación muestra esta alerta... De nuevo ¬_¬"
                );

                alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.show();
            }
            else{
                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("Welcome to the Pexels Application!");
                alert.setMessage(
                        "Don't you worry, I'm here to help you\n\n" +
                                "■ Selecting the home takes you to the main menu\n" +
                                "■ Selecting the magnifier takes you to the search menu\n" +
                                "■ Selecting the heart takes you to your favorites\n" +
                                "■ Selecting the question mark displays this alert... Again ¬_¬"
                );

                alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.show();
            }
        }

        if(item.getItemId() == R.id.pexelsNavSearch){
            Intent moveToSearch = new Intent(PexelsActivity.this, PexelsSearch.class);
            startActivity(moveToSearch);
        }

        return super.onOptionsItemSelected(item);
    }
}