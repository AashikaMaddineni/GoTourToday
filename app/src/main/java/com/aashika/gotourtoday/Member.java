package com.aashika.gotourtoday;
public class Member {

    String title;
    String image;
    String city;
    String story;
    String address;

    public Member(){

    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Member(String title, String image, String city, String story, String address){
        if (title.trim().equals("")) {
            title = "No Name";
        }
        if (city.trim().equals("")) {
            city = "Not available";
        }
        if (story.trim().equals("")) {
            story = "Not available";
        }
        if (address.trim().equals("")) {
            address = "Not available";
        }
        this.title=title;
        this.image=image;
        this.address=address;
        this.city=city;
        this.story=story;

    }
    public Member(String image, String title){
        this.image=image;
        this.title=title;
    }

    public Member(String image, String title, String story){
        this.image=image;
        this.title=title;
        this.story=story;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}