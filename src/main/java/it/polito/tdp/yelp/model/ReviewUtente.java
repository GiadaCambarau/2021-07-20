package it.polito.tdp.yelp.model;

import java.util.Objects;

public class ReviewUtente {
	private User u;
	private int n;
	
	public ReviewUtente(User u, int n) {
		super();
		this.u = u;
		this.n = n;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	@Override
	public int hashCode() {
		return Objects.hash(n, u);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewUtente other = (ReviewUtente) obj;
		return n == other.n && Objects.equals(u, other.u);
	}
	
	
	
}
