#!/bin/bash

JAVA_DIR="src/main/java"
RESOURCE_DIR="src/main/resources"
DATABASE_DIR="database"
README_FILE="README.MD"
OUTPUT_FILE="source.txt"
DEVCONTAINER_DIR=".devcontainer"

DOCKERCOMPOSE_FILE="docker-compose.yml"
DOCKERFILE="Dockerfile"



> "$OUTPUT_FILE"

echo "===== EXPORT COMPLET SPRING =====" >> "$OUTPUT_FILE"
echo "Date : $(date)" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"

# --- 1. Copie du README.md (si présent) ---
if [ -f "$README_FILE" ]; then
    echo "" >> "$OUTPUT_FILE"
    echo "======================================" >> "$OUTPUT_FILE"
    echo "FILE : $README_FILE" >> "$OUTPUT_FILE"
    echo "======================================" >> "$OUTPUT_FILE"
    
    cat "$README_FILE" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
fi

# --- 1. Copie du DOCKER.md (si présent) ---
if [ -f "$DOCKERCOMPOSE_FILE" ]; then
    echo "" >> "$OUTPUT_FILE"
    echo "======================================" >> "$OUTPUT_FILE"
    echo "FILE : $DOCKERCOMPOSE_FILE" >> "$OUTPUT_FILE"
    echo "======================================" >> "$OUTPUT_FILE"
    
    cat "$README_FILE" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
fi

# --- 1. Copie du DOCKER.md (si présent) ---
if [ -f "$DOCKERFILE" ]; then
    echo "" >> "$OUTPUT_FILE"
    echo "======================================" >> "$OUTPUT_FILE"
    echo "FILE : $DOCKERFILE" >> "$OUTPUT_FILE"
    echo "======================================" >> "$OUTPUT_FILE"
    
    cat "$README_FILE" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
fi

# --- 2. Fichiers Devcontainer ---
find "$DEVCONTAINER_DIR" -type f -name "*.*" 2>/dev/null | sort | while read -r file
do
    echo "" >> "$OUTPUT_FILE"
    echo "======================================" >> "$OUTPUT_FILE"
    echo "FILE : $file" >> "$OUTPUT_FILE"
    echo "======================================" >> "$OUTPUT_FILE"

    cat "$file" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
done

# # --- 3. Tous les fichiers Java ---
# find "$JAVA_DIR" -type f -name "*.java" 2>/dev/null | sort | while read -r file
# do
#     echo "" >> "$OUTPUT_FILE"
#     echo "======================================" >> "$OUTPUT_FILE"
#     echo "FILE : $file" >> "$OUTPUT_FILE"
#     echo "======================================" >> "$OUTPUT_FILE"

#     cat "$file" >> "$OUTPUT_FILE"
#     echo "" >> "$OUTPUT_FILE"
# done

# # --- 4. Fichiers SQL ---
# find "$DATABASE_DIR" -type f -name "*.sql" 2>/dev/null | sort | while read -r file
# do
#     echo "" >> "$OUTPUT_FILE"
#     echo "======================================" >> "$OUTPUT_FILE"
#     echo "FILE : $file" >> "$OUTPUT_FILE"
#     echo "======================================" >> "$OUTPUT_FILE"

#     cat "$file" >> "$OUTPUT_FILE"
#     echo "" >> "$OUTPUT_FILE"
# done

# # --- 5. Resources texte ---
# find "$RESOURCE_DIR" -type f \
#     ! -name "*.svg" \
#     ! -name "*.png" \
#     ! -name "*.jpg" \
#     ! -name "*.jpeg" \
#     ! -name "*.gif" \
#     ! -name "*.ico" \
#     ! -name "*.woff" \
#     ! -name "*.woff2" \
#     ! -name "*.js" \
#     ! -name "*.css" 2>/dev/null | sort | while read -r file
# do
#     echo "" >> "$OUTPUT_FILE"
#     echo "======================================" >> "$OUTPUT_FILE"
#     echo "RESOURCE : $file" >> "$OUTPUT_FILE"
#     echo "======================================" >> "$OUTPUT_FILE"

#     cat "$file" >> "$OUTPUT_FILE"
#     echo "" >> "$OUTPUT_FILE"
# done

echo " Export terminé : $OUTPUT_FILE"