package entities

import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val clases = Clase.cargarClases()

    while (true) {
        mostrarMenu()
        when (scanner.nextInt()) {
            1 -> crearClase(scanner, clases)
            2 -> crearEstudiante(scanner, clases)
            3 -> Clase.leerClases(clases)
            4 -> leerEstudiantesDeClase(scanner, clases)
            5 -> leerTodosLosEstudiantes()
            6 -> actualizarClase(scanner, clases)
            7 -> actualizarEstudiante(scanner, clases)
            8 -> eliminarClase(scanner, clases)
            9 -> eliminarEstudiante(scanner, clases)
            10 -> {
                println("El programa ha finalizado exitosamente. ¡Gracias por usar nuestra aplicación!")
                break
            }

            else -> println("Lo siento, la opción que ingresaste no es válida. Por favor, intenta de nuevo con una opción válida.")
        }
    }
}

fun mostrarMenu() {
    println("Bienvenido al Menú de Opciones CRUD para Clases y Estudiantes")
    println("1. Crear una nueva Clase")
    println("2. Registrar un nuevo Estudiante")
    println("3. Visualizar todas las Clases existentes")
    println("4. Visualizar los Estudiantes de una Clase específica")
    println("5. Visualizar todos los Estudiantes registrados")
    println("6. Actualizar la información de una Clase")
    println("7. Actualizar la información de un Estudiante")
    println("8. Eliminar una Clase existente")
    println("9. Eliminar un Estudiante registrado")
    println("10. Finalizar y salir del Programa")
    print("Por favor, seleccione una opción del menú: ")
}

fun crearClase(scanner: Scanner, clases: MutableList<Clase>) {
    println("Ingrese el ID de la Clase:")
    val id = scanner.nextInt()
    // Comprobar si el ID ya existe
    if (clases.any { it.id == id }) {
        println("Error: Ya existe una clase con el ID proporcionado.")
        return
    }
    scanner.nextLine()
    println("Ingrese el Nombre del grupo de la Clase (Clase de la mañana/Clase de la tarde):")
    val nombre = scanner.nextLine()
    println("Ingrese la Descripción de la Clase:")
    val descripcion = scanner.nextLine()
    val fechaInicio = LocalDate.now()
    val fechaFin = LocalDate.now().plusMonths(10) // Ejemplo de fecha de fin
    val nuevaClase = Clase(id, nombre, descripcion, fechaInicio, fechaFin, mutableListOf())
    Clase.crearClase(clases, nuevaClase)
}

fun crearEstudiante(scanner: Scanner, clases: MutableList<Clase>) {
    println("Ingrese el ID de la Clase a la que pertenece el Estudiante:")
    val claseId = scanner.nextInt()
    val clase = clases.find { it.id == claseId }
    if (clase != null) {
        println("Ingrese el ID del Estudiante:")
        val id = scanner.nextInt()
        // Comprobar si el ID ya existe
        if (clase.estudiantes.any { it.id == id }) {
            println("Error: Ya existe un estudiante con el ID proporcionado.")
            return
        }
        scanner.nextLine() // Consume newline
        println("Ingrese el Nombre del Estudiante:")
        val nombre = scanner.nextLine()
        var fechaNacimiento: LocalDate? = null
        while (fechaNacimiento == null) {
            println("Ingrese la Fecha de Nacimiento del Estudiante (formato AAAA-MM-DD):")
            try {
                fechaNacimiento =
                    LocalDate.parse(scanner.nextLine()) // Convierte la cadena ingresada en un objeto LocalDate
            } catch (e: DateTimeParseException) {
                println("Fecha no válida. Por favor, intente de nuevo.")
            }
        }
        println("Ingrese el Grado del Estudiante:")
        val grado = scanner.nextInt()
        println("Ingrese el Promedio del Estudiante:")
        val promedio = scanner.nextDouble()
        val nuevoEstudiante = Estudiante(id, nombre, fechaNacimiento, grado, promedio)
        Estudiante.crearEstudiante(clase.estudiantes, nuevoEstudiante)
        Clase.guardarClases(clases) // Guardar cambios
    } else {
        println("Clase no encontrada.")
    }
}

fun leerEstudiantesDeClase(scanner: Scanner, clases: MutableList<Clase>) {
    println("Ingrese el ID de la Clase cuyos estudiantes desea ver:")
    val claseId = scanner.nextInt()
    val clase = clases.find { it.id == claseId }
    if (clase != null) {
        Clase.leerEstudiantes(clase.estudiantes)
    } else {
        println("Clase no encontrada.")
    }
}

