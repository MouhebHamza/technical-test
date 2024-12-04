describe('Reservation List Component', () => {
  beforeEach(() => {
    cy.intercept('GET', '/api/reservations', {
      fixture: 'reservations.json',
    }).as('getReservations');
    cy.intercept('GET', '/api/properties/*', {
      fixture: 'property-details.json',
    }).as('getProperty');
    cy.visit('/reservations');
  });

  it('should load and display the loading spinner when fetching data', () => {
    cy.get('.spinner-border', { timeout: 10000 }).should('exist');

    cy.get('p').should('contain', 'Loading reservations, please wait...');
  });

  it('should load reservations and display them correctly after fetching data', () => {
    cy.wait('@getReservations', { timeout: 10000 });
    cy.get('.card').should('have.length', 3);

    cy.get('.card-body').eq(0).should('contain', 'Hotel Room');
    cy.get('.card-body').eq(0).should('contain', 'New York');
    cy.get('.card-body').eq(0).should('contain', '$200');
    cy.get('.card-body').eq(0).should('contain', 'From Date: 2024-12-01');
    cy.get('.card-body').eq(0).should('contain', 'To Date: 2024-12-10');
  });

  it('should apply filters and show the correct reservations based on selected filters', () => {
    cy.get('select[name="propertyType"]').select('hotel');
    cy.get('input[name="city"]').type('New York');
    cy.get('button[type="submit"]').click();

    cy.wait('@getReservations', { timeout: 10000 });

    cy.get('.card').should('have.length', 1);
    cy.get('.card-body').eq(0).should('contain', 'Hotel Room');
    cy.get('.card-body').eq(0).should('contain', 'New York');
  });

  it('should show the correct summary information', () => {
    cy.wait('@getReservations', { timeout: 10000 });

    cy.get('.summary-item')
      .eq(0)
      .should('contain', 'Total Money Spent on Hotel Rooms: $200');
    cy.get('.summary-item')
      .eq(1)
      .should('contain', 'Total Money Spent on Apartments: $0');
    cy.get('.summary-item')
      .eq(2)
      .should('contain', 'City with Most Reservations: New York');
  });

  it('should paginate reservations correctly', () => {
    cy.wait('@getReservations', { timeout: 10000 });

    cy.get('button').contains('Next').click();
    cy.wait('@getReservations', { timeout: 10000 });

    cy.get('.card').should('have.length', 3);

    cy.get('button').contains('Previous').should('not.be.disabled');

    cy.get('button').contains('Next').should('be.disabled');
  });

  it('should disable the "Previous" button on the first page', () => {
    cy.wait('@getReservations', { timeout: 10000 });

    cy.get('button').contains('Previous').should('be.disabled');
  });

  it('should show correct reservation details on the card', () => {
    cy.wait('@getReservations', { timeout: 10000 });

    cy.get('.card').each((card, index) => {
      cy.wrap(card).find('.card-body').should('contain', 'Hotel Room');
      cy.wrap(card).find('.card-body').should('contain', 'New York');
      cy.wrap(card).find('.card-body').should('contain', '$200');
      cy.wrap(card)
        .find('.card-body')
        .should('contain', 'From Date: 2024-12-01');
      cy.wrap(card).find('.card-body').should('contain', 'To Date: 2024-12-10');
    });
  });

  it('should handle empty reservation list gracefully', () => {
    cy.intercept('GET', '/api/reservations', { body: [] }).as(
      'getNoReservations'
    );
    cy.visit('/reservations');

    cy.wait('@getNoReservations', { timeout: 10000 });

    cy.get('.card').should('have.length', 0);

    cy.get('.pagination').should('not.exist');
  });
});
