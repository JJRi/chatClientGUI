package com.digimortal.chatClientGUI;

import java.io.*;
import java.net.*;

public class ChatClient {
	private Socket socket = null;
	private BufferedReader inputConsole = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	
	public  ChatClient(String address, int port	) {
		try {
			socket = new Socket(address, port);
	//https://hackr.io/blog/how-to-build-a-java-chat-app		
		}
	}
	
	public static void main(String[] args) {
		

	}

}
