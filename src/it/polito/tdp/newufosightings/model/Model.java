package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	
	private List<String> forme;
	private List<Adiacenza> adiacenze;
	private List<State> stati;
	private Map<String, State> statiIdMap;
	private Map<State, Integer> statiPesi;
	
	private Graph<State, Adiacenza> grafo;
	
	public Model() {
		NewUfoSightingsDAO dao = new NewUfoSightingsDAO();
		
		stati = new ArrayList<>(dao.loadAllStates());
		
		statiIdMap = new HashMap<>();
		statiPesi = new HashMap<>();
	}
	
	public void creaGrafo(int anno, String shape) {
		
		NewUfoSightingsDAO dao = new NewUfoSightingsDAO();
		grafo = new SimpleWeightedGraph<>(Adiacenza.class);
		
		for(State s : stati)
			statiIdMap.put(s.getId(), s);
		
		adiacenze = dao.getAllAdiacenze(statiIdMap);
		
		// vertici
		Graphs.addAllVertices(grafo, dao.loadAllStates());
		
		// archi
		for(Adiacenza a : adiacenze) {
			grafo.addEdge(a.getS1(), a.getS2(), a);
		}
		
		// peso archi
		for(Adiacenza a : grafo.edgeSet()) {
			a.setPeso(dao.getNumeroAvvistamentiAdiacenti(anno, a.getS1(), a.getS2(), shape));
			grafo.setEdgeWeight(a, dao.getNumeroAvvistamentiAdiacenti(anno, a.getS1(), a.getS2(), shape));
		}
		
		for(State s : grafo.vertexSet()) {
			
			int pesoTot = 0;
			
			for(Adiacenza a : grafo.edgesOf(s)) {
				pesoTot += a.getPeso();
			}
			statiPesi.put(s, pesoTot);
		}
		
		// System.out.println("Grafo creato con: " + grafo.vertexSet().size() + " vertici e " + grafo.edgeSet().size() + " archi.");
	}

	public List<String> getShapes(int anno) {
	
		NewUfoSightingsDAO dao = new NewUfoSightingsDAO();
		forme = new ArrayList<>(dao.loadAllShapes(anno));
		return forme;
	}

	public Map<State, Integer> getStatiPesi() {
		return statiPesi;
	}

	public void simula(int T1, int alfa, int anno) {
		Simulatore sim = new Simulatore();
		sim.init(alfa, T1, anno, grafo, statiIdMap);
		sim.run();
	}
	
	
	

}
