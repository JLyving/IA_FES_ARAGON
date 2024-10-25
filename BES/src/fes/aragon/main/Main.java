package fes.aragon.main;

import java.util.*;


class Nodo {
    String nombre;
    int heuristica;
    List<Nodo> vecinos;

    public Nodo(String nombre, int heuristica) {
        this.nombre = nombre;
        this.heuristica = heuristica;
        this.vecinos = new ArrayList<>();
    }

    public void agregarVecino(Nodo nodo) {
        vecinos.add(nodo);
    }

    @Override
    public String toString() {
        return nombre + " (Heuristica: " + heuristica + ")";
    }
}

class BusquedaEscaladaSimple {
    public List<Nodo> busqueda(Nodo inicio, Nodo objetivo) {
        List<Nodo> ruta = new ArrayList<>(); // Para almacenar la ruta
        Nodo actual = inicio;

        while (true) {
            System.out.println("Nodo actual: " + actual);
            ruta.add(actual); // Añadir el nodo actual a la ruta

            if (actual == objetivo) {
                System.out.println("Objetivo encontrado!");
                return ruta;
            }

            // Buscar el vecino con la menor heurística
            Nodo mejorVecino = null;
            for (Nodo vecino : actual.vecinos) {
                if (mejorVecino == null || vecino.heuristica < mejorVecino.heuristica) {
                    mejorVecino = vecino;
                }
            }

            // Si no hay mejor vecino, hemos llegado a un máximo local
            if (mejorVecino == null || mejorVecino.heuristica >= actual.heuristica) {
                System.out.println("Se alcanzo un maximo local en: " + actual);
                return null;
            }

            // Mover al mejor vecino
            actual = mejorVecino;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Crear nodos con sus heurísticas
        Nodo nodoA = new Nodo("A", 10);
        Nodo nodoB = new Nodo("B", 8);
        Nodo nodoC = new Nodo("C", 5);
        Nodo nodoD = new Nodo("D", 7);
        Nodo nodoE = new Nodo("E", 3);
        Nodo objetivo = new Nodo("Meta", 0);

        // Crear conexiones entre los nodos (grafo no dirigido)
        nodoA.agregarVecino(nodoB);
        nodoA.agregarVecino(nodoC);
        nodoB.agregarVecino(nodoD);
        nodoC.agregarVecino(nodoE);
        nodoD.agregarVecino(objetivo);
        nodoE.agregarVecino(objetivo);

        // Ejecutar búsqueda por escalada simple
        BusquedaEscaladaSimple escalada = new BusquedaEscaladaSimple();
        List<Nodo> ruta = escalada.busqueda(nodoA, objetivo);

        // Imprimir la ruta, si se encontró el objetivo
        if (ruta != null) {
            System.out.println("Ruta encontrada:");
            for (Nodo nodo : ruta) {
                System.out.print(nodo + " -> ");
            }
            System.out.println("Meta");
        } else {
            System.out.println("No se encontro una ruta al objetivo.");
        }
    }
}


