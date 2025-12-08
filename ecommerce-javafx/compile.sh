#!/bin/bash

# Script de compilação do E-Commerce JavaFX

echo "======================================"
echo "  Compilando E-Commerce JavaFX"
echo "======================================"

# Criar diretório de saída
mkdir -p out

# Compilar todos os arquivos Java
echo "Compilando arquivos Java..."

javac -d out \
  src/models/*.java \
  src/utils/*.java \
  src/controllers/*.java \
  src/views/*.java \
  src/MainApp.java

if [ $? -eq 0 ]; then
    echo "✓ Compilação concluída com sucesso!"
    echo ""
    echo "Para executar no IntelliJ:"
    echo "1. Abra o projeto no IntelliJ IDEA"
    echo "2. Configure o JavaFX SDK em Project Structure → Libraries"
    echo "3. Configure VM Options: --module-path \"PATH_TO_JAVAFX/lib\" --add-modules javafx.controls"
    echo "4. Execute a classe MainApp"
    echo ""
else
    echo "✗ Erro na compilação!"
    exit 1
fi
