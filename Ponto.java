/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronegeneticoraio;

/**
 *
 * @author Gustavo
 */
public class Ponto {
    
    public double px;
    public double py;
    public boolean visitado = false;
    public boolean isTorre = false;
    public double raio;
    public Ponto pai;
    public int num;
    
    public Ponto(double px, double py, double raio){
        this.px = px;
        this.py = py;
        this.raio = raio;
        this.isTorre = true;
        this.pai = this;
    }
    
    public Ponto(double px, double py, Ponto pai){
        this.px = px;
        this.py = py;
        this.pai = pai;
    }
    
    public double getRaio(){
        if(isTorre){
            return raio;
        }else{
            return 0;
        }
    }
    
    public Ponto getPai(){
        return this.pai;
    }
    
    public void imprime(){
        System.out.println("(" + px + "," + py + ")");
    }
    
    public boolean equals(Ponto p2){      
        return p2.px == px && p2.py == py;
    }
    
    public void visitou(){
        visitado = true;
    }
    
    public void naoVisitou(){
        visitado = false;
    }
    
}
