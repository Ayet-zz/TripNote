package com.ayetlaeuffer.tripnote;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    private List<StoryRecyclerView> dataset;


    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Item and view corresponding to one item
        public TextView title;
        private ImageView image;
        private TextView description;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            description=(TextView) v.findViewById(R.id.description);
            image=(ImageView) v.findViewById(R.id.image);
        }
        //fill the cells with a parameter
        public void bind(StoryRecyclerView mediaObject){
            title.setText(mediaObject.getTitle());
            description.setText(mediaObject.getDescription());
            image.setImageBitmap(mediaObject.getImage());
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public StoryAdapter(List<StoryRecyclerView> dataset) {
        this.dataset = dataset;
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
        holder.bind(myObject);
        //holder.textView.setText(dataset.get(position).getText());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}