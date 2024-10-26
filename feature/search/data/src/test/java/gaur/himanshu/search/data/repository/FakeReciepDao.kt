package gaur.himanshu.search.data.repository

import gaur.himanshu.search.data.local.RecipeDao
import gaur.himanshu.search.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRecipeDao : RecipeDao {

    val recipes = mutableListOf<Recipe>()
    override suspend fun insertRecipe(recipe: Recipe) {
        recipes.add(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipes.remove(recipe)
    }

    override fun getAllRecipes(): Flow<List<Recipe>> {

        return flowOf(recipes)
    }

    override suspend fun updateRecipe(recipe: Recipe) {

    }
}