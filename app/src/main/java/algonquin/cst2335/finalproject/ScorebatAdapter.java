package algonquin.cst2335.finalproject;

import static algonquin.cst2335.finalproject.scorebat.SB_SHARED_PREFS;
import static algonquin.cst2335.finalproject.scorebat.SBURL;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class ScorebatAdapter extends RecyclerView.Adapter<ScorebatAdapter.MyViewHolder> {

    private Context sbContext;
    private ArrayList<ScorebatModelClass> sbData;
    private Boolean clicked = false;
    ArrayList<ScorebatModelClass> favsArrayList;




    public ScorebatAdapter(ArrayList<ScorebatModelClass> sbData,Context sbContext,     ArrayList<ScorebatModelClass> favsArrayList) {
        this.sbContext = sbContext;
        this.sbData = sbData;
        this.favsArrayList = favsArrayList;
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
                SharedPreferences sbPrefs = sbContext.getSharedPreferences(SB_SHARED_PREFS, sbContext.MODE_PRIVATE);
                SharedPreferences.Editor editor = sbPrefs.edit();
                editor.putString(SBURL, sbModel.sbWatchLink1);
                editor.commit();

                Uri uri = Uri.parse(sbModel.sbWatchLink1);
                sbContext.startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

        holder.side2Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sbPrefs = sbContext.getSharedPreferences(SB_SHARED_PREFS, sbContext.MODE_PRIVATE);
                SharedPreferences.Editor editor = sbPrefs.edit();
                editor.putString(SBURL, sbModel.sbWatchLink2);
                editor.commit();

                Uri uri = Uri.parse(sbModel.sbWatchLink2);
                sbContext.startActivity(new Intent(Intent.ACTION_VIEW,uri));


            }
        });

        holder.watchLiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a new shared prefrence and saving it to the phone based
                //on the button pressed the url will be saved so that next time you load into
                //the application it will launch you last viewed page.
                SharedPreferences sbPrefs = sbContext.getSharedPreferences(SB_SHARED_PREFS, sbContext.MODE_PRIVATE);
                SharedPreferences.Editor editor = sbPrefs.edit();
                editor.putString(SBURL, sbModel.sbStreamUrl);
                editor.commit();

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
                ScorebatModelClass model = new ScorebatModelClass(sbData.get(position).title,sbData.get(position).thumbnail, sbData.get(position).date,sbData.get(position).compName,sbData.get(position).team1Name,sbData.get(position).sbWatchLink1, sbModel.team1Name, sbData.get(position).sbWatchLink2,sbData.get(position).sbStreamUrl, sbData.get(position).videoEmbed);
                favsArrayList.add(model);

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

        //Watch Button
        //Button watchButton;

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
            //watchButton = itemView.findViewById(R.id.sbHightlightsBtn);



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
               //ScorebatModelClass model = new ScorebatModelClass("Erik","","","","","","","","");
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

    public String getVideoUrl(String videoEmbed){
        String url = videoEmbed;
        String[] urlSplit = url.split("src='");
        String[] urlSplit2 = urlSplit[1].split("'");
        return urlSplit2[0];
    }

    public void saveData(){
    }
}
