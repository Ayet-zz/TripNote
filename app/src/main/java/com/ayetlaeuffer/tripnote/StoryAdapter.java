package com.ayetlaeuffer.tripnote;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private static final String TAG = "StoryAdapter";

    private List<StoryRecyclerView> dataset;

    private final RequestManager glide;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Item and view corresponding to one item
        private ImageView image;
        private TextView author;
        private TextView description;
        private TextView date;

        public ViewHolder(View v) {
            super(v);
            description = v.findViewById(R.id.description);
            author = v.findViewById(R.id.author);
            date=v.findViewById(R.id.date);
            image = v.findViewById(R.id.image);
        }
        //fill the cells with a parameter
        public void bind(StoryRecyclerView mediaObject, RequestManager glide){
            description.setText(mediaObject.getDescription());
            author.setText(mediaObject.getAuthor());
            date.setText(mediaObject.getDate());
            glide.load(mediaObject.getImage())
                    .apply(new RequestOptions().transforms(new CenterCrop()))
                    .into(image);
        }

    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public StoryAdapter(List<StoryRecyclerView> dataset, RequestManager glide) {
        this.dataset = dataset;
        this.glide=glide;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_story, parent, false);
        return new ViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        StoryRecyclerView myObject = dataset.get(position);
        holder.bind(myObject, glide);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }


}