package com.kuliah.telegram;

public class ContactModel {
    private String namaDepan;
    private String namaBelakang;
    private String nomor;

    public ContactModel(String namaDepan, String namaBelakang, String nomor) {
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.nomor = nomor;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public String getNomor() {
        return nomor;
    }
}
