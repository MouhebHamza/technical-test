describe('Property List Component', () => {
  beforeEach(() => {
    cy.visit('/properties');
  });

  it('should display properties based on city search', () => {
    cy.get('input[placeholder="Search by City"]')
      .type('New York')
      .should('have.value', 'New York');

    cy.wait(1000);
  });

  it('should apply filters and display properties accordingly', () => {
    cy.get('select[name="propertyType"]').select('hotel');

    cy.get('input[name="buildingName"]').type('Sunset Hotel');

    cy.get('input[name="minPrice"]').clear().type('100');
    cy.get('input[name="maxPrice"]').clear().type('300');

    cy.get('select[name="availabilityStatus"]').select('Available');

    cy.get('form').submit();

    cy.wait(1000);
  });

  it('should navigate to the reservation page when a property is selected', () => {
    cy.get('.cards-grid .card').first().click();

    cy.url().should('include', '/reservations/new');
    cy.url().should('include', 'propertyId=');
  });
});
