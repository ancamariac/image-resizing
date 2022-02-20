package myapp;

public class Consumer extends ThreadsAbstractClass {
	public Consumer (String name, ThreadsBufferImage threadsBuff, int inputScale, String outFileName){
		super(name, threadsBuff, inputScale, outFileName, false);
	}	
}
