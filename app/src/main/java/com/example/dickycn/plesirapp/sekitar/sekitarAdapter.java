package com.example.dickycn.plesirapp.sekitar;

/**
 * Created by diktabagus on 26/08/2017.
 */

import android.content.Context;
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

public class sekitarAdapter extends BaseAdapter {
    private Context mContext;
    private List<sekitar> sekitarList;
//    public Integer[] mThumbIds = {
//            R.drawable.pic1, R.drawable.pic2,
//            R.drawable.pic3, R.drawable.pic4
//    };
    //Constructor
    public sekitarAdapter(Context c, List<sekitar> seklist){
        mContext = c;
        this.sekitarList=seklist;
    }

    @Override
    public int getCount(){

        return sekitarList.size();
    }

    @Override
    public Object getItem (int position){
        sekitarList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position){

        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //TODO Auto-generate method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final sekitar s = sekitarList.get(position);
        if (convertView == null){
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.wisata_sekitar, null);
            TextView namaWisata = (TextView) grid.findViewById(R.id.nama_wisata);
            ImageView gambar_kotak=(ImageView) grid.findViewById(R.id.img_thumbnail);
            namaWisata.setText(s.getNamaWisata());
            String image_url = webserviceURL.lokasi_gbr+s.getImg();
            Picasso.with(mContext).load(image_url)
                    .placeholder(R.drawable.event_small)
                    .error(R.drawable.pic4).into(gambar_kotak);

        } else {
            grid = (View) convertView;
        }
        return grid;
    }
}
