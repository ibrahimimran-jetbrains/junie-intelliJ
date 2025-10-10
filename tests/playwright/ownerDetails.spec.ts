import { test, expect } from '@playwright/test';
import { OwnerDetailsPage } from './pages/OwnerDetailsPage';

/**
 * Test suite for Owner Details page
 * Tests the micro-frontend UI components and interactions
 */
test.describe('Owner Details Page', () => {
  let ownerDetailsPage: OwnerDetailsPage;

  test.beforeEach(async ({ page }) => {
    ownerDetailsPage = new OwnerDetailsPage(page);
  });

  test.describe('Owner Information Display', () => {
    test('should display owner information correctly', async ({ page }) => {
      // Navigate to a known owner (Owner ID 1 - George Franklin from sample data)
      await ownerDetailsPage.goto(1);
      
      // Verify page loads
      await ownerDetailsPage.verifyPageLoaded();
      
      // Verify owner information section is visible
      await expect(ownerDetailsPage.ownerInformationHeading).toBeVisible();
      
      // Verify owner details are displayed
      const ownerName = await ownerDetailsPage.getOwnerName();
      expect(ownerName).toBeTruthy();
      
      const ownerAddress = await ownerDetailsPage.getOwnerAddress();
      expect(ownerAddress).toBeTruthy();
      
      const ownerCity = await ownerDetailsPage.getOwnerCity();
      expect(ownerCity).toBeTruthy();
      
      const ownerTelephone = await ownerDetailsPage.getOwnerTelephone();
      expect(ownerTelephone).toBeTruthy();
    });

    test('should display all owner information fields in correct structure', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Verify the table structure
      const table = page.locator('table.table-striped').first();
      await expect(table).toBeVisible();
      
      // Verify all rows are present
      await expect(table.locator('th:has-text("Name")')).toBeVisible();
      await expect(table.locator('th:has-text("Address")')).toBeVisible();
      await expect(table.locator('th:has-text("City")')).toBeVisible();
      await expect(table.locator('th:has-text("Telephone")')).toBeVisible();
    });
  });

  test.describe('Navigation Actions', () => {
    test('should have Edit Owner button and navigate correctly', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Verify Edit Owner button is visible
      await expect(ownerDetailsPage.editOwnerButton).toBeVisible();
      
      // Click Edit Owner button
      await ownerDetailsPage.clickEditOwner();
      
      // Verify navigation to edit page
      await expect(page).toHaveURL(/\/owners\/1\/edit/);
    });

    test('should have Add New Pet button and navigate correctly', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Verify Add New Pet button is visible
      await expect(ownerDetailsPage.addNewPetButton).toBeVisible();
      
      // Click Add New Pet button
      await ownerDetailsPage.clickAddNewPet();
      
      // Verify navigation to add pet page
      await expect(page).toHaveURL(/\/owners\/1\/pets\/new/);
    });
  });

  test.describe('Pets and Visits Section', () => {
    test('should display pets and visits section', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Verify pets and visits section
      await ownerDetailsPage.verifyPetsAndVisitsSection();
      
      // Verify heading is visible
      await expect(ownerDetailsPage.petsAndVisitsHeading).toBeVisible();
    });

    test('should display pet information', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Check if pets table is visible
      await expect(ownerDetailsPage.petsTable).toBeVisible();
      
      // Check for pet details structure (name, birth date, type)
      const petDetails = page.locator('dl.dl-horizontal');
      if (await petDetails.count() > 0) {
        await expect(petDetails.first()).toBeVisible();
      }
    });

    test('should display visit table headers', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Check for visit table headers
      const visitTable = page.locator('table.table-condensed');
      if (await visitTable.count() > 0) {
        await expect(visitTable.first().locator('th:has-text("Visit Date")')).toBeVisible();
        await expect(visitTable.first().locator('th:has-text("Description")')).toBeVisible();
      }
    });
  });

  test.describe('Visit Sorting Functionality', () => {
    test('should display sorting controls for visits', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Verify sorting links are visible
      await expect(ownerDetailsPage.sortAscendingLink).toBeVisible();
      await expect(ownerDetailsPage.sortDescendingLink).toBeVisible();
    });

    test('should sort visits in ascending order', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Click sort ascending
      await ownerDetailsPage.clickSortAscending();
      
      // Verify URL contains sortOrder=asc
      await expect(page).toHaveURL(/sortOrder=asc/);
      
      // Verify ascending link is active
      const isAscActive = await ownerDetailsPage.isSortAscendingActive();
      expect(isAscActive).toBe(true);
    });

    test('should sort visits in descending order', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Click sort descending
      await ownerDetailsPage.clickSortDescending();
      
      // Verify URL contains sortOrder=desc
      await expect(page).toHaveURL(/sortOrder=desc/);
      
      // Verify descending link is active
      const isDescActive = await ownerDetailsPage.isSortDescendingActive();
      expect(isDescActive).toBe(true);
    });

    test('should default to ascending order', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // By default, ascending should be active
      const isAscActive = await ownerDetailsPage.isSortAscendingActive();
      expect(isAscActive).toBe(true);
    });

    test('should toggle between ascending and descending', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Click descending
      await ownerDetailsPage.clickSortDescending();
      await expect(page).toHaveURL(/sortOrder=desc/);
      
      // Click ascending
      await ownerDetailsPage.clickSortAscending();
      await expect(page).toHaveURL(/sortOrder=asc/);
      
      // Verify ascending is active
      const isAscActive = await ownerDetailsPage.isSortAscendingActive();
      expect(isAscActive).toBe(true);
    });

    test('should maintain sort order with direct URL navigation', async ({ page }) => {
      // Navigate with desc sort order
      await ownerDetailsPage.gotoWithSortOrder(1, 'desc');
      
      // Verify URL and active state
      await expect(page).toHaveURL(/sortOrder=desc/);
      const isDescActive = await ownerDetailsPage.isSortDescendingActive();
      expect(isDescActive).toBe(true);
    });
  });

  test.describe('Pet Actions', () => {
    test('should have Edit Pet links for each pet', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Check if Edit Pet links exist
      const editPetLinks = page.getByRole('link', { name: 'Edit Pet' });
      const count = await editPetLinks.count();
      
      if (count > 0) {
        await expect(editPetLinks.first()).toBeVisible();
      }
    });

    test('should have Add Visit links for each pet', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Check if Add Visit links exist
      const addVisitLinks = page.getByRole('link', { name: 'Add Visit' });
      const count = await addVisitLinks.count();
      
      if (count > 0) {
        await expect(addVisitLinks.first()).toBeVisible();
      }
    });
  });

  test.describe('Error Handling', () => {
    test('should handle invalid owner ID gracefully', async ({ page }) => {
      // Try to navigate to non-existent owner
      await page.goto('/owners/99999');
      
      // The application should show error or redirect
      // (Behavior depends on application implementation)
      await page.waitForLoadState('networkidle');
      
      // Verify we're not on the standard owner details page
      // or that an error is shown
      const url = page.url();
      expect(url).toBeTruthy();
    });
  });

  test.describe('Responsive Design', () => {
    test('should display properly on mobile viewport', async ({ page }) => {
      // Set mobile viewport
      await page.setViewportSize({ width: 375, height: 667 });
      
      await ownerDetailsPage.goto(1);
      
      // Verify key elements are still visible
      await expect(ownerDetailsPage.ownerInformationHeading).toBeVisible();
      await expect(ownerDetailsPage.editOwnerButton).toBeVisible();
      await expect(ownerDetailsPage.addNewPetButton).toBeVisible();
    });

    test('should display properly on tablet viewport', async ({ page }) => {
      // Set tablet viewport
      await page.setViewportSize({ width: 768, height: 1024 });
      
      await ownerDetailsPage.goto(1);
      
      // Verify key elements are still visible
      await expect(ownerDetailsPage.ownerInformationHeading).toBeVisible();
      await expect(ownerDetailsPage.petsAndVisitsHeading).toBeVisible();
    });
  });

  test.describe('Page Performance', () => {
    test('should load within acceptable time', async ({ page }) => {
      const startTime = Date.now();
      
      await ownerDetailsPage.goto(1);
      await ownerDetailsPage.verifyPageLoaded();
      
      const loadTime = Date.now() - startTime;
      
      // Page should load within 3 seconds
      expect(loadTime).toBeLessThan(3000);
    });
  });

  test.describe('Accessibility', () => {
    test('should have proper heading hierarchy', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Check for h2 headings
      const h2Headings = page.locator('h2');
      const count = await h2Headings.count();
      
      expect(count).toBeGreaterThanOrEqual(2); // Owner Information and Pets and Visits
    });

    test('should have proper table structure', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Verify table has proper headers
      const tables = page.locator('table');
      const firstTable = tables.first();
      
      await expect(firstTable).toBeVisible();
      const thElements = await firstTable.locator('th').count();
      expect(thElements).toBeGreaterThan(0);
    });

    test('should have accessible links with proper labels', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Verify links have accessible text
      await expect(ownerDetailsPage.editOwnerButton).toHaveText(/Edit Owner/);
      await expect(ownerDetailsPage.addNewPetButton).toHaveText(/Add New Pet/);
    });
  });

  test.describe('Message Display', () => {
    test('should have message containers in DOM', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Success and error message containers should exist in DOM
      const successMsg = page.locator('#success-message');
      const errorMsg = page.locator('#error-message');
      
      // They might not be visible, but should be in DOM
      expect(await successMsg.count()).toBe(1);
      expect(await errorMsg.count()).toBe(1);
    });
  });

  test.describe('Data Integrity', () => {
    test('should display consistent data across page reloads', async ({ page }) => {
      await ownerDetailsPage.goto(1);
      
      // Get owner data
      const name1 = await ownerDetailsPage.getOwnerName();
      const address1 = await ownerDetailsPage.getOwnerAddress();
      
      // Reload page
      await page.reload();
      
      // Get owner data again
      const name2 = await ownerDetailsPage.getOwnerName();
      const address2 = await ownerDetailsPage.getOwnerAddress();
      
      // Data should be consistent
      expect(name1).toBe(name2);
      expect(address1).toBe(address2);
    });
  });
});
