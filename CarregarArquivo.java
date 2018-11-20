/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronegeneticoraio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustavo
 */
public class CarregarArquivo {
 
    ArrayList<Ponto> pontos = new ArrayList();
    
    public CarregarArquivo(String arquivo){
        
        String linha;
        String[] partes;
        
        try {
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            
            br.readLine();
            int cont = 0;
            while((linha = br.readLine()) != null){
                partes = linha.split(",");
                double px = Double.parseDouble(partes[0]);
                double py = Double.parseDouble(partes[1]);
                double raio = Double.parseDouble(partes[2]);
                pontos.add(new Ponto(px,py, raio));
                cont++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CarregarArquivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CarregarArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Globals.pontosIniciais.addAll(this.pontos);
        
    }
    
    public ArrayList<Ponto> getPontos(){
        return this.pontos;
    }
    
}
