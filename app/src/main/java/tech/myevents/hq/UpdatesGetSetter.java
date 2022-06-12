package tech.myevents.hq;

class UpdatesGetSetter {

    private int id, broadcastId;
    private String interestCode, type;
    private String locationCode;
    private String broadcastName;
    private String venueTitle;
    private String eventDetails;
    private String minPrice;
    private String maxPrice;
    private String startTimestamp;
    private String endTimestamp;
    private String bitmapName;
    private String video;
    private String updateTime, desc1, desc2, desc3, desc4, broadcastRangeCode;

    public UpdatesGetSetter(int id, int broadcastId, String type, String interestCode,
                            String locationCode, String broadcastName, String venueTitle,
                            String eventDetails, String minPrice, String maxPrice,
                            String startTimestamp, String endTimestamp, String bitmapName,
                            String video, String updateTime, String desc1, String desc2,
                            String desc3, String desc4, String broadcastRangeCode) {
        this.setId(id);
        this.setBroadcastId(broadcastId);
        this.setType(type);
        this.setInterestCode(interestCode);
        this.setLocationCode(locationCode);
        this.setBroadcastName(broadcastName);
        this.setVenueTitle(venueTitle);
        this.setEventDetails(eventDetails);
        this.setMinPrice(minPrice);
        this.setMaxPrice(maxPrice);
        this.setStartTimestamp(startTimestamp);
        this.setEndTimestamp(endTimestamp);
        this.setBitmapName(bitmapName);
        this.setVideo(video);
        this.setUpdateTime(updateTime);
        this.setDesc1(desc1);
        this.setDesc2(desc2);
        this.setDesc3(desc3);
        this.setDesc4(desc4);
        this.setBroadcastRangeCode(broadcastRangeCode);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBroadcastId() {
        return broadcastId;
    }

    public void setBroadcastId(int broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getInterestCode() {
        return interestCode;
    }

    public void setInterestCode(String interestCode) {
        this.interestCode = interestCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getBroadcastName() {
        return broadcastName;
    }

    public void setBroadcastName(String broadcastName) {
        this.broadcastName = broadcastName;
    }

    public String getVenueTitle() {
        return venueTitle;
    }

    public void setVenueTitle(String venueTitle) {
        this.venueTitle = venueTitle;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(String endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public String getBitmapName() {
        return bitmapName;
    }

    public void setBitmapName(String bitmapName) {
        this.bitmapName = bitmapName;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }

    public String getDesc4() {
        return desc4;
    }

    public void setDesc4(String desc4) {
        this.desc4 = desc4;
    }

    public String getBroadcastRangeCode() {
        return broadcastRangeCode;
    }

    public void setBroadcastRangeCode(String broadcastRangeCode) {
        this.broadcastRangeCode = broadcastRangeCode;
    }
}
