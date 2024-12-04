import { Property } from './property.model'; 

export interface Reservation {
  id?: number;
  propertyId: number;
  moneySpent?: number;
  fromDate: Date;
  toDate: Date;
  property?: Property;
}
