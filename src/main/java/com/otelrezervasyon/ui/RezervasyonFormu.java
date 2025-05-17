package com.otelrezervasyon.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;

import com.otelrezervasyon.dao.FiyatDAO;
import com.otelrezervasyon.dao.RezervasyonDAO;
import com.otelrezervasyon.model.Rezervasyon;

public class RezervasyonFormu extends JFrame {
    private JTextField txtMusteriAdi, txtEmail, txtTelefon, txtOdaNumarasi;
    private JComboBox<String> cmbOdaTipi;
    private JSpinner spnGirisTarihi, spnCikisTarihi;
    private JButton btnKaydet, btnTemizle;
    private JLabel lblToplamFiyat;
    private RezervasyonDAO rezervasyonDAO;
    private FiyatDAO fiyatDAO;

    public RezervasyonFormu() {
        rezervasyonDAO = new RezervasyonDAO();
        fiyatDAO = new FiyatDAO();
        initComponents();
        guncelleTahminiFiyat();
    }

    private void initComponents() {
        setTitle("Otel Rezervasyon Formu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Müşteri Adı
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Müşteri Adı:"), gbc);
        gbc.gridx = 1;
        txtMusteriAdi = new JTextField(20);
        panel.add(txtMusteriAdi, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);

        // Telefon
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Telefon:"), gbc);
        gbc.gridx = 1;
        txtTelefon = new JTextField(20);
        panel.add(txtTelefon, gbc);

        // Giriş Tarihi
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Giriş Tarihi:"), gbc);
        gbc.gridx = 1;
        spnGirisTarihi = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnGirisTarihi, "dd/MM/yyyy");
        spnGirisTarihi.setEditor(dateEditor);
        panel.add(spnGirisTarihi, gbc);

        // Çıkış Tarihi
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Çıkış Tarihi:"), gbc);
        gbc.gridx = 1;
        spnCikisTarihi = new JSpinner(new SpinnerDateModel());
        dateEditor = new JSpinner.DateEditor(spnCikisTarihi, "dd/MM/yyyy");
        spnCikisTarihi.setEditor(dateEditor);
        panel.add(spnCikisTarihi, gbc);

        // Oda Numarası
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Oda Numarası:"), gbc);
        gbc.gridx = 1;
        txtOdaNumarasi = new JTextField(20);
        panel.add(txtOdaNumarasi, gbc);

        // Oda Tipi
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Oda Tipi:"), gbc);
        gbc.gridx = 1;
        String[] odaTipleri = {"Standart", "Deluxe", "Suite", "Aile"};
        cmbOdaTipi = new JComboBox<>(odaTipleri);
        cmbOdaTipi.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                guncelleTahminiFiyat();
            }
        });
        panel.add(cmbOdaTipi, gbc);

        // Tahmini Toplam Fiyat Etiketi
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Tahmini Toplam Fiyat:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        lblToplamFiyat = new JLabel("0.00 TL");
        lblToplamFiyat.setFont(new Font(lblToplamFiyat.getFont().getName(), Font.BOLD, lblToplamFiyat.getFont().getSize()));
        panel.add(lblToplamFiyat, gbc);

        // Butonlar
        JPanel buttonPanel = new JPanel();
        btnKaydet = new JButton("Kaydet");
        btnTemizle = new JButton("Temizle");

        btnKaydet.addActionListener(e -> rezervasyonKaydet());
        btnTemizle.addActionListener(e -> formuTemizle());

        buttonPanel.add(btnKaydet);
        buttonPanel.add(btnTemizle);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);
        
        // Tarih JSpinner'larına listener ekle
        ChangeListener tarihListener = e -> guncelleTahminiFiyat();
        spnGirisTarihi.addChangeListener(tarihListener);
        spnCikisTarihi.addChangeListener(tarihListener);
    }

    private void guncelleTahminiFiyat() {
        try {
            Date giris = (Date) spnGirisTarihi.getValue();
            Date cikis = (Date) spnCikisTarihi.getValue();
            String odaTipi = (String) cmbOdaTipi.getSelectedItem();

            if (giris == null || cikis == null || odaTipi == null || cikis.before(giris) || cikis.equals(giris)) {
                lblToplamFiyat.setText("0.00 TL");
                return;
            }

            long farkMillis = cikis.getTime() - giris.getTime();
            long gunSayisi = TimeUnit.DAYS.convert(farkMillis, TimeUnit.MILLISECONDS);

            if (gunSayisi <= 0) {
                lblToplamFiyat.setText("0.00 TL");
                return;
            }

            BigDecimal gunlukFiyat = fiyatDAO.getGunlukFiyat(odaTipi);
            if (gunlukFiyat.compareTo(BigDecimal.ZERO) <= 0) {
                 // Fiyat bulunamadı veya 0 ise, belki bir uyarı veya varsayılan gösterilebilir
                 // Şimdilik FiyatDAO uyarı veriyor, biz de 0.00 gösterelim.
                 lblToplamFiyat.setText("Fiyat Belirsiz"); 
                 return;
            }
            
            BigDecimal toplamFiyat = gunlukFiyat.multiply(new BigDecimal(gunSayisi));
            lblToplamFiyat.setText(String.format("%.2f TL", toplamFiyat));

        } catch (Exception e) {
            lblToplamFiyat.setText("Hata!");
            System.err.println("Fiyat hesaplama hatası: " + e.getMessage());
        }
    }

    private void rezervasyonKaydet() {
        try {
            Date girisTarihiForm = (Date) spnGirisTarihi.getValue();
            Date cikisTarihiForm = (Date) spnCikisTarihi.getValue();

            // Tarih kontrolleri
            Date bugun = new Date();
            Calendar calBugun = Calendar.getInstance();
            calBugun.setTime(bugun);
            calBugun.set(Calendar.HOUR_OF_DAY, 0);
            calBugun.set(Calendar.MINUTE, 0);
            calBugun.set(Calendar.SECOND, 0);
            calBugun.set(Calendar.MILLISECOND, 0);
            bugun = calBugun.getTime();

            Calendar calGiris = Calendar.getInstance();
            calGiris.setTime(girisTarihiForm);
            calGiris.set(Calendar.HOUR_OF_DAY, 0);
            calGiris.set(Calendar.MINUTE, 0);
            calGiris.set(Calendar.SECOND, 0);
            calGiris.set(Calendar.MILLISECOND, 0);
            Date girisTarihiTemiz = calGiris.getTime();
            
            if (girisTarihiTemiz.before(bugun)) {
                JOptionPane.showMessageDialog(this, "Giriş tarihi bugünden veya geçmiş bir tarihten olamaz!",
                                            "Tarih Hatası", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cikisTarihiForm.before(girisTarihiForm) || cikisTarihiForm.equals(girisTarihiForm)) {
                JOptionPane.showMessageDialog(this, "Çıkış tarihi, giriş tarihinden sonra olmalıdır!",
                                            "Tarih Hatası", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Calendar calMaxRezervasyon = Calendar.getInstance();
            calMaxRezervasyon.setTime(bugun);
            calMaxRezervasyon.add(Calendar.DAY_OF_MONTH, 30);
            Date maxRezervasyonTarihi = calMaxRezervasyon.getTime();

            if (girisTarihiTemiz.after(maxRezervasyonTarihi)) {
                JOptionPane.showMessageDialog(this, "Sadece sonraki 30 gün için rezervasyon yapabilirsiniz!",
                                            "Tarih Hatası", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Rezervasyon rezervasyon = new Rezervasyon();
            rezervasyon.setMusteriAdi(txtMusteriAdi.getText());
            rezervasyon.setMusteriEmail(txtEmail.getText());
            rezervasyon.setMusteriTelefon(txtTelefon.getText());
            rezervasyon.setGirisTarihi(girisTarihiForm);
            rezervasyon.setCikisTarihi(cikisTarihiForm);
            rezervasyon.setOdaNumarasi(Integer.parseInt(txtOdaNumarasi.getText()));
            String secilenOdaTipi = (String) cmbOdaTipi.getSelectedItem();
            rezervasyon.setOdaTipi(secilenOdaTipi);
            rezervasyon.setDurum("BEKLEMEDE");
            
            // Dinamik fiyatı al ve ayarla
            long gunFarki = TimeUnit.DAYS.convert(cikisTarihiForm.getTime() - girisTarihiForm.getTime(), TimeUnit.MILLISECONDS);
            if (gunFarki <= 0) {
                 JOptionPane.showMessageDialog(this, "Çıkış tarihi giriş tarihinden sonra olmalıdır.", "Hata", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            BigDecimal gunlukFiyat = fiyatDAO.getGunlukFiyat(secilenOdaTipi);
            if (gunlukFiyat.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, secilenOdaTipi + " için geçerli bir fiyat bulunamadı. Lütfen admin panelinden ayarlayın.", 
                                            "Fiyat Hatası", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BigDecimal toplamHesaplananFiyat = gunlukFiyat.multiply(new BigDecimal(gunFarki));
            rezervasyon.setToplamFiyat(toplamHesaplananFiyat.doubleValue());

            if (rezervasyonDAO.rezervasyonEkle(rezervasyon)) {
                JOptionPane.showMessageDialog(this, "Rezervasyon başarıyla kaydedildi!");
                formuTemizle();
            } else {
                // rezervasyonEkle false döndü, sebebini kontrol et
                if (rezervasyonDAO.isOdaDolu(rezervasyon.getOdaNumarasi(), rezervasyon.getGirisTarihi())) {
                    JOptionPane.showMessageDialog(this, "Malesef odamız dolu!", 
                                                "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Oda dolu değilse, başka bir veritabanı veya kayıt hatasıdır.
                    JOptionPane.showMessageDialog(this, "Rezervasyon kaydedilirken bir hata oluştu!", 
                                                "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
             JOptionPane.showMessageDialog(this, "Oda numarası geçerli bir sayı olmalıdır!",
                                         "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doğru şekilde doldurun!\n" + ex.getMessage(), 
                                        "Hata", JOptionPane.ERROR_MESSAGE);
             ex.printStackTrace();
        }
    }

    private void formuTemizle() {
        txtMusteriAdi.setText("");
        txtEmail.setText("");
        txtTelefon.setText("");
        txtOdaNumarasi.setText("");
        spnGirisTarihi.setValue(new Date());
        spnCikisTarihi.setValue(new Date());
        cmbOdaTipi.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RezervasyonFormu().setVisible(true);
        });
    }
} 