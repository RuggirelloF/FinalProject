package algonquin.cst2335.finalproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PexelsAdapter extends RecyclerView.Adapter<PexelsAdapter.PexelsViewHolder> {

    private Context pexelsContext;
    private ArrayList<PexelsModel> pexelsModelList;

    public PexelsAdapter(Context pexelsContext, ArrayList<PexelsModel> pexelsModelList){
        this.pexelsContext = pexelsContext;
        this.pexelsModelList = pexelsModelList;
    }

    @NonNull
    @Override
    public PexelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(pexelsContext).inflate(R.layout.pexels_model, parent, false);
        return new PexelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PexelsViewHolder holder, int position) {
        PexelsModel currentItem = pexelsModelList.get(position);

        String pexelsImageUrl = currentItem.getPexelsImageUrl();
        String pexelsCretorName = currentItem.getPexelsCreatorName();
        String pexelsDescription = currentItem.getPexelsDescription();

        holder.pexelsCreatorName.setText(pexelsCretorName);
        holder.pexelsDescription.setText(pexelsDescription);
        Glide.with(pexelsContext)
                .load(pexelsModelList
                        .get(position)
                        .getPexelsMediumUrl())
                .into(holder.pexelsImageView);
    }

    @Override
    public int getItemCount() {
        return pexelsModelList.size();
    }

    public class PexelsViewHolder extends RecyclerView.ViewHolder{

        public ImageView pexelsImageView;
        public TextView pexelsCreatorName;
        public TextView pexelsDescription;

        public PexelsViewHolder(@NonNull View itemView) {
            super(itemView);
            pexelsImageView = itemView.findViewById(R.id.pexelsImageFull);
            pexelsCreatorName = itemView.findViewById(R.id.pexelsCreatorName);
            pexelsDescription = itemView.findViewById(R.id.pexelsDescription);

        }
    }

}
