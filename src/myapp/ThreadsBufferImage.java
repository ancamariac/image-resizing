package myapp;

public class ThreadsBufferImage {
	private BMP inputBmpFile; 
	private boolean available = false;
	
	public ThreadsBufferImage(BMP in){
		inputBmpFile = in;
	}
		
	public synchronized BMP getInputBmpFile() {
		while (!available){
			try{
				// waits for the producer
				wait();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		available = false;
		notifyAll();
		return inputBmpFile;
	}

	public synchronized void setInputBmpFile(BMP inputBmpFile) {
		while (available){
			try{
				// waits for the consumer
				wait();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		this.inputBmpFile = inputBmpFile;
		available = true;
		notifyAll();
	}
}
