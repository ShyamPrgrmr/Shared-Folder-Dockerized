package com.sharedfolder.main;

import java.awt.EventQueue;

import com.sharedfolder.server.LoginWindow;

public class MainClass extends Thread{
	
	public static void main(String[] args) {
		MainClass mainClass = new MainClass();
		mainClass.start();
	}
	
	public void run(){
		LoginWindowStart login = new LoginWindowStart();
		login.start();
	}
	
	
	
	public class LoginWindowStart extends Thread{
		public void run() {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						LoginWindow loginWindow = new LoginWindow();
						loginWindow.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
}
