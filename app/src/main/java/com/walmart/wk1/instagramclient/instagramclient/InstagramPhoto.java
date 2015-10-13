package com.walmart.wk1.instagramclient.instagramclient;

import java.util.ArrayList;

/**
 * Created by dvalia on 10/11/15.
 */
public class InstagramPhoto {

    public String username;
    public String caption;
    public String imageURL;
    public int imageHeight;
    public int likesCount;
    public String profileImageURL;
    public int commentsCount;
    public String timestamp;
    public String firstComment;
    public String commentUser;
    public ArrayList<InstagramComments> comments = new ArrayList<InstagramComments>();

}
