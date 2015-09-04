package ca.stradigi.exam.instagramphotoserch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotoList extends Activity {

    public static final String CLIENT_ID="fb40e758f4d544829c938371447cf17d";

    private JSONObject imageData;
    private ListView listView;

    private ArrayList<InstagramData> instagramData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        Intent i = getIntent();
        String tag = i.getExtras().getString("tag");
        String instagramUrl = "https://api.instagram.com/v1/tags/" + tag + "/media/recent?client_id=" + CLIENT_ID;

        TextView textTag= (TextView) findViewById(R.id.textTagName);
        textTag.setText(tag);

        listView = (ListView) findViewById(R.id.photo_list_view);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(PhotoList.this, PhotoDisplayActivity.class);
                        i.putParcelableArrayListExtra("dataInfo", instagramData);
                        i.putExtra("current", position);
                        startActivityForResult(i, 0);
                    }
                }
        );

        RequestImagesTask request = new RequestImagesTask(instagramUrl, this);
        request.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == RESULT_OK) {
            instagramData = data.getParcelableArrayListExtra("dataInfo");
            if(instagramData.size() <= 0) {
                callFinish();
            }
            else {
                int cur = data.getExtras().getInt("current");
                listView.setAdapter(new PhotoListAdapter(this, instagramData));
                listView.requestFocus();
                listView.setItemsCanFocus(true);
                listView.setSelection(cur);
            }
        }
    }

    @Override
    public void onBackPressed() {
        callFinish();
    }

    private void callFinish() {
        instagramData.clear();
        finish();
    }

    private class RequestImagesTask extends AsyncTask<Void, Void, Void> {
        private String url;
        private Context c;

        public RequestImagesTask(String url, Context c) {
            super();
            this.url = url;
            this.c = c;
        }

        @Override
        protected Void doInBackground(Void... params) {
            imageData = NetManager.requestHttp(url);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            instagramData = new ArrayList<InstagramData>();

            try {
                for(int i=0;i < imageData.getJSONArray("data").length(); i++){
                    InstagramData photo = new InstagramData();
                    photo.setUsername(imageData.getJSONArray("data").getJSONObject(i).getJSONObject("user").getString("username"));
                    if (imageData.getJSONArray("data").getJSONObject(i).getJSONObject("caption") != null) {
                        photo.setCaption(imageData.getJSONArray("data").getJSONObject(i).getJSONObject("caption").getString("text"));
                    }
                    photo.setImgThumnailUrl(imageData.getJSONArray("data").getJSONObject(i).getJSONObject("images").getJSONObject("thumbnail").getString("url"));
                    photo.setImgStandardUrl(imageData.getJSONArray("data").getJSONObject(i).getJSONObject("images").getJSONObject("standard_resolution").getString("url"));

                    instagramData.add(photo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            listView.setAdapter(new PhotoListAdapter(c, instagramData));
        }

    }

}
