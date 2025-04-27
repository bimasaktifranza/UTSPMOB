package com.example.memo_ku.model;

public class Catatan {
    public String id;
    public String judul;
    public String isi;

    // Konstruktor default diperlukan oleh Firebase
    public Catatan() {
    }

    public Catatan(String id, String judul, String isi) {
        this.id = id;
        this.judul = judul;
        this.isi = isi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
