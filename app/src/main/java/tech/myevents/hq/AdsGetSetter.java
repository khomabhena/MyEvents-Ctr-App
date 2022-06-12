package tech.myevents.hq;

class AdsGetSetter {

    private int id, adId, views, likes;
    private String interestCode, brandName, title, desc1, desc2, desc3, desc4, bitmapName, viewed;
    private String liked, duration, video, when;

    AdsGetSetter(int id, int adId, int views, int likes, String interestCode, String brandName,
                 String title, String desc1, String desc2, String desc3, String desc4,
                 String bitmapName, String viewed, String liked, String duration,
                 String video, String when) {

        this.setId(id);
        this.setAdId(adId);
        this.setViews(views);
        this.setLikes(likes);
        this.setInterestCode(interestCode);
        this.setBrandName(brandName);
        this.setTitle(title);
        this.setDesc1(desc1);
        this.setDesc2(desc2);
        this.setDesc3(desc3);
        this.setDesc4(desc4);
        this.setBitmapName(bitmapName);
        this.setViewed(viewed);
        this.setLiked(liked);
        this.setDuration(duration);
        this.setVideo(video);
        this.setWhen(when);
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getBitmapName() {
        return bitmapName;
    }

    public void setBitmapName(String bitmapName) {
        this.bitmapName = bitmapName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInterestCode() {
        return interestCode;
    }

    public void setInterestCode(String interestCode) {
        this.interestCode = interestCode;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getViewed() {
        return viewed;
    }

    public void setViewed(String viewed) {
        this.viewed = viewed;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }
}
