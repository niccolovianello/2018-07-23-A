package it.polito.tdp.newufosightings.model;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;
import it.polito.tdp.newufosightings.model.Evento.TipoEvento;

public class Simulatore {
	
	private int alfa;
	private int T1;
	private int year;
	
	private Graph<State, Adiacenza> grafo;
	
	private Map<State, Double> defcon;
	private Map<String, State> statiIdMap;
	
	private PriorityQueue<Evento> queue;
	
	public void init(int alfa, int T1, int year, Graph<State, Adiacenza> grafo, Map<String, State> statiIdMap) {
		
		this.T1 = T1;
		this.alfa = alfa;
		this.year = year;
		this.grafo = grafo;
		
		this.statiIdMap = statiIdMap;
		
		
		NewUfoSightingsDAO dao = new NewUfoSightingsDAO();
		for(State s : dao.loadAllStates()) {
			defcon.put(s, 5.0);
		}
		
		queue = new PriorityQueue<>();
		
		for(Sighting s : dao.loadAllSightings()) {
			queue.add(new Evento(TipoEvento.AVVISTAMENTO, s.getDatetime(), s.getState()));
		}
	}
	
	public void run() {
		
		Evento e;
		
		while((e = queue.poll()) != null) {
			switch (e.getTipo()) {
				case AVVISTAMENTO:
					System.out.println("NUOVO AVVISTAMENTO! ");
					Double defconLevel = this.defcon.get(statiIdMap.get(e.getSiglaStato()));
					
					if(defconLevel > 1)
						defcon.put(statiIdMap.get(e.getSiglaStato()), defconLevel-1);
					
					for(State s : Graphs.neighborListOf(grafo, statiIdMap.get(e.getSiglaStato()))) {
						Random r = new Random();
						
						if(r.nextDouble() > 1-alfa/100) {
							defcon.put(s, defconLevel-0.5);
						}
					}
					break;
			}
			
		}
		
	}

}
