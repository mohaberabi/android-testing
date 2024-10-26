package gaur.himanshu.search.domain.generator

import gaur.himanshu.search.domain.model.Recipe


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
}