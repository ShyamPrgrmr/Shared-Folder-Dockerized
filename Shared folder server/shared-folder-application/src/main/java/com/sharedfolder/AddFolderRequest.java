package com.sharedfolder;

public class AddFolderRequest {
	
	String nameOfFolder;
	String[] path;
	
	public String getNameOfFolder() {
		return nameOfFolder;
	}
	public void setNameOfFolder(String nameOfFolder) {
		this.nameOfFolder = nameOfFolder;
	}
	public String[] getPath() {
		return path;
	}
	public void setPath(String[] path) {
		this.path = path;
	}
}
