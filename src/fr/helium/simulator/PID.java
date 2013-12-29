package fr.helium.simulator;

public class PID {

	public float kP, kI, kD;
	
	public PID(float kP, float kI, float kD) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
	}
	
	public float compute(float ref, float input, float dt) {
		float diff = ref-input;
		
		float P = diff;
		float I = 0;
		float D = 0;
		
		return kP*P + kI*I + kD*D;
	}
	
}