fun leerTodosLosEstudiantes() {
    val estudiantes = Estudiante.cargarEstudiantes()
    Estudiante.leerEstudiantes(estudiantes)
}

fun actualizarClase(scanner: Scanner, clases: MutableList<Clase>) {
    println("Ingrese el ID de la Clase a actualizar:")
    val id = scanner.nextInt()
    // Comprobar si el ID ya existe
    if (clases.none { it.id == id }) {
        println("Error: No existe una clase con el ID proporcionado.")
        return
    }
    scanner.nextLine() // Consume newline
    println("Ingrese el Nuevo Nombre del grupo de la Clase (Clase de la mañana/Clase de la tarde):")
    val nombre = scanner.nextLine()
    println("Ingrese la Nueva Descripción de la Clase:")
    val descripcion = scanner.nextLine()
    val fechaInicio = LocalDate.now()
    val fechaFin = LocalDate.now().plusMonths(6) // Ejemplo de fecha de fin
    val clase = clases.find { it.id == id }
    if (clase != null) {
        if (nombre.isNotBlank() && descripcion.isNotBlank()) {
            clase.nombreGrupo = nombre
            clase.descripcion = descripcion
            clase.fechaInicio = fechaInicio
            clase.fechaFin = fechaFin
            Clase.guardarClases(clases)
        } else {
            println("Nombre de grupo y descripción no pueden estar vacíos.")
        }
    }
}

fun actualizarEstudiante(scanner: Scanner, clases: MutableList<Clase>) {
    println("Ingrese el ID de la Clase a la que pertenece el Estudiante:")
    val claseId = scanner.nextInt()
    val clase = clases.find { it.id == claseId }
    if (clase != null) {
        println("Ingrese el ID del Estudiante a actualizar:")
        val id = scanner.nextInt()
        val estudiante = clase.estudiantes.find { it.id == id }
        if (estudiante != null) {
            scanner.nextLine()
            println("Ingrese el Nuevo Nombre del Estudiante:")
            val nombre = scanner.nextLine()
            var fechaNacimiento: LocalDate? = null
            while (fechaNacimiento == null) {
                println("Ingrese la Nueva Fecha de Nacimiento del Estudiante (formato AAAA-MM-DD):")
                try {
                    fechaNacimiento =
                        LocalDate.parse(scanner.nextLine()) // Convierte la cadena ingresada en un objeto LocalDate
                } catch (e: DateTimeParseException) {
                    println("Fecha no válida. Por favor, intente de nuevo.")
                }
            }
            println("Ingrese el Nuevo Grado del Estudiante:")
            val grado = scanner.nextInt()
            println("Ingrese el Nuevo Promedio del Estudiante:")
            val promedio = scanner.nextDouble()
            if (nombre.isNotBlank() && grado > 0 && promedio >= 0) {
                estudiante.nombre = nombre
                estudiante.fechaNacimiento = fechaNacimiento
                estudiante.grado = grado
                estudiante.promedio = promedio
                Clase.guardarClases(clases) // Guardar cambios
            } else {
                println("Nombre no puede estar vacío, grado debe ser mayor que 0 y promedio no puede ser negativo.")
            }
        } else {
            println("Estudiante no encontrado.")
        }
    } else {
        println("Clase no encontrada.")
    }
}

fun eliminarClase(scanner: Scanner, clases: MutableList<Clase>) {
    println("Ingrese el ID de la Clase a eliminar:")
    val id = scanner.nextInt()
    val clase = clases.find { it.id == id }
    if (clase != null) {
        clase.estudiantes.forEach { estudiante ->
            Estudiante.eliminarEstudiantePorId(estudiante.id)
        }
        Clase.eliminarClase(clases, id)
    } else {
        println("Clase no encontrada.")
    }
}

fun eliminarEstudiante(scanner: Scanner, clases: MutableList<Clase>) {
    println("Ingrese el ID de la Clase a la que pertenece el Estudiante:")
    val claseId = scanner.nextInt()
    val clase = clases.find { it.id == claseId }
    if (clase != null) {
        println("Ingrese el ID del Estudiante a eliminar:")
        val id = scanner.nextInt()
        Estudiante.eliminarEstudiante(clase.estudiantes, id)
        Clase.guardarClases(clases) // Guardar cambios
    } else {
        println("Clase no encontrada.")
    }
}