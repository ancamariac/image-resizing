package helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import myapp.BMP;
import myapp.ImageProcessing;

public class BasicHelper {
	
	// === PRINT METHODS ===
	public static void printDebugMessages(String... strings){
		for (int j = 0; j< strings.length; j++){
			System.out.println(strings[j]);
		}
	}
	
	public static void printMatrix(int height, int width, int matr[][]) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++){
				System.out.print(matr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void printInputFileProps(int width, int height) {
		System.out.println();
		System.out.print(ANSIColors.ANSI_BLUE + 
				"\n\n=== Input file properties === \n"
				+ ANSIColors.ANSI_RESET);
		System.out.print("-> width x height = " + width + " x " + height + " px\n");
		System.out.println();
	}
	
	// SET PROPERTITES FOR I/O FILES
	public static void setPropsForInputBMP(BMP bmp, int [] fileHeader) {
		// Setare proprietati de dimensiune a fisierului de intrare bmp
		bmp.setImageWidth(fileHeader[ImageProcessing.BMPwidth]); 
		bmp.setImageHeight(fileHeader[ImageProcessing.BMPheight]); 
		bmp.setImageSize(fileHeader[ImageProcessing.BMPsize]); 
	}
	
	public static void setPropsForOutputBMP(BMP outBMP, int finalWidth, int finalHeight, int finalPadding) {
		// Setare proprietati de dimensiune a fisierului de iesire bmp
		outBMP.setImageWidth(finalWidth);
		outBMP.setImageHeight(finalHeight);
		outBMP.setImagePadding(finalPadding); 
	}	
	
	// INT to BYTE conversion
	public static int[] intToBytes(int i) {
		int[] result = new int[4];
		result[1] = (i >> 16) & 0xFF;
		result[2] = (i >> 8) & 0xFF;
		result[3] = i & 0xFF;

		return result;
	}
	
	// Calcularea padding-ului cu un numar de bytes
	public static int computePaddingUsingBytes(int nrBytes) {
		int padding = 0;

		if (nrBytes % 4 == 1)
			padding = 3;
		else if (nrBytes % 4 == 2)
			padding = 2;
		else if (nrBytes % 4 == 3)
			padding = 1;
		return padding;
	}	
}
