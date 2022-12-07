package algonquin.cst2335.finalproject.sbView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.scorebatEntity;


public class ScorebatViewModel extends ViewModel {
    public MutableLiveData<ArrayList<scorebatEntity>> savedFavoutires = new MutableLiveData<>();
}
