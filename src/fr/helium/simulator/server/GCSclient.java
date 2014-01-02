package fr.helium.simulator.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.ByteBuffer;

import fr.helium.simulator.Quadcopter;

public class GCSclient extends Thread {

	private final Socket socket;
	private final Quadcopter uav;
	private BufferedReader in;
	
	public GCSclient(Socket s, Quadcopter uav) {
		this.uav = uav;
		this.socket = s;
		
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ByteBuffer decodeMsg(String msg) {
		return ByteBuffer.wrap(msg.getBytes());
	}
	
	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				ByteBuffer msg = decodeMsg(in.readLine());
				char c = msg.getChar();
				if (c=='T' || c=='P' || c=='Y' || c=='R') {
					int i = msg.getInt();
					float v = Float.intBitsToFloat(i);
					
					if (c == 'T') { // Thrust
						uav.thrust_ref = v;
						//System.out.println("Thrust: "+v);
					} else if (c == 'P') { // Pitch
						uav.pitch_ref = v;
						//System.out.println("Pitch: "+v);
					} else if (c == 'Y') { // Yaw
						uav.yaw_ref = v;
						//System.out.println("Yaw: "+v);
					} else if (c == 'R') { // Roll
						uav.roll_ref = v;
						//System.out.println("Roll: "+v);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
