package fr.helium.simulator;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import fr.helium.simulator.render.Primitives;
import fr.helium.simulator.utils.Vector;

public class Quadcopter {
	
	private float thrust = 0;
	private float yaw = 0;
	private float pitch = 0;
	private float roll = 0;
	
	public float thrust_ref = 0f;
	public float yaw_ref = 0;
	public float pitch_ref = 0;
	public float roll_ref = 0;
	
	private final PID yaw_pid = new PID(1, 0, 0);
	private final PID pitch_pid = new PID(1, 0, 0);
	private final PID roll_pid = new PID(1, 0, 0);
	
	private float x, y, z;
	
	private float m = 1;
	private float g = 9.8f;
	
	public Quadcopter() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	public void update(float dt) {
		thrust = thrust_ref;
		yaw += yaw_pid.compute(yaw_ref, yaw, dt)*dt;
		pitch += pitch_pid.compute(pitch_ref, pitch, dt)*dt;
		roll += roll_pid.compute(roll_ref, roll, dt)*dt;
		
		Vector forward = getForwardVector().mul(thrust);
		
		x += forward.x*dt;
		y += (forward.y - (m*g)*dt)*dt;
		z += forward.z*dt;
		
		if (y<0) y = 0;
		
	}
	
	public Vector getForwardVector() {
    	float x = (float) (-Math.cos(Math.toRadians(90+pitch))*Math.sin(Math.toRadians(180-yaw+roll)));
    	float y = (float) (Math.sin(Math.toRadians(90+pitch)));
    	float z = (float) (Math.cos(Math.toRadians(90+pitch))*Math.cos(Math.toRadians(180-yaw+roll)));
    	return new Vector(x, y, z).normalize();
    }
	
	public void render(float dt) {
		update(dt);
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef(x, y, z);
		
		GL11.glRotatef(pitch, 1, 0, 0);
		GL11.glRotatef(yaw, 0, 1, 0);
		GL11.glRotatef(roll, 0, 0, 1);
		
		Primitives.setColor(Color.GREEN);
		Primitives.drawCylinder(12, 1, 0.2f);
		
		GL11.glPopMatrix();
	}
	
}
