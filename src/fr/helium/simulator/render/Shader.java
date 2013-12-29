package fr.helium.simulator.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import fr.helium.simulator.utils.Vector;


public class Shader {
	private int program = -1;
	private Map<String,Integer> uniforms = new HashMap<String, Integer>();
	private IntBuffer intBuffer = BufferUtils.createIntBuffer(100);
	private FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(100);
	
	public Shader(String vert, String frag) {
		program = ShaderLoader.loadShaderPair(vert, frag);
	}
	
	public int getUniform(String name) {
		if (uniforms.containsKey(name))
			return uniforms.get(name);
		int ret = GL20.glGetUniformLocation(program, name);
		uniforms.put(name, ret);
		return ret;
	}
	
	public void setUniform(String name, int i) {
		GL20.glUniform1i(getUniform(name), i);
	}
	
	public void setUniform(String name, int[] intArray) {
		intBuffer.position(0);
		intBuffer.limit(intArray.length);
		intBuffer.put(intArray);
		intBuffer.position(0);
		GL20.glUniform1(getUniform(name), intBuffer);
	}
	
	public void setUniform(String name, float f) {
		GL20.glUniform1f(getUniform(name), f);
	}
	
	public void setUniform(String name, float[] floatArray) {
		floatBuffer.position(0);
		floatBuffer.limit(floatArray.length);
		floatBuffer.put(floatArray);
		floatBuffer.position(0);
		GL20.glUniform1(getUniform(name), floatBuffer);
	}
	
	public void setUniform(String name, Vector v) {
		GL20.glUniform3f(getUniform(name), v.x, v.y, v.z);
	}
	
	public void setUniform(String name, Vector[] vecArray) {
		floatBuffer.position(0);
		floatBuffer.limit(vecArray.length*3);
		for (int i=0 ; i<vecArray.length ; i++) {
			Vector v = vecArray[i];
			floatBuffer.put(v.x);
			floatBuffer.put(v.y);
			floatBuffer.put(v.z);
		}
		floatBuffer.position(0);
		GL20.glUniform1(getUniform(name), floatBuffer);
	}
	
	public void setUniform(String name, boolean transpose, Matrix4f matrix) {
		floatBuffer.position(0);
		floatBuffer.limit(16);
		matrix.store(floatBuffer);
		floatBuffer.position(0);
		GL20.glUniformMatrix4(getUniform(name), transpose, floatBuffer);
	}
	
	public void enable() {
		if (program != -1)
			GL20.glUseProgram(program);
	}
	
	public void disable() {
		GL20.glUseProgram(0);
	}
	
	public void dispose() {
		if (program!=-1)
			GL20.glDeleteProgram(program);
	}
}
