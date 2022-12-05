package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;

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
import java.util.Map;

public class PexelsActivity extends AppCompatActivity {

    RecyclerView pexelsRecyclerView;
    PexelsAdapter pexelsAdapter;
    ArrayList<PexelsItem> pexelsItemList;
    int pageNumber = 1;

    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    String url ="https://api.pexels.com/v1/curated/?page=" + pageNumber + "&per_page=80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pexels_activity);

        pexelsRecyclerView = findViewById(R.id.pexelsRecyclerView);
        pexelsItemList = new ArrayList<>();
        pexelsAdapter = new PexelsAdapter(this, pexelsItemList);

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

                                PexelsItem pexelsItem = new PexelsItem(pexelsImageUrl, pexelsCreatorName, pexelsDescription, pexelsOriginalUrl, pexelsMediumUrl);
                                pexelsItemList.add(pexelsItem);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_search){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText editText = new EditText(this);
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            alert.setMessage("Search Category...");
            alert.setTitle("Search Image");

            alert.setView(editText);

            alert.setPositiveButton("Search", (dialogInterface, i) -> {
                String query = editText.getText().toString().toLowerCase();

                url = "https://api.pexels.com/v1/search/?page="+pageNumber+"&per_page=80&query="+query;
                pexelsItemList.clear();
                parseJSON();
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }
}