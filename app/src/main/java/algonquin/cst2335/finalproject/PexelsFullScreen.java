package algonquin.cst2335.finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PexelsFullScreen extends AppCompatActivity {

    String pexelsOriginalUrl = "";
    ImageView pexelsImageFull;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pexels_full_screen);

        getSupportActionBar().hide();
        Intent intent = getIntent();
        pexelsOriginalUrl = intent.getStringExtra("pexelsOriginalUrl");
        pexelsImageFull = findViewById(R.id.pexelsImageFull);
        Glide.with(this).load(pexelsOriginalUrl).into(pexelsImageFull);
    }
}
