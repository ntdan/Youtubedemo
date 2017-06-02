package vn.cusc.youtubedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class VideoList extends AppCompatActivity {

    ArrayList<Video> videos = new ArrayList<>();
    Video_Adapter adp;
    ListView lst;
    String youTubeList_URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLXImJ6jTTEi72J0ZhloHFpWmKLvIGYT0Y&maxResults=50&key=AIzaSyCs93YZcIyohoDBTQTsbZjyqbnpWagk1KY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        lst = (ListView) findViewById(R.id.listView);
        new Get_YouTubeList().execute(youTubeList_URL);
    }

    class Get_YouTubeList extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            try {
                URL u = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) u.openConnection();

                StringBuilder stringBuilder = new StringBuilder();
                String str = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while( (str = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(str+"\n");
                }

                return stringBuilder.toString();

            } catch (Exception ex) {
                Log.d("json parser error: ", ex.toString());
                return "";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("String result: ", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray items = jsonObject.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    String title = items.getJSONObject(i).getJSONObject("snippet").getString("title");
                    String id = items.getJSONObject(i).getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                    String imageURL = items.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").getString("url");

                    Video video = new Video();
                    video.setTitle(title);
                    video.setId(id);

                    videos.add(video);
                    new DownLoadImage(video, imageURL).execute();
                }

                adp = new Video_Adapter(videos, VideoList.this);
                lst.setAdapter(adp);
                lst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(VideoList.this, MainActivity.class);
                        intent.putExtra("videoid", ((Video) adp.getItem(position)).getId());
                        startActivity(intent);
                        return true;
                    }
                });
            }catch(Exception ex)
            {
                Log.d("json paser server: ", ex.toString());
            }
        }
    }


    class DownLoadImage extends AsyncTask<String, String, String> {
        Video video;
        String url;

        public DownLoadImage(Video video, String url) {
            this.video = video;
            this.url = url;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(this.url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                Bitmap b = BitmapFactory.decodeStream(connection.getInputStream());
                video.setImage(b);
            } catch (Exception ex) {
                Log.d("Load image error!", ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
