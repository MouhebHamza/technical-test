import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservationListComponent } from './components/reservation-list/reservation-list.component';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MakeReservationComponent } from './components/make-reservation/make-reservation.component';
import { ScrollingModule } from '@angular/cdk/scrolling';

@NgModule({
  declarations: [ReservationListComponent, MakeReservationComponent],
  imports: [
    CommonModule,
    FormsModule,
    ScrollingModule,
    RouterModule.forChild([
      { path: '', component: ReservationListComponent },
      { path: 'new', component: MakeReservationComponent },
    ]),
  ],
})
export class ReservationModule {}
