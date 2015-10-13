package com.walmart.wk1.instagramclient.instagramclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    public static String CLIENT_ID = "7e47b8d0f7554dc8affa22b105181887";
    private ArrayList<InstagramPhoto> photos;
    private ArrayList<InstagramComments> comments;
    private InstagramPhotosAdapter photosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        photos = new ArrayList<>();

        comments = new ArrayList<>();

        //1. create adatper
        photosAdapter = new InstagramPhotosAdapter(this,photos);

        //2.create listview
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);

        //3. set adatper to list
        lvPhotos.setAdapter(photosAdapter);

        fetchPopularPhotos();
    }

    private void fetchPopularPhotos(){

//           CLIENT ID	7e47b8d0f7554dc8affa22b105181887
//        - Popular:  https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
//

        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url,null,new JsonHttpResponseHandler(){


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

//             Response
//              - Type {data => [x] => “type”} {“image”or “video”}
//              - URL {data => [x] => “images”=> “standard_resolution” => url}
//              - Caption {data => [x] => “caption”=> “text”}
//              - Author {data => [x] => “user”=> “username”}

                Log.v("DEBUG", "SUCCESS123");
                Log.v("DEBUG", response.toString());

                JSONArray photosJSON = null;

                try{
                    photosJSON = response.getJSONArray("data");
                    for(int i=0; i< photosJSON.length(); i++){

                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        photo.imageURL = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        photo.profileImageURL = photoJSON.getJSONObject("user").getString("profile_picture");
                        photo.commentsCount = photoJSON.getJSONObject("comments").getInt("count");
                        photo.timestamp = photoJSON.getString("created_time");


                        JSONArray commentsJSON = photoJSON.getJSONObject("comments").getJSONArray("data");
                        photo.firstComment = commentsJSON.getJSONObject(0).getString("text");
                        photo.commentUser = commentsJSON.getJSONObject(0).getJSONObject("from").getString("username");

                        int maxComments = Math.min(photo.commentsCount, 2);

                        for( int j=0; j <= maxComments; j++ ){

                            InstagramComments comment = new InstagramComments();
                            comment.createdTime = commentsJSON.getJSONObject(j).getLong("created_time");
                            comment.text = commentsJSON.getJSONObject(j).getString("text");
                            comment.username = commentsJSON.getJSONObject(j).getJSONObject("from").getString("username");
                            comment.profileUrl = commentsJSON.getJSONObject(j).getJSONObject("from").getString("profile_picture");
                            comment.fullname = commentsJSON.getJSONObject(j).getJSONObject("from").getString("full_name");
                            comments.add(comment);

                        }

                        photo.comments = comments;
                        photos.add(photo);

                        //clean up local array comments.
                        comments.clear();
                    }
                }catch(JSONException  e){

                }

                photosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //super.onFailure(statusCode, headers, responseString, throwable);

                //Error Handling
                Log.v("DEBUG", "ERROR");
            }
        });
    }
}
