package myapp;


import util.Utils;
import java.util.Scanner;
public class MainClass {

	public static void main(String[] args) {
		System.out.println("	=== Let's start the image processing! ===\n"); 
		String bmpInputFileName = null;
		int scale = 0;
		String bmpOutputFileName = null;
		
		bmpInputFileName = args[0];
		scale = new Integer(args[1]);

		Scanner myObj = new Scanner(System.in);
		System.out.println("- Please, type the output file name:");
	    String fileName = myObj.nextLine();  // Citire valoare introdusa de la tastatura
	    bmpOutputFileName = fileName;
	    

		BmpFile inputFile = new BmpFile();
		inputFile.setFileName(bmpInputFileName);
		
		ReadBmpFile b = new ReadBmpFile(inputFile);
		Producator p1 = new Producator("Producator",b,bmpInputFileName);
		Consumator c1 = new Consumator("Consumator",b,scale, bmpOutputFileName);
		p1.start();
		c1.start();
	}
}
