package myapp;

public class BMP {
	
	/* === Caracteristicile fisierului de input === */
	
	private int[] inputFileHeader;
	private String inputFileName;
	
	/* === Caracteristicile imaginii === */
	
	private int imageHeight; 	// inaltimea imagine
	private int imageWidth; 	// latime imagine
	private int imageNoBytes; 	// numarul de bytes al imaginii fara padding si header
	private int imageSize; 		// dimensiune imagine
	
	private int imageTotalBytes; 	// numarul total de bytes
	private int imagePadding; 		// padding-ul imaginii
	private int rowLength; 			// numarul de bytes pentru o singura linie
	
	/* Input File Header getter and setter */ 
	public int[] getInputFileHeader() {
		return inputFileHeader;
	}
	public void setInputFileHeader(int[] header) {
		this.inputFileHeader = header;
	}
	
	/* Input File getter and setter */ 
	public String getInputFileName() {
		return inputFileName;
	}
	public void setInputFileName(String fileName) {
		this.inputFileName = fileName;
	}
	
	/* Image Height getter and setter */
	public int getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(int height) {
		this.imageHeight = height;
	}
	
	/* Image Width getter and setter */ 
	public int getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(int width) {
		this.imageWidth = width;
	}
	
	/* Number Of Bytes getter and setter */
	public int getImageNoBytes() {
		return imageNoBytes;
	}
	public void setNoBytes(int noBytes) {
		this.imageNoBytes = noBytes;
	}
	
	/* Image size getter and setter */
	public int getImageSize() {
		return imageSize;
	}
	public void setImageSize(int sizeOfImage) {
		this.imageSize = sizeOfImage;
	}
	
	/* Image total number of bytes getter and setter */
	public int getImageTotalBytes() {
		return imageTotalBytes;
	}
	public void setImageTotalBytes(int nrTotalBytes) {
		this.imageTotalBytes = nrTotalBytes;
	}
	
	/* Padding getter and setter */
	public int getImagePadding() {
		return imagePadding;
	}
	public void setImagePadding(int padding) {
		this.imagePadding = padding;
	}
	
	/* Row Length getter and setter */
	public int getRowLength() {
		return rowLength;
	}
	public void setRowLength(int rowLen) {
		this.rowLength = rowLen;
	}		
}
