# Clase Estudiante
class Estudiante(Persona, Calificable):
    def _init_(self, nombre: str, id: str):
        super()._init_(nombre, id)
        self.calificaciones: List[float] = []
    
    def agregar_calificacion(self, calificacion: float):
        self.calificaciones.append(calificacion)
    
    def calcular_promedio(self) -> float:
        if not self.calificaciones:
            raise PromedioInvalidoException("El estudiante no tiene calificaciones registradas")
        return sum(self.calificaciones) / len(self.calificaciones)
    
    def mostrar_informacion(self):
        print(f"Estudiante: {self.nombre}, ID: {self.id}, Calificaciones: {' '.join(map(str, self.calificaciones))}")

# Clase Curso
class Curso:
    def _init_(self, nombre_curso: str, profesor: Persona):
        self.nombre_curso = nombre_curso
        self.profesor = profesor
        self.estudiantes: List[Estudiante] = []
    
    def agregar_estudiante(self, estudiante: Estudiante):
        self.estudiantes.append(estudiante)
    
    def mostrar_informacion(self):
        print(f"Curso: {self.nombre_curso}")
        print("Profesor asignado:")
        self.profesor.mostrar_informacion()
        print("Estudiantes inscritos:")
        for estudiante in self.estudiantes:
            estudiante.mostrar_informacion()
