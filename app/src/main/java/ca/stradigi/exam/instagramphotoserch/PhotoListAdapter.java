package ca.stradigi.exam.instagramphotoserch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PhotoListAdapter extends ArrayAdapter<InstagramData> {

    public PhotoListAdapter(Context context, List<InstagramData> photos){
        super(context, android.R.layout.simple_list_item_1, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramData photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_photo_row,parent,false);
        }

        TextView caption = (TextView) convertView.findViewById(R.id.textCaption);
        TextView username = (TextView) convertView.findViewById(R.id.textUsername);
        ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);

        caption.setText(photo.getCaption());
        username.setText("from: " + photo.getUsername());

        imgPhoto.setImageBitmap(null);
        PhotoDownloader task = new PhotoDownloader(imgPhoto);
        if (!task.searchPhoto(photo.getImgThumnailUrl()))
            task.execute(photo.getImgThumnailUrl());

        return convertView;
    }

}