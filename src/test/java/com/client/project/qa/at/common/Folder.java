package com.client.project.qa.at.common;

import java.io.File;

public class Folder {
	
	int foldersCount=0;
	
	File file = new File(System.getProperty("user.dir")+"\\"+GetProperties.getConfigPropety("downloadsPath"));
	
	int getFoldCount(){
		System.out.println(file.getAbsolutePath());
		File[] f = file.listFiles();
		for(File fi : f){
			if(fi.isDirectory())
				foldersCount++;
		}
		return foldersCount;
	}
	
	public static void main(String[] a){
		Folder folder = new Folder();
		System.out.println(folder.getFoldCount());;
	}
}
