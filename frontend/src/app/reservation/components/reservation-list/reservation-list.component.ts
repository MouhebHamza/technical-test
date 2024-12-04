import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { Reservation } from '../../../models/reservation.model'
import { ReservationService } from '../../../services/reservation.service'
import { PropertyService } from '../../../services/property.service'
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  // changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReservationListComponent implements OnInit {
  public reservations: Reservation[] = [];
  public paginatedReservations: Reservation[] = [];
  public currentPage = 1;
  public readonly pageSize = 8;
  public totalReservations = 0;
  public totalMoneySpentHotel = 0;
  public totalMoneySpentApartment = 0;
  public mostPopularCity = '';

  public loading = false;

  public filters = {
    propertyType: '',
    buildingName: '',
    city: '',
    address: '',
    country: '',
    fromDate: '',
    toDate: '',
    minPrice: 0,
    maxPrice: 10000,
  };

  constructor(
    private reservationService: ReservationService,
    private propertyService: PropertyService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadReservations();
  }

  loadReservations(): void {
    this.loading = true;
    const filtersWithCorrectDate = {
      ...this.filters,
      fromDate: this.formatDate(this.filters.fromDate),
      toDate: this.formatDate(this.filters.toDate),
    };

    this.reservationService.getReservations(filtersWithCorrectDate).subscribe(
      (data) => {
        this.reservations = data;
        this.totalReservations = data.length;
        this.loadPropertiesForReservations();
        this.paginateReservations();
        this.calculateSummary();
      },
      (error) => {
        console.error('Error loading reservations:', error);
      }
    );
  }

  loadPropertiesForReservations(): void {
    const propertyRequests = this.reservations.map((reservation) => {
      if (reservation.propertyId) {
        return this.propertyService
          .getPropertyById(reservation.propertyId)
          .toPromise()
          .then(
            (property) => {
              reservation.property = property;
            },
            (error) => {
              console.error('Error loading property for reservation:', error);
            }
          );
      }
      return Promise.resolve();
    });

    Promise.all(propertyRequests).then(() => {
      this.paginateReservations();
      this.loading = false;
    });
  }

  paginateReservations(): void {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    this.paginatedReservations = this.reservations.slice(
      startIndex,
      startIndex + this.pageSize
    );
  }

  onPageChange(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.paginateReservations();
    }
  }

  onSubmit(): void {
    this.loadReservations();
  }

  formatDate(date: string): string {
    if (!date) return '';
    const [year, month, day] = date.split('-');
    return `${year}-${month}-${day}T00:00:00`;
  }

  calculateSummary(): void {
    this.totalMoneySpentHotel = this.reservations
      .filter(
        (reservation) => reservation.property?.propertyType === 'Hotel Room'
      )
      .reduce((sum, reservation) => sum + (reservation.moneySpent ?? 0), 0);

    this.totalMoneySpentApartment = this.reservations
      .filter(
        (reservation) => reservation.property?.propertyType === 'Apartment'
      )
      .reduce((sum, reservation) => sum + (reservation.moneySpent ?? 0), 0);

    const cityCounts = this.reservations.reduce((acc, reservation) => {
      const city = reservation.property?.city;
      if (city) {
        acc[city] = (acc[city] || 0) + 1;
      }
      return acc;
    }, {} as { [key: string]: number });

    this.mostPopularCity =
      Object.keys(cityCounts).length > 0
        ? Object.keys(cityCounts).reduce((a, b) =>
            cityCounts[a] > cityCounts[b] ? a : b
          )
        : 'No Reservations';
  }

  get totalPages(): number {
    return Math.ceil(this.totalReservations / this.pageSize);
  }
}