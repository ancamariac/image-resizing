package myapp;

import util.Utils;

public abstract class AbstractClass extends Thread {
	private boolean role;
	private ReadBmpFile input;
	private String inputFileName;
	private int scale;
	private String bmpOutputFileName;

	public AbstractClass(String name, boolean role, ReadBmpFile readAndProc, String fileName){
		super(name);
		this.role = role;
		this.input = readAndProc;
		this.inputFileName = fileName;
		//System.out.println("Constructor " + getName()); // afiseaza cand se apeleaza constructorul specific producatorului
	}
	public AbstractClass (String name, boolean role, ReadBmpFile readAndProc, int scale, String outFileName){
		super(name);
		this.role = role;
		this.input = readAndProc;
		this.scale = scale;
		this.bmpOutputFileName = outFileName;
		//System.out.println("Constructor " + getName()); // afiseaza cand se apeleaza constructorul specific consumatorului
	}
	
	public void start(){
		System.out.println("\n\nStarting " + getName());
		super.start();
	}

	public void run(){
		System.out.println("\n\nRunning " + getName());
		if (role){ // producator
			ReadAndProcessBmpFile initProcess = new ReadAndProcessBmpFile(); 
			// se citeste headerul si se seteaza dimensiunile necesare pentru procesare
			BmpFile readInputFile = initProcess.processInputFile(inputFileName);
			// se seteaza datele obtinute pentru a fi transmise consumatorului
			input.setInputBmpFile(readInputFile);
			System.out.println("Producatorul va astepta");
			try{
				sleep (1000);
			}catch(InterruptedException e){
				System.out.println("Eroare producer ");
			}
		}else{ //cosnumator
			BmpFile readInputFile = input.getInputBmpFile();
			// matrice in care se stocheaza bytes din fisier
			int[][] bytesMatrix = ReadAndProcessBmpFile.transformBmpFileToBytesMatrix(readInputFile);
			// transformare "bytes" in "pixeli"
			int[][] newPixelsMatrix = ReadAndProcessBmpFile.transformBytesMatrixToPixelsMatrix(readInputFile, bytesMatrix, scale);
			// transformare pixeli in bytes
			BmpFile initOutputFile = ReadAndProcessBmpFile.initOutputBmpFile(readInputFile, newPixelsMatrix, bmpOutputFileName);
			// se obtine fisierul de iesire 
			ReadAndProcessBmpFile.getOutputMaximizedBmpFile(readInputFile, initOutputFile);
		}
		System.out.println("Going dead" + getName());
		Utils.printOutMessagesWithTimestamp(" End prelucrare imagine");
		
	}
}
