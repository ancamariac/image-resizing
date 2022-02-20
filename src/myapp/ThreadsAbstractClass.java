package myapp;

import helper.BasicHelper;
import helper.MatrixOpHelper;

public abstract class ThreadsAbstractClass extends Thread {
	
	private ThreadsBufferImage inputBMPFile;
	private String inputBMPFileName;
	private String outputBMPFileName;
	
	private boolean type;
	private int scaleFactor;
	public long endTime = 0;
		
	public ThreadsAbstractClass(String name, ThreadsBufferImage processBMPFile, String inputFileName, boolean type){
		super(name);
		this.type = type;
		this.inputBMPFile = processBMPFile;
		this.inputBMPFileName = inputFileName;
	}
	public ThreadsAbstractClass (String name, ThreadsBufferImage processBMPFile, int scaleFactor, String outFileName, boolean type){
		super(name);
		this.type = type;
		this.inputBMPFile = processBMPFile;
		this.scaleFactor = scaleFactor;
		this.outputBMPFileName = outFileName;
	}
	
	public void start(){
		System.out.println("Starting " + getName());
		super.start();
	}

	public void run(){
		System.out.println("The " + getName() + " is running ...a");
		/* Se verifica tipul si
		 * daca acesta este 1, inseamna ca ne vom duce pe threadul
		 * de producator, daca este 0, ne vom duce pe threadul
		 * de consumator */
		if (type) { 
			// === Producer ===
			/* Se initializeaza procesul de citire si procesare a fisierului bmp
			 * dupa care urmeaza citirea header-ului imagine si setarea
			 * proprietatilor fisierului de input,
			 * si datele obtinute pentru a transmise consumatorului */
			
			ImageProcessing startProcess = new ImageProcessing(); 		
			BMP readInputFile = startProcess.imageProcessOnInputFile(inputBMPFileName);
			inputBMPFile.setInputBmpFile(readInputFile);
			System.out.println("Producer is waiting ...");
			try{
				sleep (1000);
			}catch(InterruptedException e){
				System.out.println("Eroare producer ");
			}
		} else { 
			// === Consumer ===
			/* Se va forma matricea in care se stocheaza bytes din fisier
			 * dupa care acestia vor fi convertiti in pixeli
			 * se va face conversia inversa a acestora inapoi in bytes,
			 * obtinandu-se in final fisierul de iesire*/
			
			BMP inFile = inputBMPFile.getInputBmpFile();
			int[][] matrixOfBytes = MatrixOpHelper.convertBytesToPixelMatrix(inFile);
			int[][] resultPixelMatrix = MatrixOpHelper.convertBytesToPixelMatrix(inFile, matrixOfBytes, scaleFactor);
			
			// Initierea prelucrarii imaginii
			BMP initOutputFile = ImageProcessing.processOutputFile(inFile, resultPixelMatrix, outputBMPFileName);
			ImageProcessing.getFinalHeader(inFile, initOutputFile);
		}
	}
}
