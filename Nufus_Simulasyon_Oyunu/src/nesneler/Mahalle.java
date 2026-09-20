/** 
* 
* @author Asude Elif Karaağaç 
* @since 27.03.2026 
* <p> 
*  Mahalle nesnelerini oluşturacak Mahalle sınıfı, kişi nesnelerini bir arada tutar ve yönetir ilçeye veri sunar
* </p> 
*/ 

package nesneler;

import java.util.ArrayList;
import java.util.List;

public class Mahalle {
	private String ad;
	private List<Kisi> yasayanKisiler; // mahallr bazlı yaşayan kişileri bir listede tutmak için yasayanKisiler listesi hazırladım, iki nesne arası sahiplik ilişkisi
	
	public Mahalle(String ad) {
		this.ad = ad;
		this.yasayanKisiler = new ArrayList<>(); // bu liste sürekli artarak değisiklik göstereceği için sabit boyut yerine bir ArrayList ile genişleyebilir liste yapısı kurmak daha uygun
	}
	
	//kontrollü erişim sağlayan kapsülleme ile private olarak tanımladığım için yapılacak işlemleri dolaylı olarak yapmayı sağlayacak olan fonksiyonlar, 
	public void kisiEkle(Kisi kisi) {
		this.yasayanKisiler.add(kisi);
	}
	
	//listenin boyutunu döndürüyor ki nüfusa dolaylı erişim sağlaalım
	public int getM_Nufus() {
		return yasayanKisiler.size();
	}
	
	//yine aynı şekilde private erişim sağalatacak olan getterler
	public String getM_Ad() {return ad;}
	public List<Kisi> getYasayanKisiler(){return yasayanKisiler;}
}
