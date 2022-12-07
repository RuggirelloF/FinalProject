package algonquin.cst2335.finalproject;

public class PexelsModel {

    private String pexelsImageUrl;
    private String pexelsCreatorName;
    private String pexelsDescription;
    private String pexelsOriginalUrl;
    private String pexelsMediumUrl;

    public PexelsModel(String pexelsImageUrl, String pexelsCreatorName, String pexelsDescription, String pexelsOriginalUrl, String pexelsMediumUrl){
        this.pexelsImageUrl = pexelsImageUrl;
        this.pexelsCreatorName = pexelsCreatorName;
        this.pexelsDescription = pexelsDescription;
        this.pexelsOriginalUrl = pexelsOriginalUrl;
        this.pexelsMediumUrl = pexelsMediumUrl;
    }

    public String getPexelsImageUrl() {
        return pexelsImageUrl;
    }

    public String getPexelsCreatorName() {
        return pexelsCreatorName;
    }

    public String getPexelsDescription() {
        return pexelsDescription;
    }

    public String getPexelsOriginalUrl() {
        return pexelsOriginalUrl;
    }

    public String getPexelsMediumUrl() {
        return pexelsMediumUrl;
    }
}
