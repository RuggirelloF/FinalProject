package algonquin.cst2335.finalproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.databinding.EventDetailsBinding;

public class EventDetailsFragment extends Fragment {

    Event selected;
    RequestQueue queue1 = null;
    RequestQueue queue2 = null;
    ImageRequest imgReq1= null;
    ImageRequest imgReq2= null;
    Bitmap image;

    public EventDetailsFragment(Event event) {
        selected = event;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventDetailsBinding binding = EventDetailsBinding.inflate(inflater);

        String thumbUrl = selected.getEventThumbnailURL();
        String venueUrl = selected.getVenueImg();
        binding.eventName.setText(selected.getName());
        binding.eventType.setText(selected.getType());
        binding.eventDate.setText(selected.getDate());
        binding.eventTime.setText(selected.getTime());
        binding.eventVenue.setText(selected.getVenue());
        binding.address.setText(selected.getAddress()+". "+selected.getPostalCode());
        binding.cityCountry.setText(selected.getCity()+", "+selected.getState()+", "+selected.getCountry());
        binding.ticketPrice.setText("Minimum Price:  " + selected.getMinPrice()+" "+selected.getCurrency() +
                                 "\nMaximum Price:    " + selected.getMaxPrice() + " " + selected.getCurrency());

        binding.buyTicketButton.setOnClickListener(click -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selected.getBuyUrl()));
            startActivity(browserIntent);
        });

        if (selected.isSaved()) {
            image = BitmapFactory.decodeFile(getActivity().getFilesDir() +"/" + selected.id + ".png");
            binding.eventImg.setImageBitmap(image);
            binding.saveButton.setText(R.string.remove);
            binding.saveButton.setOnClickListener(click -> {
                EventDatabase db = Room.databaseBuilder(this.getActivity().getApplicationContext(), EventDatabase.class, "EventDatabase").build();
                EventDAO eventDAO = db.eventDAO();
                Executor thread = Executors.newSingleThreadExecutor();
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity(), R.style.AlertDialogTheme);
                builder.setMessage(R.string.delete_warning)
                        .setTitle(R.string.alart_title)
                        .setPositiveButton(R.string.yes, (dialog, cl) -> {
                            String deletedMessage = String.format(getResources().getString(R.string.deleted_event), selected.getName());
                            Snackbar.make( binding.getRoot(), deletedMessage, Snackbar.LENGTH_LONG)
                                    .setAction(R.string.undo, clik ->{
                                        Executor thread2 = Executors.newSingleThreadExecutor();
                                        thread2.execute(() -> {
                                            eventDAO.insertEvent(selected);
                                            FileOutputStream fOUt = null;
                                            try {
                                                fOUt = getActivity().openFileOutput(selected.id + ".png", Context.MODE_PRIVATE);
                                                image.compress(Bitmap.CompressFormat.PNG, 100, fOUt);
                                                fOUt.flush();
                                                fOUt.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        });
                                    })
                                    .show();
                            thread.execute(() -> {
                                eventDAO.deleteEvent(selected);
                                getActivity().deleteFile(selected.getName() + ".png");
                            });

                        })
                        .setNegativeButton(R.string.no, (dialog, cl) -> { })
                        .create()
                        .show();

            });
        } else {
            queue1 = Volley.newRequestQueue(this.getContext());
            imgReq1 = new ImageRequest(thumbUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    image = bitmap;
                    binding.eventImg.setImageBitmap(image);
                }
            }, 1200, 1024, ImageView.ScaleType.CENTER, null, error -> {});
            queue2 = Volley.newRequestQueue(this.getContext());
            queue1.add(imgReq1);
            imgReq2 = new ImageRequest(venueUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    image = bitmap;
                    binding.venueImg.setImageBitmap(image);
                }
            }, 200, 200, ImageView.ScaleType.CENTER, null, error -> {});
            queue2.add(imgReq2);
            binding.saveButton.setText(R.string.save);
            binding.saveButton.setOnClickListener(click -> {
                EventDatabase db = Room.databaseBuilder(this.getActivity().getApplicationContext(), EventDatabase.class, "EventDatabase").build();
                EventDAO eventDAO = db.eventDAO();
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() -> {
                    selected.setSaved(true);
                    try {
                        int rowAffected = (int)eventDAO.insertEvent(selected);
                        FileOutputStream fOUt = null;
                        fOUt = getActivity().openFileOutput(selected.id + ".png", Context.MODE_PRIVATE);
                        image.compress(Bitmap.CompressFormat.PNG, 100, fOUt);
                        fOUt.flush();
                        fOUt.close();
                        getActivity().runOnUiThread(() -> {
                            if (rowAffected != 0) {
                                String message = selected.getName() + getResources().getString(R.string.saved_to_favorite);
                                Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this.getActivity(), R.string.failed_to_save, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (SQLiteConstraintException e) {
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(this.getActivity(), R.string.event_exists, Toast.LENGTH_SHORT).show();
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                });
            });
        }

        return binding.getRoot();
    }


}