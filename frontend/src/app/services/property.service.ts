import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators'; 
import { Property } from '../models/property.model';

@Injectable({
  providedIn: 'root',
})
export class PropertyService {
  private baseUrl = 'http://localhost:8081/api/properties';
  private cache = new Map<string, any>(); 

  constructor(private http: HttpClient) {}

  getAllProperties(): Observable<Property[]> {
    const cacheKey = 'getAllProperties';
    if (this.cache.has(cacheKey)) {
      return of(this.cache.get(cacheKey)!);
    }

    return this.http
      .get<Property[]>(`${this.baseUrl}`)
      .pipe(tap((data) => this.cache.set(cacheKey, data)));
  }

  searchProperties(filters: any): Observable<Property[]> {
    const cacheKey = JSON.stringify(filters);
    if (this.cache.has(cacheKey)) {
      return of(this.cache.get(cacheKey)!);
    }

    let params = new HttpParams();
    Object.keys(filters).forEach((key) => {
      if (filters[key]) {
        params = params.append(key, filters[key]);
      }
    });

    return this.http
      .get<Property[]>(`${this.baseUrl}/search`, { params })
      .pipe(tap((data) => this.cache.set(cacheKey, data)));
  }

  getPropertyById(id: number): Observable<Property> {
    const cacheKey = `getPropertyById-${id}`;
    if (this.cache.has(cacheKey)) {
      return of(this.cache.get(cacheKey)!);
    }

    return this.http
      .get<Property>(`${this.baseUrl}/${id}`)
      .pipe(tap((data) => this.cache.set(cacheKey, data)));
  }
}
