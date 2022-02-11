import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Recipe} from "../models";
import {ActivatedRoute, Router} from "@angular/router";
import {RecipeService} from "../recipe.service";

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css']
})
export class RecipeDetailComponent implements OnInit {

  recipe: Recipe;
  id: string;
  ingredients: string[] = [];



  constructor(private route: ActivatedRoute,
              private recipeSvc: RecipeService,
              private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getRecipe(this.id);
  }

  getRecipe(id: string) {
    console.log("recipe-detail testing")
    this.recipeSvc.getRecipe(id)
      .then(r => {
        this.recipe = r
        console.log('recipe > ', typeof this.recipe)
        this.ingredients = this.recipe.ingredients

      });
  }

}
