/** 
* 
* @author Asude Elif Karaağaç 
* @since 13.04.2026 
* <p> 
*  Ekranı temizleme işlemi için oluşturulan sınıf, hocamızın bizimle paylaştığı videodan
* </p> 
*/ 


package main;

import java.io.IOException;

public class Console {
	@SuppressWarnings("deprecation") // exec de uyarı verdi o uyarıdan çözümü olarak bunu ekletti
	public static void clear() {
		try {
			if(System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
			else {
				Runtime.getRuntime().exec("clear");
			}
		}
		catch(IOException | InterruptedException ex) {
			
		}
	}
}
