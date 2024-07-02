#!/bin/bash

# Si el directorio "Lenguajes_2024_1" existe en el directorio actual, cambia a ese directorio
if [[ -d "Lenguajes_2024_1" ]]; then
    cd Lenguajes_2024_1
# De lo contrario, sube en la jerarquía de directorios hasta que el directorio actual sea "Lenguajes_2024_1"
elif [[ ! $(pwd) =~ Lenguajes_2024_1$ ]]; then
    while [[ ! $(pwd) =~ Lenguajes_2024_1$ ]]; do
        cd ..
    done
fi

echo "Generando analizador léxico java, directorio actual: $(pwd)"

jflex -d src/model/scanner/ src/data/Lexer.flex