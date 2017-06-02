package vn.cusc.youtubedemo;

import android.graphics.Bitmap;

/**
 * Created by ntdan on 6/2/2017.
 */
public class Video {
    String id;
    String title;
    Bitmap image;

    public Video() {
    }

    public Video(String id, String title, Bitmap image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
