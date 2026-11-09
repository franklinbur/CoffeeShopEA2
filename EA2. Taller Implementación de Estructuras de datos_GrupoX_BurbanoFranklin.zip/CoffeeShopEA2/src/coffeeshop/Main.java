package coffeeshop;

import coffeeshop.view.MenuPrincipal;

/**
 * Punto de entrada principal para la aplicación CoffeeShopEA2.
 * 
 * Proyecto: CoffeeShopEA2 - Sistema de gestión de pedidos de una cafetería
 * Asignatura: Estructuras de Datos
 * Programa: Tecnología en Desarrollo de Software
 * Institución: Institución Universitaria Digital de Antioquia (IU Digital)
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando CoffeeShopEA2...");
        MenuPrincipal menu = new MenuPrincipal();
        menu.iniciar();
    }
}

