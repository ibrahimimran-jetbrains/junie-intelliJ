./mvnw clean install -B
cd tests/playwright && npm install --ignore-scripts && npx playwright install --with-deps
