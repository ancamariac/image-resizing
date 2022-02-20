package myapp;

import java.util.Scanner;

import helper.ANSIColors;

public class Main {
	
	public static void main(String[] args) {
		
		long finishRead, startRead;
		
		System.out.println(ANSIColors.ANSI_PURPLE 
						   + "	=== Let's start the image processing! ===\n"
						   + ANSIColors.ANSI_RESET); 
		
		String bmpInputFileName = null;
		String bmpOutputFileName = null;
		int scale = 0;
		
		startRead = System.currentTimeMillis();
	    
		Scanner readBuff = new Scanner(System.in);
		
		System.out.println("- Please, type the input file name:");
		
		// Citire valoare introdusa de la tastatura - denumirea fisierului de intrare
	    String fileNameIn = readBuff.nextLine();  
	    bmpInputFileName = fileNameIn;
		
		System.out.println("\n- Now, type the output file name:");
		
		// Citire valoare introdusa de la tastatura - denumirea fisierului de iesire
	    String fileNameOut = readBuff.nextLine();  
	    bmpOutputFileName = fileNameOut;
		
		// Verificarea argumentelor in linia de comanda
		if (args.length > 0) {
			// Citire parametru din linia de comanda - factor scalare
			scale = Integer.parseInt(args[0]);
		} else {
			System.out.println("\n- Please, enter the scale factor:");
			// Citire valoare introdusa de la tastatura - factor scalare
		    String scaleFactor = readBuff.nextLine();  
		    scale = Integer.parseInt(scaleFactor);
		}	
			
	    finishRead = System.currentTimeMillis();
	    
		BMP inputFile = new BMP();
		inputFile.setInputFileName(bmpInputFileName);
		
		ThreadsBufferImage buffer = new ThreadsBufferImage(inputFile);
		
		Producer producer = new Producer("Producer", buffer, bmpInputFileName);
		Consumer consumer = new Consumer("Consumer", buffer, scale, bmpOutputFileName);
	    		
		producer.start();
		consumer.start();
		
		long elap1 = finishRead-startRead;
		System.out.println(ANSIColors.ANSI_GREEN + 
				"\nElapsed time for reading the image: " + elap1
				+ ANSIColors.ANSI_RESET);
	}
}
