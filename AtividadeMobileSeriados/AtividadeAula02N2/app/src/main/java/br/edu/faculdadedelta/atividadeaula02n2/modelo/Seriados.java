package br.edu.faculdadedelta.atividadeaula02n2.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Seriados implements Serializable {

     private Long id;
     private String genero;
     private String status;
     private String nome;
     private String comentario;
     private int nota;
     private Date data;

    public Seriados() {
    }

    public Seriados(Long id, String genero, String status, String nome, String comentario, int nota, Date data) {
        this.id = id;
        this.genero = genero;
        this.status = status;
        this.nome = nome;
        this.comentario = comentario;
        this.nota = nota;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seriados)) return false;
        Seriados seriados = (Seriados) o;
        return id.equals(seriados.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
