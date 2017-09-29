package com.example.dickycn.plesirapp.rekomendasi;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dickycn.plesirapp.R;
import com.example.dickycn.plesirapp.webserviceURL;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rejak on 8/22/2017.
 */

public class CustomListAdapter extends BaseAdapter {

    private Activity context;
    private List<wisata> listwisata;
    public CustomListAdapter(Activity context, List<wisata> listwisata) {
        this.context = context;
        this.listwisata = listwisata;
    }

    @Override
    public int getCount() {
        return listwisata.size();
    }

    @Override
    public Object getItem(int i) {
        return listwisata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.wisata_list, null,true);
        final wisata m = listwisata.get(position);
        Log.d("judul",m.getTitle());
        //image


        ImageView gbr=(ImageView) rowView.findViewById(R.id.coverImageView);
        TextView Judul1 = (TextView) rowView.findViewById(R.id.titleTextView);

        String image_url= webserviceURL.lokasi_gbr+m.getThumbnailUrl();
        Picasso.with(context).load(image_url)
                .placeholder(R.drawable.cob) // optional
                .error(R.drawable.cob).into(gbr);

        Judul1.setText(m.getThumbnailUrl());

        return rowView;

    };
}