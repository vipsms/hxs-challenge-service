package com.eeduspace.challenge.test.mina;


public class TestMessageClient {
	public static void main(String[] args) {
		MessageClient messageClient=new MessageClient();
		messageClient.connect("192.168.1.171", 9876, 30000, "da95e1572e134177875c18a3289de842");
		messageClient.connect("192.168.1.171", 9876, 30000, "3b3bb884616346ebb193f5d82961b1c1");
		//messageClient.connect(, , , );
//        messageClient.close();
	}
}
