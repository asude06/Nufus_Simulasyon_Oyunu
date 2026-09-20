/** 
* 
* @author Asude Elif Karaağaç 
* @since 27.03.2026 
* <p> 
*  Program çalıştıracak olan main sınıfı, başlatmayı sağlayan ve diğer ekran işlemleri ile kullanıcı etkileşimi sağlar
* </p> 
*/ 

package main;

import oyun.Oyun;
import nesneler.Sehir;
import nesneler.Ilce;
import nesneler.Mahalle;
import nesneler.Kisi;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in); // tüm scanner işlemleri için scanner değişkenini main sınıfına statik olarak tek bir giriş akışı oluşturdum

    public static void main(String[] args) {
    	main.Console.clear(); // ilk olarak cmd de açıldığında ekranı temizliyorum
        Oyun oyun = new Oyun(); // programın çalışmasıyla yeni bir oyun oluşturuluyor 

        // ilk girdileri alıyoruz kullanıcıdan
        System.out.print("Tur sayisini girin: ");
        int turSayisi = scanner.nextInt();
        scanner.nextLine(); //buffer temizleme

        System.out.println("Oyun formatına uyacak sekilde bosluklu giris yapiniz (ör:18 25 79): ");
        String girdi = "";
        boolean formatDogru = false; // format dışı 2 basamaktan fazlası veya negatif değer girilirse eğer diye bu kontrolü sağlamak gerekiyor

        while (!formatDogru) {
            girdi = scanner.nextLine(); // şehirlerin nufusları giriliyor
            String[] parcalar = girdi.split("\\s+"); // Boşluklara göre ayırma işlemi
            boolean hataVar = false;

            for (String parca : parcalar) {
                // Sayı mı ve 2 basamaklı mı (10-99 arası mı) kontrol eder
                if (parca.length() != 2 || !parca.matches("\\d+")) {
                    hataVar = true;
                    break;
                }
            }

            // hata olması durumunda direkt bir hata mesajı ile program sonlandırılıyor
            if (hataVar) {
                System.out.println("HATA: Lütfen sadece 2 basamaklı sayılar giriniz (ör: 10 25 99):");
            } else {
                formatDogru = true;
            }
        }

        //Girilen bilgiler ile Oyun sınıfına kurulumu yaptırıyoruz, şehirler burdan girilip boşluğa göre parçalanıyor
        oyun.simülasyonuHazirla(girdi);

        //Turlar başlamadan önceki durumu yazdırıyoruz, oyunun kendi kurallarına uygun başlayacak şekline 
        System.out.println("\nTurlara hazırlık sonu durumu:");
        sehirleriListele(oyun.getSehirler());
        System.out.println();

        // turlar gerçekleştirilir ve son durumları yazılır
        for(int i = 1; i <= turSayisi; i++) {
            oyun.turuGerceklestir(); // Sadece hesaplamalar yapılır, kuralları artış oranı ile nufusu arttırır ve 4 basamağı geçerse böler
            
            System.out.println("------" + i + ".TUR------");
            sehirleriListele(oyun.getSehirler()); // Main kendi metodunu çağırır
            
            try { Thread.sleep(1000); } catch (InterruptedException e) {} // turların ekranda gözükmesi için biraz bekletme işlemi
        }
        
        // işlemlere şahit olduktan sonra diğer aşama için turların durumlarını ekrandan temizliyoruz
        main.Console.clear();
        
        // Turlar bittiği an en son oluşan nüfus tablosunu yazdırıyoruz
        System.out.println("\n------ OYUN SONU DURUM ------");
        sehirleriListele(oyun.getSehirler());
        System.out.println("-----------------------------");

        // Ekrana turlar bitiikten sonraki son halini yazdırdıktan sonra diğer aşama olan şehir seçip nufusunu yazdırma işlemine geçiyoruz
        secimEkrani(oyun.getSehirler());
        // burası ayrı bir fonksiyon olarak işlemleri hallediyor ve main in en sonuna geçiyoruz
        
        System.out.println("Programi kapatmak icin herhangi bir tusa basin...");
        
        try {
            // Standart giriş akışını temizlemek için
            System.in.skip(System.in.available()); 
            // Kullanıcıdan bir giriş bekler (Enter dahil herhangi bir tuş vuruşu)
            System.in.read(); 
        } catch (Exception e) {
            // Hata olsa bile programın kapanmasına izin verir
        }

        System.out.println("Program kapatiliyor...");
        System.exit(0);
    }

    // listeleme Static olmalı ki main içinden çağrılabilsin
    public static void sehirleriListele(List<Sehir> sehirler) {
        for(int i = 0; i < sehirler.size(); i++) {
            Sehir sehir = sehirler.get(i);
            System.out.print("[" + sehir.getToplamNufus() + "]"); // listede bulunan sehirlerin nufuslarını köşeli prantezler içinde istenilen formatta yazdırmayı sağlıyor
            
            if((i + 1) % 5 != 0 && i != sehirler.size() - 1){ // 5 erli gruplar haalinde yazılacak ve 1 tane fazladan - yi çıkaracak
                System.out.print("-");
            }
            if((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    // seçim ekranı fonksiyonu
    public static void secimEkrani(List<Sehir> sehirler) {
        System.out.println("\n         ŞEHİR DETAYLI SORGULAMA EKRANI         ");
        System.out.println("================================================");

        System.out.print("SATIR numarasını gir(0'dan baslayarak): ");
        int satir = scanner.nextInt();

        System.out.print("SÜTUN numarasını gir(0'dan baslayarak): ");
        int sutun = scanner.nextInt();

        int hedefIndeks = (satir * 5) + sutun; // yukarda aldığımız satır ve sütun bilgisini matrissel olarak index ile bulmamızı kolaylaştıracak

        if (hedefIndeks >= 0 && hedefIndeks < sehirler.size()) {
            Sehir secilenSehir = sehirler.get(hedefIndeks);
            System.out.println("Şehir: " + secilenSehir.getS_Ad() + " - Nüfus: " + secilenSehir.getToplamNufus()); // seçilen şehrin adını ve nüfusunu

            for (Ilce ilce : secilenSehir.getIlceler()) { // içerisindeki tüm ilçelerini döngü ile
                int ilceNufus = 0;
                for (Mahalle m : ilce.getMahalleler()) ilceNufus += m.getYasayanKisiler().size();
                
                System.out.println("  İlçe: " + ilce.getI_Ad() + " - Nüfus: " + ilceNufus); //ilçelerin adlarını ve nüfuslarını
                
                for (Mahalle mahalle : ilce.getMahalleler()) { // ilçeye ait tüm mahallleri teker teker içerisinde yaşayan kişileri ekrana id isim soyisim yas olarak yazdırır
                    System.out.println("    Mahalle: " + mahalle.getM_Ad() + " - Nüfus: " + mahalle.getYasayanKisiler().size());
                    System.out.println("    Kişiler:");
                    for (Kisi kisi : mahalle.getYasayanKisiler()) {
                        System.out.println("      " + kisi.getId() + " - " + kisi.getIsim() + " " + kisi.getSoyisim() + " - " + kisi.getYas());
                    }
                }
            }
        } else {
            System.out.println("\n[!] HATA: " + hedefIndeks + " nolu indekste bir şehir bulunamadı."); // matrissel formumuzun dışında kalan bir indeks girilir ise de hata mesajı vermesini sağladım
        }
        
    }
}