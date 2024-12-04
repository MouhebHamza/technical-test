import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyService } from '../../../services/property.service';
import { ReservationService } from '../../../services/reservation.service';

interface Property {
  id: number;
  buildingName: string;
  propertyType: string;
  city: string;
  country: string;
  address: string;
  price: number;
  availability: string;
}

interface ReservationData {
  property: Property;
  moneySpent?: number;
  fromDate: Date;
  toDate: Date;
}

@Component({
  selector: 'app-reservation-form',
  templateUrl: './make-reservation.component.html',
  styleUrls: ['./make-reservation.component.scss'],
  // changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MakeReservationComponent implements OnInit {
  public reservationData: ReservationData = {
    property: {
      id: 0,
      buildingName: '',
      propertyType: '',
      city: '',
      country: '',
      address: '',
      price: 0,
      availability: '',
    },
    moneySpent: 0,
    fromDate: new Date(),
    toDate: new Date(),
  };

  public successMessage: string = '';
  public errorMessage: string = '';
  public reservationSubmitted: boolean = false;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private propertyService: PropertyService,
    private reservationService: ReservationService
  ) {}

  ngOnInit(): void {
    const propertyId = this.activatedRoute.snapshot.queryParams['propertyId'];

    if (propertyId) {
      this.propertyService.getPropertyById(propertyId).subscribe(
        (property) => {
          this.reservationData.property = property;
        },
        (error) => {
          this.errorMessage = 'Error loading property details';
        }
      );
    } else {
      this.errorMessage = 'Property ID is missing';
    }
  }

  formatDate(date: Date): string {
    if (!date) return '';

    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    const hours = ('0' + date.getHours()).slice(-2);
    const minutes = ('0' + date.getMinutes()).slice(-2);
    const seconds = ('0' + date.getSeconds()).slice(-2);
    const milliseconds = ('00' + date.getMilliseconds()).slice(-3);

    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}.${milliseconds}`;
  }

  onSubmit(): void {
    if (
      !this.reservationData.property.id ||
      !this.reservationData.fromDate ||
      !this.reservationData.toDate
    ) {
      this.errorMessage = 'Please fill all fields correctly.';
      return;
    }

    const fromDate = new Date(this.reservationData.fromDate);
    const toDate = new Date(this.reservationData.toDate);

    const reservationPayload = {
      propertyId: this.reservationData.property.id,
      fromDate: this.formatDate(fromDate),
      toDate: this.formatDate(toDate),
    };

    this.reservationService.createReservation(reservationPayload).subscribe(
      (response) => {
        this.successMessage = 'Reservation successfully confirmed!';
        this.reservationSubmitted = true;
      },
      (error) => {
        if (error.status === 409) {
          this.errorMessage =
            error.error || 'There was a conflict with your reservation.';
        } else {
          this.errorMessage =
            'There was an error with your reservation. Please try again.';
        }
        console.error('Error creating reservation:', error);
      }
    );
  }

  resetForm(): void {
    this.reservationData = {
      property: {
        id: 0,
        buildingName: '',
        propertyType: '',
        city: '',
        country: '',
        address: '',
        price: 0,
        availability: '',
      },
      moneySpent: 0,
      fromDate: new Date(),
      toDate: new Date(),
    };
    this.successMessage = '';
    this.errorMessage = '';
    this.reservationSubmitted = false;
  }

  navigateToProperties(): void {
    this.router.navigate(['/properties']);
  }
}
