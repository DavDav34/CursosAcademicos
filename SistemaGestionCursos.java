
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Excepciones personalizadas
class PagoInvalidoException extends Exception {
    public PagoInvalidoException(String message) {
        super(message);
    }
}

class PromedioInvalidoException extends Exception {
    public PromedioInvalidoException(String message) {
        super(message);
    }
}

// Interfaces
interface Pagable {
    double calcularPago() throws PagoInvalidoException;
}

interface Calificable {
    double calcularPromedio() throws PromedioInvalidoException;
}

// Clase abstracta Persona
abstract class Persona {
    protected String nombre;
    protected String id;

    public Persona(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
    }

    public abstract void mostrarInformacion();
}

// Clases de profesores
class ProfesorTiempoCompleto extends Persona implements Pagable {
    private double salarioMensual;

    public ProfesorTiempoCompleto(String nombre, String id, double salarioMensual) {
        super(nombre, id);
        this.salarioMensual = salarioMensual;
    }

    @Override
    public double calcularPago() throws PagoInvalidoException {
        if (salarioMensual <= 0) {
            throw new PagoInvalidoException("El salario mensual debe ser mayor que cero");
        }
        return salarioMensual;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Profesor tiempo completo: " + nombre + ", ID: " + id + ", Salario: " + salarioMensual);
    }
}

class ProfesorPorHoras extends Persona implements Pagable {
    private int horasTrabajadas;
    private double pagoPorHora;

    public ProfesorPorHoras(String nombre, String id, int horasTrabajadas, double pagoPorHora) {
        super(nombre, id);
        this.horasTrabajadas = horasTrabajadas;
        this.pagoPorHora = pagoPorHora;
    }

    @Override
    public double calcularPago() throws PagoInvalidoException {
        double pago = horasTrabajadas * pagoPorHora;
        if (pago <= 0) {
            throw new PagoInvalidoException("El pago debe ser mayor que cero");
        }
        return pago;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Profesor por horas: " + nombre + ", ID: " + id + 
                           ", Horas: " + horasTrabajadas + ", Pago por hora: " + pagoPorHora);
    }
}

// Clase Estudiante
class Estudiante extends Persona implements Calificable {
    private List<Double> calificaciones;

    public Estudiante(String nombre, String id) {
        super(nombre, id);
        this.calificaciones = new ArrayList<>();
    }

    public void agregarCalificacion(double calificacion) {
        calificaciones.add(calificacion);
    }

    @Override
    public double calcularPromedio() throws PromedioInvalidoException {
        if (calificaciones.isEmpty()) {
            throw new PromedioInvalidoException("El estudiante no tiene calificaciones registradas");
        }
        double suma = 0;
        for (double calificacion : calificaciones) {
            suma += calificacion;
        }
        return suma / calificaciones.size();
    }

    @Override
    public void mostrarInformacion() {
        System.out.print("Estudiante: " + nombre + ", ID: " + id + ", Calificaciones: ");
        for (double calificacion : calificaciones) {
            System.out.print(calificacion + " ");
        }
        System.out.println();
    }
}

// Clase Curso
class Curso {
    private String nombreCurso;
    private List<Estudiante> estudiantes;
    private Persona profesor;

    public Curso(String nombreCurso, Persona profesor) {
        this.nombreCurso = nombreCurso;
        this.profesor = profesor;
        this.estudiantes = new ArrayList<>();
    }

    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void mostrarInformacion() {
        System.out.println("Curso: " + nombreCurso);
        System.out.println("Profesor asignado:");
        profesor.mostrarInformacion();
        System.out.println("Estudiantes inscritos:");
        for (Estudiante estudiante : estudiantes) {
            estudiante.mostrarInformacion();
        }
    }
}

