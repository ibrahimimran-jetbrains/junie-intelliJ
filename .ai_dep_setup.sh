#!/bin/bash
set -euo pipefail
cd /home/runner/work/junie-intelliJ/junie-intelliJ
./mvnw --batch-mode dependency:resolve && ./mvnw --batch-mode compile test-compile
