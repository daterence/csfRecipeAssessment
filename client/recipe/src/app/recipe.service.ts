import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Recipe, RecipeSummary, ResponseMessage} from "./models";
import {lastValueFrom} from "rxjs";

const URL_API_RECIPE = 'http://localhost:8080/api/';

@Injectable()
export class RecipeService {

  constructor(private http: HttpClient) { }

  // Task 4
  getAllRecipes(): Promise<RecipeSummary[]> {

    return lastValueFrom(
      this.http.get<RecipeSummary[]>( URL_API_RECIPE + 'recipes')
    )
  }
  // Task 5
  getRecipe(id: string): Promise<Recipe> {
    console.log(id)
    return lastValueFrom(
      this.http.get<Recipe>(URL_API_RECIPE + 'recipe/' + id)
    )
  }

  postRecipe(recipe: Partial<Recipe>): Promise<ResponseMessage> {
    const url = URL_API_RECIPE + 'recipe';
    console.log("SERVICE >>", recipe)

    const headers = {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }

    return lastValueFrom(
      this.http.post<ResponseMessage>(url, JSON.stringify(recipe), {headers})
    )
  }
}
