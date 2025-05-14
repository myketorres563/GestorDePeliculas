package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.controler.FormularioPeliculasControler;

import java.util.Scanner;

public class MainView {
    private final Scanner scanner;

    public MainView() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú principal y gestiona la selección del usuario
     */
    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n=== GESTOR PELÍCULAS - MENÚ PRINCIPAL ===");
            System.out.println("1. Gestionar Películas");
            System.out.println("2. Gestionar Directores");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1 -> mostrarMenuPeliculas();
                case 2 -> mostrarMenuDirectores();
                case 0 -> System.out.println("¡Hasta pronto!");
                default -> System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);
    }

    /**
     * Muestra el menú de gestión de películas
     */
    private void mostrarMenuPeliculas() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE PELÍCULAS ===");
            System.out.println("1. Agregar película");
            System.out.println("2. Buscar película");
            System.out.println("3. Listar todas las películas");
            System.out.println("4. Modificar película");
            System.out.println("5. Eliminar película");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1 -> new FormularioPeliculasControler().crearNuevaPelicula();
                case 2 -> buscarPelicula();
                case 3 -> listarPeliculas();
                case 4 -> modificarPelicula();
                case 5 -> eliminarPelicula();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);
    }

    /**
     * Muestra el menú de gestión de directores
     */
    private void mostrarMenuDirectores() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE DIRECTORES ===");
            System.out.println("1. Agregar director");
            System.out.println("2. Buscar director");
            System.out.println("3. Listar todos los directores");
            System.out.println("4. Modificar director");
            System.out.println("5. Eliminar director");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1 -> agregarDirector();
                case 2 -> buscarDirector();
                case 3 -> listarDirectores();
                case 4 -> modificarDirector();
                case 5 -> eliminarDirector();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);
    }
    

    // Métodos para la gestión de películas
    private void agregarPelicula() {
        System.out.println("Función agregar película - Por implementar");
    }

    private void buscarPelicula() {
        System.out.println("Función buscar película - Por implementar");
    }

    private void listarPeliculas() {
        System.out.println("Función listar películas - Por implementar");
    }

    private void modificarPelicula() {
        System.out.println("Función modificar película - Por implementar");
    }

    private void eliminarPelicula() {
        System.out.println("Función eliminar película - Por implementar");
    }

    // Métodos para la gestión de directores
    private void agregarDirector() {
        System.out.println("Función agregar director - Por implementar");
    }

    private void buscarDirector() {
        System.out.println("Función buscar director - Por implementar");
    }

    private void listarDirectores() {
        System.out.println("Función listar directores - Por implementar");
    }

    private void modificarDirector() {
        System.out.println("Función modificar director - Por implementar");
    }

    private void eliminarDirector() {
        System.out.println("Función eliminar director - Por implementar");
    }

    // Métodos para la gestión de actores
    private void agregarActor() {
        System.out.println("Función agregar actor - Por implementar");
    }

    private void buscarActor() {
        System.out.println("Función buscar actor - Por implementar");
    }

    private void listarActores() {
        System.out.println("Función listar actores - Por implementar");
    }

    private void modificarActor() {
        System.out.println("Función modificar actor - Por implementar");
    }

    private void eliminarActor() {
        System.out.println("Función eliminar actor - Por implementar");
    }

    // Métodos para la gestión de géneros
    private void agregarGenero() {
        System.out.println("Función agregar género - Por implementar");
    }

    private void buscarGenero() {
        System.out.println("Función buscar género - Por implementar");
    }

    private void listarGeneros() {
        System.out.println("Función listar géneros - Por implementar");
    }

    private void modificarGenero() {
        System.out.println("Función modificar género - Por implementar");
    }

    private void eliminarGenero() {
        System.out.println("Función eliminar género - Por implementar");
    }
}