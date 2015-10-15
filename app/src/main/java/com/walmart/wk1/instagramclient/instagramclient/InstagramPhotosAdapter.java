package com.walmart.wk1.instagramclient.instagramclient;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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


    private static class ViewHolder{

        TextView tvUsername;
        TextView tvCount;
        TextView tvCaption;
        TextView tvCommentsCount;
        TextView tvComments;
        TextView tvTime;

        ImageView ivPhoto;
        ImageView ivProfileImage;

        LinearLayout linearLayout;

    }
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, 0, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        //get data from this view
        InstagramPhoto photo = getItem(position);

//        System.out.println("getView of user: " + photo.username);

        ViewHolder viewHolder;

        //check if we are using a recycle view; if not we need to inflate
        if(convertView == null){
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false);

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_photo,parent,false);

            //lookup the views in populating data(image, caption, profileImage, username, likes)
            viewHolder.tvUsername = (TextView)convertView.findViewById(R.id.tvUsername);
            viewHolder.tvCount = (TextView)convertView.findViewById(R.id.tvCount);
            viewHolder.tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);

            viewHolder.ivPhoto = (ImageView)convertView.findViewById(R.id.ivPhoto);
            viewHolder.ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);


            viewHolder.tvCommentsCount = (TextView)convertView.findViewById(R.id.tvCommentsCount);
            viewHolder.tvComments = (TextView)convertView.findViewById(R.id.tvComments);
            viewHolder.tvTime = (TextView)convertView.findViewById(R.id.tvTime);
            viewHolder.linearLayout = (LinearLayout)convertView.findViewById(R.id.linearlayout);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();


        }

        //clear out imageview incase having an old image
        viewHolder.ivPhoto.setImageResource(0);
        viewHolder.linearLayout.removeAllViews();//removing any prior views


        //insert image using picasso
        Picasso.with(getContext()).load(photo.imageURL).into(viewHolder.ivPhoto);
        Picasso.with(getContext()).load(photo.profileImageURL).into(viewHolder.ivProfileImage);

        viewHolder.tvCaption.setText(photo.caption);
        viewHolder.tvUsername.setText(photo.username);
        viewHolder.tvCount.setText(Integer.toString(photo.likesCount));
        viewHolder.tvCommentsCount.setText("view all " + photo.commentsCount + " comments");
        viewHolder.tvTime.setText(getRelativeTime(photo.timestamp));




//        ArrayList<String> commentsArray;
//        commentsArray = new ArrayList<String>();


        for(int i=0; i<photo.comments.size(); i++){

            String commentStr = null;

//            System.out.println("***Start of FOR*** " + photo.username);
            if(photo.comments.get(i).text !=null && photo.comments.get(i).username != null) {
                commentStr = photo.comments.get(i).username + ": " + photo.comments.get(i).text;

//                System.out.println("PhotoAdatper: " + i + " of " + photo.comments.size());
//                System.out.println("PhotoAdatper: " + commentStr);
            }

            //commentsArray.add(strComment);

            TextView tvText = new TextView(getContext());
           // tvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvText.setText(commentStr);

            viewHolder.linearLayout.addView(tvText);

//            System.out.println("***End of FOR***" + photo.username);

        }

//        if(commentStr == null){
//            commentStr = photo.commentUser + ": " +  photo.firstComment;
//
//        }

      //  viewHolder.tvComments.setText(commentStr);


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
