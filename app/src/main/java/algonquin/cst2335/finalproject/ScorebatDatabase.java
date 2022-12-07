package algonquin.cst2335.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {scorebatEntity.class}, version = 1)
public abstract class ScorebatDatabase extends RoomDatabase {
public abstract scorebatDao sbDao();
}
