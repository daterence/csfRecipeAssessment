import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {RecipeService} from "../recipe.service";
import {Router} from "@angular/router";
import {Recipe} from "../models";

@Component({
  selector: 'app-recipe-add',
  templateUrl: './recipe-add.component.html',
  styleUrls: ['./recipe-add.component.css']
})
export class RecipeAddComponent implements OnInit {

  form: FormGroup


  constructor(private fb: FormBuilder,
              private recipeSvc: RecipeService,
              private router: Router) { }

  ngOnInit(): void {
    this.createForm()
  }

  createForm() {
    this.form = this.fb.group({
      title: this.fb.control('', Validators.required),
      image: this.fb.control('', Validators.required),
      instruction: this.fb.control('', Validators.required),
      ingredients: this.IngredientsList()
    })
  }

  onAddRecipe() {
    const recipe = this.form.value as Partial<Recipe>

    this.recipeSvc.postRecipe(recipe)
      .then(result => {
        this.form.reset()
        this.router.navigate(['/'])
        console.log(">>> Result >", result)
      })
      .catch(error => {
        alert('An error has occurred')
        console.log(">> Error >> ", error)
      })
    // recipeSvc addRecipe
  }

  Ingredient(ing: string) {
    return this.fb.group({
      name: this.fb.control(ing || '', Validators.required)
    })
  }

  IngredientsList(ingredients: string[] = ['']) {
    const list = this.fb.array([]);
    for (let i of ingredients) {
      list.push(this.Ingredient(i));
    }
    return list;
  }

  onAddIngredient() {
    (<FormArray>this.form.get('ingredients')).push(new FormGroup({
      'name': new FormControl('', Validators.required)
    }))
  }

  get controls() {
    return (<FormArray> this.form.get('ingredients')).controls;
  }

  onDeleteIngredient(index: number) {
    (<FormArray>this.form.get('ingredients')).removeAt(index);
  }
}
