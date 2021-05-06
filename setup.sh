#!/bin/bash
rm settings.gradle

LIST_OF_MODULES=(
    'annotations'
    'browser'
    'processors'
)

for mod in ${LIST_OF_MODULES[@]}; do
    echo "include '$mod'" >> settings.gradle
done

cat settings.gradle