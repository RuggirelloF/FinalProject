package algonquin.cst2335.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ScorebatAdapter extends RecyclerView.Adapter<ScorebatAdapter.MyViewHolder> {

    private Context sbContext;
    private ArrayList<ScorebatModelClass> sbData;


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


    }


    @Override
    public int getItemCount() {
        return sbData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView thumbnail;
        TextView date;
        TextView compName;

        //side1
        Button side1Name;
        String match1Url;

        //side2
        Button side2Name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.sb_title);
            thumbnail = itemView.findViewById(R.id.sb_thumbnail);
            date = itemView.findViewById(R.id.sb_date);
            compName = itemView.findViewById(R.id.sb_comp_title);
            side1Name = itemView.findViewById(R.id.sb_watchmatch1);
            side2Name = itemView.findViewById(R.id.sb_watchmatch2);

        }
    }
}
