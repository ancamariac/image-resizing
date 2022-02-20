package myapp;

public class Producer extends ThreadsAbstractClass {
	
	
	public Producer (String name, ThreadsBufferImage threadsBuff, String fileName){
		super(name, threadsBuff, fileName, true);
	}
}
