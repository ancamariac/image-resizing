package myapp;


public class BmpFile {

	private String fileName;
	private int[] header;
	
	// se calculeaza in functie de cele de mai sus
	private int width; // latimea imaginii in pixeli
	private int height; // inaltimea imaginii in pixeli
	private int rowSize; // dimensiunea unei linii de bytes 
	private int nrBytes; // dimensiunea imaginii fara header si fara padding
	private int sizeOfImage; // dimensiunea imaginii fara header
	private int padding; // sizeOfImage - nrBytes
	private int nrTotalBytes; 
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int[] getHeader() {
		return header;
	}
	public void setHeader(int[] header) {
		this.header = header;
	}
	public int getRowSize() {
		return rowSize;
	}
	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}
	public int getNrBytes() {
		return nrBytes;
	}
	public void setNrBytes(int nrBytes) {
		this.nrBytes = nrBytes;
	}
	public int getPadding() {
		return padding;
	}
	public void setPadding(int padding) {
		this.padding = padding;
	}
	public int getNrTotalBytes() {
		return nrTotalBytes;
	}
	public void setNrTotalBytes(int nrTotalBytes) {
		this.nrTotalBytes = nrTotalBytes;
	}
	public int getSizeOfImage() {
		return sizeOfImage;
	}
	public void setSizeOfImage(int sizeOfImage) {
		this.sizeOfImage = sizeOfImage;
	}
	
}
