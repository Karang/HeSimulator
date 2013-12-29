package fr.helium.simulator.server;

import java.net.Socket;

import fr.helium.simulator.Quadcopter;

public class GCSclient extends Thread {

	private final Socket socket;
	private final Quadcopter uav;
	
	public GCSclient(Socket s, Quadcopter uav) {
		this.uav = uav;
		this.socket = s;
	}
	
	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				int t;
				String tmp="" ;
				t = socket.getInputStream().read();
				tmp = tmp + (char)t;
				
				System.out.println(tmp) ;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