# Clase principal con menú
class SistemaGestionCursos:
    def __init__(self):
        self.personas: List[Persona] = []
        self.cursos: List[Curso] = []
    
    def ejecutar(self):
        while True:
            print("\n=== Sistema de Gestión de Cursos ===")
            print("1. Registrar profesor")
            print("2. Registrar estudiante")
            print("3. Crear curso")
            print("4. Agregar estudiante a curso")
            print("5. Agregar calificación a estudiante")
            print("6. Calcular pago de profesor")
            print("7. Calcular promedio de estudiante")
            print("8. Mostrar todas las personas")
            print("9. Mostrar todos los cursos")
            print("10. Salir")
            
            try:
                opcion = int(input("Seleccione una opción: "))
            except ValueError:
                print("Por favor ingrese un número válido")
                continue
            
            if opcion == 1:
                self.registrar_profesor()
            elif opcion == 2:
                self.registrar_estudiante()
            elif opcion == 3:
                self.crear_curso()
            elif opcion == 4:
                self.agregar_estudiante_a_curso()
            elif opcion == 5:
                self.agregar_calificacion()
            elif opcion == 6:
                self.calcular_pago_profesor()
            elif opcion == 7:
                self.calcular_promedio_estudiante()
            elif opcion == 8:
                self.mostrar_personas()
            elif opcion == 9:
                self.mostrar_cursos()
            elif opcion == 10:
                print("Sistema cerrado. ¡Hasta luego!")
                break
            else:
                print("Opción no válida")
    
    def registrar_profesor(self):
        print("\n--- Registrar Profesor ---")
        print("1. Profesor tiempo completo")
        print("2. Profesor por horas")
        try:
            tipo = int(input("Seleccione el tipo: "))
        except ValueError:
            print("Por favor ingrese un número válido")
            return
        
        nombre = input("Nombre: ")
        id = input("ID: ")
        
        if tipo == 1:
            try:
                salario = float(input("Salario mensual: "))
                self.personas.append(ProfesorTiempoCompleto(nombre, id, salario))
                print("Profesor registrado con éxito")
            except ValueError:
                print("Por favor ingrese un número válido para el salario")
        elif tipo == 2:
            try:
                horas = int(input("Horas trabajadas: "))
                pago_hora = float(input("Pago por hora: "))
                self.personas.append(ProfesorPorHoras(nombre, id, horas, pago_hora))
                print("Profesor registrado con éxito")
            except ValueError:
                print("Por favor ingrese números válidos para horas y pago por hora")
        else:
            print("Opción no válida")
    
    def registrar_estudiante(self):
        print("\n--- Registrar Estudiante ---")
        nombre = input("Nombre: ")
        id = input("ID: ")
        self.personas.append(Estudiante(nombre, id))
        print("Estudiante registrado con éxito")
    
    def crear_curso(self):
        print("\n--- Crear Curso ---")
        nombre_curso = input("Nombre del curso: ")
        
        # Mostrar profesores disponibles
        profesores = [p for p in self.personas if isinstance(p, (ProfesorTiempoCompleto, ProfesorPorHoras))]
        if not profesores:
            print("No hay profesores registrados. Registre un profesor primero.")
            return
        
        print("Profesores disponibles:")
        for i, profesor in enumerate(profesores):
            print(f"{i}. {profesor.nombre}")
        
        try:
            seleccion = int(input("Seleccione un profesor: "))
            profesor_seleccionado = profesores[seleccion]
            self.cursos.append(Curso(nombre_curso, profesor_seleccionado))
            print("Curso creado con éxito")
        except (ValueError, IndexError):
            print("Selección no válida")
    
    def agregar_estudiante_a_curso(self):
        if not self.cursos:
            print("No hay cursos registrados. Cree un curso primero.")
            return
        
        print("\n--- Agregar Estudiante a Curso ---")
        print("Cursos disponibles:")
        for i, curso in enumerate(self.cursos):
            print(f"{i}. {curso.nombre_curso}")
        
        try:
            curso_seleccionado = int(input("Seleccione un curso: "))
            curso = self.cursos[curso_seleccionado]
        except (ValueError, IndexError):
            print("Selección no válida")
            return
        
        # Mostrar estudiantes disponibles
        estudiantes = [p for p in self.personas if isinstance(p, Estudiante)]
        if not estudiantes:
            print("No hay estudiantes registrados. Registre un estudiante primero.")
            return
        
        print("Estudiantes disponibles:")
        for i, estudiante in enumerate(estudiantes):
            print(f"{i}. {estudiante.nombre}")
        
        try:
            estudiante_seleccionado = int(input("Seleccione un estudiante: "))
            curso.agregar_estudiante(estudiantes[estudiante_seleccionado])
            print("Estudiante agregado al curso con éxito")
        except (ValueError, IndexError):
            print("Selección no válida")
    
    def agregar_calificacion(self):
        print("\n--- Agregar Calificación ---")
        
        # Mostrar estudiantes
        estudiantes = [p for p in self.personas if isinstance(p, Estudiante)]
        if not estudiantes:
            print("No hay estudiantes registrados.")
            return
        
        print("Estudiantes disponibles:")
        for i, estudiante in enumerate(estudiantes):
            print(f"{i}. {estudiante.nombre}")
        
        try:
            seleccion = int(input("Seleccione un estudiante: "))
            estudiante = estudiantes[seleccion]
            calificacion = float(input("Ingrese la calificación: "))
            estudiante.agregar_calificacion(calificacion)
            print("Calificación agregada con éxito")
        except (ValueError, IndexError):
            print("Selección no válida")
    
    def calcular_pago_profesor(self):
        print("\n--- Calcular Pago de Profesor ---")
        
        # Mostrar profesores
        profesores = [p for p in self.personas if isinstance(p, (ProfesorTiempoCompleto, ProfesorPorHoras))]
        if not profesores:
            print("No hay profesores registrados.")
            return
        
        print("Profesores disponibles:")
        for i, profesor in enumerate(profesores):
            print(f"{i}. {profesor.nombre}")
        
        try:
            seleccion = int(input("Seleccione un profesor: "))
            profesor = profesores[seleccion]
            pago = profesor.calcular_pago()
            print(f"El pago para {profesor.nombre} es: {pago}")
        except (ValueError, IndexError):
            print("Selección no válida")
        except PagoInvalidoException as e:
            print(f"Error al calcular pago: {e}")
    
    def calcular_promedio_estudiante(self):
        print("\n--- Calcular Promedio de Estudiante ---")
        
        # Mostrar estudiantes
        estudiantes = [p for p in self.personas if isinstance(p, Estudiante)]
        if not estudiantes:
            print("No hay estudiantes registrados.")
            return
        
        print("Estudiantes disponibles:")
        for i, estudiante in enumerate(estudiantes):
            print(f"{i}. {estudiante.nombre}")
        
        try:
            seleccion = int(input("Seleccione un estudiante: "))
            estudiante = estudiantes[seleccion]
            promedio = estudiante.calcular_promedio()
            print(f"El promedio de {estudiante.nombre} es: {promedio}")
        except (ValueError, IndexError):
            print("Selección no válida")
        except PromedioInvalidoException as e:
            print(f"Error al calcular promedio: {e}")
    
    def mostrar_personas(self):
        print("\n--- Lista de Personas ---")
        for persona in self.personas:
            persona.mostrar_informacion()
    
    def mostrar_cursos(self):
        print("\n--- Lista de Cursos ---")
        for curso in self.cursos:
            curso.mostrar_informacion()
            print()

# Ejecutar el sistema
if __name__ == "__main__":
    sistema = SistemaGestionCursos()
    sistema.ejecutar()