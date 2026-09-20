/** 
* 
* @author Asude Elif Karaağaç 
* @since 27.03.2026 
* <p> 
*  Şehirleri oluşturan oyunun tüm kurallarını içeren Oyun sınıfı, tur bazlı artış oranı ve 4 basamaklı olduğunda bölünme işlemleri
* </p> 
*/ 

package oyun;
import com.github.javafaker.Faker;

import main.Console; //console da temizleme işlemi var farklı bir pakette olduğu için onu import ettim
import nesneler.*; // nesneler paketindeki her şeyi dahil eder çünkü oyun başlaması ile oluşacak olan şehir ilçe mahalle kişilerin hepsi farklı bir pakatte

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Oyun {
	private List<Sehir> sehirler; // şehirlerin tutulduğu listeyi tanımladık
	private int turSayisi;
	private Faker faker; // şehir ilçe mahalle ve kişilerimize gerçekçi veriler ile oluşturmamızı sağlayan kütüphanemiz
	private Scanner scanner;
	
	
	public Oyun() { // kurucu fonksiyonu
		this.sehirler = new ArrayList<>();
		this.faker = new Faker(Locale.forLanguageTag("tr")); // türkçe şehir, isim ve soy isimlerde sorunsuz çalışıyor ama ilçe ve mahalle isimleri konusunda eksik  
		this.scanner = new Scanner(System.in);		
	}
	
	//main den çağrılan fonksiyon, mainde kullanıcı tarafından girilen şehir nufulsarını boşluklara göre parçalıyor
	public void simülasyonuHazirla(String girdi) {
	    String[] sayilar = girdi.split(" "); 
	    for(int i = 0; i < sayilar.length; i++) { 
	    	int deger = Integer.parseInt(sayilar[i]);
	        sehirOlustur(deger); // ve her birini sehir oluştur fonksiyonu içerisne göndererek girilen değerin şehir olarak tanımlanması gerçekleştirilyor
	    }
	}

	// Tek bir turu döndüren işlem, yönetim kolaylığı açısından
	public void turuGerceklestir() {
	    turla();
	    sehirleriBol();
	}
		
	// şehir ilk kurulumunda nüfusun ilçelere ve mahalleler arasında eşit dağılımını sağlar
	public void sehirOlustur(int girilenSayi) {
		// sehir oluşturulurken girilen sayıların bir tanesi bir şehri temsil edecek o sayısın değeri ise nüfusunu, onlar bas ilçe sayısını, birler bas mahalle sayısını
		
		// ilk olarak şehrin temsili
		int ilceSayisi = girilenSayi / 10; // sayının 10 bölümünün bölümü onlar basamağını verir
		int mahalleSayisi = girilenSayi % 10; // sayınıın 10 a modu ise 10 a bölümünün kalanını verirr yani birler basamağını
		int sehirin_Mevcut_Nufusu = girilenSayi;
		
		// sonrasında ana 2 kuraldan ilki olan mahlle sayisi ilçelere eşit oranda bölünecek
		//burda tam bölünmediği durumun ayrıntısını belirliyoruz o zaman bölünebileceği en yakın sayıyıa arttırıyoruz
		
		if(ilceSayisi > 0) { // zaten ilçe sayısının 0 olması gibi bir durumu kabul etmediğimizden bu şartı kontrol ediyoruz
			if(mahalleSayisi == 0) {
				mahalleSayisi = ilceSayisi; // mahallenin sıfır olduğu durumlarda direkt tam bölüneceği kendisine dönüştürüyoruz, herhangi bir arttırma yapmadan
			}
			while(mahalleSayisi % ilceSayisi != 0) {
				mahalleSayisi++; // tam bölünmüyorsa birer birer artır
				// ama egerki birler basamağı 9 u geçerse bu artırma işleminde o zaman yönergede verildiği gibi 9 dan sonra tekrar 1 den başlayacak ve tam bölünene gelene kadar artacak
				if(mahalleSayisi > 9) {
					mahalleSayisi = 1;
				}
			}
		}
		
		// yeni düzeltilmiş sayı üzerinden nüfusu başlat
	    sehirin_Mevcut_Nufusu = (ilceSayisi * 10) + mahalleSayisi;
		
		// ikinci ana kural nüfus mahallelere eşit oranda bölünecek burda
		
		if(mahalleSayisi > 0) {
			while(sehirin_Mevcut_Nufusu % mahalleSayisi != 0) {
				sehirin_Mevcut_Nufusu++;
			}
		}
		
		// sonraki aşama olarak faker yaardımıyla sehir nesnelerini olusturtacağız
	
		Sehir yeniSehir = new Sehir(faker.address().city()); // nromal heapte olustururken faker dedikten sonra adres ve sehir secilecek sekilde sundu
		int mahalleBasinaNufus; // mahallenin 0 olması durumunda nüfusun da 0 olacağını belirtiyoruz aynı zamanda
		
		if(mahalleSayisi > 0) {
			mahalleBasinaNufus = sehirin_Mevcut_Nufusu / mahalleSayisi;
		}
		else {
			mahalleBasinaNufus = 0;
		}
		
		//şimdi ise faker ilçe ve mahalle nesnelerini olusturacağız
		// bi ilce olusurken eşit bölünmüş mahalle sayisi da onunla birlikte oluşacak içiçe  döngüde ve katta mahalle oller oluşurken de orda bulunan kişiler de aynı şekilde oluşacak
		for(int i=0; i<ilceSayisi; i++) {
			Ilce ilce = new Ilce(faker.address().state()); //once sehre bağlı ilçeleri 
			
			// sonrasında ilçelere bağlı mahalleri oluşturuyoruz ve tabiki her ilçede eşit sayida mahalle olacakü
			int ilceBasinaMahalle = mahalleSayisi / ilceSayisi;
			
			for(int j=0; j< ilceBasinaMahalle;j++) {
				Mahalle mahalle = new Mahalle(faker.address().streetName()); // ve yine aynı şekilde ilçelere ait mahalleleri
				
				for(int k=0; k<mahalleBasinaNufus; k++) {
					// Kisi sınıfında isim ve soyisimi ayrı ayrı tanımladığım için de burda ayrı  ayrı alıyorum
					String isim = faker.name().firstName();
					String soyisim = faker.name().lastName();
					int yas = (int) (Math.random() * 51); // kisilerin yasları 0-50 arasi rastegele olusacaktı
					mahalle.kisiEkle(new Kisi(isim, soyisim, yas));
				}
				ilce.mahalleEkle(mahalle);
			}
			yeniSehir.ilceEkle(ilce);
		}
		yeniSehir.setMevcutSayi(sehirin_Mevcut_Nufusu); // başlangıç sayısını kaydet
		this.sehirler.add(yeniSehir); // olusan şehri şehirler listesine kaydeder
	}
	
	
	public void turla() {
	    for(Sehir sehir : sehirler) { // sehirler listesindeki tüm şehirleri tek tek döner
	        int mevcutSayi = sehir.getMevcutSayi();
	        int birler = mevcutSayi % 10;
	        int onlar = (mevcutSayi / 10) % 10;
	        
	        int artisOrani = birler + onlar; // artiiş oranı her zaman birler ve onlar basamağının toplamıyla elde ediliyor nufusumuz tur işlemlerinde 2 basamağın üzerine çıksa bile bu şekilde gncellenmeye devam ediyor
	        boolean sifirKurali = (artisOrani == 0); // yönergede de ayrıyetten belirtildiği gibi eğer toplamlar sıfır olursa nufus 1 artacak
	        
	        for (Ilce ilce : sehir.getIlceler()) {
	            for (Mahalle mahalle : ilce.getMahalleler()) {
	                
	                // Yaş artırma her tur atmada tüm kişilerin yaşları 1 artmak durumunda
	                for (Kisi kisi : mahalle.getYasayanKisiler()) {
	                    kisi.yasArttir();
	                }

	                int suAnkiMahalleNufusu = mahalle.getYasayanKisiler().size();
	                int eklenecekKisiSayisi;
	                
	                if (sifirKurali) {
	                    eklenecekKisiSayisi = 1; //yukarda belirttiğim özel durum burda 1 artacak şeklinde uygulanıyor 
	                } else {
	                    eklenecekKisiSayisi = (suAnkiMahalleNufusu * artisOrani) - suAnkiMahalleNufusu; // nufusun güncel durumunu hesaplıyoruz
	                }

	                // Yeni kişileri ekleme
	                for (int i = 0; i < eklenecekKisiSayisi; i++) {
	                    // 0-50 arası rastgele yaş üretiyoruz
	                    int rastgeleYas = (int) (Math.random() * 51); 
	                    
	                    //yeni eklenen kişi rastgele bir yaşta oluşacak
	                    mahalle.kisiEkle(new Kisi(faker.name().firstName(), faker.name().lastName(), rastgeleYas));
	                }
	            }
	        }
	        sehir.setMevcutSayi(sehir.getToplamNufus()); // şehrin tur sonu güncel nufusunu kesinleştiriyoruz
	    }
	}
	
	// en önemlli bir diğer kural ise şehrin nüfusun 4 basamağı geçtiği durumda ilçe sayısına göre bölme işlemi 
	//ilçe saysı çift ise direkt yarısı içeriisndeki mahalle ve kişilerle birlikte yeni şehre taşınacak ama eğerki tekse o zaman ilçe sayısının yarısının 1 eksiği oranında taşınacak
	//yani örnek olarak 9 ilçe ise 4 ü taşınacak 5i eski şehirde kalacak 
	public void sehirleriBol() {
	    List<Sehir> yeniOlusanSehirler = new ArrayList<>(); //yeni olışan şehirleri de işlem sırasında geçici olarak kaydedip en son tur sonunda asıl şehirler listemize ekliyoruz
	    
	    int baslangicBoyutu = sehirler.size();
	    for (int i = 0; i < baslangicBoyutu; i++) {
	        Sehir mevcutSehir = sehirler.get(i);
	        
	        // Şehir sadece 1000 ve üstüyse bölünür
	        if (mevcutSehir.getToplamNufus() >= 1000) {
	            Sehir yeniSehir = new Sehir(faker.address().cityName()); // yei şehre kaer ile isim atamaları yapıyoruz
	            List<Ilce> mevcutIlceler = mevcutSehir.getIlceler(); // tüm işlemler ilçeler üzerinden gerçekleşeceği için mevcut şehirdeki ilçeleri alıyoruz

	            // 2 den fazla ise direkt yukarda yazdığım şeilde bölme işlemleri gerçekleştirilyor
	            if (mevcutIlceler.size() >= 2) { 
	                int tasinacakAdet = mevcutIlceler.size() / 2; //burdaki bölmede zaten istediğimiz koşul sağlanıyor int olark
	                for (int j = 0; j < tasinacakAdet; j++) {
	                    yeniSehir.ilceEkle(mevcutIlceler.remove(mevcutIlceler.size() - 1)); // yeni şehre taşıyoruz
	                }
	            } 
	            //ama eğer ki ilçe sayısı 1 ise bölme ilçeler üzerinden olmaz çünkü 1 ilçe bölünmez bu durumda yeni şehrin ilçesini de 1 olarak ayarlayıp mahalleleri yukardaki şekilde bölme işlemine tabi tutyoruz
	            else if (mevcutIlceler.size() == 1) {
	                Ilce anaIlce = mevcutIlceler.get(0);
	                List<Mahalle> mahalleler = anaIlce.getMahalleler();
	                Ilce yeniIlce = new Ilce(faker.address().cityName() + " İlçesi");

	                //yukardaki şekilde normal bölünme uygulanacak
	                if (mahalleler.size() >= 2) {
	                    int tasinacakMahalle = mahalleler.size() / 2;
	                    for (int j = 0; j < tasinacakMahalle; j++) {
	                        yeniIlce.mahalleEkle(mahalleler.remove(mahalleler.size() - 1));
	                    }
	                } 
	                //aynı şekilde mahallenin de 0 veya 1 olursa bölünemeyeceği için yeni şehirde 1 mahalle olacak ve bu sefer de kişiler yukardaki şekilde bölünme yaşayacak
	                else {
	                    Mahalle anaMahalle = mahalleler.get(0);
	                    Mahalle yeniMahalle = new Mahalle(faker.address().streetName() + " Mahallesi");
	                    
	                    int tasinacakKisi = anaMahalle.getYasayanKisiler().size() / 2;
	                    for (int j = 0; j < tasinacakKisi; j++) {
	                        yeniMahalle.kisiEkle(anaMahalle.getYasayanKisiler().remove(anaMahalle.getYasayanKisiler().size() - 1));
	                    }
	                    yeniIlce.mahalleEkle(yeniMahalle);
	                }
	                yeniSehir.ilceEkle(yeniIlce);
	            }

	            //yeni atamaları setter lar ile sağladık
	            mevcutSehir.setMevcutSayi(mevcutSehir.getToplamNufus());
	            yeniSehir.setMevcutSayi(yeniSehir.getToplamNufus());
	            
	            yeniOlusanSehirler.add(yeniSehir);// şehri geçici listemize kaydettik
	        }
	    }
	    sehirler.addAll(yeniOlusanSehirler); // en son tüm geçici listeyi asıl listenin sonuna ekledik bu işlem tur sonunda gerçekleşiyor
	}
		
	public List<Sehir> getSehirler() { //listenin güncel hali toparlanıyor
	    return this.sehirler;
	}
}