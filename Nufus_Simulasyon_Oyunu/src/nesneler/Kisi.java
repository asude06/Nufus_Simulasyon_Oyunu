/** 
* 
* @author Asude Elif Karaağaç 
* @since 27.03.2026 
* <p> 
*  Kisi nesnelerini oluşturacak Kisi sınıfı, tutulması istenen bilgilerin tanımlanması ve dışardan erişim sağlayan getterlar
* </p> 
*/ 

package nesneler;

public class Kisi {
	private static int idSayac = 1; // statik olması id nin doğrudan sınıfa ait olmasını sağladı ki nesnelere bağlı olmasın
	private int id;
	private String isim; // kişilerin isim ve soy isimlerini ayrı ayrı tuttum faker kütüphanesinden daha çeşitli isimler elde etmek için  
	private String soyisim;
	private int yas;
	
	public Kisi(String isim, String soyisim, int yas) {
		this.isim = isim;
		this.soyisim = soyisim;
		this.yas = yas;
		this.id = idSayac++;
	}
	
	public void yasArttir() {
		this.yas++;
	}
	
	// private olarak tanımladıklarımı dışardan yani diğer pakaetlerdeki fonksiyonlardan erişimi bir get tır ile sağladım
	public int getId() {return id;} 
	public String getIsim() {return isim;}
	public String getSoyisim() {return soyisim;}
	public int getYas() {return yas;}
	
}
