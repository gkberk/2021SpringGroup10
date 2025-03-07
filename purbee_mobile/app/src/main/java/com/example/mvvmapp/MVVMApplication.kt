package com.example.mvvmapp

import android.app.Application
import com.example.mvvmapp.data.db.AppDatabase
import com.example.mvvmapp.data.network.MyApi
import com.example.mvvmapp.data.network.NetworkConnectionInterceptor
import com.example.mvvmapp.data.preferences.PreferenceProvider
import com.example.mvvmapp.data.repositories.QuotesRepository
import com.example.mvvmapp.data.repositories.UserRepository
import com.example.mvvmapp.ui.auth.AuthViewModelFactory
import com.example.mvvmapp.ui.home.profile.ProfileViewModelFactory
import com.example.mvvmapp.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


// This is the base class of our application
// It is instantiated before anything else
class MVVMApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }

    }
}