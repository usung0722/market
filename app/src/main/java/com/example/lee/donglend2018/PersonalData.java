package com.example.lee.donglend2018;


public class PersonalData {
    private String member_id;
    private String member_selete;
    private String member_title;
    private String member_price;
    private String member_contents;
    private String member_img;
    private String member_x;
    private String member_y;

    public String getMember_id() {
        return member_id;
    }

    public String getMember_selete() {
        if(member_selete.equals("sell")){
            member_selete ="팝니다";
        }
        else if(member_selete.equals("buy")){
            member_selete ="삽니다";
        }

        return member_selete;
    }

    public String getMember_title() {
        return member_title;
    }
    public String getMember_price() {
        return member_price;
    }
    public String getMember_contents() {
        return member_contents;
    }
    public String getMember_img() {
        return member_img;
    }
    public String getMember_x() {
        return member_x;
    }
    public String getMember_y() {
        return member_y;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public void setMember_selete(String member_selete) {
        this.member_selete = member_selete;
    }

    public void setMember_title(String member_title) {
        this.member_title = member_title;
    }
    public void setMember_price(String member_price) {
        this.member_price = member_price;
    }
    public void setMember_contents(String member_contents) {
        this.member_contents = member_contents;
    }
    public void setMember_img(String member_img) {
        this.member_img = member_img;
    }
    public void setMember_x(String member_x) {
        this.member_x = member_x;
    }
    public void setMember_y(String member_y) {
        this.member_y = member_y;
    }




}
