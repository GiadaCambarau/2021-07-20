package it.polito.tdp.yelp.model;

import java.util.Objects;

public class GradoSimil {
	private User u;
	private int grado;
	public GradoSimil(User u, int grado) {
		super();
		this.u = u;
		this.grado = grado;
	}
	public User getU() {
		return u;
	}
	public void setU(User u) {
		this.u = u;
	}
	public int getGrado() {
		return grado;
	}
	public void setGrado(int grado) {
		this.grado = grado;
	}
	@Override
	public int hashCode() {
		return Objects.hash(grado, u);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GradoSimil other = (GradoSimil) obj;
		return grado == other.grado && Objects.equals(u, other.u);
	}
	@Override
	public String toString() {
		return  u + " GRADO: " + grado ;
	}
	
	

}