// Clase principal con menú
public class SistemaGestionCursos {
    private static List<Persona> personas = new ArrayList<>();
    private static List<Curso> cursos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Sistema de Gestión de Cursos ===");
            System.out.println("1. Registrar profesor");
            System.out.println("2. Registrar estudiante");
            System.out.println("3. Crear curso");
            System.out.println("4. Agregar estudiante a curso");
            System.out.println("5. Agregar calificación a estudiante");
            System.out.println("6. Calcular pago de profesor");
            System.out.println("7. Calcular promedio de estudiante");
            System.out.println("8. Mostrar todas las personas");
            System.out.println("9. Mostrar todos los cursos");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    registrarProfesor();
                    break;
                case 2:
                    registrarEstudiante();
                    break;
                case 3:
                    crearCurso();
                    break;
                case 4:
                    agregarEstudianteACurso();
                    break;
                case 5:
                    agregarCalificacion();
                    break;
                case 6:
                    calcularPagoProfesor();
                    break;
                case 7:
                    calcularPromedioEstudiante();
                    break;
                case 8:
                    mostrarPersonas();
                    break;
                case 9:
                    mostrarCursos();
                    break;
                case 10:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        System.out.println("Sistema cerrado. ¡Hasta luego!");
    }

    private static void registrarProfesor() {
        System.out.println("\n--- Registrar Profesor ---");
        System.out.println("1. Profesor tiempo completo");
        System.out.println("2. Profesor por horas");
        System.out.print("Seleccione el tipo: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("ID: ");
        String id = scanner.nextLine();

        if (tipo == 1) {
            System.out.print("Salario mensual: ");
            double salario = scanner.nextDouble();
            personas.add(new ProfesorTiempoCompleto(nombre, id, salario));
        } else if (tipo == 2) {
            System.out.print("Horas trabajadas: ");
            int horas = scanner.nextInt();
            System.out.print("Pago por hora: ");
            double pagoHora = scanner.nextDouble();
            personas.add(new ProfesorPorHoras(nombre, id, horas, pagoHora));
        } else {
            System.out.println("Opción no válida");
        }
        System.out.println("Profesor registrado con éxito");
    }

    private static void registrarEstudiante() {
        System.out.println("\n--- Registrar Estudiante ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("ID: ");
        String id = scanner.nextLine();
        personas.add(new Estudiante(nombre, id));
        System.out.println("Estudiante registrado con éxito");
    }

    private static void crearCurso() {
        System.out.println("\n--- Crear Curso ---");
        System.out.print("Nombre del curso: ");
        String nombreCurso = scanner.nextLine();

        // Mostrar profesores disponibles
        System.out.println("Profesores disponibles:");
        int i = 0;
        for (Persona persona : personas) {
            if (persona instanceof ProfesorTiempoCompleto || persona instanceof ProfesorPorHoras) {
                System.out.println(i + ". " + persona.nombre);
                i++;
            }
        }

        if (i == 0) {
            System.out.println("No hay profesores registrados. Registre un profesor primero.");
            return;
        }

        System.out.print("Seleccione un profesor: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        // Obtener profesor seleccionado
        Persona profesor = null;
        int contador = 0;
        for (Persona persona : personas) {
            if (persona instanceof ProfesorTiempoCompleto || persona instanceof ProfesorPorHoras) {
                if (contador == seleccion) {
                    profesor = persona;
                    break;
                }
                contador++;
            }
        }

        if (profesor != null) {
            cursos.add(new Curso(nombreCurso, profesor));
            System.out.println("Curso creado con éxito");
        } else {
            System.out.println("Selección no válida");
        }
    }

    private static void agregarEstudianteACurso() {
        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados. Cree un curso primero.");
            return;
        }

        System.out.println("\n--- Agregar Estudiante a Curso ---");
        System.out.println("Cursos disponibles:");
        for (int i = 0; i < cursos.size(); i++) {
            System.out.println(i + ". " + cursos.get(i).getNombreCurso());
        }
        System.out.print("Seleccione un curso: ");
        int cursoSeleccionado = scanner.nextInt();
        scanner.nextLine();

        // Mostrar estudiantes disponibles
        System.out.println("Estudiantes disponibles:");
        int j = 0;
        for (Persona persona : personas) {
            if (persona instanceof Estudiante) {
                System.out.println(j + ". " + persona.nombre);
                j++;
            }
        }

        if (j == 0) {
            System.out.println("No hay estudiantes registrados. Registre un estudiante primero.");
            return;
        }

        System.out.print("Seleccione un estudiante: ");
        int estudianteSeleccionado = scanner.nextInt();
        scanner.nextLine();

        // Obtener estudiante seleccionado
        Estudiante estudiante = null;
        int contador = 0;
        for (Persona persona : personas) {
            if (persona instanceof Estudiante) {
                if (contador == estudianteSeleccionado) {
                    estudiante = (Estudiante) persona;
                    break;
                }
                contador++;
            }
        }

        if (estudiante != null && cursoSeleccionado >= 0 && cursoSeleccionado < cursos.size()) {
            cursos.get(cursoSeleccionado).agregarEstudiante(estudiante);
            System.out.println("Estudiante agregado al curso con éxito");
        } else {
            System.out.println("Selección no válida");
        }
    }

    private static void agregarCalificacion() {
        System.out.println("\n--- Agregar Calificación ---");
        
        // Mostrar estudiantes
        System.out.println("Estudiantes disponibles:");
        int i = 0;
        for (Persona persona : personas) {
            if (persona instanceof Estudiante) {
                System.out.println(i + ". " + persona.nombre);
                i++;
            }
        }

        if (i == 0) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        System.out.print("Seleccione un estudiante: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        // Obtener estudiante seleccionado
        Estudiante estudiante = null;
        int contador = 0;
        for (Persona persona : personas) {
            if (persona instanceof Estudiante) {
                if (contador == seleccion) {
                    estudiante = (Estudiante) persona;
                    break;
                }
                contador++;
            }
        }

        if (estudiante != null) {
            System.out.print("Ingrese la calificación: ");
            double calificacion = scanner.nextDouble();
            scanner.nextLine();
            estudiante.agregarCalificacion(calificacion);
            System.out.println("Calificación agregada con éxito");
        } else {
            System.out.println("Selección no válida");
        }
    }

    private static void calcularPagoProfesor() {
        System.out.println("\n--- Calcular Pago de Profesor ---");
        
        // Mostrar profesores
        System.out.println("Profesores disponibles:");
        int i = 0;
        for (Persona persona : personas) {
            if (persona instanceof ProfesorTiempoCompleto || persona instanceof ProfesorPorHoras) {
                System.out.println(i + ". " + persona.nombre);
                i++;
            }
        }

        if (i == 0) {
            System.out.println("No hay profesores registrados.");
            return;
        }

        System.out.print("Seleccione un profesor: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        // Obtener profesor seleccionado
        Persona profesor = null;
        int contador = 0;
        for (Persona persona : personas) {
            if (persona instanceof ProfesorTiempoCompleto || persona instanceof ProfesorPorHoras) {
                if (contador == seleccion) {
                    profesor = persona;
                    break;
                }
                contador++;
            }
        }

        if (profesor != null && profesor instanceof Pagable) {
            try {
                double pago = ((Pagable) profesor).calcularPago();
                System.out.println("El pago para " + profesor.nombre + " es: " + pago);
            } catch (PagoInvalidoException e) {
                System.out.println("Error al calcular pago: " + e.getMessage());
            }
        } else {
            System.out.println("Selección no válida");
        }
    }

    private static void calcularPromedioEstudiante() {
        System.out.println("\n--- Calcular Promedio de Estudiante ---");
        
        // Mostrar estudiantes
        System.out.println("Estudiantes disponibles:");
        int i = 0;
        for (Persona persona : personas) {
            if (persona instanceof Estudiante) {
                System.out.println(i + ". " + persona.nombre);
                i++;
            }
        }

        if (i == 0) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        System.out.print("Seleccione un estudiante: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        // Obtener estudiante seleccionado
        Estudiante estudiante = null;
        int contador = 0;
        for (Persona persona : personas) {
            if (persona instanceof Estudiante) {
                if (contador == seleccion) {
                    estudiante = (Estudiante) persona;
                    break;
                }
                contador++;
            }
        }

        if (estudiante != null) {
            try {
                double promedio = estudiante.calcularPromedio();
                System.out.println("El promedio de " + estudiante.nombre + " es: " + promedio);
            } catch (PromedioInvalidoException e) {
                System.out.println("Error al calcular promedio: " + e.getMessage());
            }
        } else {
            System.out.println("Selección no válida");
        }
    }

    private static void mostrarPersonas() {
        System.out.println("\n--- Lista de Personas ---");
        for (Persona persona : personas) {
            persona.mostrarInformacion();
        }
    }

    private static void mostrarCursos() {
        System.out.println("\n--- Lista de Cursos ---");
        for (Curso curso : cursos) {
            curso.mostrarInformacion();
            System.out.println();
        }
    }
}
