package gaur.himanshu.search.ui.generator

import gaur.himanshu.search.domain.model.Recipe
import gaur.himanshu.search.domain.model.RecipeDetails


object FakeRecipeGenerator {
    val recipe = Recipe(
        idMeal = "idMeal",
        strArea = "India",
        strCategory = "category",
        strYoutube = "strYoutube",
        strTags = "tag1,tag2",
        strMeal = "Chicken",
        strMealThumb = "strMealThumb",
        strInstructions = "strInstructions",
    )
    val details = RecipeDetails(
        "",
        "", "",
        "", "", "", "", "", listOf()
    )
}