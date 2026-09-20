/** 
* 
* @author Asude Elif Karaağaç 
* @since 27.03.2026 
* <p> 
*  İlçe nesnelerini oluşturacak Ilce sınıfı, mahalleleri yönetir ve nüfusu dinamik olarak mahallelerden toplayarak elde eder
* </p> 
*/ 

package nesneler;

import java.util.ArrayList;
import java.util.List;

public class Ilce {
	private String ad;
	private List<Mahalle> mahalleler; // ilçe nesnesinin yapısı da mahalleninkine benzer şekilde bir dinamik liste yapısıyla mahalleleri yönetmeyi sağlıyoe
	
	public Ilce(String ad) {
		this.ad = ad;
		this.mahalleler = new ArrayList<>(); // burda da sabit boyutlu bir dizi yerine genişleyebilen ArrayList kullandım
	}
	
	public void mahalleEkle(Mahalle mahalle) {
		this.mahalleler.add(mahalle);
	}
	
	//şehir nufusu sorduğunda zincirleme şekilde nufs hesabı yapılmış olacak
	public int getINufus() {
		int toplam = 0;
		
		for(Mahalle m: mahalleler) { // mahallelerdeki nesneleri tek tek m olarak
			toplam += m.getM_Nufus(); // ilçelerin nüfusu tur başı değişeceği iin dinamik olarak mahallelere bakarak hesaplayacak
		}
		return toplam;
	}
	
	// private a kontrollü erişim sağalayan getter ile gerekli bilgileri dışardan ulaşılabilir kıldım
	public String getI_Ad() {return ad;}
	public List<Mahalle> getMahalleler(){return mahalleler;}
}
