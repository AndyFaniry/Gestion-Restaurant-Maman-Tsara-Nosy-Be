#!/bin/bash

JAVA_DIR="src/main/java"
RESOURCE_DIR="src/main/resources"
OUTPUT_FILE="source.txt"

> "$OUTPUT_FILE"

echo "===== EXPORT COMPLET SPRING =====" >> "$OUTPUT_FILE"
echo "Date : $(date)" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"

# Tous les fichiers Java
find "$JAVA_DIR" -type f -name "*.java" | sort | while read file
do
    echo "" >> "$OUTPUT_FILE"
    echo "======================================" >> "$OUTPUT_FILE"
    echo "FILE : $file" >> "$OUTPUT_FILE"
    echo "======================================" >> "$OUTPUT_FILE"

    cat "$file" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
done

# Resources texte
find "$RESOURCE_DIR" -type f \
    ! -name "*.svg" \
    ! -name "*.png" \
    ! -name "*.jpg" \
    ! -name "*.jpeg" \
    ! -name "*.gif" \
    ! -name "*.ico" \
    | sort | while read file
do
    echo "" >> "$OUTPUT_FILE"
    echo "======================================" >> "$OUTPUT_FILE"
    echo "RESOURCE : $file" >> "$OUTPUT_FILE"
    echo "======================================" >> "$OUTPUT_FILE"

    cat "$file" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
done

echo " Export terminé : $OUTPUT_FILE"