package com.sharedfolder.client;

public class DataStorage {
	
	String currentPath[];
	String nameOfFolder;
	
	
	public String[] getCurrentPath() {
		return currentPath;
	}
	public void setCurrentPath(String[] currentPath) {
		this.currentPath = currentPath;
	}
	public String getNameOfFolder() {
		return nameOfFolder;
	}
	public void setNameOfFolder(String nameOfFolder) {
		this.nameOfFolder = nameOfFolder;
	}

}
