package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	/**
	 * transforma un intreg in bytes (in 3 valori care reprezinta culorile pixelilor)
	 */
	public static int[] toBytes(int i) {
		int[] result = new int[4];
		result[1] = (i >> 16) & 0xFF;
		result[2] = (i >> 8) & 0xFF;
		result[3] = i & 0xFF;

		return result;
	}
	/**
	 * 	calcularea paddingului in functie de numarul final de bytes 
	 */
	public static int obtainPaddingByNrBytes(int nrBytes){
		int padding = 0;
		// calculare padding
		if (nrBytes % 4 == 1)
			padding = 3;
		else if (nrBytes % 4 == 2)
			padding = 2;
		else if (nrBytes % 4 == 3)
			padding = 1;
		return padding;
	}
	
	/**
	 * afiseaza un numar variabil de mesaeje  
	 */
	public static void printOutMessages(String... strings){
		for (int i=0; i< strings.length; i++){
			System.out.println(strings[i]);
		}
	}
	
	/**
	 * afiseaza mesaje si timpul curent 
	 */
	public static void printOutMessagesWithTimestamp(String... strings){
		for (int i=0; i< strings.length; i++){
			Date dataCurenta = new Date();
			//dd/MM/yyyy HH:mm:ss.SS
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SS");
			String timpulRezultat = sdf.format(dataCurenta);
			System.out.println(timpulRezultat +":"+ strings[i]);
		}
	}
}
