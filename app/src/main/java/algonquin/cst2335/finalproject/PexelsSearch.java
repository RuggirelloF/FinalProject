package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Locale;

public class PexelsSearch extends AppCompatActivity {

    SharedPreferences prefs;
    private static final String SHARED_PREF_NAME = "MyData";
    private static final String KEY_SEARCH = "search";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pexels_search);

        EditText searchBar = findViewById(R.id.pexelsSearchEditText);

        findViewById(R.id.pexelsSearchButton).setOnClickListener(click -> {
            pexelsSearch();
        });

        prefs = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String search = prefs.getString(KEY_SEARCH, null);

        searchBar.setText(search);
    }

    public void pexelsSearch(){
        EditText searchBar = findViewById(R.id.pexelsSearchEditText);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_SEARCH, searchBar.getText().toString());
        editor.apply();

        Intent searchImage = new Intent(PexelsSearch.this, PexelsActivity.class);
        searchImage.putExtra("SearchParam", searchBar.getText().toString());
        startActivity(searchImage);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pexels_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String language = Locale.getDefault().getLanguage();

        if(item.getItemId() == R.id.pexelsNavHelp){

            if(language == "es"){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("¡Bienvenido al Menú de Busqueda!");
                alert.setMessage(
                        "No te preocupes, estoy acá para ayudarte\n\n" +
                                "1. Ingresá un parametro de busqueda\n" +
                                "2. Apretá el botón \"BUSCAR\"\n" +
                                "3. Disfruta de la imagenes que se muestren\n\n" +
                                "■ Seleccionar la flecha te devuleve a la pagina anterior\n" +
                                "■ Seleccionar la casa te lleva al menú de incio\n" +
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

                alert.setTitle("Welcome to the Search Menu!");
                alert.setMessage(
                        "Don't you worry, I'm here to help you\n\n" +
                                "1. Enter a search parameter\n" +
                                "2. Press the \"SEARCH\" pexelsFragmentFavBttn\n" +
                                "3. Enjoy the displayed images\n\n" +
                                "■ Selecting the back arrow takes you back to the previous page\n" +
                                "■ Selecting the home takes you to the main menu\n" +
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

        if(item.getItemId() == R.id.pexelsNavBack){
            Intent moveToMain = new Intent(PexelsSearch.this, PexelsActivity.class);
            startActivity(moveToMain);
        }

        return super.onOptionsItemSelected(item);
    }
}