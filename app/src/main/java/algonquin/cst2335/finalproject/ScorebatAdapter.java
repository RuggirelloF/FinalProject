package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;

public class ScorebatAdapter extends RecyclerView.Adapter<ScorebatAdapter.MyViewHolder> {

    private Context sbContext;
    private ArrayList<ScorebatModelClass> sbData;
    private Boolean clicked = false;



    public ScorebatAdapter(ArrayList<ScorebatModelClass> sbData,Context sbContext) {
        this.sbContext = sbContext;
        this.sbData = sbData;
    }

    @NonNull
    @Override
    public ScorebatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(sbContext);
        view = inflater.inflate(R.layout.scorebat_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ScorebatModelClass sbModel = sbData.get(position);
        holder.title.setText(sbModel.getTitle());
        holder.date.setText(sbModel.getDate());
        holder.compName.setText(sbModel.getCompName());
        holder.side1Name.setText("Watch " + sbModel.getTeam1Name());
        holder.side2Name.setText("Watch " + sbModel.getTeam2Name());

        Picasso.get().load(sbModel.getThumbnail()).into(holder.thumbnail);

        //Initial Visibility
        holder.watchLiveBtn.setVisibility(View.GONE);
        holder.side1Name.setVisibility(View.GONE);
        holder.side2Name.setVisibility(View.GONE);
        holder.thumbnail.setVisibility(View.GONE);
        holder.favButton.setVisibility(View.GONE);

        holder.side1Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(sbModel.sbWatchLink1);
                sbContext.startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

        holder.side2Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(sbModel.sbWatchLink2);
                sbContext.startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

        holder.watchLiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(sbModel.sbStreamUrl);
                sbContext.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.watchLiveBtn.getVisibility() == View.GONE){
                    Toast.makeText(sbContext, sbModel.getTitle(), Toast.LENGTH_SHORT).show();
                    //holder.sbInfoMatchTitle.setText(sbModel.getTitle());
                    holder.watchLiveBtn.setVisibility(View.VISIBLE);
                    holder.side1Name.setVisibility(View.VISIBLE);
                    holder.side2Name.setVisibility(View.VISIBLE);
                    holder.thumbnail.setVisibility(View.VISIBLE);
                    holder.favButton.setVisibility(View.VISIBLE);
                }else {
                    holder.watchLiveBtn.setVisibility(View.GONE);
                    holder.side1Name.setVisibility(View.GONE);
                    holder.side2Name.setVisibility(View.GONE);
                    holder.thumbnail.setVisibility(View.GONE);
                    holder.favButton.setVisibility(View.GONE);
                }
            }
        });
        //holder.itemView.setOnClickListener
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });


    }


    @Override
    public int getItemCount() {
        return sbData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //BASE
        TextView title;
        ImageView thumbnail;
        TextView date;
        TextView compName;
        Button watchLiveBtn;

        //side1
        Button side1Name;
        String match1Url;

        //side2
        Button side2Name;
        String match2Url;

        //view
        public CardView cardView;

        //fav Button
        Button favButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.sb_title);
            thumbnail = itemView.findViewById(R.id.sb_thumbnail);
            date = itemView.findViewById(R.id.sb_date);
            compName = itemView.findViewById(R.id.sb_comp_title);
            side1Name = itemView.findViewById(R.id.sb_watchmatch1);
            side2Name = itemView.findViewById(R.id.sb_watchmatch2);
            watchLiveBtn = itemView.findViewById(R.id.sb_live_match_btn);
            cardView = itemView.findViewById(R.id.sb_recyclerView);
            favButton = itemView.findViewById(R.id.favButton);



        }
    }

    public void showAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(sbContext);
        alert.setTitle("Add to Favourites?");
        alert.setMessage("Yes or no?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(sbContext, "Added to Favourites", Toast.LENGTH_SHORT).show();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(sbContext, "Nothing was saved.", Toast.LENGTH_SHORT).show();
            }
        });
        alert.show();
    }
}
