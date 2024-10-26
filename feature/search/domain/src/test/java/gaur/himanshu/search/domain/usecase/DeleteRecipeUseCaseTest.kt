package gaur.himanshu.search.domain.usecase

import gaur.himanshu.search.domain.generator.FakeRecipeGenerator
import gaur.himanshu.search.domain.repository.SearchRepository
import gaur.himanshu.search.domain.use_cases.DeleteRecipeUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class DeleteRecipeUseCaseTest {


    private lateinit var repo: SearchRepository
    private lateinit var deleteRecipe: DeleteRecipeUseCase


    @Before
    fun setup() {
        repo = mock()
        deleteRecipe = DeleteRecipeUseCase(repo)
    }


    @Test
    fun `when repo returns done use case also returns done`() = runTest {
        val recipe = FakeRecipeGenerator.recipe
        `when`(repo.deleteRecipe(recipe)).thenReturn(Unit)
        val result = deleteRecipe(recipe).first()

    }
}