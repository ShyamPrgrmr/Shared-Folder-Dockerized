package com.sharedfolder;

public class FolderResponse {
	String nameOfFolder;
	FolderResponse files[];
	boolean isFolder;
	
	
	public boolean isFolder() {
		return isFolder;
	}

	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public String getNameOfFolder() {
		return nameOfFolder;
	}
	public void setNameOfFolder(String nameOfFolder) {
		this.nameOfFolder = nameOfFolder;
	}
	public FolderResponse[] getFiles() {
		return files;
	}
	public void setFiles(FolderResponse[] files) {
		this.files = files;
	}
			
}
