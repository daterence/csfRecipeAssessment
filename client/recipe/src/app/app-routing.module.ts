import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RecipeListComponent} from "./recipe-list/recipe-list.component";
import {RecipeDetailComponent} from "./recipe-detail/recipe-detail.component";
import {RecipeAddComponent} from "./recipe-add/recipe-add.component";

const routes: Routes = [
  {path: '', component: RecipeListComponent},
  {path: 'recipe/:id', component: RecipeDetailComponent},
  {path: 'add', component: RecipeAddComponent},
  {path: '**', redirectTo: '/', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
