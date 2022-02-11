import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Recipe, RecipeSummary} from "./models";
import {lastValueFrom} from "rxjs";

@Injectable()
export class RecipeService {

  constructor(private http: HttpClient) { }

  // Task 4
  getAllRecipes(): Promise<RecipeSummary[]> {

    return lastValueFrom(
      this.http.get<RecipeSummary[]>('http://localhost:8080/api/recipes')
    )
  }
  // Task 5
  getRecipe(id: string): Promise<Recipe> {
    console.log(id)
    return lastValueFrom(
      this.http.get<Recipe>('http://localhost:8080/api/recipe/'.concat(id))
    )
  }
}
