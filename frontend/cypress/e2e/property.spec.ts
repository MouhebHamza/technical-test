describe('PropertyService E2E Tests', () => {
  it('should fetch a list of properties', () => {
    cy.intercept('GET', '/api/properties').as('getProperties');

    cy.get('button.fetch-properties').should('be.visible').click();

    cy.wait('@getProperties', { timeout: 40000 });

    cy.get('.property-list').should('have.length.greaterThan', 0);

    cy.get('.property-list').first().should('contain', 'Property Name');
  });

  it('should create a new property', () => {
    cy.get('button.create-property').should('be.visible').click();

    cy.get('input[name="property-name"]').type('New Property');
    cy.get('input[name="address"]').type('123 New Street');
    cy.get('input[name="price"]').type('500');

    cy.get('button.submit').click();

    cy.wait('@createProperty', { timeout: 5000 });

    cy.get('.property-list').should('contain', 'New Property');

    cy.get('.property-confirmation').should(
      'contain',
      'Property successfully created'
    );
  });
});
