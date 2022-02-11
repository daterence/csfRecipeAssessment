package ibf2021.assessment.csf.server.controllers;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeRestController {

    private final Logger logger = Logger.getLogger(RecipeRestController.class.getName());

    @Autowired
    private RecipeService recipeSvc;

    @GetMapping(path = "{recipeId}")
    public ResponseEntity<String> getRecipe(@PathVariable String recipeId) {
        Optional<Recipe> recipeDetails = recipeSvc.getRecipeById(recipeId);

        if (recipeDetails.isPresent()) {
            logger.info("Recipe for ID: %s exist".formatted(recipeId));
            Recipe recipe = recipeDetails.get();
            JsonArrayBuilder ingredientsBuilder = Json.createArrayBuilder();
            for (String ingredient: recipe.getIngredients()) {
                ingredientsBuilder.add(ingredient);
            }
            JsonArray ingredients = ingredientsBuilder.build();
            logger.info("ingredients >> " + ingredients);

            JsonObject jo = Json.createObjectBuilder()
                    .add("title", recipe.getTitle())
                    .add("id", recipe.getId())
                    .add("image", recipe.getImage())
                    .add("ingredients", ingredients.toString())
                    .add("instruction", recipe.getInstruction())
                    .build();

            return ResponseEntity.ok(jo.toString());
        } else {
            logger.info("Recipe for ID: %s does not exist".formatted(recipeId));
            JsonObject jo = Json.createObjectBuilder()
                    .add("payload","ID invalid")
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jo.toString());
        }

    }
}
