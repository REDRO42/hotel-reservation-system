package com.otelrezervasyon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.otelrezervasyon.model.Rezervasyon;
import com.otelrezervasyon.util.DatabaseConnection;

public class RezervasyonDAO {
    
    public boolean isOdaDolu(int odaNumarasi, Date girisTarihi) {
        String sql = "SELECT COUNT(*) FROM rezervasyonlar WHERE oda_numarasi = ? AND giris_tarihi = ? AND durum != 'IPTAL'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, odaNumarasi);
            pstmt.setDate(2, new java.sql.Date(girisTarihi.getTime()));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Oda durumu kontrol hatası: " + e.getMessage());
        }
        return false;
    }

    public boolean rezervasyonEkle(Rezervasyon rezervasyon) {
        if (isOdaDolu(rezervasyon.getOdaNumarasi(), rezervasyon.getGirisTarihi())) {
            return false;
        }
        String sql = "INSERT INTO rezervasyonlar (musteri_adi, musteri_email, musteri_telefon, " +
                    "giris_tarihi, cikis_tarihi, oda_numarasi, oda_tipi, toplam_fiyat, durum) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, rezervasyon.getMusteriAdi());
            pstmt.setString(2, rezervasyon.getMusteriEmail());
            pstmt.setString(3, rezervasyon.getMusteriTelefon());
            pstmt.setDate(4, new java.sql.Date(rezervasyon.getGirisTarihi().getTime()));
            pstmt.setDate(5, new java.sql.Date(rezervasyon.getCikisTarihi().getTime()));
            pstmt.setInt(6, rezervasyon.getOdaNumarasi());
            pstmt.setString(7, rezervasyon.getOdaTipi());
            pstmt.setDouble(8, rezervasyon.getToplamFiyat());
            pstmt.setString(9, rezervasyon.getDurum());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Rezervasyon ekleme hatası: " + e.getMessage());
            return false;
        }
    }
    
    public boolean rezervasyonGuncelle(Rezervasyon rezervasyon) {
        String sql = "UPDATE rezervasyonlar SET musteri_adi=?, musteri_email=?, musteri_telefon=?, " +
                    "giris_tarihi=?, cikis_tarihi=?, oda_numarasi=?, oda_tipi=?, toplam_fiyat=?, durum=? " +
                    "WHERE id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, rezervasyon.getMusteriAdi());
            pstmt.setString(2, rezervasyon.getMusteriEmail());
            pstmt.setString(3, rezervasyon.getMusteriTelefon());
            pstmt.setDate(4, new java.sql.Date(rezervasyon.getGirisTarihi().getTime()));
            pstmt.setDate(5, new java.sql.Date(rezervasyon.getCikisTarihi().getTime()));
            pstmt.setInt(6, rezervasyon.getOdaNumarasi());
            pstmt.setString(7, rezervasyon.getOdaTipi());
            pstmt.setDouble(8, rezervasyon.getToplamFiyat());
            pstmt.setString(9, rezervasyon.getDurum());
            pstmt.setInt(10, rezervasyon.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Rezervasyon güncelleme hatası: " + e.getMessage());
            return false;
        }
    }
    
    public boolean rezervasyonSil(int id) {
        String sql = "DELETE FROM rezervasyonlar WHERE id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Rezervasyon silme hatası: " + e.getMessage());
            return false;
        }
    }
    
    public Rezervasyon rezervasyonGetir(int id) {
        String sql = "SELECT * FROM rezervasyonlar WHERE id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rezervasyonOlustur(rs);
            }
        } catch (SQLException e) {
            System.err.println("Rezervasyon getirme hatası: " + e.getMessage());
        }
        return null;
    }
    
    public List<Rezervasyon> tumRezervasyonlariGetir() {
        List<Rezervasyon> rezervasyonlar = new ArrayList<>();
        String sql = "SELECT * FROM rezervasyonlar";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                rezervasyonlar.add(rezervasyonOlustur(rs));
            }
        } catch (SQLException e) {
            System.err.println("Tüm rezervasyonları getirme hatası: " + e.getMessage());
        }
        return rezervasyonlar;
    }
    
    private Rezervasyon rezervasyonOlustur(ResultSet rs) throws SQLException {
        Rezervasyon rezervasyon = new Rezervasyon();
        rezervasyon.setId(rs.getInt("id"));
        rezervasyon.setMusteriAdi(rs.getString("musteri_adi"));
        rezervasyon.setMusteriEmail(rs.getString("musteri_email"));
        rezervasyon.setMusteriTelefon(rs.getString("musteri_telefon"));
        rezervasyon.setGirisTarihi(rs.getDate("giris_tarihi"));
        rezervasyon.setCikisTarihi(rs.getDate("cikis_tarihi"));
        rezervasyon.setOdaNumarasi(rs.getInt("oda_numarasi"));
        rezervasyon.setOdaTipi(rs.getString("oda_tipi"));
        rezervasyon.setToplamFiyat(rs.getDouble("toplam_fiyat"));
        rezervasyon.setDurum(rs.getString("durum"));
        return rezervasyon;
    }

    // Belirli bir tarihte dolu olan oda numaralarını getirir (Admin Paneli için)
    public Set<Integer> getDoluOdaNumaralari(Date tarih) {
        Set<Integer> doluOdaNolari = new HashSet<>();
        String sql = "SELECT DISTINCT oda_numarasi FROM rezervasyonlar WHERE giris_tarihi <= ? AND cikis_tarihi > ? AND durum != 'IPTAL'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            java.sql.Date sqlDate = new java.sql.Date(tarih.getTime());
            pstmt.setDate(1, sqlDate);
            pstmt.setDate(2, sqlDate);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                doluOdaNolari.add(rs.getInt("oda_numarasi"));
            }
        } catch (SQLException e) {
            System.err.println("Dolu oda numaralarını getirme hatası (Tarih: " + tarih + "): " + e.getMessage());
            e.printStackTrace();
        }
        return doluOdaNolari;
    }
} 