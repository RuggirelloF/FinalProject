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

        binding.sbfComptext.setText(sbModel.compName);
        binding.sbfCompTitle.setText(sbModel.title);
        binding.sbfTeamtext.setText(sbModel.team1Name + " vs " + sbModel.getTeam2Name() );
        return binding.getRoot();
    }


    ScorebatModelClass sbModel;
    public MatchDetailsFragment(ScorebatModelClass sbModel){
        sbModel = sbModel;
    }

}
