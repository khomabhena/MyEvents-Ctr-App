package tech.myevents.hq;

class EventsGetSetterWaiting {

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
    private String video;
    private String when;
    private String confCOde;
    private String broadcastCharge, status;

    EventsGetSetterWaiting(int id, int eventId, int views, int likes, String interestCode,
                    String locationCode, String eventName, String eventVenue,
                    String eventDetails, String minPrice, String maxPrice,
                    String startTimestamp, String endTimestamp, String bitmapName,
                    String viewed, String liked, String video,String when, String confCOde, String broadcastCharge, String status){
        this.setId(id);
        this.setEventId(eventId);
        this.setViews(views);
        this.setLikes(likes);
        this.setInterestCode(interestCode);
        this.setLocationCode(locationCode);
        this.setEventName(eventName);
        this.setEventVenue(eventVenue);
        this.setEventDetails(eventDetails);
        this.setMinPrice(minPrice);
        this.setMaxPrice(maxPrice);
        this.setStartTimestamp(startTimestamp);
        this.setEndTimestamp(endTimestamp);
        this.setViewed(viewed);
        this.setLiked(liked);
        this.setVideo(video);
        this.setBitmapName(bitmapName);
        this.setWhen(when);
        this.setConfCOde(confCOde);
        this.setBroadcastCharge(broadcastCharge);
        this.setStatus(status);

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfCOde() {
        return confCOde;
    }

    public void setConfCOde(String confCOde) {
        this.confCOde = confCOde;
    }

    public String getBroadcastCharge() {
        return broadcastCharge;
    }

    public void setBroadcastCharge(String broadcastCharge) {
        this.broadcastCharge = broadcastCharge;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    String getVideo() {
        return video;
    }

    private void setVideo(String video) {
        this.video = video;
    }

    String getBitmapName() {
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

    String getLiked() {
        return liked;
    }

    void setLiked(String liked) {
        this.liked = liked;
    }

    String getViewed() {
        return viewed;
    }

    void setViewed(String viewed) {
        this.viewed = viewed;
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

    public int getId() {
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

    int getLikes() {
        return likes;
    }

    void setLikes(int likes) {
        this.likes = likes;
    }

    public String getLocationCode() {
        return locationCode;
    }

    private void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    String getMaxPrice() {
        return maxPrice;
    }

    private void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    String getMinPrice() {
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

    int getViews() {
        return views;
    }

    void setViews(int views) {
        this.views = views;
    }
}
