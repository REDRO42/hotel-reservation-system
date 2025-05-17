package com.otelrezervasyon.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.otelrezervasyon.dao.FiyatDAO;
import com.otelrezervasyon.dao.OdaDAO;
import com.otelrezervasyon.dao.RezervasyonDAO;
import com.otelrezervasyon.model.FiyatAyar;
import com.otelrezervasyon.model.Oda;

public class AdminPaneli extends JFrame {

    private JSpinner spnTarihSecici;
    private JButton btnYenile;
    private JTable tblOdaDurumlari;
    private DefaultTableModel tableModelOdaDurum;

    // Fiyat Yönetimi için UI elemanları
    private JTable tblFiyatAyarlari;
    private DefaultTableModel tableModelFiyatAyar;
    private JComboBox<String> cmbFiyatOdaTipi;
    private JTextField txtGunlukFiyat;
    private JButton btnFiyatKaydet;
    private JButton btnFiyatSil;

    private OdaDAO odaDAO;
    private RezervasyonDAO rezervasyonDAO;
    private FiyatDAO fiyatDAO;

    public AdminPaneli() {
        odaDAO = new OdaDAO();
        rezervasyonDAO = new RezervasyonDAO();
        fiyatDAO = new FiyatDAO();

        setTitle("Admin Paneli");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Oda Durumları", createOdaDurumPaneli());
        tabbedPane.addTab("Fiyat Ayarları", createFiyatAyarPaneli());

        add(tabbedPane);

        // İlk sekmedeki verileri yükle
        odaDurumlariniYukle();
        fiyatAyarlariniYukle();
    }

