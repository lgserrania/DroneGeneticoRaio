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
public class DroneGeneticoRaio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CarregarArquivo ca = new CarregarArquivo("instancia_drone_1000.csv");
        Globals.maxX();
        Globals.maxY();
        AlgoritmoGenetico ag = new AlgoritmoGenetico();
        Individuo indi = ag.resolver();
        
        System.out.println(indi.distancia());
        Exibir e = new Exibir(indi.getCromossomo());
    }
    
}
