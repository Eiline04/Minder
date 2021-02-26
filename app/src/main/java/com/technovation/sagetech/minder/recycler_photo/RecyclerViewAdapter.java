 package com.technovation.sagetech.minder.recycler_photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.technovation.sagetech.minder.R;

import java.util.ArrayList;

 public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ArrayList<RecyclerModel> mList;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<RecyclerModel> mList){

        this.context = context;
        this.mList = mList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent ,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RecyclerModel cardModel = mList.get(position);
        holder.textView.setText(cardModel.getImageName());
        Glide.with(context).load(cardModel.getImageUrl()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.photoName_TextView);
            imageView = itemView.findViewById(R.id.m_image);
        }
    }


}
