package algonquin.cst2335.finalproject;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class scorebatEntity {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

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

    @ColumnInfo(name = "streamURL")
    public String streamURL;

    public scorebatEntity(){}
    public scorebatEntity(String team1Name, String team2Name,
                          String compName, String date,
                          String title, String sbWatchLink1,
                          String sbWatchLink2, String imageURL,
                          String streamURL){
        this.team1Name = team1Name;
        this.tean2Name = team2Name;
        this.compName = compName;
        this.date = date;
        this.title = title;
        this.sbWatchLink1 = sbWatchLink1;
        this.sbWatchLink2 = sbWatchLink2;
        this.imageURL = imageURL;
        this.streamURL = streamURL;
    }
}
