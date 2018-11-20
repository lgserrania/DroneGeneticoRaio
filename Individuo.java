/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronegeneticoraio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Gustavo
 */
public class Individuo implements Comparable<Individuo>{
    
    private double notaAvaliacao ;
    private int geracao;
    private double dist;
    private ArrayList<Ponto> cromossomo = new ArrayList<>();
    
    public Individuo(ArrayList<Ponto> pontos){
        this.notaAvaliacao = 0.0;
        this.geracao = 0;
        this.cromossomo = pontos;
    }
    
    public void avaliacao(){
        double nota = 0.0;
        
        for(int i = 0; i < this.cromossomo.size() - 1; i++){
            nota += Globals.calcDistancia(this.cromossomo.get(i), this.cromossomo.get(i + 1));
        }
        nota += Globals.calcDistancia(this.cromossomo.get(this.cromossomo.size() - 1), this.cromossomo.get(0));
        
        if(!this.todosVisitados()){
            nota = 10000;
        }
        
        this.notaAvaliacao = 10000/nota;
    }
    
    public double distancia(){
        double distancia = 0.0;
        
        for(int i = 0; i < this.cromossomo.size() - 1; i++){
            distancia += Globals.calcDistancia(this.cromossomo.get(i), this.cromossomo.get(i + 1));
        }
        distancia += Globals.calcDistancia(this.cromossomo.get(this.cromossomo.size() - 1), this.cromossomo.get(0));
        this.dist = distancia;
        return distancia;
    }
    
    public boolean todosVisitados(){
        
        boolean achou;

        for(Ponto p1 : Globals.pontosIniciais){
            achou = false;
            for(Ponto p2 : this.cromossomo){
                if(Globals.calcDistancia(p1,p2) <= p1.getRaio()){
                    achou = true;
                }
            }
            if(!achou){
                return false;
            }
        }
        
        return true;
    }
    
    public Individuo mutacao(Double taxaMutacao) {
        //System.out.println("Antes da mutaÃ§Ã£o: " + this.cromossomo);
        for (int i = 0; i < this.cromossomo.size(); i++) {
            if (Math.random() < taxaMutacao) {
                double tipo = Math.random() * 1;
                if(tipo <= 0.5){
                    Ponto p1 = this.cromossomo.get(i);
                    Random gen = new Random();
                    double x = 0;
                    double y = 0;
                    Ponto np = null;

                    do{
                        x = gen.nextDouble() * (Globals.maiorx);
                        y = gen.nextDouble() * (Globals.maiory); 
                        np = new Ponto(x, y, p1.getPai());
                    }while(Globals.calcDistancia(p1.getPai(), np) > p1.getPai().getRaio());
                        
                    this.cromossomo.set(i, np);
                }else{
                    Ponto p1 = null;
                    Ponto p2 = null;
                    Random gen = new Random();
                    
                    do{
                        p1 = this.cromossomo.get(gen.nextInt(this.cromossomo.size()));
                        p2 = this.cromossomo.get(gen.nextInt(this.cromossomo.size()));
                    }while(p1.equals(p2));
                    
                    int indexp1 = this.cromossomo.indexOf(p1);
                    int indexp2 = this.cromossomo.indexOf(p2);
                    
                    this.cromossomo.set(indexp1, p2);
                    this.cromossomo.set(indexp2, p1);
                }
            }
        }
        //System.out.println("Depois da mutaÃ§Ã£o: " + this.cromossomo);
        return this;
    }

    @Override
    public int compareTo(Individuo o) {
        if(this.notaAvaliacao > o.notaAvaliacao){
            return -1;
        }else if(this.notaAvaliacao < o.notaAvaliacao){
            return 1;
        }else{
            return 0;
        }
    }

    public double getNotaAvaliacao() {
        return notaAvaliacao;
    }

    public void setNotaAvaliacao(double notaAvaliacao) {
        this.notaAvaliacao = notaAvaliacao;
    }

    public int getGeracao() {
        return geracao;
    }

    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    public ArrayList<Ponto> getCromossomo() {
        return cromossomo;
    }

    public void setCromossomo(ArrayList<Ponto> cromossomo) {
        this.cromossomo = cromossomo;
    }
    
}
