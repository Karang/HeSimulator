package fr.helium.simulator.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import fr.helium.simulator.Quadcopter;

public class GCSconnector extends Thread {

	private ServerSocket server;
	private GCSclient client;
	
	private Quadcopter uav;
	
	private final int PORT = 11337;
	
	public GCSconnector(Quadcopter uav) {
		try {
			server = new ServerSocket(PORT);
			server.setSoTimeout(1000);
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.uav = uav;
	}
	
	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				Socket s = server.accept();
				if (client==null) {
					System.out.println("GCS connected !");
					client = new GCSclient(s, uav);
					client.start();
				}
			} catch(IOException e) {
				
			}
		}
		
		if (client != null) {
			client.interrupt();
		}
	}
	
}
