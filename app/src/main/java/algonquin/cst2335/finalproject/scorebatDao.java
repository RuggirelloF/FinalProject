package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface scorebatDao {

    @Insert
    public  void insertMessage(scorebatEntity sbEntity);

    @Query("SELECT * FROM scorebatEntity;")
    public List<scorebatEntity> getAllFavs();

    @Delete
    public void deleteMessage(scorebatEntity sbEntity);
}
