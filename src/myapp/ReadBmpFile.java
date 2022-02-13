package myapp;

public class ReadBmpFile {
	private BmpFile inputBmpFile; 
	private boolean available = false;
	public ReadBmpFile(BmpFile in){
		inputBmpFile = in;
	}
		
	public synchronized BmpFile getInputBmpFile() {
		while (!available){
			try{
				wait();// asteapta producatorul 
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		available = false;
		notifyAll();
		return inputBmpFile;
	}

	public synchronized void setInputBmpFile(BmpFile inputBmpFile) {
		while (available){
			try{
				wait();//asteapta consumatorul 
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		this.inputBmpFile = inputBmpFile;
		available = true;
		notifyAll();
	}
}
