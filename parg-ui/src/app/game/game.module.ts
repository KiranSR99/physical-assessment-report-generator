import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GameRoutingModule } from './game-routing.module';
import { ListGamesComponent } from './list-games/list-games.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    ListGamesComponent
  ],
  imports: [
    CommonModule,
    GameRoutingModule,
    ReactiveFormsModule
  ]
})
export class GameModule { }
