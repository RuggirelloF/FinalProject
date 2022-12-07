package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import algonquin.cst2335.finalproject.databinding.ActivityTicketMasterBinding;
import algonquin.cst2335.finalproject.databinding.EventBinding;

public class TicketMasterActivity extends AppCompatActivity {

    private ActivityTicketMasterBinding binding;
    private EventViewModel eventModel;
    private RecyclerView.Adapter<MyRowHolder> myAdaptor;
    private ArrayList<Event> eventsList = new ArrayList<>();
    protected RequestQueue queue = null;
    protected String cityName;
    protected int radius;
    EventDetailsFragment eventFragment;
    EventDatabase db;
    EventDAO eventDAO;
    String urlPrefix;
    String searchURL;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketMasterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getResources().getString(R.string.ticket_master_name));
        actionBar.setDisplayHomeAsUpEnabled(true);



        db = Room.databaseBuilder(getApplicationContext(), EventDatabase.class, "EventDatabase").fallbackToDestructiveMigration().build();
        eventDAO = db.eventDAO();

        eventModel = new ViewModelProvider(this).get(EventViewModel.class);
        queue = Volley.newRequestQueue(this);

        binding.searchButton.setOnClickListener(click -> {
            String cityInput = binding.citySearch.getText().toString();
            String radiusInput = binding.radiusSearch.getText().toString();
            if (!cityInput.equals("") && !radiusInput.equals("") && !radiusInput.equals("0")) {
                cityName = cityInput;
                radius = Integer.parseInt(radiusInput);
                search(cityName, radius);
            } else if (cityInput.equals("") ) {;
                Toast.makeText(this, R.string.invalid_city, Toast.LENGTH_SHORT).show();
            } else if (radiusInput.equals("")) {
                Toast.makeText(this, R.string.no_radius, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.zero_radius, Toast.LENGTH_SHORT).show();
            }

            if (!binding.radiusSearch.getText().toString().equals("")) {
                radius = Integer.parseInt(binding.radiusSearch.getText().toString());
            } else {
                radius = 0;
            }

        });

        myAdaptor = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                EventBinding eventBinding = EventBinding.inflate(getLayoutInflater(),parent, false);
                return new MyRowHolder((eventBinding.getRoot()));
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.eventDateText.setText("");
                holder.eventNameText.setText("");
                Event obj = eventsList.get(position);
                holder.eventDateText.setText(obj.getDate());
                holder.eventNameText.setText(obj.getName());
            }

            @Override
            public int getItemCount() {
                return eventsList.size();
            }
        };

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            runOnUiThread(() -> binding.eventsRecyclerView.setAdapter(myAdaptor));
        });

        eventModel.selectedEvent.observe(this, newEvent -> {
            eventFragment = new EventDetailsFragment(newEvent);
            hideKeyboard(binding.getRoot());
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("")
                    .replace(R.id.fragmentLocation, eventFragment)
                    .commit();
        });
        binding.eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.citySearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        binding.radiusSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = this.prefs.edit();
        editor.putString("apiPrefix", this.urlPrefix);
        editor.putString("city", this.cityName);
        editor.putInt("radius", this.radius);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        this.urlPrefix = prefs.getString("apiPrefix", "https://app.ticketmaster.com/discovery/v2/events.json?apikey=P92vWxEOv7rnD5pM2xn6XjbVzGP8fvM4");
        this.cityName = prefs.getString("city", "");
        this.radius = prefs.getInt("radius", 0);
        search(cityName, radius);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId", "SetTextI18n"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.favoriteList:
                if (getSupportFragmentManager().getBackStackEntryCount() > 0 ) {
                    super.onBackPressed();
                }
                binding.resultText.setText(R.string.Favorites);
                Executor thread = Executors.newSingleThreadExecutor();
                AtomicReference<List<Event>> events = new AtomicReference<>();
                thread.execute(() -> {
                    events.set(eventDAO.getAllEvent());
                    if( events.get().size() != eventsList.size()) {
                        eventsList.clear();
                        eventsList.addAll(events.get());

                        runOnUiThread(() -> {
                            myAdaptor.notifyDataSetChanged();
                            eventModel.events.setValue(eventsList);
                        });

                    }
                });
                break;
        }

        return true;


    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void search(String city, int radius) {
        eventsList.clear();
        try {
            this.searchURL = this.urlPrefix + "&city=" + URLEncoder.encode(city, "UTF-8") + "&radius=" + radius;
            @SuppressLint({"SetTextI18n", "NotifyDataSetChanged", "SimpleDateFormat"}) JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, this.searchURL, null, response -> {
                try {
                    JSONArray events = response.getJSONObject("_embedded").getJSONArray("events"); //got the Array

                    for (int i = 0; i < events.length(); i++) {

                        JSONObject eventJson = events.getJSONObject(i); //got a single event in the array
                        String eventDate = "";
                        String eventTime = "";
                        if (eventJson.getJSONObject("dates").getJSONObject("start").getString("noSpecificTime").equals("false")) {
                            eventDate = eventJson.getJSONObject("dates").getJSONObject("start").getString("localDate");
                            eventTime = eventJson.getJSONObject("dates").getJSONObject("start").getString("localDate");
                        } else {
                            eventDate = eventJson.getJSONObject("dates").getJSONObject("start").getString("localDate");
                            String timezone = eventJson.getJSONObject("dates").getString("timezone");
                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            format.setTimeZone(TimeZone.getTimeZone(timezone));
                            eventDate = format.format(Objects.requireNonNull(new SimpleDateFormat("yyyy-MM-dd").parse(eventDate)));
                            eventTime = format.format(Objects.requireNonNull(new SimpleDateFormat("HH:mm").parse(eventDate)));
                        }
                        int minPrice = 0;
                        int maxPrice = 0;
                        String currency = "";
                        if (eventJson.has("priceRanges")) {
                            minPrice = eventJson.getJSONArray("priceRanges").getJSONObject(0).getInt("min");
                            maxPrice = eventJson.getJSONArray("priceRanges").getJSONObject(0).getInt("max") ;
                            currency = eventJson.getJSONArray("priceRanges").getJSONObject(0).getString("currency");
                        }
                        Event event = new Event(
                                eventJson.getString("name"),
                                eventJson.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name"),
                                eventDate,
                                eventTime,
                                eventJson.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("address").getString("line1"),
                                eventJson.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("postalCode"),
                                eventJson.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("city").getString("name"),
                                eventJson.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("state").getString("stateCode"),
                                eventJson.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("country").getString("name"),
                                eventJson.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONArray("images").getJSONObject(0).getString("url"),
                                eventJson.getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name"),
                                eventJson.getString("url"),
                                eventJson.getJSONArray("images").getJSONObject(1).getString("url"),
                                minPrice, maxPrice, currency,
                                false
                        );
                        //Event event = new Event(eventJson);
                        eventsList.add(event);
                    }//end of for loop
                    myAdaptor.notifyDataSetChanged();
                    eventModel.events.setValue(eventsList);
                    binding.resultText.setText(String.format(getResources().getString(R.string.show_result),cityName.toUpperCase(), radius));
                } catch (JSONException e) {
                    e.printStackTrace();
                    binding.resultText.setText(String.format(getResources().getString(R.string.no_result),cityName.toUpperCase(), radius));
                    eventsList.clear();
                    myAdaptor.notifyDataSetChanged();
                    eventModel.events.setValue(null);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            },  (error) -> {});

            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView eventDateText;
        TextView eventNameText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(click -> {
                int position = getAbsoluteAdapterPosition();
                Event selected = eventsList.get(position);
                eventModel.selectedEvent.postValue(selected);
            });

            eventDateText = itemView.findViewById(R.id.datePreview);
            eventNameText = itemView.findViewById(R.id.namePreview);
        }

    }

}