package algonquin.cst2335.finalproject;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

@Entity
public class Event {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Venue")
    private String venue;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "Time")
    private String time;

    @ColumnInfo(name = "Address")
    private String address;

    @ColumnInfo(name = "PostalCode")
    private String postalCode;

    @ColumnInfo(name = "City")
    private String city;

    @ColumnInfo(name = "State")
    private String state;

    @ColumnInfo(name = "Venue_Image")
    private String venueImg;

    @ColumnInfo(name = "Country")
    private String country;

    @ColumnInfo(name = "Event_Type")
    private String type;

    @ColumnInfo(name = "Ticket_Url")
    private String buyUrl;

    @ColumnInfo(name = "Image_Url")
    private String eventThumbnailURL;

    @ColumnInfo(name = "Minimum_Price")
    private int minPrice;

    @ColumnInfo(name = "Maximum_Price")
    private int maxPrice;

    @ColumnInfo(name = "Currency")
    private String currency;

    @ColumnInfo(name = "isSaved")
    private boolean isSaved;

    /**
     * Default Constructor
     * @param name
     * @param venue
     * @param date
     * @param time
     * @param address
     * @param postalCode
     * @param city
     * @param state
     * @param country
     * @param venueImg
     * @param type
     * @param buyUrl
     * @param eventThumbnailURL
     * @param minPrice
     * @param maxPrice
     * @param currency
     * @param isSaved
     */
    public Event(@NonNull String name, String venue, String date, String time, String address, String postalCode, String city, String state, String country, String venueImg, String type, String buyUrl, String eventThumbnailURL, int minPrice, int maxPrice, String currency, boolean isSaved) {
        this.name = name;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.venueImg = venueImg;
        this.type = type;
        this.buyUrl = buyUrl;
        this.eventThumbnailURL = eventThumbnailURL;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.currency = currency;
        this.isSaved = isSaved;
    }

    /**
     * Constructor for parsing data from JSON block from "app.ticketmaster.com" <a href="https://app.ticketmaster.com">"app.ticketmaster.com"</a>
     * @param jsonEvent
     * @throws JSONException If the JSON Blocks
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public Event(JSONObject jsonEvent) throws JSONException, ParseException {
        this(
                jsonEvent.getString("name"),
                jsonEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name"),
                "",
                "",
                jsonEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("address").getString("line1"),
                jsonEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("postalCode"),
                jsonEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("city").getString("name"),
                jsonEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("state").getString("stateCode"),
                jsonEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("country").getString("name"),
                jsonEvent.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONArray("images").getJSONObject(0).getString("url"),
                jsonEvent.getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name"),
                jsonEvent.getString("url"),
                jsonEvent.getJSONArray("images").getJSONObject(1).getString("url"),
                0, 0, "",
                false);
        // Date Extraction
        if (jsonEvent.getJSONObject("dates").getJSONObject("start").getString("noSpecificTime").equals("false")) {
            this.date = jsonEvent.getJSONObject("dates").getJSONObject("start").getString("localDate");
            this.time = jsonEvent.getJSONObject("dates").getJSONObject("start").getString("localDate");
        }
        // Currency Extraction
        if (jsonEvent.has("priceRanges")) {
            this.minPrice = jsonEvent.getJSONArray("priceRanges").getJSONObject(0).getInt("min");
            this.maxPrice = jsonEvent.getJSONArray("priceRanges").getJSONObject(0).getInt("max") ;
            this.currency = jsonEvent.getJSONArray("priceRanges").getJSONObject(0).getString("currency");
        }
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVenueImg() {
        return venueImg;
    }

    public void setVenueImg(String venueImg) {
        this.venueImg = venueImg;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuyUrl() {
        return buyUrl;
    }

    public void setBuyUrl(String buyUrl) {
        this.buyUrl = buyUrl;
    }

    public String getEventThumbnailURL() {
        return eventThumbnailURL;
    }

    public void setEventThumbnailURL(String eventThumbnailURL) {
        this.eventThumbnailURL = eventThumbnailURL;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
