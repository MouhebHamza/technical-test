import { Component, OnInit } from '@angular/core';
import { Property } from '../../../models/property.model';
import { PropertyService } from '../../../services/property.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss'],
  // changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PropertyListComponent implements OnInit {
  public searchBy = 'city';
  public filters = {
    buildingName: '',
    propertyType: '',
    city: '',
    country: '',
    address: '',
    minPrice: 50,
    maxPrice: 500,
    availability: '',
  };

  public searchText: string = '';
  public properties: Property[] = [];
  public paginatedProperties: Property[] = [];
  public currentPage: number = 1;
  public pageSize: number = 6;
  public totalProperties: number = 0;

  constructor(
    private propertyService: PropertyService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.onSubmit();
  }

  onSubmit(): void {
    const payload = {
      ...this.filters,
      minPrice: this.filters.minPrice,
      maxPrice: this.filters.maxPrice,
    };

    this.propertyService.searchProperties(payload).subscribe(
      (data) => {
        this.properties = data;
        this.totalProperties = data.length;
        this.paginateProperties();
      },
      (error) => {
        console.error('Error fetching properties:', error);
      }
    );
  }

  onSearchTextChange(): void {
    this.onSubmit();
  }

  paginateProperties(): void {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    this.paginatedProperties = this.properties.slice(
      startIndex,
      startIndex + this.pageSize
    );
  }

  onPageChange(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.paginateProperties();
    }
  }

  onSelectProperty(property: any): void {
    this.router.navigate([`/reservations/new`], {
      queryParams: { propertyId: property.id },
    });
  }

  get totalPages(): number {
    return Math.ceil(this.totalProperties / this.pageSize);
  }
}
