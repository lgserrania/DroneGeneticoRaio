/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronegeneticoraio;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Gustavo
 */
public class Exibir extends JFrame{
    
    private ArrayList<Ponto> solucao;
    //private double raio = Globals.raio;
    private double auxDraw = 0.06;
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.blue);
        //Desenha os círculos e os pontos no centro dos mesmos
        Globals.pontosIniciais.forEach((p) -> {
            double x = p.px-(p.getRaio());
            double y = p.py-(p.getRaio());
            Shape circulo = new Ellipse2D.Double(x * auxDraw, y * auxDraw, p.getRaio() * 2 * auxDraw, p.getRaio() * 2 * auxDraw);
            g2d.draw(circulo);
            Shape ponto = new Line2D.Double(p.px * auxDraw, p.py * auxDraw, p.px * auxDraw, p.py * auxDraw);
            g2d.draw(ponto);
        });
        //Traça a linha do caminho 
        for(int i = 0; i < solucao.size() - 1; i++){
            g2d.setColor(Color.red);
            Ponto p1 = solucao.get(i);
            Ponto p2 = solucao.get(i + 1);
            Shape linha = new Line2D.Double(p1.px * auxDraw, p1.py * auxDraw, p2.px * auxDraw, p2.py * auxDraw);
            g2d.draw(linha);
        }
        Ponto p1 = solucao.get(solucao.size() - 1);
        Ponto p2 = solucao.get(0);
        Shape linha = new Line2D.Double(p1.px * auxDraw, p1.py * auxDraw, p2.px * auxDraw, p2.py * auxDraw);
        g2d.draw(linha);
    }
    
    public Exibir(ArrayList<Ponto> solucao){
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setBackground(Color.WHITE);
        this.solucao = solucao;
        this.repaint();
        this.setVisible(true);
    }
    
}
