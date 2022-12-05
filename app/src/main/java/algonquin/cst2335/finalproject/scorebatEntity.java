package algonquin.cst2335.finalproject;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class scorebatEntity {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "team1Name")
    public String team1Name;

    @ColumnInfo(name = "team2Name")
    public String tean2Name;

    @ColumnInfo(name = "compName")
    public String compName;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "sbWatchLink1")
    public String sbWatchLink1;

    @ColumnInfo(name = "sbWatchLink2")
    public String sbWatchLink2;

    @ColumnInfo(name = "imageURL")
    public String imageURL;
}
