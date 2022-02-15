package com.example.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.di.ApplicationComponent
import com.example.cryptoapp.data.di.DaggerApplicationComponent
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.workers.RefreshDataWorkerFactory

class CoinApp : Application(), Configuration.Provider {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                RefreshDataWorkerFactory(
                    coinInfoDao = AppDatabase.getInstance(this).coinPriceInfoDao(),
                    apiService = ApiFactory.apiService,
                    mapper = CoinMapper()
                )
            )
            .build()
    }
}