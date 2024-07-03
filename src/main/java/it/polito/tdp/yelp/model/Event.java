package it.polito.tdp.yelp.model;

import java.util.Objects;

public class Event implements Comparable<Event> {
	public enum EventType{
		intervista,
		ferie
	}
	private EventType type;
	private User intervistato;
	private int intervistatore;
	private int tempo;
	public Event(EventType type, User intervistato, int intervistatore, int tempo) {
		super();
		this.type = type;
		this.intervistato = intervistato;
		this.intervistatore = intervistatore;
		this.tempo = tempo;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public User getIntervistato() {
		return intervistato;
	}
	public void setIntervistato(User intervistato) {
		this.intervistato = intervistato;
	}
	public int getIntervistatore() {
		return intervistatore;
	}
	public void setIntervistatore(int intervistatore) {
		this.intervistatore = intervistatore;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	@Override
	public int hashCode() {
		return Objects.hash(intervistato, intervistatore, tempo, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(intervistato, other.intervistato) && intervistatore == other.intervistatore
				&& tempo == other.tempo && type == other.type;
	}
	@Override
	public int compareTo(Event o) {
		return this.tempo-o.tempo;
	}
	
	

}
