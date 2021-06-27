package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private SimpleWeightedGraph<Director, DefaultWeightedEdge> grafo;
	private ImdbDAO dao;
	private List<Arco> archi;
	private Map<Integer, Director> idMap;
	private Integer anno;
	private List<Director> percorsoBest;
	private Integer attoriCondivisiBest;
	
	public Model() {
		this.dao = new ImdbDAO();
	}

	public String creaGrafo(Integer anno) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.archi = new ArrayList<>();
		this.idMap = new HashMap<>();
		this.anno = anno;
		
		this.dao.getVertex(anno, idMap);
		Graphs.addAllVertices(this.grafo, this.idMap.values());
		
		this.archi = this.dao.getEdges(anno, this.idMap);
		for(Arco a : this.archi)
			Graphs.addEdgeWithVertices(this.grafo, a.getDirettore1(), a.getDirettore2(), a.getPeso());
		
		return String.format("GRAFO CREATO!!\n\n#VERTICI: %s\n#ARCHI: %s", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
	}

	public List<Director> getAllVertex() {
		List<Director> temp = new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(temp);
		
		return temp;
	}
	
	public Integer getAnno() {
		return this.anno;
	}

	public Map<Director, Integer> getAllAdiacenti(Director scelto) {
		Collections.sort(this.archi);
		
		Map<Director, Integer> result = new LinkedHashMap<>();
		for(Arco a : this.archi) {
			
			if(a.getDirettore1().equals(scelto))
				result.put(a.getDirettore2(), a.getPeso());
			else if(a.getDirettore2().equals(scelto))
				result.put(a.getDirettore1(), a.getPeso());
		}
		
		return result;
	}

	public void trovaPercorso(Integer c, Director scelto) {
		this.percorsoBest = new ArrayList<>();
		this.attoriCondivisiBest = 0;
		
		List<Director> parziale = new ArrayList<>();
		parziale.add(scelto);
		
		this.calcolaPercorso(parziale, c, 0);
	}

	private void calcolaPercorso(List<Director> parziale, Integer c, Integer attoriCondivisi) {
		
		if(attoriCondivisi <= c) {
			
			if(parziale.size()>this.percorsoBest.size()) {
				
				this.percorsoBest = new ArrayList<>(parziale);
				this.attoriCondivisiBest = attoriCondivisi;
			}
		}
		
		Director precedente = parziale.get(parziale.size()-1);
		for(Director d : Graphs.neighborListOf(this.grafo, precedente)) {
			
			if(!parziale.contains(d)) {
				Integer condivisi = (int)this.grafo.getEdgeWeight(this.grafo.getEdge(precedente, d));
				
				attoriCondivisi += condivisi;
				parziale.add(d);
				
				this.calcolaPercorso(parziale, c, attoriCondivisi);
				
				attoriCondivisi -= condivisi;
				parziale.remove(parziale.size()-1);
			}
		}
	}

	public List<Director> getPercorsoBest() {
		return percorsoBest;
	}

	public Integer getAttoriCondivisiBest() {
		return attoriCondivisiBest;
	}
	
	public Integer getRegistiCoinvoltiBest() {
		return percorsoBest.size();
	}

}
