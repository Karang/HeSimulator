package fr.helium.simulator.render;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

import org.lwjgl.util.glu.Sphere;

import fr.helium.simulator.utils.Vector;

public class Primitives {

	public static void drawCube(float width, float height, float length) {
		float w = width / 2.f;
		float h = height / 2.f;
		float l = length / 2.f;

		glPushMatrix();
		
		glTranslatef(0,h,0);
		drawPlane(Vector.UP, width, length);
		
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,-h,0);
		drawPlane(Vector.DOWN, width, length);
		
		glPopMatrix();
		glPushMatrix();
		glTranslatef(w,0,0);
		drawPlane(Vector.EAST, height, length);
		
		glPopMatrix();
		glPushMatrix();
		glTranslatef(-w,0,0);
		drawPlane(Vector.WEST, height, length);
		
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,0,l);
		drawPlane(Vector.NORTH, height, width);
		
		glPopMatrix();
		glPushMatrix();
		glTranslatef(0,0,-l);
		drawPlane(Vector.SOUTH, height, width);
		
		glPopMatrix();
	}
	
	public static void drawCube(float width, float height, float length, boolean[] faces) {
		float w = width / 2.f;
		float h = height / 2.f;
		float l = length / 2.f;

		if (faces[0]) {
			glPushMatrix();
			glTranslatef(0,h,0);
			drawPlane(Vector.UP, width, length);
			glPopMatrix();
		}
		
		if (faces[1]) {
			glPushMatrix();
			glTranslatef(0,-h,0);
			drawPlane(Vector.DOWN, width, length);
			glPopMatrix();
		}
		
		if (faces[2]) {
			glPushMatrix();
			glTranslatef(w,0,0);
			drawPlane(Vector.EAST, height, length);
			glPopMatrix();
		}
		
		if (faces[3]) {
			glPushMatrix();
			glTranslatef(-w,0,0);
			drawPlane(Vector.WEST, height, length);
			glPopMatrix();
		}
		
		if (faces[4]) {
			glPushMatrix();
			glTranslatef(0,0,l);
			drawPlane(Vector.NORTH, height, width);
			glPopMatrix();
		}
		
		if (faces[5]) {
			glPushMatrix();
			glTranslatef(0,0,-l);
			drawPlane(Vector.SOUTH, height, width);
			glPopMatrix();
		}
	}
	
	public static void setColor(Color c) {
		glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	}
	
	public static void drawGuiRect(float x, float y, float w, float h, float u1, float v1, float u2, float v2) {
		glBegin(GL_QUADS);
		
		glTexCoord2f(u1, v1);
		glVertex3f(x, y, 0.f);
		
		glTexCoord2f(u1, v2);
		glVertex3f(x, y-h, 0.f);
		
		glTexCoord2f(u2, v2);
		glVertex3f(x+w, y-h, 0.f);
		
		glTexCoord2f(u2, v1);
		glVertex3f(x+w, y, 0.f);
		
		glEnd();
	}

	public static void drawPlane(Vector normal, float width, float length) {
		float w = width / 2.f;
		float l = length / 2.f;

		Vector u, v;
		if (normal.equals(Vector.UP)) {
			u = Vector.NORTH.mul(l);
			v = Vector.EAST.mul(w);
		} else if (normal.equals(Vector.DOWN)) {
			u = Vector.EAST.mul(w);
			v = Vector.NORTH.mul(l);
		} else {
			u = normal.cross(Vector.UP);
			v = normal.cross(u);
			u = u.mul(l);
			v = v.mul(w);
		}
		
		Vector p1 = v.add(u).mul(-1);
		Vector p2 = u.sub(v);
		Vector p3 = u.add(v);
		Vector p4 = v.sub(u);
		
		glBegin(GL_TRIANGLES);
		
		glTexCoord2f(1, 1);
		drawVertex(p1, normal);
		glTexCoord2f(0, 1);
		drawVertex(p2, normal);
		glTexCoord2f(0, 0);
		drawVertex(p3, normal);
		
		glTexCoord2f(0, 0);
		drawVertex(p3, normal);
		glTexCoord2f(1, 0);
		drawVertex(p4, normal);
		glTexCoord2f(1, 1);
		drawVertex(p1, normal);

		glEnd();
	}
	
	public static void drawFace(Vector p1, Vector p2, Vector p3, Vector n1, Vector n2, Vector n3, Color c1, Color c2, Color c3) {		
		glBegin(GL_TRIANGLES);
		
		glColor4f(c1.getRed() / 255f, c1.getGreen() / 255f, c1.getBlue() / 255f, c1.getAlpha() / 255f);
		drawVertex(p1, n1);
		glColor4f(c2.getRed() / 255f, c2.getGreen() / 255f, c2.getBlue() / 255f, c2.getAlpha() / 255f);
		drawVertex(p2, n2);
		glColor4f(c3.getRed() / 255f, c3.getGreen() / 255f, c3.getBlue() / 255f, c3.getAlpha() / 255f);
		drawVertex(p3, n3);
		
		glEnd();
	}
	
	public static void drawFace(Vector p1, Vector p2, Vector p3, Color c1, Color c2, Color c3) {
		final Vector n = p1.sub(p2).cross(p2.sub(p3));
		
		glBegin(GL_TRIANGLES);
		
		glColor4f(c1.getRed() / 255f, c1.getGreen() / 255f, c1.getBlue() / 255f, c1.getAlpha() / 255f);
		drawVertex(p1, n);
		glColor4f(c2.getRed() / 255f, c2.getGreen() / 255f, c2.getBlue() / 255f, c2.getAlpha() / 255f);
		drawVertex(p2, n);
		glColor4f(c3.getRed() / 255f, c3.getGreen() / 255f, c3.getBlue() / 255f, c3.getAlpha() / 255f);
		drawVertex(p3, n);
		
		glEnd();
	}

	public static void drawFace(Vector p1, Vector p2, Vector p3) {
		final Vector n = p1.sub(p2).cross(p2.sub(p3));
		
		glBegin(GL_TRIANGLES);
		
		drawVertex(p1, n);
		drawVertex(p2, n);
		drawVertex(p3, n);
		
		glEnd();
	}
	
	public static void drawSphere(int resolution, float radius) {
		/*double radius1 = 0;
		double radius2 = 0;

		double angle = 0;
		double dAngle =  (Math.PI / resolution);

		float x = 0;
		float y = 0;

		glShadeModel(GL_SMOOTH);
		glColor3f(1,0,0);
		
		for(int i = 0; i < resolution; i++) {
			angle = Math.PI/2 - i*dAngle;
			radius1 = radius * Math.cos(angle);
			float z1 = (float) (radius * Math.sin(angle));

			angle = Math.PI/2 - (i+1)*dAngle;
			radius2 = radius * Math.cos(angle);
			float z2 = (float) (radius * Math.sin(angle));

			glBegin(GL_TRIANGLE_STRIP);

			for(int j = 0; j <= 2*resolution; j++) {
				double cda = Math.cos(j * dAngle);
				double sda = Math.sin(j * dAngle);

				x = (float) (radius1 * cda);
				y = (float) (radius1 * sda);
				glVertex3f(x, y, z1);
				x = (float) (radius2 * cda);
				y = (float) (radius2 * sda);
				glVertex3f(x, y, z2);
			}
			glEnd();
			
		}*/
		Sphere s = new Sphere();
		s.draw(radius, resolution, resolution);
	}
	
	public static void drawCylinder(int resolution, float radius, float length) {
		double dAngle = 2*Math.PI / resolution;
		double angle = 0;
		
		Vector c1 = new Vector(0, 0, 0);
		Vector c2 = new Vector(0, length, 0);
		
		glBegin(GL_TRIANGLES);
		for (int i=0 ; i<resolution ; i++) {
			Vector p1 = new Vector((float) Math.cos(angle+dAngle)*radius, length, (float) Math.sin(angle+dAngle)*radius);
			Vector n1 = normal(c1, p1);
			Vector p2 = new Vector((float) Math.cos(angle)*radius, length, (float) Math.sin(angle)*radius);
			Vector n2 = normal(c1, p2);
			Vector p3 = new Vector((float) Math.cos(angle)*radius, 0, (float) Math.sin(angle)*radius);
			Vector n3 = normal(c2, p3);
			Vector p4 = new Vector((float) Math.cos(angle+dAngle)*radius, 0, (float) Math.sin(angle+dAngle)*radius);
			Vector n4 = normal(c2, p4);
			
			
			drawVertex(c2, Vector.UP);
			drawVertex(p1, Vector.UP);
			drawVertex(p2, Vector.UP);

			drawVertex(c1, Vector.DOWN);
			drawVertex(p3, Vector.DOWN);
			drawVertex(p4, Vector.DOWN);
			
			drawVertex(p3, n3);
			drawVertex(p2, n2);
			drawVertex(p1, n1);
			
			drawVertex(p1, n1);
			drawVertex(p4, n4);
			drawVertex(p3, n3);

			angle += dAngle;
		}
		glEnd();
	}
	
	private static Vector normal(Vector p1, Vector p2) {
		return p2.sub(p1).normalize();
	}
	
	private static void drawVertex(Vector vertex, Vector normal) {
		glNormal3f(normal.x,normal.y,normal.z);
		glVertex3f(vertex.x, vertex.y, vertex.z);
	}
}
