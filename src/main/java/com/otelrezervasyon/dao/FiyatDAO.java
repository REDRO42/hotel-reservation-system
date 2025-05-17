package com.otelrezervasyon.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.otelrezervasyon.model.FiyatAyar;
import com.otelrezervasyon.util.DatabaseConnection;

public class FiyatDAO {

    // Belirli bir oda tipi için günlük fiyatı getirir
    public BigDecimal getGunlukFiyat(String odaTipi) {
        String sql = "SELECT gunluk_fiyat FROM fiyat_ayarlari WHERE oda_tipi = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, odaTipi);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("gunluk_fiyat");
            }
        } catch (SQLException e) {
            System.err.println(odaTipi + " için fiyat getirme hatası: " + e.getMessage());
            e.printStackTrace();
        }
        System.err.println("Uyarı: " + odaTipi + " için fiyat ayarı bulunamadı, varsayılan 0.0 kullanılıyor.");
        return BigDecimal.ZERO;
    }

    // Tüm fiyat ayarlarını listele (Admin paneli için)
    public List<FiyatAyar> getAllFiyatAyarlari() {
        List<FiyatAyar> ayarlar = new ArrayList<>();
        String sql = "SELECT fiyat_ayar_id, oda_tipi, gunluk_fiyat FROM fiyat_ayarlari ORDER BY oda_tipi ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                FiyatAyar ayar = new FiyatAyar();
                ayar.setFiyatAyarId(rs.getInt("fiyat_ayar_id"));
                ayar.setOdaTipi(rs.getString("oda_tipi"));
                ayar.setGunlukFiyat(rs.getBigDecimal("gunluk_fiyat"));
                ayarlar.add(ayar);
            }
        } catch (SQLException e) {
            System.err.println("Tüm fiyat ayarlarını getirme hatası: " + e.getMessage());
            e.printStackTrace();
        }
        return ayarlar;
    }

    // Yeni fiyat ayarı ekle veya mevcut olanı güncelle (UPSERT mantığı)
    public boolean fiyatAyarKaydetVeyaGuncelle(FiyatAyar fiyatAyar) {
        String checkSql = "SELECT fiyat_ayar_id FROM fiyat_ayarlari WHERE oda_tipi = ?";
        String updateSql = "UPDATE fiyat_ayarlari SET gunluk_fiyat = ? WHERE oda_tipi = ?";
        String insertSql = "INSERT INTO fiyat_ayarlari (oda_tipi, gunluk_fiyat) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, fiyatAyar.getOdaTipi());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    pstmt.setBigDecimal(1, fiyatAyar.getGunlukFiyat());
                    pstmt.setString(2, fiyatAyar.getOdaTipi());
                    return pstmt.executeUpdate() > 0;
                }
            } else {
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, fiyatAyar.getOdaTipi());
                    pstmt.setBigDecimal(2, fiyatAyar.getGunlukFiyat());
                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                fiyatAyar.setFiyatAyarId(generatedKeys.getInt(1));
                            }
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Fiyat ayarı kaydetme/güncelleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Fiyat ayarını sil (Admin paneli için)
    public boolean fiyatAyarSil(String odaTipi) {
        String sql = "DELETE FROM fiyat_ayarlari WHERE oda_tipi = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, odaTipi);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(odaTipi + " için fiyat ayarı silme hatası: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
} 