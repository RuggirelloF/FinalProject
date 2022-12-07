package algonquin.cst2335.finalproject;

public class ScorebatModelClass
{
    //BASE
    String title;
    String thumbnail;
    String date;
    String sbStreamUrl;

    //COMPETITION
    String compName;


    //side1
    String team1Name;
    String sbWatchLink1;


    //side2
    String team2Name;
    String sbWatchLink2;



    //video
    String videoEmbed;

    public String getVideoEmbed() {
        return videoEmbed;
    }

    public void setVideoEmbed(String videoEmbed) {
        this.videoEmbed = videoEmbed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }


    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getSbWatchLink1() {
        return sbWatchLink1;
    }

    public void setSbWatchLink1(String sbWatchLink1) {
        this.sbWatchLink1 = sbWatchLink1;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public String getSbWatchLink2() {
        return sbWatchLink2;
    }

    public void setSbWatchLink2(String sbWatchLink2) {
        this.sbWatchLink2 = sbWatchLink2;
    }

    public String getSbStreamUrl() {
        return sbStreamUrl;
    }

    public void setSbStreamUrl(String sbStreamUrl) {
        this.sbStreamUrl = sbStreamUrl;
    }

    public ScorebatModelClass(String title, String thumbnail, String date,
                                    String compName, String team1Name, String sbWatchLink1,
                                        String team2Name, String sbWatchLink2, String sbStreamUrl){
        this.title = title;
        this.date = date;
        this.thumbnail = thumbnail;
        this.compName = compName;
        this.team1Name = team1Name;
        this.sbWatchLink1 = sbWatchLink1;
        this.team2Name = team2Name;
        this.sbWatchLink2 = sbWatchLink2;
        this.sbStreamUrl = sbStreamUrl;
        this.sbStreamUrl = sbStreamUrl;

    }
}
