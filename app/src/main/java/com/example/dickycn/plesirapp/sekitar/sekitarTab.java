package com.example.dickycn.plesirapp.sekitar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dickycn.plesirapp.ApiVolley;
import com.example.dickycn.plesirapp.R;
import com.example.dickycn.plesirapp.webserviceURL;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class sekitarTab extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    GridView grid;
    TextView title;
    private ProgressDialog pDialog;
    private List<sekitar> seklist = new ArrayList<sekitar>();

    public View v;
    private MapView mMapView;
    private GoogleMap googleMap;
    public String provider;
    public LatLng userLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public Marker myLocation;
    public Double myLat,myLong;
    public LatLng MyLoc, wisata1;
//    private OnFragmentInteractionListener mListener;

//    public double myLong, myLa;

    //percobaan ke dua
    private FusedLocationProviderClient mFusedLocationClient;


    public sekitarTab() {

        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static sekitarTab newInstance(String param1, String param2) {
        sekitarTab fragment = new sekitarTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        v = inflater.inflate(com.example.dickycn.plesirapp.R.layout.fragment_sekitar_tab, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) v.getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap1) {
                googleMap = googleMap1;

                if (ActivityCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.INTERNET
                    }, 10);
                    // TODO: Consider calling
                    return;
                } else {
                }
                googleMap.setMyLocationEnabled(true);

                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(v.getContext());

                mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            MyLoc = new LatLng(location.getLatitude(),location.getLongitude());
                            myLat = location.getLatitude();
                            myLong = location.getLongitude();
                            float zoomLevel = 13; //This goes up to 21
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MyLoc, zoomLevel));
                            Toast.makeText(getActivity(), "My Position : "+myLat+","+myLong, Toast.LENGTH_SHORT).show();
                           /* myLocation = googleMap.addMarker(new MarkerOptions().position(MyLoc)
                                    .title("Lokasiku")
                                    );*/
                        }else {
                            Toast.makeText(getActivity(), "location null", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                locationManager = (LocationManager)v.getContext().getSystemService(Context.LOCATION_SERVICE);
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        MyLoc = new LatLng(location.getLatitude(),location.getLongitude());
                        if(myLocation != null){
                            myLocation.remove();
                            /*myLocation = googleMap.addMarker(new MarkerOptions().position(MyLoc)
                                    .title("Lokasiku")
                                    );*/

                        }else {
                            /*myLocation = googleMap.addMarker(new MarkerOptions().position(MyLoc)
                                    .title("Lokasiku")
                                    );*/
                        }
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(i);

                    }
                };
                locationManager.requestLocationUpdates("gps",1000,1,locationListener);
                /*wisata1 = new LatLng(-6.966667, 110.416664);
                googleMap.addMarker(new MarkerOptions().position(wisata1)
                        .title("tempat wisata")
                        .snippet("oleh rezza")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_2)));*/

            }
        });

        final sekitarAdapter adapter = new sekitarAdapter(v.getContext(), seklist);
        grid = (GridView)v.findViewById(R.id.grid_view);
        grid.setAdapter(adapter);



        JSONObject json = new JSONObject();
        ApiVolley req = new ApiVolley(getContext(), json, "GET", webserviceURL.get_sekitar, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                seklist.clear();

                hidePDialog();
                // Important Note : need to use try catch when parsing JSONObject, no need when parsing string

                try {
                   // Log.d("cekks", result.toString());
                    JSONObject responseAPI = new JSONObject(result);
                    JSONArray arr = responseAPI.getJSONArray("response");
                    String status = responseAPI.getJSONObject("metadata").getString("status");
                    responseAPI = null;
                    for (int i = 0; i <= arr.length(); i++) {
                        JSONObject ar = arr.getJSONObject(i);
                        sekitar a = new sekitar();
                        String namaWisata = ar.getString("nama_wisata");
                        String kategori=ar.getString("deskripsi");
                        Double locationWisataLat = Double.parseDouble(ar.getString("latitude")),
                                locationWisataLong=Double.parseDouble(ar.getString("longtitude"));

                      //  wisata1 = new LatLng(-6.966667, 110.416664);
                        //Log.d("cekks2", ar.getString("latitude"));
                        //Log.d("cekks3", Integer.toString(arr.length()));
                        LatLng locWisata = new LatLng(locationWisataLat,locationWisataLong);
                        googleMap.addMarker(new MarkerOptions().position(locWisata)
                                .title(namaWisata)
                                .snippet(kategori)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_2)));
                        Log.d("cekks2", Double.toString( CalculationByDistance(MyLoc,locWisata)));
//

                        Log.d("cekbawah","metu");
                        if(CalculationByDistance(MyLoc,locWisata)<= 5){
                            Log.d("cekbawah","metu");
                            a.setId_wisata(ar.getInt("id_wisata"));
                            //a.setKategori(ar.getString("nama_kategori"));
                            a.setNamaWisata(namaWisata);
                            a.setImg(ar.getString("pic_wisata"));
                            a.setLatitude(locationWisataLat);
                            a.setLongtitude(locationWisataLong);
                            seklist.add(a);
                            Log.d("cekbawah","metu");
                        //Toast.makeText(getActivity(), "distance = "+CalculationByDistance(MyLoc,locWisata), Toast.LENGTH_SHORT).show(); // 1

                        }
//                        Toast.makeText(getActivity(), "distance = "+CalculationByDistance(MyLoc,locWisata), Toast.LENGTH_SHORT).show(); // 1
//                        Toast.makeText(getActivity(), "distance to blora= "+CalculationByDistance(MyLoc,wisata1), Toast.LENGTH_SHORT).show(); //519

                    }


                } catch (Exception e) {

                    e.printStackTrace();
//                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String result) {

            }
        });

        return v;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog(){
        if (pDialog !=null){
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return kmInDec;
    }

//    protected void createLocationRequest() {
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(10000);
//        mLocationRequest.setFastestInterval(5000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.INTERNET
                        }, 10);
                        return;
                    }googleMap.setMyLocationEnabled(true);
//                locationManager.requestLocationUpdates("gps",5000,0,locationListener);
                return;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
}
