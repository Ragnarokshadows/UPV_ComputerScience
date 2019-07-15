# 1. Estilos de interacción


## Interfaces de línea de comando
### Pros
* Instrucciones directas
* Flexibles, opciones, múltiples argumentos
* Acciones rápidas y concisas

### Contras
* Difícil de aprender y recordar
* No muestra ayuda
* Órdenes crípticas, complejas y variables
* Poca tolerancia a errores y alta frecuencia
* Feedback difícil

### Diseño
* Nombres representativos
* Sintaxis consistente para comandos y respuestas
* Nombres cortos
* Abreviar respuestas
* Limitar caminos hasta un fin
* Macros


## Menús
* Buenos para usuarios nuevos o intermitentes
* Lentos para usuarios avanzados

### Diseño
* Conjunto de opciones
* Autoexplicativos y distinguibles
* Palabras clave
* Binarios: valores por defecto y atajos
* Radio buttons: excluyentes
* Checkbox: opciones binarias
* Mostrar teclas de aceleración

### Tipos
* Secuencias: wizard
* Simultáneos: web
* Árbol
* Mapas: site index
* Redes cíclicas y acíclicas


## Formularios
* Introducción de datos

### Diseño
* Títulos significativos
* Secuencia y brevedad
* Agrupación de campos
* Terminología familiar y constante
* Movimiento guiado
* Prevención y corrección de errores (feedback)
* Resaltar campos obligatorios


## Manipulación directa
* Drag 'n Drop, WYSIWYG
* Representan mejor la tarea
* Pueden ser difíciles para ciertas situaciones

## Interfaces por lenguaje natural
* Sin sintaxis específica
* Requiere aclaraciones
* Requiere nicho y avances tecnológicos

# 2. Diseño conceptual
* Escenario de tarea: caso a cubrir con la aplicación
* Escenario de uso: caso cubierto por la aplicación
* Casos de uso concretos: acciones del usuario y respuesta del sistema en la realización de las distintas tareas
* Diagrama de contenidos (prototipo de baja fidelidad)
    * Representa la relación entre tareas y posibles pantallas
    1. Objetos de tarea primarios, atributos y acciones
        * Organizar los elementos de tareas como un lenguaje OO.
        * En los casos de uso, subrayar los objetos y subrayado doble para atributos
    2. Contenedores
        * Representar para cada contenedor las acciones (de usuario y sistema), los objetos y enlaces relacionados
        * Contenedor principal como núcleo de la aplicación, como centro, pero no necesariamente realizador de todas las acciones
    3. Conexiones
        * Relacionar contenedores para mostrar la navegabilidad de la app.
        * > modal, >> no modal, paralelo
        * Los enlaces pueden ser condicionales.
    4. Evaluar con los casos de uso concretos para verificar que son correctos

# 3. Diseño físico

## Principios de diseño
Reglas generales y que necesitan concretarse en cada caso.
* Visibilidad
* Affordance: los controles hacen lo que creemos
* Realimentación
* Simplicidad
* Estructura
* Consistencia
* Tolerancia: avisar y recuperar errores (antes o después de que se cometan)

### Principios de Nielsen
1.  Visibilidad del estado del sistema
2.  Relación con el mundo real*
3.  Control y libertad: ctrl-z
4.  Consistencia, estándares
5.  Prevención de errores
6.  Reconocer controles
7.  Flexibilidad: aceleradores, personalización
8.  Diseño minimalista
9.  Ayudar ante errores*
10. Ayuda y docs

## Guías de estilo
Convenciones de diseño para una plataforma o familia de productos.
Describen controles y el modo de usarlos. Estilo de las aplicaciones.

Para crear una imagen corporativa

## Principios de una buena composición
1. Grupos naturales
2. Separar componentes de la actividad actual (ventanas)
3. Resaltar acciones/componentes importantes
4. Espacio blanco
5. Controles con affordance
6. Equilibrio estética-usabilidad

## Diseño de la interfaz de usuario
1. Estructurar la interacción
2. Controlar la interacción
3. Introducir la información


# 4. Prototipado
Modelo del producto final, para probar y modificar de forma más real.

## Baja fidelidad
* Storyboards: contar los escenarios de uso.
* Papel: simular las pantallas.
## Baja-media fidelidad
* Wireframe: interfaz vacía con la colocación de los principales componentes
## Alta fidelidad
* Mockups


# Evaluación
Parte imporante del desarrollo. Durante y después. 5-20%
Testear el sistema en las condiciones de uso final.

## Métricas de usabilidad
* Actual (current value), caso mejor (target value), planeado, caso peor (peor valor aceptado)

## Tipos
* Bug search:                   evaluacion diagnóstica
* Cumplimiento de requisitos:   evaluación por medidas
* Durante desarrollo:           evaluación formativa
* Al final:                     evaluación sumativa
* Inicial, informal:            evaluación exploratoria
* Al final, formal:             evaluación de validación
* Elegir entre opciones, stats: evaluación competitiva
                                evaluación con usuarios
                                evaluación experta

## Con usuarios
* Al menos 5, representativos de los distintos perfiles
* De campo, versiones beta
    * Muchos participantes y bugs
    * Información más genérica, menos reacciones momentáneas específicas
* Prueba de romper el sistema, o comparativa con otra versión/competidor
* Controlados
    * En laboratorios midiendo más o menos variables
    * Experimento preparado y probado
    * Bienvenida, consentimiento, filtrado, ejecución, entrevista, pago
    * Encuestas como modo de obtener infomación cuantitativa y estructurada
    * Escalas de 3, 5, 7 elementos, acuerdo....desacuerdo

## Expertos
* Más baratos y rápidos. Pruebas iniciales
* Sólo preceden a la evaluación con usuarios

## Pruebas de aceptación
Prueba final independiente para mostrar que se cumplen las condiciones y requerimientos mostrados inicialmente.
