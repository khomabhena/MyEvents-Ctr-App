package tech.myevents.hq;

class NowPlayingGetSetter {

    private int id, eventId, views, likes;
    private String interestCode;
    private String locationCode;
    private String eventName;
    private String eventVenue;
    private String eventDetails;
    private String minPrice;
    private String maxPrice;
    private String startTimestamp;
    private String endTimestamp;
    private String bitmapName;
    private String viewed;
    private String liked;
    private String video, when;

    NowPlayingGetSetter(int id, int eventId, int views, int likes, String interestCode,
                        String locationCode, String eventName, String eventVenue,
                        String eventDetails, String minPrice, String maxPrice,
                        String startTimestamp, String endTimestamp, String bitmapName,
                        String viewed, String liked, String video, String when) {

        this.setBitmapName(bitmapName);
        this.setEndTimestamp(endTimestamp);
        this.setEventDetails(eventDetails);
        this.setEventId(eventId);
        this.setEventName(eventName);
        this.setEventVenue(eventVenue);
        this.setId(id);
        this.setInterestCode(interestCode);
        this.setLiked(liked);
        this.setLikes(likes);
        this.setLocationCode(locationCode);
        this.setMaxPrice(maxPrice);
        this.setMinPrice(minPrice);
        this.setStartTimestamp(startTimestamp);
        this.setVideo(video);
        this.setViewed(viewed);
        this.setViews(views);
        this.setWhen(when);
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getBitmapName() {
        return bitmapName;
    }

    private void setBitmapName(String bitmapName) {
        this.bitmapName = bitmapName;
    }

    String getEndTimestamp() {
        return endTimestamp;
    }

    private void setEndTimestamp(String endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    String getEventDetails() {
        return eventDetails;
    }

    private void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    int getEventId() {
        return eventId;
    }

    private void setEventId(int eventId) {
        this.eventId = eventId;
    }

    String getEventName() {
        return eventName;
    }

    private void setEventName(String eventName) {
        this.eventName = eventName;
    }

    String getEventVenue() {
        return eventVenue;
    }

    private void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    String getInterestCode() {
        return interestCode;
    }

    private void setInterestCode(String interestCode) {
        this.interestCode = interestCode;
    }

    String getLiked() {
        return liked;
    }

    void setLiked(String liked) {
        this.liked = liked;
    }

    int getLikes() {
        return likes;
    }

    void setLikes(int likes) {
        this.likes = likes;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    private void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    private void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    String getStartTimestamp() {
        return startTimestamp;
    }

    private void setStartTimestamp(String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    String getVideo() {
        return video;
    }

    private void setVideo(String video) {
        this.video = video;
    }

    String getViewed() {
        return viewed;
    }

    void setViewed(String viewed) {
        this.viewed = viewed;
    }

    int getViews() {
        return views;
    }

    void setViews(int views) {
        this.views = views;
    }

}