package com.otelrezervasyon.model;

import java.util.Date;

public class Rezervasyon {
    private int id;
    private String musteriAdi;
    private String musteriEmail;
    private String musteriTelefon;
    private Date girisTarihi;
    private Date cikisTarihi;
    private int odaNumarasi;
    private String odaTipi;
    private double toplamFiyat;
    private String durum;

    // Constructor
    public Rezervasyon() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMusteriAdi() {
        return musteriAdi;
    }

    public void setMusteriAdi(String musteriAdi) {
        this.musteriAdi = musteriAdi;
    }

    public String getMusteriEmail() {
        return musteriEmail;
    }

    public void setMusteriEmail(String musteriEmail) {
        this.musteriEmail = musteriEmail;
    }

    public String getMusteriTelefon() {
        return musteriTelefon;
    }

    public void setMusteriTelefon(String musteriTelefon) {
        this.musteriTelefon = musteriTelefon;
    }

    public Date getGirisTarihi() {
        return girisTarihi;
    }

    public void setGirisTarihi(Date girisTarihi) {
        this.girisTarihi = girisTarihi;
    }

    public Date getCikisTarihi() {
        return cikisTarihi;
    }

    public void setCikisTarihi(Date cikisTarihi) {
        this.cikisTarihi = cikisTarihi;
    }

    public int getOdaNumarasi() {
        return odaNumarasi;
    }

    public void setOdaNumarasi(int odaNumarasi) {
        this.odaNumarasi = odaNumarasi;
    }

    public String getOdaTipi() {
        return odaTipi;
    }

    public void setOdaTipi(String odaTipi) {
        this.odaTipi = odaTipi;
    }

    public double getToplamFiyat() {
        return toplamFiyat;
    }

    public void setToplamFiyat(double toplamFiyat) {
        this.toplamFiyat = toplamFiyat;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    @Override
    public String toString() {
        return "Rezervasyon{" +
                "id=" + id +
                ", musteriAdi='" + musteriAdi + '\'' +
                ", musteriEmail='" + musteriEmail + '\'' +
                ", musteriTelefon='" + musteriTelefon + '\'' +
                ", girisTarihi=" + girisTarihi +
                ", cikisTarihi=" + cikisTarihi +
                ", odaNumarasi=" + odaNumarasi +
                ", odaTipi='" + odaTipi + '\'' +
                ", toplamFiyat=" + toplamFiyat +
                ", durum='" + durum + '\'' +
                '}';
    }
} 