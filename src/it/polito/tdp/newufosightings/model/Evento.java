package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Evento {
	
	public enum TipoEvento{
		AVVISTAMENTO,
		RIPRISTINO_DEFCON
	}
	
	private TipoEvento tipo;
	private LocalDateTime data;
	private String siglaStato;
	
	public Evento(TipoEvento tipo, LocalDateTime data, String siglaStato) {
		this.tipo = tipo;
		this.data = data;
		this.siglaStato = siglaStato;
	}

	public TipoEvento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public String getSiglaStato() {
		return siglaStato;
	}

	public void setSiglaStato(String siglaStato) {
		this.siglaStato = siglaStato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((siglaStato == null) ? 0 : siglaStato.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Evento other = (Evento) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (siglaStato == null) {
			if (other.siglaStato != null)
				return false;
		} else if (!siglaStato.equals(other.siglaStato))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
}