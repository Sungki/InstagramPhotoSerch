package ca.stradigi.exam.instagramphotoserch;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class PhotoDownloader extends AsyncTask<String, Void, Bitmap> {
    private static HashMap<String, Bitmap> photoMap;
    private final WeakReference<ImageView> imageViewReference;
    private String url;

    public PhotoDownloader(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);

        if (photoMap == null) {
            photoMap = new HashMap<String, Bitmap>();
        }
    }

    protected Bitmap doInBackground(String... params) {
        url = params[0];

        if (photoMap.containsKey(url)) {
            return photoMap.get(url);
        }

        return NetManager.downloadBitmap(url);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        photoMap.put(url,bitmap);

        if(isCancelled()) {
            return;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public boolean searchPhoto(String url) {
        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {

                if (photoMap.containsKey(url)) {
                    imageView.setImageBitmap(photoMap.get(url));
                    return true;
                }
            }
        }

        return false;
    }
}
