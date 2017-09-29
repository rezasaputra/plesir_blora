package com.example.dickycn.plesirapp.sekitar;

/**
 * Created by diktabagus on 26/08/2017.
 */

public class sekitar {
    private String namaWisata,img, kategori;
    private Double latitude, longtitude;
    private int id_wisata;
    public sekitar(){

    }
    public sekitar(String namaWisata,String img, String kategori,String rute, String deskripsi, String transportasi,
                   String alamat, Double latitude, Double longtitude, String rating,int id_wisata){
        this.id_wisata = id_wisata;
        this.namaWisata = namaWisata;
        this.kategori = kategori;
        this.img = img;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getNamaWisata() {
        return namaWisata;
    }

    public void setNamaWisata(String namaWisata) {
        this.namaWisata = namaWisata;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public int getId_wisata() {
        return id_wisata;
    }

    public void setId_wisata(int id_wisata) {
        this.id_wisata = id_wisata;
    }
}
