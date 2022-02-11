package ibf2021.assessment.csf.server.controllers;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeRestController {

    private final Logger logger = Logger.getLogger(RecipeRestController.class.getName());

    @Autowired
    private RecipeService recipeSvc;

    @GetMapping(path = "/{recipeId}")
    public ResponseEntity<String> getRecipe(@PathVariable String recipeId) {
        Optional<Recipe> recipeDetails = recipeSvc.getRecipeById(recipeId);

        if (recipeDetails.isPresent()) {
            logger.info("Recipe for ID: %s exist".formatted(recipeId));
            Recipe recipe = recipeDetails.get();
            JsonObjectBuilder recipeBuilder = Json.createObjectBuilder();
            JsonArrayBuilder ingredientsBuilder = Json.createArrayBuilder();
            recipeBuilder.add("id", recipe.getId());
            recipeBuilder.add("title", recipe.getTitle());
            recipeBuilder.add("image", recipe.getImage());
            recipeBuilder.add("instruction", recipe.getInstruction());

            recipe.getIngredients().forEach(ingredientsBuilder::add);
            recipeBuilder.add("ingredients", ingredientsBuilder);

            return ResponseEntity.ok(recipeBuilder.build().toString());
        } else {
            logger.info("Recipe for ID: %s does not exist".formatted(recipeId));
            JsonObject jo = Json.createObjectBuilder()
                    .add("payload","ID invalid")
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jo.toString());
        }

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postRecipe(@RequestBody String request) {
        logger.info("Recipe" + request);

        Recipe recipe = new Recipe();

        try (InputStream is = new ByteArrayInputStream(request.getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject data = reader.readObject();

            recipe.setTitle(data.getString("title"));
            recipe.setImage(data.getString("image"));
            recipe.setInstruction(data.getString("instruction"));

            JsonArray ingredients = data.getJsonArray("ingredients");
            for (JsonValue ingredient: ingredients) {
                recipe.addIngredient(ingredient.toString().replaceAll("^\"|\"$", ""));
            }

            recipeSvc.addRecipe(recipe);

            JsonObject jo = Json.createObjectBuilder()
                    .add("message", "created")
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(jo.toString());
        } catch (Exception e) {
            logger.info("CATCH >>> %s".formatted(e));

            JsonObject jo = Json.createObjectBuilder()
                    .add("error", e.toString())
                    .build();

            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(jo.toString());
        }

    }


}
