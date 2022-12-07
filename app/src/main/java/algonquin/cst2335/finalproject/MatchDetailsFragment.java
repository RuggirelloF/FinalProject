package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.finalproject.databinding.MatchDetailsFragmentBinding;

public class MatchDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        MatchDetailsFragmentBinding binding = MatchDetailsFragmentBinding.inflate(inflater);

        return binding.getRoot();
    }


    ScorebatModelClass sbModel;
    public MatchDetailsFragment(ScorebatModelClass sbModel){
        sbModel = sbModel;
    }

}
