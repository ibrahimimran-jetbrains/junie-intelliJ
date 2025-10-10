import { Page, Locator, expect } from '@playwright/test';

/**
 * Page Object Model for the Owner Details page
 * Represents the UI structure and interactions for /owners/{ownerId}
 */
export class OwnerDetailsPage {
  readonly page: Page;
  
  // Owner Information Section
  readonly ownerInformationHeading: Locator;
  readonly ownerNameCell: Locator;
  readonly ownerAddressCell: Locator;
  readonly ownerCityCell: Locator;
  readonly ownerTelephoneCell: Locator;
  
  // Buttons
  readonly editOwnerButton: Locator;
  readonly addNewPetButton: Locator;
  
  // Pets and Visits Section
  readonly petsAndVisitsHeading: Locator;
  readonly petsTable: Locator;
  
  // Visit Sorting
  readonly sortAscendingLink: Locator;
  readonly sortDescendingLink: Locator;
  
  // Messages
  readonly successMessage: Locator;
  readonly errorMessage: Locator;

  constructor(page: Page) {
    this.page = page;
    
    // Owner Information Section
    this.ownerInformationHeading = page.getByRole('heading', { name: 'Owner Information' });
    this.ownerNameCell = page.locator('table.table-striped').first().locator('td b');
    this.ownerAddressCell = page.locator('table.table-striped').first().locator('tr:has-text("Address") td');
    this.ownerCityCell = page.locator('table.table-striped').first().locator('tr:has-text("City") td');
    this.ownerTelephoneCell = page.locator('table.table-striped').first().locator('tr:has-text("Telephone") td');
    
    // Buttons
    this.editOwnerButton = page.getByRole('link', { name: 'Edit Owner' });
    this.addNewPetButton = page.getByRole('link', { name: 'Add New Pet' });
    
    // Pets and Visits Section
    this.petsAndVisitsHeading = page.getByRole('heading', { name: 'Pets and Visits' });
    this.petsTable = page.locator('table.table-striped').last();
    
    // Visit Sorting
    this.sortAscendingLink = page.locator('a[title="Sort Ascending"]');
    this.sortDescendingLink = page.locator('a[title="Sort Descending"]');
    
    // Messages
    this.successMessage = page.locator('#success-message');
    this.errorMessage = page.locator('#error-message');
  }

  /**
   * Navigate to the owner details page
   * @param ownerId - The ID of the owner to view
   */
  async goto(ownerId: number) {
    await this.page.goto(`/owners/${ownerId}`);
  }

  /**
   * Navigate to the owner details page with sort order
   * @param ownerId - The ID of the owner to view
   * @param sortOrder - The sort order for visits (asc or desc)
   */
  async gotoWithSortOrder(ownerId: number, sortOrder: 'asc' | 'desc') {
    await this.page.goto(`/owners/${ownerId}?sortOrder=${sortOrder}`);
  }

  /**
   * Get owner name from the page
   */
  async getOwnerName(): Promise<string> {
    return await this.ownerNameCell.textContent() || '';
  }

  /**
   * Get owner address from the page
   */
  async getOwnerAddress(): Promise<string> {
    return await this.ownerAddressCell.textContent() || '';
  }

  /**
   * Get owner city from the page
   */
  async getOwnerCity(): Promise<string> {
    return await this.ownerCityCell.textContent() || '';
  }

  /**
   * Get owner telephone from the page
   */
  async getOwnerTelephone(): Promise<string> {
    return await this.ownerTelephoneCell.textContent() || '';
  }

  /**
   * Click the Edit Owner button
   */
  async clickEditOwner() {
    await this.editOwnerButton.click();
  }

  /**
   * Click the Add New Pet button
   */
  async clickAddNewPet() {
    await this.addNewPetButton.click();
  }

  /**
   * Click the sort ascending link
   */
  async clickSortAscending() {
    await this.sortAscendingLink.click();
  }

  /**
   * Click the sort descending link
   */
  async clickSortDescending() {
    await this.sortDescendingLink.click();
  }

  /**
   * Check if sort ascending link is active (has text-primary class)
   */
  async isSortAscendingActive(): Promise<boolean> {
    const className = await this.sortAscendingLink.getAttribute('class');
    return className?.includes('text-primary') || false;
  }

  /**
   * Check if sort descending link is active (has text-primary class)
   */
  async isSortDescendingActive(): Promise<boolean> {
    const className = await this.sortDescendingLink.getAttribute('class');
    return className?.includes('text-primary') || false;
  }

  /**
   * Get all pet names displayed on the page
   */
  async getPetNames(): Promise<string[]> {
    const petElements = await this.page.locator('dl.dl-horizontal dd').first().allTextContents();
    return petElements;
  }

  /**
   * Get visit dates for a specific pet
   * @param petName - The name of the pet
   */
  async getVisitDatesForPet(petName: string): Promise<string[]> {
    const petRow = this.page.locator(`tr:has-text("${petName}")`);
    const visitDates = await petRow.locator('table.table-condensed tbody tr td').first().allTextContents();
    return visitDates;
  }

  /**
   * Click Edit Pet link for a specific pet
   * @param petName - The name of the pet to edit
   */
  async clickEditPet(petName: string) {
    const petRow = this.page.locator(`tr:has-text("${petName}")`);
    await petRow.getByRole('link', { name: 'Edit Pet' }).click();
  }

  /**
   * Click Add Visit link for a specific pet
   * @param petName - The name of the pet
   */
  async clickAddVisit(petName: string) {
    const petRow = this.page.locator(`tr:has-text("${petName}")`);
    await petRow.getByRole('link', { name: 'Add Visit' }).click();
  }

  /**
   * Check if success message is visible
   */
  async isSuccessMessageVisible(): Promise<boolean> {
    return await this.successMessage.isVisible();
  }

  /**
   * Check if error message is visible
   */
  async isErrorMessageVisible(): Promise<boolean> {
    return await this.errorMessage.isVisible();
  }

  /**
   * Get success message text
   */
  async getSuccessMessage(): Promise<string> {
    return await this.successMessage.textContent() || '';
  }

  /**
   * Get error message text
   */
  async getErrorMessage(): Promise<string> {
    return await this.errorMessage.textContent() || '';
  }

  /**
   * Verify owner information is displayed correctly
   */
  async verifyOwnerInformation(expectedName: string, expectedAddress: string, expectedCity: string, expectedTelephone: string) {
    await expect(this.ownerInformationHeading).toBeVisible();
    await expect(this.ownerNameCell).toHaveText(expectedName);
    await expect(this.ownerAddressCell).toHaveText(expectedAddress);
    await expect(this.ownerCityCell).toHaveText(expectedCity);
    await expect(this.ownerTelephoneCell).toHaveText(expectedTelephone);
  }

  /**
   * Verify that pets and visits section is displayed
   */
  async verifyPetsAndVisitsSection() {
    await expect(this.petsAndVisitsHeading).toBeVisible();
    await expect(this.petsTable).toBeVisible();
  }

  /**
   * Verify page loads successfully
   */
  async verifyPageLoaded() {
    await expect(this.ownerInformationHeading).toBeVisible();
    await expect(this.editOwnerButton).toBeVisible();
    await expect(this.addNewPetButton).toBeVisible();
  }
}
