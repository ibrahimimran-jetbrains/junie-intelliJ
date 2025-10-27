#!/bin/bash
set -e
./mvnw dependency:resolve -B && ./mvnw verify -B
