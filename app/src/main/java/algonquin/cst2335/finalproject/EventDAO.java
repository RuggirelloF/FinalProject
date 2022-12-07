package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDAO {
    @Insert
    public long insertEvent(Event event);

    @Query("SELECT * FROM Event")
    List<Event> getAllEvent();

    @Delete
    void deleteEvent(Event event);
}
