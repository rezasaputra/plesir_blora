package com.example.dickycn.plesirapp.rekomendasi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dickycn.plesirapp.ApiVolley;
import com.example.dickycn.plesirapp.R;
import com.example.dickycn.plesirapp.webserviceURL;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.dickycn.plesirapp.R.id.rek;

/**
 * Created by diktabagus on 12/08/2017.
 */

public class RekomendasiTab extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    public RekomendasiTab() {
        // Required empty public constructor
    }

    public static RekomendasiTab newInstance(String param1, String param2) {
        RekomendasiTab fragment = new RekomendasiTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ListView list;
    CustomListAdapter adapter;
    private ProgressDialog pDialog;
    private List<wisata> wisataList = new ArrayList<wisata>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //setUserData(user);
                } else {
                  //  goLogInScreen();
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        pDialog = new ProgressDialog(getActivity());
        View v= inflater.inflate(R.layout.fragment_rekomendasi, container, false);
        TextView a=(TextView) v.findViewById(rek);
        a.setText("apo");
        adapter = new CustomListAdapter(getActivity(), wisataList);
        list=(ListView)v.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
               /* wisata a = wisataList.get(position);
                String jud =a.getjudul();
                String sub =a.getsubjudul();
                String ins =a.getinstansi();
                String desk=a.getdesk();
                int har =a.getharga();
                int id =a.getid();
                int kuota=a.getkuota();
                int daftar=a.getdaftar();

                String im = webserviceURL.img+a.getimg();
                Intent in = new Intent(getActivity().getApplicationContext(), home_detailacara.class);
                in.putExtra("id_user",uid);
                in.putExtra("id",id);
                in.putExtra("judul", jud);
                in.putExtra("sub", sub);
                in.putExtra("ins", ins);
                in.putExtra("har", har);
                in.putExtra("img", im);
                in.putExtra("desk",desk);
                in.putExtra("kuota",kuota);
                in.putExtra("daftar",daftar);
                startActivity(in);*/
                //Toast.makeText(getActivity().getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });

        wisata ar= new wisata();

       /* ar.setTitle("coba");
        ar.setThumbnailUrl("cooo");
        wisataList.add(ar);
        ar.setTitle("coba1");
        ar.setThumbnailUrl("cooo");
        wisataList.add(ar);*/

        wisataList.clear();
        JSONObject json = new JSONObject();
        ApiVolley req = new ApiVolley(getContext(), json, "GET", webserviceURL.get_wisata, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("cekk",result.toString());
                hidePDialog();
                // Important Note : need to use try catch when parsing JSONObject, no need when parsing string

                try {
                    JSONObject responseAPI = new JSONObject(result);
                    JSONArray arr = responseAPI.getJSONArray("response");
                    String status = responseAPI.getJSONObject("metadata").getString("status");
                    responseAPI = null;
                    for(int i=0;i<arr.length();i++){
                        JSONObject ar = arr.getJSONObject(i);
                        wisata a= new wisata();

                        a.setTitle(ar.getString("nama_wisata"));
                        a.setThumbnailUrl(ar.getString("pic_wisata"));

                        wisataList.add(a);
                    }
                    //Log.d("cek isi",ar.getString("judul_acara"));

                } catch (Exception e) {

                    e.printStackTrace();
//                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(), "Terjadi kesalahan saat memuat data", Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String result) {
                Log.d("cek","eror");
            }
        });


        return  v;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }


    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
