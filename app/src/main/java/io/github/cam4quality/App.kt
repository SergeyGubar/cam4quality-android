package io.github.cam4quality

import android.app.Application
import com.google.gson.GsonBuilder
import io.github.cam4quality.network.api.*
import io.github.cam4quality.network.constant.NetworkConstants
import io.github.cam4quality.network.repository.*
import io.github.cam4quality.utility.helper.SharedPrefHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class App : Application() {

    private val networkModule = module {
        single {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }

    private val loginModule = module {
        single { get<Retrofit>().create(LoginApi::class.java) }
        factory { LoginRepository(get()) }
    }

    private val factoriesModule = module {
        single { get<Retrofit>().create(FactoriesApi::class.java) }
        factory { FactoriesRepository(get(), get()) }
    }

    private val utilsModule = module {
        single { SharedPrefHelper(get()) }
    }

    private val usersModule = module {
        single { get<Retrofit>().create(UsersApi::class.java) }
        factory { UsersRepository(get(), get()) }
    }

    private val qualityParamsModule = module {
        single { get<Retrofit>().create(QualityParamsApi::class.java) }
        factory { QualityParamsRepository(get(), get()) }
    }

    private val qualityParamsDeviationsModule = module {
        single { get<Retrofit>().create(QualityParamsDeviationsApi::class.java) }
        factory { QualityParamsDeviationsRepository(get(), get()) }
    }

    private val modules = listOf(
        networkModule,
        factoriesModule,
        loginModule,
        utilsModule,
        usersModule,
        qualityParamsModule,
        qualityParamsDeviationsModule
    )

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            modules(this@App.modules)
            androidContext(this@App)
        }
    }
}