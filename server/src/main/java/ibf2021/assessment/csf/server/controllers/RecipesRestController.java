package ibf2021.assessment.csf.server.controllers;

/* Write your request hander in this file */

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipesRestController {

    private final Logger logger = Logger.getLogger(RecipesRestController.class.getName());

    @Autowired
    private RecipeService recipeSvc;

    @GetMapping
    public ResponseEntity<String> getAllRecipes() {
        List<Recipe> recipes = recipeSvc.getAllRecipes();
        List<String> recipesString = new LinkedList<>();

        for (Recipe recipe: recipes) {
            JsonObject jo = Json.createObjectBuilder()
                    .add("id", recipe.getId())
                    .add("title", recipe.getTitle())
                    .build();

            recipesString.add(jo.toString());
        }
        return ResponseEntity.ok(recipesString.toString());
    }

}
