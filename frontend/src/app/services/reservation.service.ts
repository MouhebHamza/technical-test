import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Reservation } from '../models/reservation.model';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  private baseUrl = 'http://localhost:8081/api/reservations';
  private cache = new Map<string, Reservation[]>();

  constructor(private http: HttpClient) {}

  getReservationsByDateRange(
    start: string,
    end: string
  ): Observable<Reservation[]> {
    const cacheKey = `${start}-${end}`;
    if (this.cache.has(cacheKey)) {
      return of(this.cache.get(cacheKey)!);
    }

    const params = new HttpParams().set('start', start).set('end', end);
    return this.http
      .get<Reservation[]>(`${this.baseUrl}/dates`, { params })
      .pipe(tap((data) => this.cache.set(cacheKey, data)));
  }

  createReservation(
    reservationData: Reservation | any
  ): Observable<Reservation> {
    
    return this.http.post<Reservation>(this.baseUrl, reservationData);
  }

  getReservations(filters: any): Observable<Reservation[]> {
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
      .get<Reservation[]>(`${this.baseUrl}/search`, { params })
      .pipe(tap((data) => this.cache.set(cacheKey, data)));
  }
}
