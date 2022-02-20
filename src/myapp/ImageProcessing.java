package myapp;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import helper.ANSIColors;
import helper.BasicHelper;

import helper.BasicHelper;

import helper.BasicHelper;

public class ImageProcessing implements InputInt {
	
	/*  === Procesarea fisierului BMP ===  */
	
	public static final int headerLen = 54;	// dimensiunea header-ului
	public static final int BMPwidth = 18;	// latimea fisierului BMP
	public static final int BMPheight = 22;	// inaltimea fisierului BMP
	public static final int BMPsize = 34;	// dimensiunea in bytes a fisierului BMP
	
	private static final int pixelNrOfBytes = 3;
	
	public static int[][] processedMatrix; 	// matricea de pixeli a imaginii procesate
	public static int processedImageWidth;	// latimea imaginii procesate
	public static int processedImageHeight;	// inaltimea imaginii procesate
			
	// Calcularea header-ului pentru noul fisier rezultat
	
	public static BMP getFinalHeader(BMP in, BMP out){
		
		// Header rezultat
		int[] finalHeader = new int[headerLen];
		int sizeOfBMP = 0;

		if (in == null || out == null){
			return null;
		}
		
		// Formula pentru calcularea dimensiunilor imaginii post-procesare, ce contine header si pixeli
		sizeOfBMP = headerLen + (out.getImageHeight() * 
				(out.getImageWidth() + out.getImagePadding())); 
		
		// Calcularea efectiva a header-ului
		for (int i = 0; i < headerLen; i++) {
			
			if (i == 2){
				
			}else if (i == BMPwidth){
				finalHeader[i] = processedImageWidth;
			}else if (i == BMPheight){
				finalHeader[i] = out.getImageHeight();
			}else if (i == BMPsize){
				finalHeader[i] = out.getImageHeight() * (out.getImageWidth() + out.getImagePadding());
			}
		}
		
		// pe pozitiile care nu reprezinta dimensiunile imaginii, se copiaza valorile din headerul fisierului initial
		for (int i = 0; i < headerLen; i++) {
			if (i >= 0 && i < 2){
				finalHeader[i] = in.getInputFileHeader()[i];
			}
			if (i > 5 && i != BMPwidth && i != BMPheight && i < BMPsize){
				finalHeader[i] = in.getInputFileHeader()[i];
			}
			if (i > BMPsize + pixelNrOfBytes){
				finalHeader[i] = in.getInputFileHeader()[i];
			}
		}
		
		// Calcularea header-ului fisierului de iesire
		out.setInputFileHeader(finalHeader); 
		
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(out.getInputFileName());
			
			// adaugarea noului header in fisierul de output
			for (int i = 0; i < headerLen; i++){
				try {
					fout.write(out.getInputFileHeader()[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			// scriere matrice finala de bytes in fisierul de iesire
			for (int i = 0; i < out.getImageHeight(); i++){
				for (int l = 0; l < out.getImageWidth() + out.getImagePadding(); l++) {
					try {
						fout.write(processedMatrix[i][l]);
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
				System.out.println("Err on closing output file" + e.getMessage());
			}
		}

		try {
			FileInputStream anotherFin = new FileInputStream(out.getInputFileName());
						
			int outputWidth = 0;
			int outputHeight = 0;
			int[] outputHeader = new int[headerLen];
			
			int file = 0;
			int j = 0;
			
			// Header post procesare		
			while ((file = anotherFin.read()) != -1 && j < headerLen) {
				outputHeader[j] = file;
				j = j + 1;
			}

			System.out.print(ANSIColors.ANSI_BLUE + 
					"\n=== Output file properties ===\n"
					+ ANSIColors.ANSI_RESET);

			outputWidth = outputHeader[BMPwidth];
			outputHeight = outputHeader[BMPheight];

			// Proprietatile finale ale imaginii post procesate
			System.out.print("-> width x height = " + outputWidth + " x " + outputHeight + " px\n");
			System.out.println();

			anotherFin.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return out;
	}
	
	public static BMP processOutputFile(BMP in, int[][] newPixelsMat, String outFileName){
		
		long finishProcess, startProcess;
		startProcess = System.currentTimeMillis();
		
		int col = 0;
		int[] result = new int[pixelNrOfBytes];
		
		BMP out = new BMP();
		out.setInputFileName(outFileName);
		
		// Se calculeaza proprietatile pentru matricea de bytes
		// Inaltimea ramane neschimbata
		int finalHeight = processedImageHeight; 
		// Latimea se modifica deoarece un pixel are 3 bytes
		int finalWidth = processedImageWidth * pixelNrOfBytes; 

		// Setare padding
		int finalPadding = BasicHelper.computePaddingUsingBytes(finalWidth);
		
		// Matricea procesata
		processedMatrix = new int[finalHeight][finalWidth + finalPadding]; 
		
		// Transformarea pixelilor in bytes
		for (int k = 0; k < processedImageHeight; k++) {
			col = 0;
			for (int q = 0; q < processedImageWidth; q++) {
				result = BasicHelper.intToBytes(newPixelsMat[k][q]);
				processedMatrix[k][col] = result[1];
				processedMatrix[k][col + pixelNrOfBytes-2] = result[pixelNrOfBytes-1];
				processedMatrix[k][col + pixelNrOfBytes-1] = result[pixelNrOfBytes];
				col = col + 3;
				if (col == finalWidth){
					if (finalPadding == 1){
						processedMatrix[k][col] = 0;
					} else if (finalPadding == pixelNrOfBytes-1) {
						processedMatrix[k][col] = 0;
						processedMatrix[k][col + pixelNrOfBytes-2] = 0;
					} else if (finalPadding == 3) {
						processedMatrix[k][col] = 0;
						processedMatrix[k][col + pixelNrOfBytes-2] = 0;
						processedMatrix[k][col + pixelNrOfBytes-1] = 0;
					} else if(finalPadding==4){
						 processedMatrix[k][col]=0;
						 processedMatrix[k][col + pixelNrOfBytes-2]=0;
						 processedMatrix[k][col + pixelNrOfBytes-1]=0;
						 processedMatrix[k][col + pixelNrOfBytes]=0;
					}
				}
			}
		}

		// Afisarea formei finale a matricei procesate

		System.out.print(ANSIColors.ANSI_BLUE + 
				"\n===  Processed Matrix  ===\n"
				+ ANSIColors.ANSI_RESET);		
		//BasicHelper.printMatrix(finalHeight, finalWidth+finalPadding, processedMatrix);
		for (int i = 0; i < finalHeight; i++) {
			for (int j = 0; j < finalWidth+finalPadding; j++){
				if (j <= 10) {
					System.out.print(ANSIColors.ANSI_RED_BG + processedMatrix[i][j] + " " 
									+ ANSIColors.ANSI_RESET);
				} else if (j >= 11 && j <= 20) {
					System.out.print(ANSIColors.ANSI_GREEN_BG + processedMatrix[i][j] + " " 
							+ ANSIColors.ANSI_RESET);
				} else if (j >= 25 && j <= 36) {
					System.out.print(ANSIColors.ANSI_PURPLE_BG + processedMatrix[i][j] + " " 
							+ ANSIColors.ANSI_RESET);
				}
				
			}
			System.out.println();
		}
			
		// Setarea valorilor finale pentru fisierul de iesire	
		BasicHelper.setPropsForOutputBMP(out, finalWidth, finalHeight, finalPadding);
		
		finishProcess = System.currentTimeMillis();
		long elap2 = finishProcess-startProcess;
		System.out.println(ANSIColors.ANSI_GREEN + 
				"\nElapsed time for processing the image: " + elap2
				+ ANSIColors.ANSI_RESET);
		return out;
	}
	
	@Override
	public BMP imageProcessOnInputFile(String inputFile){
		
		// Se declara un obiect de tip BMP pentru a fi prelucrat
		BMP bmp = new BMP();
		// Se seteaza numele fisierului BMP
		bmp.setInputFileName(inputFile);
		
		// Se verifica daca exista un fisier de intrare valid
		if (inputFile != null){
			try {
				int fileByte = 0;
				int readContor = 0;
				
				// Se creeaza un stream pentru a citi datele din fisierul de input
				FileInputStream inputFileStream = new FileInputStream(inputFile);
				
				// Se retine header-ul fisierului
				int[] fileHeader = new int[headerLen]; 
				
				while ((fileByte = inputFileStream.read()) != -1 && readContor < headerLen) {
					fileHeader[readContor] = fileByte;				
					readContor = readContor + 1;
				}
				
				bmp.setInputFileHeader(fileHeader);
				BasicHelper.setPropsForInputBMP(bmp, fileHeader);

				BasicHelper.printInputFileProps(bmp.getImageWidth(), bmp.getImageHeight());

				inputFileStream.close();
				
			} catch (Exception e) {
				System.out.println(e);
			}
			
			// Calcularea numarului de bytes de pe o linie a matricei
			bmp.setRowLength((headerLen/BMPwidth) * bmp.getImageWidth()); 
			
			// Calcularea numarului de bytes din fisier fara padding
			bmp.setNoBytes(bmp.getRowLength() * bmp.getImageHeight()); 
			
			// Calcularea padding-ului total pentru fisierul de intrare
			bmp.setImagePadding(bmp.getImageSize() - bmp.getImageNoBytes());
			
			// Calcularea numarului total de bytes
			bmp.setImageTotalBytes(bmp.getRowLength() * bmp.getImageHeight() + bmp.getImagePadding());
		}
		
		return bmp;
	}
}
