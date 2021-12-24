package com.example.pencatatanpenduduk.model;

public class Penduduk {

    String namaLengkap, alamat, tanggalLahir, tanggalTercatat, jenisKelamin,nomorTlp, gaji, agama, hobi, foto;
    int _id;

    public Penduduk(int _id,String namaLengkap, String alamat, String tanggalLahir, String tanggalTercatat, String jenisKelamin, String nomorTlp, String gaji, String agama, String hobi, String foto) {
        this._id = _id;
        this.namaLengkap = namaLengkap;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
        this.tanggalTercatat = tanggalTercatat;
        this.jenisKelamin = jenisKelamin;
        this.nomorTlp = nomorTlp;
        this.gaji = gaji;
        this.agama = agama;
        this.hobi = hobi;
        this.foto = foto;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getnamaLengkap() {
        return namaLengkap;
    }

    public void setnamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String gettanggalLahir() {
        return tanggalLahir;
    }

    public void settanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String gettanggalTercatat() {
        return tanggalTercatat;
    }

    public void settanggalTercatat(String tanggalTercatat) {
        this.tanggalTercatat = tanggalTercatat;
    }

    public String getjenisKelamin() {
        return jenisKelamin;
    }

    public void setjenisKelamin(String jenisKelamin) {
        jenisKelamin = jenisKelamin;
    }

    public String getNomorTlp() {
        return nomorTlp;
    }

    public void setNomorTlp(String nomorTlp) {
        this.nomorTlp = nomorTlp;
    }

    public String getGaji() {
        return gaji;
    }

    public void setGaji(String gaji) {
        this.gaji = gaji;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getHobi() {
        return hobi;
    }

    public void setHobi(String hobi) {
        this.hobi = hobi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
