package com.otelrezervasyon.model;

import java.math.BigDecimal;

public class FiyatAyar {
    private int fiyatAyarId;
    private String odaTipi;
    private BigDecimal gunlukFiyat;
    // private java.sql.Timestamp guncellenmeTarihi; // İsteğe bağlı

    public FiyatAyar() {
    }

    public FiyatAyar(String odaTipi, BigDecimal gunlukFiyat) {
        this.odaTipi = odaTipi;
        this.gunlukFiyat = gunlukFiyat;
    }

    // Getter ve Setter metotları
    public int getFiyatAyarId() {
        return fiyatAyarId;
    }

    public void setFiyatAyarId(int fiyatAyarId) {
        this.fiyatAyarId = fiyatAyarId;
    }

    public String getOdaTipi() {
        return odaTipi;
    }

    public void setOdaTipi(String odaTipi) {
        this.odaTipi = odaTipi;
    }

    public BigDecimal getGunlukFiyat() {
        return gunlukFiyat;
    }

    public void setGunlukFiyat(BigDecimal gunlukFiyat) {
        this.gunlukFiyat = gunlukFiyat;
    }

    // public java.sql.Timestamp getGuncellenmeTarihi() {
    //     return guncellenmeTarihi;
    // }

    // public void setGuncellenmeTarihi(java.sql.Timestamp guncellenmeTarihi) {
    //     this.guncellenmeTarihi = guncellenmeTarihi;
    // }

    @Override
    public String toString() {
        return "FiyatAyar{" +
                "fiyatAyarId=" + fiyatAyarId +
                ", odaTipi='" + odaTipi + '\'' +
                ", gunlukFiyat=" + gunlukFiyat +
                '}';
    }
} 