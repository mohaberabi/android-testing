package gaur.himanshu.searchrecipeapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import gaur.himanshu.search.data.di.SearchDataModule
import gaur.himanshu.search.domain.repository.SearchRepository
import gaur.himanshu.searchrecipeapp.fakes.FakeRepository
import gaur.himanshu.searchrecipeapp.local.AppDatabase
import javax.inject.Singleton


@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SearchDataModule::class]
)
@Module
object TestDi {

    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()


    @Singleton
    @Provides
    fun provideSearchRepository(): SearchRepository = FakeRepository()
}