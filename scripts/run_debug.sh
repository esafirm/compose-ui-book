#/usr/bin/env bash

./gradlew :sample:kspKotlinDesktop --no-daemon -Dorg.gradle.debug=true -Dkotlin.compiler.execution.strategy=in-process --rerun-tasks -a

