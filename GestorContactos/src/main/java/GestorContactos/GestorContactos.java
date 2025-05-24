/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

 package GestorContactos;

 /**
  *
  * @author User
  */
 import java.io.*;
 import java.util.*;
 
 public class GestorContactos {
 
     static class Contacto {
         private String nombre;
         private String telefono;
         private String correo;
 
         public Contacto(String nombre, String telefono, String correo) {
             this.nombre = nombre;
             this.telefono = telefono;
             this.correo = correo;
         }
 
         public String getNombre() { return nombre; }
         public String getTelefono() { return telefono; }
         public String getCorreo() { return correo; }
 
         @Override
         public String toString() {
             return "Nombre: " + nombre + ", Telefono: " + telefono + ", Correo: " + correo;
         }
     }
 
     // Lista de contactos
     static List<Contacto> contactos = new ArrayList<>();
     static final String ARCHIVO = "contactos.txt";
 
     public static void main(String[] args) {
         cargarDesdeArchivo();
         Scanner sc = new Scanner(System.in);
         int opcion;
 
         do {
             System.out.println("\n--- MENU ---");
             System.out.println("1. Agregar contacto");
             System.out.println("2. Mostrar contactos");
             System.out.println("3. Buscar contacto");
             System.out.println("4. Eliminar contacto");
             System.out.println("5. Salir");
             System.out.print("Seleccione una opcion: ");
             opcion = sc.nextInt();
             sc.nextLine(); 
 
             switch (opcion) {
                 case 1 -> agregarContacto(sc);
                 case 2 -> mostrarContactos();
                 case 3 -> buscarContacto(sc);
                 case 4 -> eliminarContacto(sc);
                 case 5 -> System.out.println("Saliendo");
                 default -> System.out.println("Opción invalida.");
             }
         } while (opcion != 5);
     }
 
     static void agregarContacto(Scanner sc) {
         System.out.print("Nombre: ");
         String nombre = sc.nextLine();
         System.out.print("Telefono: ");
         String telefono = sc.nextLine();
         System.out.print("Correo: ");
         String correo = sc.nextLine();
 
         boolean existe = contactos.stream().anyMatch(c ->
             c.getNombre().equalsIgnoreCase(nombre) || c.getTelefono().equals(telefono) || c.getCorreo().equalsIgnoreCase(correo)
         );
 
         if (existe) {
             System.out.println("Ya existe un contacto con ese nombre, telefono o correo.");
             return;
         }
 
         Contacto nuevo = new Contacto(nombre, telefono, correo);
         contactos.add(nuevo);
         guardarEnArchivo();
         System.out.println("Contacto agregado.");
     }
 
     static void mostrarContactos() {
         if (contactos.isEmpty()) {
             System.out.println("Lista vacia.");
         } else {
             System.out.println("\n Contactos:");
             for (Contacto c : contactos) {
                 System.out.println(c);
             }
         }
     }
 
     static void buscarContacto(Scanner sc) {
         System.out.print("Ingrese el nombre a buscar: ");
         String nombre = sc.nextLine();
         boolean encontrado = false;
 
         for (Contacto c : contactos) {
             if (c.getNombre().equalsIgnoreCase(nombre)) {
                 System.out.println("Encontrado: " + c);
                 encontrado = true;
             }
         }
 
         if (!encontrado) {
             System.out.println("No se encontro ningun contacto con ese nombre.");
         }
     }
 
     static void eliminarContacto(Scanner sc) {
         System.out.print("Ingrese el nombre a eliminar: ");
         String nombre = sc.nextLine();
 
         boolean eliminado = contactos.removeIf(c -> c.getNombre().equalsIgnoreCase(nombre));
 
         if (eliminado) {
             guardarEnArchivo();
             System.out.println("Contacto eliminado.");
         } else {
             System.out.println("No se encontró el contacto.");
         }
     }
 
     static void guardarEnArchivo() {
         try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
             for (Contacto c : contactos) {
                 pw.println(c.getNombre() + ";" + c.getTelefono() + ";" + c.getCorreo());
             }
         } catch (IOException e) {
             System.out.println("Error al guardar el archivo.");
         }
     }
 
     static void cargarDesdeArchivo() {
         File archivo = new File(ARCHIVO);
         if (!archivo.exists()) return;
 
         try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
             String linea;
             while ((linea = br.readLine()) != null) {
                 String[] partes = linea.split(";");
                 if (partes.length == 3) {
                     contactos.add(new Contacto(partes[0], partes[1], partes[2]));
                 }
             }
         } catch (IOException e) {
             System.out.println("Error al leer el archivo.");
         }
     }
 }