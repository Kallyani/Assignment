package com.ays.assignment.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ays.assignment.R;
import com.ays.assignment.model.Canada;
import com.ays.assignment.util.CustomVolleyRequest;

import java.util.List;

/**
 * Created by Kuhu on 5/2/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;

    List<Canada> canada;

    public CardAdapter(List<Canada> canada, Context context) {
        super();
        this.canada = canada;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Canada c = canada.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();

        if (c.getThumbnailUrl() != null) {
            holder.imageView.setVisibility(View.VISIBLE);
            imageLoader.get(c.getThumbnailUrl(), ImageLoader.getImageListener(holder.imageView, 0, 0));
            holder.imageView.setImageUrl(c.getThumbnailUrl(), imageLoader);
        } 

        if (c.getTitle().equalsIgnoreCase(" ")) {
            holder.textViewTitle.setVisibility(View.GONE);
        } else {
            holder.textViewTitle.setVisibility(View.VISIBLE);
            holder.textViewTitle.setText(c.getTitle());
        }

        if (c.getDescription().equalsIgnoreCase(" ")) {
            holder.textViewDescription.setVisibility(View.GONE);
        } else {
            holder.textViewDescription.setVisibility(View.VISIBLE);
            holder.textViewDescription.setText(c.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return canada.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView imageView;
        public TextView textViewTitle;
        public TextView textViewDescription;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.niv_thumbnail);
            textViewTitle = (TextView) itemView.findViewById(R.id.tv_title);
            textViewDescription = (TextView) itemView.findViewById(R.id.tv_description);

        }
    }

}
