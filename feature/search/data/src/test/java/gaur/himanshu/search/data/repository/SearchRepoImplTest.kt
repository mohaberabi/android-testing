package gaur.himanshu.search.data.repository

import gaur.himanshu.search.data.generator.FakeRecipeGenerator
import gaur.himanshu.search.data.local.RecipeDao
import gaur.himanshu.search.data.mappers.toDomain
import gaur.himanshu.search.data.model.RecipeDetailsResponse
import gaur.himanshu.search.data.model.RecipeResponse
import gaur.himanshu.search.data.remote.SearchApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class SearchRepoImplTest() {


    private lateinit var repo: SearchRepoImpl


    private lateinit var api: SearchApiService
    private lateinit var recipeDao: FakeRecipeDao


    @Before
    fun setup() {
        api = mock()
        recipeDao = FakeRecipeDao()
        repo = SearchRepoImpl(api, recipeDao)
    }

    @Test
    fun ` get recipes returns success with recipes`() = runTest {
        val recipes = FakeRecipeGenerator.getRecipeResponse()

        `when`(api.getRecipes("pizza")).thenReturn(
            Response.success(
                200,
                recipes
            )
        )

        val result = repo.getRecipes("pizza")

        assertEquals(recipes.meals?.toDomain(), result.getOrThrow())
    }


    @Test
    fun `when  get recipes returns null result failure is returned `() = runTest {

        `when`(api.getRecipes("pizza")).thenReturn(
            Response.success(
                200,
                RecipeResponse()
            )
        )

        val result = repo.getRecipes("pizza")
        val message = "error occurred"
        assertEquals(message, result.exceptionOrNull()?.message)
    }

    @Test
    fun `when  get recipes returns failure  result failure is returned `() = runTest {

        `when`(api.getRecipes("pizza")).thenReturn(
            Response.error(404, ResponseBody.create(null, ""))
        )

        val result = repo.getRecipes("pizza")
        val message = "error occurred"
        assertEquals(message, result.exceptionOrNull()?.message)
    }

    @Test
    fun `when  get recipes throws  exception  result failure is returned `() = runTest {

        `when`(api.getRecipes("pizza")).thenThrow(
            RuntimeException("error")
        )

        val result = repo.getRecipes("pizza")
        val message = "error"
        assertEquals(message, result.exceptionOrNull()?.message)
    }

    @Test
    fun ` get recipesDetail returns success with recipes`() = runTest {
        val details = FakeRecipeGenerator.getRecipeDetails()

        `when`(api.getRecipeDetails("pizza")).thenReturn(
            Response.success(
                200,
                details
            )
        )

        val result = repo.getRecipeDetails("pizza")

        assertEquals(details.meals?.first()?.toDomain(), result.getOrThrow())
    }

    @Test
    fun `when  get recipesDetail returns null result failure is returned `() = runTest {

        `when`(api.getRecipeDetails("pizza")).thenReturn(
            Response.success(
                200,
                RecipeDetailsResponse(meals = emptyList())
            )
        )

        val result = repo.getRecipeDetails("pizza")
        val message = "error occurred"
        assertEquals(message, result.exceptionOrNull()?.message)
    }

    @Test
    fun `when  get recipesDetail returns failure  result failure is returned `() = runTest {

        `when`(api.getRecipeDetails("pizza")).thenReturn(
            Response.error(
                404,
                ResponseBody.create(null, "")
            )
        )

        val result = repo.getRecipeDetails("pizza")
        val message = "error occurred"
        assertEquals(message, result.exceptionOrNull()?.message)
    }

    @Test
    fun `when  get recipesDetail throws exception  result failure is returned `() = runTest {

        `when`(api.getRecipeDetails("pizza")).thenThrow(
            RuntimeException("oops")
        )

        val result = repo.getRecipeDetails("pizza")
        val message = "oops"
        assertEquals(message, result.exceptionOrNull()?.message)
    }

    @Test
    fun `recipe added to local  source  `() = runTest {
        val recipe = FakeRecipeGenerator.getRecipeResponse().meals!!.toDomain().first()
        repo.insertRecipe(recipe)
        val expectedSize = 1
        assertEquals(recipeDao.recipes.size, expectedSize)

    }

    @Test
    fun `recipe removed  from  local  source  `() = runTest {
        val recipe = FakeRecipeGenerator.getRecipeResponse().meals!!.toDomain().first()
        repo.insertRecipe(recipe)
        assertEquals(recipeDao.recipes.size, 1)
        repo.deleteRecipe(recipe)
        assertEquals(recipeDao.recipes.size, 0)

    }

    @Test
    fun `get recipes returns correctly from the local source`() = runTest {
        val recipe = FakeRecipeGenerator.getRecipeResponse().meals!!.toDomain().first()
        repo.insertRecipe(recipe)
        assertEquals(recipeDao.recipes.size, 1)
        val recipes = repo.getAllRecipes().toList().first()
        assertEquals(recipes.first(), recipe)

    }
}