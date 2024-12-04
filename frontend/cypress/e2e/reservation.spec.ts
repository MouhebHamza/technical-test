describe('ReservationService E2E Tests', () => {
  it('should fetch reservations by date range', () => {
    cy.intercept('GET', '/api/reservations').as('getReservations');

    cy.get('button.fetch-reservations').should('be.visible').click();

    cy.wait('@getReservations', { timeout: 40000 });

    cy.get('.reservation-list').should('have.length.greaterThan', 0);
  });

  it('should create a new reservation', () => {
    cy.get('button.create-reservation').should('be.visible').click();
    cy.get('.reservation-confirmation').should(
      'contain',
      'Reservation successfully created'
    );
  });
});
