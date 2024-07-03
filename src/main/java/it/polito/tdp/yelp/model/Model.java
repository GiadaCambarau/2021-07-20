package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	private YelpDao dao;
	private Graph<User, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new YelpDao();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}
	
	public void creaGrafo(int n, int anno) {
		List<User> utenti = dao.getUsers(n);
		Graphs.addAllVertices(this.grafo, utenti);
		for (User u1 : this.grafo.vertexSet()) {
			for (User u2: this.grafo.vertexSet()) {
				if (!u1.equals(u2)) {
					int peso = dao.getSimilarita(u1, u2, anno);
					if (peso>0) {
						Graphs.addEdge(this.grafo, u1, u2, peso);
					}
				}
				
			}
		}
	}
	
	public int getV() {
		return this.grafo.vertexSet().size();
	}
	public int getA() {
		return this.grafo.edgeSet().size();
	}
	
	public Set<User> getVertici() {
		return this.grafo.vertexSet();
	}
	
	public List<GradoSimil> piuSimili(User u1){
		List<User> vicini = Graphs.neighborListOf(this.grafo, u1);
		int max =0;
		List<GradoSimil> result = new ArrayList<>();
		for (User u: vicini) {
			int peso = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(u, u1));
			if (peso> max ) {
				max =peso;
			}
		}
		for (User u: vicini) {
			int peso = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(u, u1));
			if (peso == max ) {
				result.add(new GradoSimil(u, peso));
			}
		}
		return result;
	}
	
	public void simula(int utenti, int intervistatori) {
		
	}
	
	
	
	
}
