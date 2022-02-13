package myapp;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import util.Utils;


public class ReadAndProcessBmpFile implements InputFileInterface {
	
	private static int newWidth;
	private static int newHeight;
	private static int[][] newMat;
	
	/**
	 * citeste fisierul de intrare si seteaza (calculand) toate atributele obiectului bmpFile de intrare
	 */
	@Override
	public BmpFile processInputFile(String fileName){
		BmpFile bmpFile = new BmpFile();
		bmpFile.setFileName(fileName);
		if (fileName != null && fileName.trim().length()>0){
			try {
				FileInputStream fin = new FileInputStream(fileName);
				int fisierCharacter = 0;
				int k = 0;
				int[] header = new int[54]; // vector in care se stocheaza headerul fisierului
				// citire header din fisier
				System.out.println();
				System.out.println("Headerul fisierului de intrare:");
				
				while ((fisierCharacter = fin.read()) != -1 && k < 54) {
					header[k] = fisierCharacter;
					// afisare header fisier de intrare
					if (k < 2)
						System.out.print((char) fisierCharacter + " ");
					else
						System.out.print(fisierCharacter + " ");
					k = k + 1;
				}
				
				bmpFile.setHeader(header);
				bmpFile.setWidth(header[18]); // latimea imaginii in pixeli
				bmpFile.setHeight(header[22]); // inaltimea imaginii in pixeli
				bmpFile.setSizeOfImage(header[34]); //dimensiunea imaginii in bytes, fara header

				System.out.println();
				System.out.print("Dimensiunile imaginii de intrare: ");
				System.out.print("latime: " + bmpFile.getWidth() + " pixeli, ");
				System.out.print("inaltime: " + bmpFile.getHeight() + " pixeli ");
				System.out.println();

				fin.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
			// calculare dimensiune matrice de bytes care reprezinta pixelii
			bmpFile.setRowSize(3 * bmpFile.getWidth()); // numarul de bytes de pe o linie a matricei, fara padding
			bmpFile.setNrBytes(bmpFile.getRowSize() * bmpFile.getHeight()); // numarul de bytes din fisier fara padding
			
			// calculare si setare padding total pentru fisierul de intrare
			bmpFile.setPadding(bmpFile.getSizeOfImage() - bmpFile.getNrBytes());
			
			// numarul total de bytes din fisier, fara header
			bmpFile.setNrTotalBytes(bmpFile.getRowSize() * bmpFile.getHeight() 
					+ bmpFile.getPadding());
			System.out.println();
		}
		return bmpFile;
	}
	
	/**
	 * transformare fisier bmp din intrare in matrice de bytes (doar cei care reprezinta pixelii si paddingul)
	 */
	public static int[][] transformBmpFileToBytesMatrix(BmpFile in){
		if (in == null){
			return null;
		}
		int fileCharacter = 0;
		int indexCol = 0;
		int indexLine = 0;
		// declarare matrice de bytes care reprezinta pixelii 
		int[][] bytesMat = new int[in.getHeight()][in.getRowSize() + in.getPadding()/in.getHeight()];

		// citire din fisier
		try {
			FileInputStream fin2 = new FileInputStream(in.getFileName());
			fin2.skip(in.getHeader().length); // incepe citirea din fisier dupa ce se termina headerul
		
			while ((fileCharacter = fin2.read()) != -1) {
				if (indexCol < in.getRowSize() + in.getPadding()/in.getHeight()) { 
					bytesMat[indexLine][indexCol] = fileCharacter;
					indexCol = indexCol + 1;
				} else {
					indexLine = indexLine + 1;
					indexCol = 0;
					bytesMat[indexLine][indexCol] = fileCharacter;
					indexCol = indexCol + 1;
				}
			}
			
			fin2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println();
		// afisare matrice de bytes
		System.out.println("Matricea corespunzatoare imaginii de intrare: ");
		for (int a = 0; a < in.getHeight(); a++) {
			for (int b = 0; b < in.getRowSize() + in.getPadding()/in.getHeight(); b++){
				System.out.print(bytesMat[a][b] + " ");
			}
			System.out.println();
		}
		return bytesMat;
	}
	
	/**
	 * transforma matricea de bytes in matrice de pixels
	 */
	public static int[][] transformBytesMatrixToPixelsMatrix(BmpFile in, int[][] bytesMatrix, int scale){
		if (in == null || bytesMatrix == null){
			return null;
		}
		// transformare "bytes" in "pixeli"
		int RGB = 0; // se stocheaza valoarea unui pixel
		int t = 0;
		int[] pixelsVector = new int[in.getWidth() * in.getHeight()]; // vector de pixeli
		// mai intai se salveaza pixelii intr-un vector care va fi utilizat la procesarea imaginii,
		// iar apoi se va trece totul intr-o matrice
		int index = 0;
		for (int z = 0; z < in.getHeight(); z++) {
			t = 0;
			for (int v = 0; v <= in.getRowSize(); v++) {
				if (v % 3 == 0 && v != 0) {
					// se transforma cate 3 valori din matrice intr-o singura valoare cu urmatoarea formula:
					RGB = (bytesMatrix[z][v - 3] * 65536) + (bytesMatrix[z][v - 2] * 256) + bytesMatrix[z][v - 1];
					pixelsVector[index] = RGB;
					t = t + 1;
					index = index + 1;
				}
			}
		}

		System.out.println();
		
		// calculare dimensiuni matrice noua de pixeli
		newWidth = scale * t; // latimea matricei de pixeli
		newHeight = scale * in.getHeight(); // inaltimea matricei de pixeli
		int[] newPixelsVector = new int[newWidth * newHeight]; // vector in care se salveaza pixelii multiplicati

		// algoritmul de copiere a pixelilor
		// se copiaza fiecare pixel de un numar de ori egal cu scale
		int targetIdx = 0;
		for (int x = 0; x < newHeight; x++) {
			int iUnscaled = x / scale;
			for (int j = 0; j < newWidth; j++) {
				int jUnscaled = j / scale;
				newPixelsVector[targetIdx++] = pixelsVector[iUnscaled * in.getWidth() + jUnscaled];
			}
		}
		// scrierea vectorului nou de pixeli sub forma de matrice
		int ind = 0;
		int[][] newPixelsMatrix = new int[newHeight][newWidth]; // declarare matrice
		for (int line = 0; line < newHeight; line++){
			for (int column = 0; column < newWidth; column++) {
				newPixelsMatrix[line][column] = newPixelsVector[ind];
				ind = ind + 1;
			}
		}
		// apelare functie cu numar variabil de parametri
		Utils.printOutMessages("Matricea de pixeli multiplicati:");
		System.out.println();
		for (int a = 0; a < newHeight; a++) {
			for (int b = 0; b < newWidth; b++){
				System.out.print(newPixelsMatrix[a][b] + " "); // afisare matrice
			}
			System.out.println();
		}
		
		return newPixelsMatrix;
	} 
	
	/**
	 * initializarea fisierului de iesire  
	 */
	public static BmpFile initOutputBmpFile(BmpFile in, int[][] newPixelsMat, String outFileName){
		BmpFile out = new BmpFile();
		out.setFileName(outFileName);
		
		// calculare dimensiuni matrice noua de bytes
		int finalHeight = newHeight; // inaltimea nu se modifica
		int finalWidth = newWidth * 3; // latimea se modifica deoarece un pixel are 3 bytes

		// verificare padding nou
		int newPadding = Utils.obtainPaddingByNrBytes(finalWidth);
		
		newMat = new int[finalHeight][finalWidth+newPadding]; // declarare matrice finala ce contine imaginea procesata
		int col = 0;
		int[] rez = new int[3];

		
		// transformare pixeli in bytes si adaugare padding
		for (int p = 0; p < newHeight; p++) {
			col = 0;
			for (int q = 0; q < newWidth; q++) {
				rez = Utils.toBytes(newPixelsMat[p][q]);
				newMat[p][col] = rez[1];
				newMat[p][col + 1] = rez[2];
				newMat[p][col + 2] = rez[3];
				col = col + 3;
				if (col == finalWidth){
					if (newPadding == 1){
						newMat[p][col] = 0;
					} else if (newPadding == 2) {
						newMat[p][col] = 0;
						newMat[p][col + 1] = 0;
					} else if (newPadding == 3) {
						newMat[p][col] = 0;
						newMat[p][col + 1] = 0;
						newMat[p][col + 2] = 0;
					} else if(newPadding==4){
						 newMat[p][col]=0;
						 newMat[p][col+1]=0;
						 newMat[p][col+2]=0;
						 newMat[p][col+3]=0;
					}
				}
			}
		}

		// afisare matrice finala
		System.out.println();
		System.out.print("Matricea finala, cu imaginea procesata:");
		System.out.println();
		for (int a = 0; a < finalHeight; a++) {
			for (int b = 0; b < finalWidth+newPadding; b++){
				System.out.print(newMat[a][b] + " ");
			}
			System.out.println();
		}
		// setarea valorilor finale pentru fisierul de iesire		
		out.setWidth(finalWidth);
		out.setHeight(finalHeight);
		out.setPadding(newPadding);
		return out;
	}
	
	/**
	 * obtine fisierul de iesire maximizat
	 */
	public static BmpFile getOutputMaximizedBmpFile(BmpFile in, BmpFile out){
		if (in == null || out == null){
			return null;
		}

		// header nou
		int[] newHeader = new int[54];
		int newSizeBMP = 0;
		// calcularea dimensiunilor imaginii dupa procesare
		newSizeBMP = 54 + (out.getHeight() * (out.getWidth() + out.getPadding())); // dimensiunea totala a imaginii (header+pixeli)
		// scrierea noului header
		for (int hIndex = 0; hIndex < 54; hIndex++) {
			if (hIndex == 2){
				newHeader[hIndex] = newSizeBMP;
			}else if (hIndex == 18){
				newHeader[hIndex] = newWidth;
			}else if (hIndex == 22){
				newHeader[hIndex] = out.getHeight();
			}else if (hIndex == 34){
				newHeader[hIndex] = out.getHeight() * (out.getWidth() + out.getPadding());
			}
		}
		// pe pozitiile care nu reprezinta dimensiunile imaginii, se copiaza valorile din headerul fisierului initial
		for (int hIndex1 = 0; hIndex1 < 54; hIndex1++) {
			if (hIndex1 >= 0 && hIndex1 < 2){
				newHeader[hIndex1] = in.getHeader()[hIndex1];
			}
			if (hIndex1 > 5 && hIndex1 != 18 && hIndex1 != 22 && hIndex1 < 34){
				newHeader[hIndex1] = in.getHeader()[hIndex1];
			}
			if (hIndex1 > 37){
				newHeader[hIndex1] = in.getHeader()[hIndex1];
			}
		}
		out.setHeader(newHeader); // setare header fisier de iesire
		
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(out.getFileName());
			// scriere header nou in fisierul de iesire
			for (int w = 0; w < 54; w++){
				try {
					fout.write(out.getHeader()[w]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// scriere matrice finala de bytes in fisierul de iesire
			for (int y = 0; y < out.getHeight(); y++){
				for (int l = 0; l < out.getWidth() + out.getPadding(); l++) {
					try {
						fout.write(newMat[y][l]);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			try{
				fout.close();
			}catch(Exception e){
				System.out.println("Eroare incercare inchidere fisier de iesire" + e.getMessage());
			}
		}

		try {
			FileInputStream fin5 = new FileInputStream(out.getFileName());
			int fisier21 = 0;
			int k = 0;
			int width2 = 0;
			int height2 = 0;
			int[] header2 = new int[54];
			// citire header fisier final (dupa procesare)
			System.out.println();
			System.out.println("Headerul fisierului de iesire:");
			while ((fisier21 = fin5.read()) != -1 && k < 54) {
				header2[k] = fisier21;
				if (k < 2){
					System.out.print((char) fisier21 + " ");
				}else{
					System.out.print(fisier21 + " ");
				}
				k = k + 1;
			}
			System.out.println();
			System.out.print("Dimensiunile imaginii procesate: ");

			width2 = header2[18];
			height2 = header2[22];

			// apelare functie cu numar variabil de parametri
			Utils.printOutMessages("latime: " + width2 + " pixeli");
			Utils.printOutMessages("inaltime: " + height2 + " pixeli ");

			fin5.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return out;
	}
}
