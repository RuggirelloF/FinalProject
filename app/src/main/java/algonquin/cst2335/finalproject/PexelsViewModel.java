package algonquin.cst2335.finalproject;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PexelsViewModel extends ViewModel {
    public MutableLiveData<ArrayList<PexelsModel>> images = new MutableLiveData< >();
    public MutableLiveData<PexelsModel> selectedImage = new MutableLiveData< >();
}
