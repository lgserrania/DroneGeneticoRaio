/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronegeneticoraio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Gustavo
 */
public class AlgoritmoGenetico {
    
    private int tamanhoPopulacao = 0;
    private ArrayList<Individuo> populacao = new ArrayList();
    private ArrayList<Ponto> solucaoInicial = new ArrayList();
    
    private Individuo melhorSolucao;
    
    public AlgoritmoGenetico(){
        
    }
    
    public void addIndividuo(Individuo individuo){
        this.populacao.add(individuo);
        tamanhoPopulacao++;
    }
    
    public void ordenaPopulacao(){
        Collections.sort(this.populacao);
    }
    
    public void melhorIndividuo(Individuo individuo){
        if(individuo.getNotaAvaliacao() > this.melhorSolucao.getNotaAvaliacao()){
            this.melhorSolucao.setCromossomo(individuo.getCromossomo());
            this.melhorSolucao.avaliacao();
            this.melhorSolucao.distancia();
        }
    }
    
    public double somaAvaliacao(){
        double soma = 0.0;
        for(Individuo individuo : this.populacao){
            soma += individuo.getNotaAvaliacao();
        }
        return soma;
    }
    
    public int selecionaPai(Double somaAvaliacao){
        int pai = -1;
        double valorSorteado = Math.random() * somaAvaliacao;
        double soma = 0.0;
        int i = 0;
        while(i < this.populacao.size() && soma < valorSorteado){
            soma += this.populacao.get(i).getNotaAvaliacao();
            pai++;
            i++;
        }
        return pai;
    }
    
    public void crossover(Individuo i1, Individuo i2){
        int corte = (int) Math.round(Math.random() * i1.getCromossomo().size());
        
        ArrayList<Ponto> filho1 = new ArrayList();
        filho1.addAll(i2.getCromossomo().subList(0, corte));
        filho1.addAll(i1.getCromossomo().subList(corte, i1.getCromossomo().size()));
        
        ArrayList<Ponto> filho2 = new ArrayList();
        filho2.addAll(i1.getCromossomo().subList(0, corte));
        filho2.addAll(i2.getCromossomo().subList(corte, i2.getCromossomo().size()));
        
        Individuo ni1 = new Individuo(filho1);
        ni1.mutacao(Globals.taxaMutacao);
        ni1.avaliacao();
        ni1.distancia();
        Individuo ni2 = new Individuo(filho2);
        ni2.mutacao(Globals.taxaMutacao);
        ni2.avaliacao();
        ni2.distancia();
        this.populacao.add(ni1);
        this.populacao.add(ni2);
        
    }
    
    public Individuo resolver(){
        Random gen = new Random();
        this.melhoresIniciais();
        
        for(int i = 0; i < Globals.tamPopulacao; i++){
            System.out.println("Gerando população " + i);
            ArrayList<Ponto> pontos = new ArrayList();
//            ArrayList<Ponto> pontosAdicionados = new ArrayList();
//            for(int j = 0; j < Globals.pontosIniciais.size(); j++){
//                
//                Ponto p;
//                
//                do{
//                    p = Globals.pontosIniciais.get(gen.nextInt(Globals.pontosIniciais.size()));
//                }while(pontosAdicionados.contains(p));
//                
//                pontosAdicionados.add(p);
//                
//                double x = 0;
//                double y = 0;
//                Ponto np = null;
//
//                do{
//                    x = gen.nextDouble() * (Globals.maiorx);
//                    y = gen.nextDouble() * (Globals.maiory); 
//                    np = new Ponto(x, y, p);
//                }while(Globals.calcDistancia(p, np) > p.getRaio());
//
//                pontos.add(np);
//            }
            

            Individuo indi = new Individuo(this.HillClimb((int) (Math.random() * 10000)));
            indi.avaliacao();
            indi.distancia();
            this.populacao.add(indi);
            
        }
        
        this.ordenaPopulacao();
        this.melhorSolucao = this.populacao.get(0);
        
        for (int geracao = 0; geracao < Globals.numGeracoes; geracao++) {
            System.out.println("******************* Geração " + geracao + "********************");
            double somaAvaliacao = this.somaAvaliacao();
            ArrayList<Individuo> novaPopulacao = new ArrayList();

            for(int i = 0; i < Globals.tamPopulacao / 2; i++){
                int pai1 = this.selecionaPai(somaAvaliacao);
                int pai2 = this.selecionaPai(somaAvaliacao);
                
                this.crossover(this.populacao.get(pai1), this.populacao.get(pai2));
            }
            
            for(Individuo indi : this.populacao){
                indi.avaliacao();
                indi.distancia();
            }
            
            somaAvaliacao = this.somaAvaliacao();

            for(int i = 0; i < Globals.tamPopulacao; i++){
                int escolhido = this.selecionaPai(somaAvaliacao);
                
                novaPopulacao.add(this.populacao.get(escolhido));
            }
            
            this.setPopulacao(novaPopulacao);
            
            this.ordenaPopulacao();
            this.melhorIndividuo(this.populacao.get(0));
            
            //System.out.println(this.melhorSolucao.distancia());
        }
        
        return this.melhorSolucao;
    }
    
