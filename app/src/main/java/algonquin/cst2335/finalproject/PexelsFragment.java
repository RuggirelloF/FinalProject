package algonquin.cst2335.finalproject;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.Locale;

import algonquin.cst2335.finalproject.databinding.PexelsFragmentBinding;

public class PexelsFragment extends Fragment {

    PexelsModel selected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        PexelsFragmentBinding binding = PexelsFragmentBinding.inflate(inflater);


        String pexelsOriginalUrl = selected.getPexelsOriginalUrl();
        String pexelsMediumUrl = selected.getPexelsMediumUrl();
        String pexelsUrl = selected.getPexelsImageUrl();

        String pexelsCreatorName = selected.getPexelsCreatorName();
        String pexelsDescription = selected.getPexelsDescription();
        String dimensions = "Dimesnsions: ";

        Glide.with(this).load(pexelsOriginalUrl).into(binding.imageView);
        binding.pexelsFragmentAuthor.setText(pexelsCreatorName);
        binding.pexelsFragmentDesc.setText(pexelsDescription);
        binding.pexelsFragmentSize.setText(dimensions);
        binding.pexelsFragmentURL.setText(pexelsUrl);

        String language = Locale.getDefault().getLanguage();

        binding.pexelsFragmentFavBttn.setOnClickListener(click -> {

            if (language == "es"){
                AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());

                alert.setTitle("Favoritos");
                alert.setMessage("¿Querés agregar esta imagen a tus favoritos?");

                alert.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.show();
            }
            else{
                AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());

                alert.setTitle("Favorites");
                alert.setMessage("Do you wish to add this image to your favorites?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.show();
            }

        });

        binding.pexelsFragmentDownBttn.setOnClickListener(click -> {

            if (language == "es"){
                AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());

                alert.setTitle("Descargar");
                alert.setMessage("¿Querés guardar esta imagen en tu dispositivo?");

                alert.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.show();
            }
            else{
                AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());

                alert.setTitle("Download");
                alert.setMessage("Do you wish to save this image to your device?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.show();
            }

        });

        return binding.getRoot();
    }
}