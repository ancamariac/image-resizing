package helper;

import java.io.FileInputStream;
import java.io.IOException;

import myapp.BMP;
import myapp.ImageProcessing;

public class MatrixOpHelper {

public static int[][] convertBytesToPixelMatrix(BMP in, int[][] bytesMatrix, int scaleFactor) {
		
		int[] pixelsArray = new int[in.getImageWidth() * in.getImageHeight()];
		int contor = 0;
		int index = 0;
		
		// Valoarea unui pixel in RGB
		int RGBvalue = 0; 
		
		// NULL checks
		if (in == null || bytesMatrix == null || scaleFactor == 0) {
			return null;
		}
							
		// Salvarea pixelilor intr-un vector pentru a fi mai apoi transpusi in matrice
		
		for (int i = 0; i < in.getImageHeight(); i++) {
			contor = 0;
			for (int j = 0; j <= in.getRowLength(); j++) {
				if (j % 3 == 0 && j != 0) {
					// Formula pentru convertirea a cate 3 valori din matrice intr-o singura valoare
					RGBvalue = (bytesMatrix[i][j - 3] * 65536) + (bytesMatrix[i][j - 2] * 256) + bytesMatrix[i][j - 1];
					pixelsArray[index] = RGBvalue;
					contor = contor + 1;
					index = index + 1;
				}
			}
		}

		// Proprietatile matricei procesate
		ImageProcessing.processedImageWidth = scaleFactor * contor; 
		ImageProcessing.processedImageHeight = scaleFactor * in.getImageHeight(); 
		int[] newPixelsVector = new int[ImageProcessing.processedImageWidth * ImageProcessing.processedImageHeight]; // vector in care se salveaza pixelii multiplicati

		// Copierea pixelilor
		
		int targetIdx = 0;
		for (int x = 0; x < ImageProcessing.processedImageHeight; x++) {
			int iUnscaled = x / scaleFactor;
			for (int j = 0; j < ImageProcessing.processedImageWidth; j++) {
				int jUnscaled = j / scaleFactor;
				newPixelsVector[targetIdx++] = pixelsArray[iUnscaled * in.getImageWidth() + jUnscaled];
			}
		}
		// Transpunerea vectorului de pixeli intr-o matrice
		int ind = 0;
		int[][] newPixelsMatrix = new int[ImageProcessing.processedImageHeight][ImageProcessing.processedImageWidth]; // declarare matrice
		for (int line = 0; line < ImageProcessing.processedImageHeight; line++){
			for (int column = 0; column < ImageProcessing.processedImageWidth; column++) {
				newPixelsMatrix[line][column] = newPixelsVector[ind];
				ind = ind + 1;
			}
		}
		
		return newPixelsMatrix;
	} 
	
	public static int[][] convertBytesToPixelMatrix(BMP in) {
		
		if (in == null) {
			return null;
		}
		int fileByte = 0;
		int j = 0;
		int i = 0;
		
		// Matricea de bytes ce urmeaza sa fie convertita
		int[][] matrixOfBytes = new int[in.getImageHeight()][in.getRowLength() + in.getImagePadding()/in.getImageHeight()];

		// Citirea matricei 
		try {
			FileInputStream fin2 = new FileInputStream(in.getInputFileName());
			fin2.skip(in.getInputFileHeader().length); 
		
			while ((fileByte = fin2.read()) != -1) {
				if (j < in.getRowLength() + in.getImagePadding()/in.getImageHeight()) { 
					matrixOfBytes[i][j] = fileByte;
					j = j + 1;
				} else {
					i = i + 1;
					j = 0;
					matrixOfBytes[i][j] = fileByte;
					j = j + 1;
				}
			}
			
			fin2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return matrixOfBytes;
	}
}
