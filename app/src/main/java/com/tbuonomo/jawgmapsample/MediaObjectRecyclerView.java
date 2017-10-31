package com.tbuonomo.jawgmapsample;

/**
 * Created by aurel on 31/10/2017.
 */

public class MediaObjectRecyclerView {
        private String text;
        private String imageUrl;

        public MediaObjectRecyclerView(String text, String imageUrl) {
            this.text = text;
            this.imageUrl = imageUrl;
        }
        public MediaObjectRecyclerView(String text) {
        this.text = text;
    }

        public String getText() {
            return text;
        }

        public String getImageUrl() {
            return imageUrl;
        }

    public void setText(String text) {
        this.text = text;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
