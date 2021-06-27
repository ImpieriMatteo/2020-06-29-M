package it.polito.tdp.imdb.model;

public class Arco implements Comparable<Arco>{
	
	private Director direttore1;
	private Director direttore2;
	private Integer peso;
	
	public Arco(Director direttore1, Director direttore2, Integer peso) {
		this.direttore1 = direttore1;
		this.direttore2 = direttore2;
		this.peso = peso;
	}

	public Director getDirettore1() {
		return direttore1;
	}

	public void setDirettore1(Director direttore1) {
		this.direttore1 = direttore1;
	}

	public Director getDirettore2() {
		return direttore2;
	}

	public void setDirettore2(Director direttore2) {
		this.direttore2 = direttore2;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direttore1 == null) ? 0 : direttore1.hashCode());
		result = prime * result + ((direttore2 == null) ? 0 : direttore2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arco other = (Arco) obj;
		if (direttore1 == null) {
			if (other.direttore1 != null)
				return false;
		} else if (!direttore1.equals(other.direttore1))
			return false;
		if (direttore2 == null) {
			if (other.direttore2 != null)
				return false;
		} else if (!direttore2.equals(other.direttore2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Arco [direttore1=" + direttore1 + ", direttore2=" + direttore2 + ", peso=" + peso + "]";
	}

	@Override
	public int compareTo(Arco o) {
		return -this.peso.compareTo(o.peso);
	}
	

}
