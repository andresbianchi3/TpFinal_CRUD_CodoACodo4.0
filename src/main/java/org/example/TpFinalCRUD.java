package org.example;

import java.sql.*;
import java.util.Scanner;

public class TpFinalCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/dbproductos";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establecer la conexión con la base de datos
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Crear una nueva tabla 'productos' si no existe
            statement = conn.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS productos (id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(50), precio DOUBLE)";
            statement.executeUpdate(createTableQuery);

            // Menú de opciones
            Scanner scanner = new Scanner(System.in);

            int opcion;
            do {
                System.out.println("CRUD - Seleccione una opción:");
                System.out.println("1. Crear un nuevo producto");
                System.out.println("2. Obtener todos los productos");
                System.out.println("3. Actualizar un producto");
                System.out.println("4. Eliminar un producto");
                System.out.println("0. Salir");

                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        // Lógica para crear un nuevo producto
                        scanner.nextLine(); // Limpiar el búfer del escáner
                        System.out.println("Ingrese el nombre del producto:");
                        String nombre = scanner.nextLine();
                        System.out.println("Ingrese el precio del producto:");
                        double precio = scanner.nextDouble();

                        String insertQuery = "INSERT INTO productos (nombre, precio) VALUES (?, ?)";
                        PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
                        insertStatement.setString(1, nombre);
                        insertStatement.setDouble(2, precio);
                        insertStatement.executeUpdate();

                        System.out.println("Producto creado exitosamente");
                        break;

                    case 2:
                        // Lógica para obtener todos los productos
                        String selectQuery = "SELECT * FROM productos";
                        resultSet = statement.executeQuery(selectQuery);

                        System.out.println("Lista de productos:");
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            nombre = resultSet.getString("nombre");
                            precio = resultSet.getDouble("precio");
                            System.out.println("ID: " + id + ", Nombre: " + nombre + ", Precio: $" + precio);
                        }
                        break;

                    case 3:
                        // Lógica para actualizar un producto
                        System.out.println("Ingrese el ID del producto a actualizar:");
                        int productoId = scanner.nextInt();
                        scanner.nextLine(); // Limpiar el búfer del escáner
                        System.out.println("Ingrese el nuevo nombre del producto:");
                        String nuevoNombre = scanner.nextLine();
                        System.out.println("Ingrese el nuevo precio del producto:");
                        double nuevoPrecio = scanner.nextDouble();

                        String updateQuery = "UPDATE productos SET nombre = ?, precio = ? WHERE id = ?";
                        PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
                        updateStatement.setString(1, nuevoNombre);
                        updateStatement.setDouble(2, nuevoPrecio);
                        updateStatement.setInt(3, productoId);
                        updateStatement.executeUpdate();

                        System.out.println("Producto actualizado exitosamente");
                        break;

                    case 4:
                        // Lógica para eliminar un producto
                        System.out.println("Ingrese el ID del producto a eliminar:");
                        productoId = scanner.nextInt();

                        String deleteQuery = "DELETE FROM productos WHERE id = ?";
                        PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);
                        deleteStatement.setInt(1, productoId);
                        deleteStatement.executeUpdate();

                        System.out.println("Producto eliminado exitosamente");
                        break;

                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;

                    default:
                        System.out.println("Opción inválida");
                        break;
                }

                System.out.println(); // Espacio en blanco para separar las iteraciones

            } while (opcion != 0);

            scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar las conexiones y recursos
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}