/** 
* 
* @author Asude Elif Karaağaç 
* @since 27.03.2026 
* <p> 
*  Sehir nesnelerini oluşturacak Sehir sınıfı, ilçeleri yönetiyor toplam nüfusu alt birimlerden elde ediyori ve şehrin en kritik özelliği olan bölünme için kullanılacak olan verileri yönetiyor
* </p> 
*/ 

package nesneler;

import java.util.ArrayList;
import java.util.List;

public class Sehir {
	private String ad;
	private List<Ilce> ilceler;
	private int mevcutSayi;
	
	public Sehir(String ad) {
		this.ad = ad;
		this.ilceler = new ArrayList<>();
		this.mevcutSayi = 0;
	}
	
	public void ilceEkle(Ilce ilce) {
		this.ilceler.add(ilce);
	}
	
	//burda da toplam nufusu zincirleme olarak ilçelerden elde ediyoruz
	public int getToplamNufus() {
		int toplam = 0;
		for(Ilce i: ilceler) {
			toplam += i.getINufus();
		}
		return toplam;
	}
	
	public int getMevcutSayi() {
		return mevcutSayi;
	}
	
	public void setMevcutSayi(int mevcutSayi) { // set fonksiyonu gelen değeri burdaki yeni değer olarak tekrar atamayı sağlıyor
		this.mevcutSayi = mevcutSayi;
	}
	
	// private olanlara erişim sağlatan getterler ve bazı bölünme sonrası için setterlar
	public String getS_Ad() {return ad;}
	public void setAd(String ad) {this.ad = ad;} //bolunmeden sonrasi icin
	public List<Ilce> getIlceler() {return ilceler;}
	public void setIlceler(List<Ilce> ilceler) {this.ilceler = ilceler;} //yine bölünme sonrası yeni olanları mevcur olarak ayarlamak
}