# Taller 1: Analizador léxico
## Categorías léxicas

## palabras clave

- if
- else
- while
- for
- return
- break
- continue
- fn
- bool
- int
- float
- string
- true
- false
- null
### Extras:
- mean
- max
- min
- median
- mode

| Nombre         | Expresión regular |
|----------------|-------------------|
| Identificador  | [a-zA-Z_][a-zA-Z0-9_]* |
| Número entero  | [0-9]+ |
| Número decimal | [0-9]+\.[0-9]+ |
| Operador       | [+ - * /] |
| Delimitador    | [; , ( ) { } ] |

### Operadores aritméticos

| Operador | Descripción |
|----------|-------------|
| +        | Suma        |
| -        | Resta       |
| *        | Multiplicación |
| /        | División    |
| %        | Módulo      |
| ^        | Potencia    |

### Operadores relacionales

| Operador | Descripción |
|----------|-------------|
| <        | Menor que   |
| <=       | Menor o igual que |
| >        | Mayor que   |
| >=       | Mayor o igual que |
| ==       | Igual a     |
| !=       | Diferente de |

### Operadores lógicos

| Operador | Descripción |
|----------|-------------|
| &        | AND lógico  |
| \|       | OR lógico   |
| !        | NOT lógico  |


### Operador de asignación

| Operador | Descripción |
|----------|-------------|
| =        | Asignación  |

### Operadores de agrupación o delimitadores parentesis

| Operador | Descripción |
|----------|-------------|
| (        | Paréntesis izquierdo |
| )        | Paréntesis derecho |
| {        | Llave izquierda |
| }        | Llave derecha |
| [        | Corchete izquierdo |
| ]        | Corchete derecho |

### Signos de puntuación

| Signo | Descripción |
|-------|-------------|
| ,     | Coma        |
| ;     | Punto y coma |
| .     | Punto       |
| :     | Dos puntos  |

### Literales

| Literal        | Descripción |
|----------------|-------------|
| {NumeroEntero} | Número entero |
| {NumeroFlotante} | Número decimal |
| {String} | Cadena de texto |

### Errores

| Error | Descripción |
|-------|-------------|
| 0{Numero}+ | Número que comienza con cero |
| . | Símbolo no encontrado |
| {Numero}+{Identificador} | Identificador que comienza con dígito |

### Fin de archivo

| Token | Descripción |
|-------|-------------|
| \<EOF\> | Fin de archivo |