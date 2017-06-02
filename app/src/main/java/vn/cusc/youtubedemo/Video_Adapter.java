package vn.cusc.youtubedemo;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ntdan on 6/2/2017.
 */
public class Video_Adapter extends BaseAdapter {

    ArrayList<Video> list;
    Context context;

    public Video_Adapter(ArrayList<Video> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         Video_Item item;

        if(convertView == null)
        {
            item = new Video_Item();
            convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.video_item, parent, false);

            item.image = (ImageView) convertView.findViewById(R.id.imageView);
            item.title = (TextView) convertView.findViewById(R.id.tvTitle);
            item.id = (TextView) convertView.findViewById(R.id.tvID);

            convertView.setLongClickable(true);
            convertView.setTag(item);
        }
        else
        {
            item =(Video_Item)convertView.getTag();
        }

        item.title.setText(list.get(position).getTitle());
        item.id.setText(list.get(position).getId());
        item.image.setImageBitmap(list.get(position).getImage());

        return convertView;
    }


    class Video_Item
    {
        ImageView image;
        TextView title, id;
    }
}
