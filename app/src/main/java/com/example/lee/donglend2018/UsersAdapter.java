package com.example.lee.donglend2018;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CustomViewHolder>{

    private ArrayList<PersonalData> mList = null;
    private Activity context = null;



    public UsersAdapter(Activity context, ArrayList<PersonalData> list) {
        this.context = context;
        this.mList = list;

    }



    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView price;
        protected TextView selete;
        protected TextView titile;
        protected TextView contents;
        protected ImageView img;
        RelativeLayout parentLayout;



        public CustomViewHolder(View view) {
            super(view);
            this.selete = (TextView) view.findViewById(R.id.textView_list_selete);
            this.titile = (TextView) view.findViewById(R.id.textView_list_title);
            this.price = (TextView) view.findViewById(R.id.textView_list_price);
            this.img = (ImageView) view.findViewById(R.id.imageViewlist);
            parentLayout = itemView.findViewById(R.id.parent_layout);



        }
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }





    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {

        viewholder.selete.setText(mList.get(position).getMember_selete());
        viewholder.titile.setText(mList.get(position).getMember_title());
        viewholder.price.setText(mList.get(position).getMember_price()+"원");

        Log.d("imgadd", "POST response  - " + mList.get(position).getMember_img());

       Glide.with(context).asBitmap().load("http://49.170.233.37/"+mList.get(position).getMember_img()).into(viewholder.img);

        viewholder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("call item", "onClick: clicked on: "+ mList.get(position).getMember_title());


                Intent intent = new Intent(context, viewItem.class);
                intent.putExtra("id", mList.get(position).getMember_id());
                intent.putExtra("selete", mList.get(position).getMember_selete());
                intent.putExtra("title", mList.get(position).getMember_title());
                intent.putExtra("price", mList.get(position).getMember_price());
                intent.putExtra("contents", mList.get(position).getMember_contents());
                intent.putExtra("img", "http://49.170.233.37/"+mList.get(position).getMember_img());
                intent.putExtra("x", mList.get(position).getMember_x());
                intent.putExtra("y", mList.get(position).getMember_y());
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }






}