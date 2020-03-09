package com.recyclerview_retrofit.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.recyclerview_retrofit.R;
import com.recyclerview_retrofit.model.DataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.MyViewHolder> {

    private static final String TAG = DataListAdapter.class.getSimpleName();
    private Context context;
    List<DataModel.AppliedProduct> data;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name,  txt_content;
        ImageView image_logo;

        MyViewHolder(View view) {
            super(view);
            txt_name = (TextView) view.findViewById(R.id.txt_name);
            txt_content = (TextView) view.findViewById(R.id.txt_content);
            image_logo= (ImageView) view.findViewById(R.id.image_logo);
        }
    }

    public DataListAdapter(List<DataModel.AppliedProduct> data, Context context) {
        this.context = context;
        this.data = data;
    }

    @Override
    public DataListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_item, parent, false);


        DataListAdapter.MyViewHolder myViewHolder = new DataListAdapter.MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final DataListAdapter.MyViewHolder holder, final int listPosition) {

        holder.txt_name.setText(data.get(listPosition).getName());
        holder.txt_content.setText(data.get(listPosition).getContent());
        /*try {
            Picasso.get().load(data.get(listPosition).getImagePath()).into(holder.image_logo);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
