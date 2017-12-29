
package com.example.emmanuelbulacio.agendacontatos;

public class Curso {
    private String nome,area,tempo;

    public Curso(String nome,String area,String tempo){
        this.nome=nome;
        this.tempo=tempo;
        this.area=area;
    }

    public void setTempo(String nTempo){
        tempo=nTempo;
    }

    public String getTempo(){
        return tempo;
    }

    public String getNome(){
        return nome;
    }

    public String getArea(){
        return area;
    }

    @Override
    public String toString() { return nome+" - "+area +" - "+tempo+"hs\n";}
}