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
				if (socket.getInputStream().available() >= 2) {
					char c = (char)socket.getInputStream().read();
					if (c=='T' || c=='P' || c=='Y' || c=='R') {
						int i = socket.getInputStream().read();
						float v = (i-127) / 127f;
						
						if (c == 'T') { // Thrust
							uav.thrust_ref = v;
							System.out.println("Thrust: "+v);
						} else if (c == 'P') { // Pitch
							uav.pitch_ref = v*360;
							//System.out.println("Pitch: "+v*360);
						} else if (c == 'Y') { // Yaw
							uav.yaw_ref = v*360;
							//System.out.println("Yaw: "+v*360);
						} else if (c == 'R') { // Roll
							uav.roll_ref = v*360;
							//System.out.println("Roll: "+v*360);
						}
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
