# Playwright UI Tests for Spring PetClinic

This directory contains end-to-end UI tests for the Spring PetClinic application using Playwright.

## Overview

These tests are designed to validate the micro-frontend components and user interactions in the PetClinic application. The tests follow the Page Object Model (POM) pattern for better maintainability and reusability.

## Project Structure

```
tests/playwright/
├── README.md                    # This file
├── pages/                       # Page Object Models
│   └── OwnerDetailsPage.ts     # POM for Owner Details page
└── ownerDetails.spec.ts        # Test specifications for Owner Details
```

## Prerequisites

- Node.js (version 18 or higher)
- npm or yarn package manager
- Spring PetClinic application running on http://localhost:8080

## Installation

Playwright and its dependencies are already installed. To reinstall or update:

```bash
npm install
```

To install browser binaries:

```bash
npx playwright install
```

## Running Tests

### Run all tests (headless mode)
```bash
npm test
```

### Run tests in headed mode (see browser)
```bash
npm run test:headed
```

### Run tests in debug mode
```bash
npm run test:debug
```

### Run tests with UI mode (interactive)
```bash
npm run test:ui
```

### Run specific test file
```bash
npx playwright test ownerDetails.spec.ts
```

### Run specific test by name
```bash
npx playwright test -g "should display owner information correctly"
```

### Run tests on specific browser
```bash
npx playwright test --project=chromium
npx playwright test --project=firefox
npx playwright test --project=webkit
```

## View Test Reports

After running tests, view the HTML report:

```bash
npm run test:report
```

## Test Coverage

### Owner Details Page Tests

The `ownerDetails.spec.ts` file contains comprehensive tests for the Owner Details page:

#### 1. Owner Information Display
- Displays owner information correctly
- Shows all fields in proper table structure

#### 2. Navigation Actions
- Edit Owner button navigation
- Add New Pet button navigation

#### 3. Pets and Visits Section
- Displays pets and visits section
- Shows pet information (name, birth date, type)
- Displays visit table headers

#### 4. Visit Sorting Functionality
- Display sorting controls
- Sort visits in ascending order
- Sort visits in descending order
- Default to ascending order
- Toggle between sort orders
- Maintain sort order with direct URL navigation

#### 5. Pet Actions
- Edit Pet links for each pet
- Add Visit links for each pet

#### 6. Error Handling
- Handle invalid owner ID gracefully

#### 7. Responsive Design
- Mobile viewport (375x667)
- Tablet viewport (768x1024)

#### 8. Page Performance
- Page load time validation (< 3 seconds)

#### 9. Accessibility
- Proper heading hierarchy
- Proper table structure
- Accessible links with proper labels

#### 10. Message Display
- Success and error message containers

#### 11. Data Integrity
- Consistent data across page reloads

## Page Object Model

### OwnerDetailsPage

The `OwnerDetailsPage` class provides a clean interface for interacting with the Owner Details page:

**Key Methods:**
- `goto(ownerId)` - Navigate to owner details page
- `gotoWithSortOrder(ownerId, sortOrder)` - Navigate with sort parameter
- `getOwnerName()`, `getOwnerAddress()`, `getOwnerCity()`, `getOwnerTelephone()` - Get owner information
- `clickEditOwner()` - Click edit owner button
- `clickAddNewPet()` - Click add new pet button
- `clickSortAscending()`, `clickSortDescending()` - Control visit sorting
- `isSortAscendingActive()`, `isSortDescendingActive()` - Check sort state
- `clickEditPet(petName)` - Edit specific pet
- `clickAddVisit(petName)` - Add visit for specific pet
- `verifyOwnerInformation()` - Assert owner details
- `verifyPageLoaded()` - Verify page loaded successfully

## Configuration

The tests are configured via `playwright.config.ts` in the project root:

- **Base URL**: http://localhost:8080
- **Browsers**: Chromium, Firefox, WebKit, Mobile Chrome, Mobile Safari
- **Parallel Execution**: Enabled (disable on CI)
- **Retries**: 2 on CI, 0 locally
- **Screenshots**: On failure
- **Videos**: Retained on failure
- **Traces**: On first retry
- **Auto Start Server**: Yes (runs `./mvnw spring-boot:run`)

## Writing New Tests

### 1. Create a Page Object Model

Create a new file in `pages/` directory:

```typescript
import { Page, Locator, expect } from '@playwright/test';

export class MyPageName {
  readonly page: Page;
  readonly myElement: Locator;

  constructor(page: Page) {
    this.page = page;
    this.myElement = page.locator('#my-element');
  }

  async goto() {
    await this.page.goto('/my-path');
  }

  async clickElement() {
    await this.myElement.click();
  }
}
```

### 2. Create Test Specification

Create a new test file:

```typescript
import { test, expect } from '@playwright/test';
import { MyPageName } from './pages/MyPageName';

test.describe('My Feature', () => {
  let myPage: MyPageName;

  test.beforeEach(async ({ page }) => {
    myPage = new MyPageName(page);
  });

  test('should do something', async ({ page }) => {
    await myPage.goto();
    await myPage.clickElement();
    // assertions...
  });
});
```

## Best Practices

1. **Use Page Object Model**: Encapsulate page logic in POM classes
2. **Descriptive Test Names**: Use clear, descriptive test names
3. **Independent Tests**: Each test should be independent and able to run in isolation
4. **Wait for Elements**: Use Playwright's auto-waiting instead of manual waits
5. **Assertions**: Use Playwright's expect assertions for better error messages
6. **Selectors**: Prefer user-facing selectors (role, text) over CSS/XPath
7. **Clean Up**: No test should depend on another test's state

## Debugging

### Visual Debugging
```bash
npm run test:debug
```

### Generate Tests with Codegen
```bash
npm run test:codegen
```

### View Traces
When a test fails, a trace is captured. View it:
```bash
npx playwright show-trace trace.zip
```

## CI/CD Integration

The tests are configured to run in CI environments:
- Automatic retries (2x)
- Sequential execution (workers: 1)
- No test.only allowed
- HTML and list reporters

## Troubleshooting

### Application Not Running
Ensure Spring Boot application is running:
```bash
./mvnw spring-boot:run
```

### Port Already in Use
If port 8080 is in use, stop other services or configure a different port in `playwright.config.ts`.

### Browser Installation Issues
Reinstall browsers:
```bash
npx playwright install --force
```

### Timeout Issues
If tests timeout, increase timeout in playwright.config.ts:
```typescript
use: {
  timeout: 60000, // 60 seconds
}
```

## Contributing

When adding new tests:
1. Follow the existing POM pattern
2. Group related tests in describe blocks
3. Use meaningful test descriptions
4. Add appropriate assertions
5. Test edge cases and error scenarios
6. Ensure tests pass on all browsers

## Resources

- [Playwright Documentation](https://playwright.dev)
- [Playwright Test API](https://playwright.dev/docs/api/class-test)
- [Best Practices](https://playwright.dev/docs/best-practices)
- [Selectors Guide](https://playwright.dev/docs/selectors)

## Support

For issues or questions about the tests, please refer to:
- Playwright documentation: https://playwright.dev
- Project guidelines: `.junie/guidelines.md`
- Task tracking: `docs/tasksCompleted.md`
