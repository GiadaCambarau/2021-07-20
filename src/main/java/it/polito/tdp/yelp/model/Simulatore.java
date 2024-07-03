package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.yelp.model.Event.EventType;

public class Simulatore {
	//paramentri ingresso
	private int daIntervistare;
	private int intervistatori;
	private Graph<User, DefaultWeightedEdge> grafo;
	private List<User> utentiPresenti;
	private Model model;
	
	//simulazione
	private PriorityQueue<Event> queue;
	private int giornalistiLiberi;
	private int utentiRimanenti;
	
	//parametri uscita
	private int nGiorni;
	private Map<Integer, Integer> mappa;
	
	
	public Simulatore(Graph<User, DefaultWeightedEdge> grafo) {
		super();
		this.grafo = grafo;
		this.model = new Model();
	}
	
	public void initialize(int x1, int x2) {
		this.daIntervistare = x1;
		this.intervistatori = 0;
		
	
		this.utentiPresenti = new ArrayList<User>(this.grafo.vertexSet());
		this.utentiRimanenti = utentiPresenti.size();
		//devo inizializzare la mappa con i giornalisti
		 this.queue = new PriorityQueue<>();
		for (int i=0; i < x2; i++) {
			mappa.put(i, 0);
		}
		int i =0;
		//aggiungo gli eventi alla coda
		//aggiungi un nuovo evento iniziale fino a che non sono finiti gli
		//utenti da integrvistare e fino  a che gli intervistatori occupati
		//sono minori o uguali a quelli disponibili
		
		while (utentiRimanenti>0 && intervistatori< x2) {
			User interv = getUtenteRandom(utentiPresenti);
			Event e = new Event(EventType.intervista, interv, i, 1);
			i++;
			intervistatori++;
			utentiRimanenti--;
		}
	}
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = queue.poll();
			this.nGiorni = e.getTempo();
			process(e);
		}
	}

	private void process(Event e) {
		int tempo = e.getTempo();
		EventType type = e.getType();
		User intervistato = e.getIntervistato();
		int gior = e.getIntervistatore();
		
		switch(type) {
		case intervista:
				//se p <0.60 incarico portato a termine
				//se possibile si assegna il giornalista a uno dei più simili
			//se posso scegliere scelgo casualmente
			//se non ce ne sono scelgo a caso tra gli utenti non ancora intervistati
			utentiPresenti.remove(intervistato);
			utentiRimanenti--;
			double p = Math.random();
			if (p<0.6) {
				List<User> vicini = piuSimili(intervistato);
				vicini.retainAll(utentiPresenti);
				User next = null;
				if (!vicini.isEmpty()) {
				//scelgo il prossimo utente da intervistare 
					next = getUtenteRandom(vicini);
				}else {
					//se è vuota scelo casualmente tra quelli rimasti
					next = getUtenteRandom(utentiPresenti);
				}
				//aumento gli intervistati per il giornalista
				mappa.put(gior, mappa.get(gior)+1);
				queue.add(new Event(EventType.intervista, next, gior, tempo+1));
			}
			else if (p<0.8) {
				//chiede giorno di ferie
				queue.add(new Event(EventType.ferie, intervistato, gior, tempo+1));
				mappa.put(gior, mappa.get(gior)+1);
			}else {
				//deve reintervistare lo stesso quindi non aumento il numero di intervistati
				queue.add(new Event(EventType.intervista, intervistato, gior, tempo+1));
			}
		case ferie:
			queue.add(new Event(EventType.intervista, intervistato, gior, tempo));
		}
			
		
	}

	private User getUtenteRandom(List<User> utentiPresenti2) {
		double random = Math.random();
		int indice = (int) (random*utentiPresenti2.size());
		return utentiPresenti2.get(indice);
	}
	
	public List<User> piuSimili(User u1){
		List<User> vicini = Graphs.neighborListOf(this.grafo, u1);
		int max =0;
		List<User> result = new ArrayList<>();
		for (User u: vicini) {
			int peso = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(u, u1));
			if (peso> max ) {
				max =peso;
			}
		}
		for (User u: vicini) {
			int peso = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(u, u1));
			if (peso == max ) {
				result.add(u);
			}
		}
		return result;
	}
	
	
	
	
}
