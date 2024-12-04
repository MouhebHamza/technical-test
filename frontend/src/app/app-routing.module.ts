import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: '/properties', pathMatch: 'full' },
  {
    path: 'properties',
    loadChildren: () =>
      import('./property/property.module').then((m) => m.PropertyModule),
  },
  {
    path: 'reservations',
    loadChildren: () =>
      import('./reservation/reservation.module').then(
        (m) => m.ReservationModule
      ),
  },
  { path: '**', redirectTo: '/properties' }, 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
