package com.walmart.wk1.instagramclient.instagramclient;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dvalia on 10/11/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {


    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, 0, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        //get data from this view
        InstagramPhoto photo = getItem(position);


        //check if we are using a recycle view; if not we need to inflate
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false);
        }

        //lookup the views in populating data(image, caption, profileImage, username, likes)
        TextView tvUsername = (TextView)convertView.findViewById(R.id.tvUsername);
        TextView tvCount = (TextView)convertView.findViewById(R.id.tvCount);
        TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);

        ImageView ivPhoto = (ImageView)convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);


        TextView tvCommentsCount = (TextView)convertView.findViewById(R.id.tvCommentsCount);
        TextView tvComments = (TextView)convertView.findViewById(R.id.tvComments);
        TextView tvTime = (TextView)convertView.findViewById(R.id.tvTime);



        //clear out imageview incase having an old image
        ivPhoto.setImageResource(0);

        //insert image using picasso
        Picasso.with(getContext()).load(photo.imageURL).into(ivPhoto);
        Picasso.with(getContext()).load(photo.profileImageURL).into(ivProfileImage);

        tvCaption.setText(photo.caption);
        tvUsername.setText(photo.username);
        tvCount.setText(Integer.toString(photo.likesCount));
        tvCommentsCount.setText("view all " + photo.commentsCount + " comments");
        tvTime.setText(getRelativeTime(photo.timestamp));




//        ArrayList<String> commentsArray;
//        commentsArray = new ArrayList<String>();

        String commentStr = null;
        for(int i=0; i<photo.comments.size(); i++){

            if(photo.comments.get(i).text !=null && photo.comments.get(i).username != null)
              commentStr += photo.comments.get(i).username + " " +  photo.comments.get(i).text + "\n";

            //commentsArray.add(strComment);
        }

        if(commentStr == null){
            commentStr = photo.commentUser + ": " +  photo.firstComment;

        }

        tvComments.setText(commentStr);


//        //ListView comments
//        ArrayAdapter<String> aCommentAdapter;
//        ListView lvComments = (ListView)convertView.findViewById(R.id.lvComments);
//        aCommentAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, commentsArray);
//        lvComments.setAdapter(aCommentAdapter);
//
//        aCommentAdapter.notifyDataSetChanged();

        //return the created item as a view
        return convertView;

    }

    // Utility functions.
    public String getRelativeTime(String timestamp) {
        Long ltime = Long.parseLong(timestamp) * 1000;
        String time = DateUtils.getRelativeTimeSpanString(ltime, System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS).toString();
        return time;
    }
}