    private JPanel createOdaDurumPaneli() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Tarih Seçin:"));
        Date bugun = new Date();
        SpinnerDateModel dateModel = new SpinnerDateModel(bugun, null, null, Calendar.DAY_OF_MONTH);
        spnTarihSecici = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnTarihSecici, "dd/MM/yyyy");
        spnTarihSecici.setEditor(dateEditor);
        topPanel.add(spnTarihSecici);
        btnYenile = new JButton("Durumları Yenile");
        btnYenile.addActionListener(e -> odaDurumlariniYukle());
        topPanel.add(btnYenile);

        String[] columnNamesOda = {"Oda Numarası", "Oda Tipi", "Kapasite", "Durum"};
        tableModelOdaDurum = new DefaultTableModel(columnNamesOda, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tablo hücreleri düzenlenemez
            }
        };
        tblOdaDurumlari = new JTable(tableModelOdaDurum);
        JScrollPane scrollPaneOda = new JScrollPane(tblOdaDurumlari);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPaneOda, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFiyatAyarPaneli() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Fiyatları listeleyen tablo
        String[] columnNamesFiyat = {"Oda Tipi", "Günlük Fiyat (TL)"};
        tableModelFiyatAyar = new DefaultTableModel(columnNamesFiyat, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblFiyatAyarlari = new JTable(tableModelFiyatAyar);
        tblFiyatAyarlari.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblFiyatAyarlari.getSelectedRow() != -1) {
                int selectedRow = tblFiyatAyarlari.getSelectedRow();
                cmbFiyatOdaTipi.setSelectedItem(tableModelFiyatAyar.getValueAt(selectedRow, 0).toString());
                txtGunlukFiyat.setText(tableModelFiyatAyar.getValueAt(selectedRow, 1).toString());
            }
        });
        panel.add(new JScrollPane(tblFiyatAyarlari), BorderLayout.CENTER);

        // Giriş Formu Paneli
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Oda Tipi:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        String[] odaTipleri = {"Standart", "Deluxe", "Suite", "Aile"};
        cmbFiyatOdaTipi = new JComboBox<>(odaTipleri);
        formPanel.add(cmbFiyatOdaTipi, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Günlük Fiyat (TL):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        txtGunlukFiyat = new JTextField(10);
        formPanel.add(txtGunlukFiyat, gbc);

        btnFiyatKaydet = new JButton("Kaydet/Güncelle");
        btnFiyatKaydet.addActionListener(e -> fiyatAyarKaydetAction());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(btnFiyatKaydet, gbc);
        
        btnFiyatSil = new JButton("Seçili Fiyatı Sil");
        btnFiyatSil.addActionListener(e -> fiyatAyarSilAction());
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(btnFiyatSil, gbc);

        panel.add(formPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void fiyatAyarlariniYukle() {
        tableModelFiyatAyar.setRowCount(0); // Tabloyu temizle
        List<FiyatAyar> ayarlar = fiyatDAO.getAllFiyatAyarlari();
        for (FiyatAyar ayar : ayarlar) {
            tableModelFiyatAyar.addRow(new Object[]{ayar.getOdaTipi(), ayar.getGunlukFiyat()});
        }
    }

    private void fiyatAyarKaydetAction() {
        String odaTipi = (String) cmbFiyatOdaTipi.getSelectedItem();
        String fiyatStr = txtGunlukFiyat.getText();
        if (odaTipi == null || fiyatStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Oda tipi ve fiyat boş bırakılamaz!", "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            BigDecimal gunlukFiyat = new BigDecimal(fiyatStr.replace(',', '.'));
            if (gunlukFiyat.compareTo(BigDecimal.ZERO) < 0) {
                 JOptionPane.showMessageDialog(this, "Fiyat negatif olamaz!", "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            FiyatAyar ayar = new FiyatAyar(odaTipi, gunlukFiyat);
            if (fiyatDAO.fiyatAyarKaydetVeyaGuncelle(ayar)) {
                JOptionPane.showMessageDialog(this, "Fiyat ayarı başarıyla kaydedildi/güncellendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                fiyatAyarlariniYukle();
                txtGunlukFiyat.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Fiyat ayarı kaydedilirken/güncellenirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir fiyat girin (örn: 150.75)", "Geçersiz Fiyat Formatı", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void fiyatAyarSilAction() {
        int selectedRow = tblFiyatAyarlari.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için tablodan bir fiyat ayarı seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String odaTipi = tableModelFiyatAyar.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, 
            odaTipi + " için fiyat ayarını silmek istediğinize emin misiniz?",
            "Silme Onayı", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (fiyatDAO.fiyatAyarSil(odaTipi)) {
                JOptionPane.showMessageDialog(this, odaTipi + " için fiyat ayarı başarıyla silindi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                fiyatAyarlariniYukle();
                txtGunlukFiyat.setText("");
            } else {
                JOptionPane.showMessageDialog(this, odaTipi + " için fiyat ayarı silinirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void odaDurumlariniYukle() {
        Date secilenTarihUtil = (Date) spnTarihSecici.getValue();
        Date bugunTemiz = getGununBasi(new Date());
        Date secilenTarihTemiz = getGununBasi(secilenTarihUtil);
        Calendar calMax = Calendar.getInstance();
        calMax.setTime(bugunTemiz);
        calMax.add(Calendar.DAY_OF_MONTH, 30);
        Date maxIleriTarih = getGununSonu(calMax.getTime());
        if (secilenTarihTemiz.before(bugunTemiz) || secilenTarihTemiz.after(maxIleriTarih)) {
             JOptionPane.showMessageDialog(this, 
                "Lütfen bugünden itibaren sonraki 30 gün içinde bir tarih seçin.", 
                "Geçersiz Tarih", JOptionPane.WARNING_MESSAGE);
        }
        tableModelOdaDurum.setRowCount(0);
        List<Oda> tumOdalar = odaDAO.tumOdalariGetir();
        Set<Integer> doluOdaNolari = rezervasyonDAO.getDoluOdaNumaralari(secilenTarihUtil);
        if (tumOdalar.isEmpty()) {
            tableModelOdaDurum.addRow(new Object[]{"Veritabanında oda bulunamadı.", "", "", ""});
            return;
        }
        for (Oda oda : tumOdalar) {
            String durum = doluOdaNolari.contains(oda.getOdaNumarasi()) ? "Dolu" : "Boş";
            tableModelOdaDurum.addRow(new Object[]{
                    oda.getOdaNumarasi(),
                    oda.getOdaTipi(),
                    oda.getKapasite(),
                    durum
            });
        }
    }
    
    private Date getGununBasi(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getGununSonu(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    // Bu main metodu test amaçlıdır, normalde RezervasyonFormu'ndan açılır.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Önce veritabanında odaların olduğundan emin olun.
            // Oda verilerini popüle etmek için bir script/kod çalıştırılmalı.
            AdminPaneli adminPaneli = new AdminPaneli();
            adminPaneli.setVisible(true);
        });
    }
} 