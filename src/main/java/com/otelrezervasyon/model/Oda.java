package com.otelrezervasyon.model;

public class Oda {
    private int odaId; // Veritabanındaki auto-increment ID
    private int odaNumarasi;
    private String odaTipi;
    private int kapasite;
    // İsteğe bağlı: private java.sql.Timestamp eklenmeTarihi;

    public Oda() {
    }

    public Oda(int odaNumarasi, String odaTipi, int kapasite) {
        this.odaNumarasi = odaNumarasi;
        this.odaTipi = odaTipi;
        this.kapasite = kapasite;
    }

    // Getter ve Setter metotları
    public int getOdaId() {
        return odaId;
    }

    public void setOdaId(int odaId) {
        this.odaId = odaId;
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

    public int getKapasite() {
        return kapasite;
    }

    public void setKapasite(int kapasite) {
        this.kapasite = kapasite;
    }

    @Override
    public String toString() {
        return "Oda{" +
                "odaId=" + odaId +
                ", odaNumarasi=" + odaNumarasi +
                ", odaTipi='" + odaTipi + '\'' +
                ", kapasite=" + kapasite +
                '}';
    }
} 