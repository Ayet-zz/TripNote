package com.tbuonomo.jawgmapsample;

import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<MediaObjectRecyclerView> dataset;


    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Item and view corresponding to one item
        public TextView textView;
        //private VideoView VideoView;
        private ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.mytv);
            imageView=(ImageView) v.findViewById(R.id.myiv);
            //VideoView=(VideoView) v.findViewById(R.id.myvv);
        }
        //fill the cells with a parameter
        public void bind(MediaObjectRecyclerView mediaObject){
            textView.setText(mediaObject.getText());
            if(mediaObject.getImageUrl()!=null) {
                Picasso.with(imageView.getContext()).load(mediaObject.getImageUrl()).into(imageView);
            }
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemAdapter(List<MediaObjectRecyclerView> dataset) {
        this.dataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_message, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        MediaObjectRecyclerView myObject = dataset.get(position);
        holder.bind(myObject);
        //holder.textView.setText(dataset.get(position).getText());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}