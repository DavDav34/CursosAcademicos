// modifi
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