    public void melhoresIniciais(){
        
        double menorDist = Double.MAX_VALUE;
        
        Ponto menorp1 = null;
        Ponto menorp2 = null;
        
        for(int i = 0; i < Globals.pontosIniciais.size() - 1; i++){
            for(int j = i + 1; j < Globals.pontosIniciais.size(); j++){
                Ponto p1 = Globals.pontosIniciais.get(i);
                Ponto p2 = Globals.pontosIniciais.get(j);
                double dist = Globals.calcDistancia(p1, p2);
                if(dist < menorDist){
                    menorDist = dist;
                    menorp1 = p1;
                    menorp2 = p2;
                }
            }
        }
        
        this.solucaoInicial.add(menorp1);
        this.solucaoInicial.add(menorp2);
        
        while(this.solucaoInicial.size() < Globals.pontosIniciais.size()){
            Ponto p1 = this.solucaoInicial.get(this.solucaoInicial.size() - 1);
            menorDist = Double.MAX_VALUE;
            Ponto menorPonto = null;
            for(int i = 0; i <Globals.pontosIniciais.size(); i++){
                Ponto p2 = Globals.pontosIniciais.get(i);
                if(!p2.equals(p1) && !this.solucaoInicial.contains(p2)){
                    if(Globals.calcDistancia(p1,p2) < menorDist){
                        menorDist = Globals.calcDistancia(p1,p2);
                        menorPonto = p2;
                    }
                }
            }
            this.solucaoInicial.add(menorPonto);
        }
    }
    
    public ArrayList<Ponto> HillClimb(int numInteracoes){
        
        ArrayList<Ponto> solucao = new ArrayList();
        solucao.addAll(this.solucaoInicial);
        Random gen = new Random();
        for(int i = 0; i < numInteracoes; i++){
            boolean repaint = true;
            Ponto p1 = solucao.get(gen.nextInt(solucao.size()));      

            double x = 0;
            double y = 0;
            Ponto np = null;

            do{
                x = gen.nextDouble() * (Globals.maiorx + p1.getPai().getRaio());
                y = gen.nextDouble() * (Globals.maiory + p1.getPai().getRaio()); 
                np = new Ponto(x, y, p1.getPai());
            }while(!Globals.verificaPonto(np));
            
            double antiDist = this.distanciaSolucao(solucao);
            solucao.set(solucao.indexOf(p1), np);
            
            
            if(this.distanciaSolucao(solucao) > antiDist || !this.verificaVisitados(solucao)){
                solucao.set(solucao.indexOf(np), p1);
            }
            
        }
        return solucao;
    }
    
    public double distanciaSolucao(ArrayList<Ponto> solucao){
        double total = 0;
        double dist = 0;
        
        for(int i = 0; i < solucao.size() - 1; i++){
            Ponto p1 = solucao.get(i);
            Ponto p2 = solucao.get(i + 1);
            
            dist = Math.sqrt(Math.pow((p2.px - p1.px), 2) + Math.pow((p2.py - p1.py), 2));
            total += dist;
        }
        
        Ponto p1 = solucao.get(solucao.size() - 1);
        Ponto p2 = solucao.get(0);
        dist = Math.sqrt(Math.pow((p2.px - p1.px), 2) + Math.pow((p2.py - p1.py), 2));
        total += dist;
        
        return total;
    }
    
    public boolean verificaVisitados(ArrayList<Ponto> solucao){
        for(Ponto p1 : solucao){
            for(Ponto p2 : Globals.pontosIniciais){
                if(Globals.calcDistancia(p1,p2) > p2.getRaio()){
                    return false;
                }
            }
            
        }
        
        return true;
    }
    
    public ArrayList<Individuo> getPopulacao() {
        return populacao;
    }

    public void setPopulacao(ArrayList<Individuo> populacao) {
        this.populacao = populacao;
    }
}
