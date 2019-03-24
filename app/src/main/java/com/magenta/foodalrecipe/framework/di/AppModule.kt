package com.magenta.foodalrecipe.framework.di

/**
 * Created by ${User} on ${Date}
 */


/*
@Module
open class AppModule(val dao: RecipeDatabaseDao, val food2ForkAPI: Food2ForkAPI) {

    @Provides
    fun coroutineIO(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    @Singleton
    @Provides
    fun provideCache(): Food2ForkLocalCache = Food2ForkLocalCache(dao, coroutineIO())

    @Singleton
    @Provides
    fun provideFood2ForkRepo() = Food2ForkRepository(food2ForkAPI, provideCache())

    @Singleton
    @Provides
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideFood2ForkRepo())
    }
}
*/