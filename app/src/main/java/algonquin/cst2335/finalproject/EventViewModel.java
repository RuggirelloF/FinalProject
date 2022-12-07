package algonquin.cst2335.finalproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class EventViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Event>> events = new MutableLiveData<>();
    public MutableLiveData<Event> selectedEvent = new MutableLiveData<>();
}