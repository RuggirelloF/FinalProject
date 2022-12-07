package algonquin.cst2335.finalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;

public class MainActivity extends NavigationDrawer {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setActivityTitle("Homepage");
        setContentView(activityMainBinding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(android.R.menu., menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
//            case android.R.id.about:
//                Toast.makeText(this,"Version:1.0 \n" +
//                        "Created by:\n" +
//                        "Alex Situ \n" +
//                        "Kelsey-Lyn Frederick-Dubois \n" +
//                        "Sachleen Kaur \n" +
//                        "Thomas Graham", Toast.LENGTH_LONG).show();
//                break;
//
//            case android.R.id.help:
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage("Use the navigation menu on the left of the screen to access different activities");
//                builder.create().show();
//                break;
        }

        return true;
    }
}