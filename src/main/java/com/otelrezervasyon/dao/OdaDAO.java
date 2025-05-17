package com.otelrezervasyon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.otelrezervasyon.model.Oda;
import com.otelrezervasyon.util.DatabaseConnection;

public class OdaDAO {

    // Tüm odaları listele
    public List<Oda> tumOdalariGetir() {
        List<Oda> odalar = new ArrayList<>();
        String sql = "SELECT oda_id, oda_numarasi, oda_tipi, kapasite FROM odalar ORDER BY oda_numarasi ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Oda oda = new Oda();
                oda.setOdaId(rs.getInt("oda_id"));
                oda.setOdaNumarasi(rs.getInt("oda_numarasi"));
                oda.setOdaTipi(rs.getString("oda_tipi"));
                oda.setKapasite(rs.getInt("kapasite"));
                odalar.add(oda);
            }
        } catch (SQLException e) {
            System.err.println("Tüm odaları getirme hatası: " + e.getMessage());
            e.printStackTrace();
        }
        return odalar;
    }

    // Belirli bir oda numarasina göre oda getir (İleride gerekebilir)
    public Oda odaGetir(int odaNumarasi) {
        String sql = "SELECT oda_id, oda_numarasi, oda_tipi, kapasite FROM odalar WHERE oda_numarasi = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, odaNumarasi);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Oda oda = new Oda();
                oda.setOdaId(rs.getInt("oda_id"));
                oda.setOdaNumarasi(rs.getInt("oda_numarasi"));
                oda.setOdaTipi(rs.getString("oda_tipi"));
                oda.setKapasite(rs.getInt("kapasite"));
                return oda;
            }
        } catch (SQLException e) {
            System.err.println("Oda getirme hatası (No: " + odaNumarasi + "): " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // Oda ekleme (Veri popülasyonu için kullanılabilir veya admin paneline eklenebilir)
    public boolean odaEkle(Oda oda) {
        String sql = "INSERT INTO odalar (oda_numarasi, oda_tipi, kapasite) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, oda.getOdaNumarasi());
            pstmt.setString(2, oda.getOdaTipi());
            pstmt.setInt(3, oda.getKapasite());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        oda.setOdaId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Oda ekleme hatası: " + e.getMessage());
            if (e.getSQLState().equals("23000")) {
                System.err.println("Oda numarası " + oda.getOdaNumarasi() + " zaten mevcut olabilir.");
            }
            e.printStackTrace();
        }
        return false;
    }
} 