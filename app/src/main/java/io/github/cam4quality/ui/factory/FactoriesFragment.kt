package io.github.cam4quality.ui.factory

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.FactoriesRepository
import io.github.cam4quality.network.repository.LoginRepository
import io.github.cam4quality.ui.BaseFragment
import io.github.cam4quality.ui.SpacesItemDecoration
import io.github.cam4quality.utility.extension.*
import io.github.cam4quality.utility.helper.SharedPrefHelper
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class FactoriesFragment : BaseFragment() {

    companion object {
        fun newInstance() = FactoriesFragment()
    }

    override val layout: Int = R.layout.fragment_factories

    private val recycler by bind<RecyclerView>(R.id.factories_recycler)
    private val fab by bind<FloatingActionButton>(R.id.factories_fab)
    private val factoriesAdapter = FactoriesAdapter()
    private val factoriesRepository: FactoriesRepository by inject()
    private val loginRepository: LoginRepository by inject()
    private val prefHelper: SharedPrefHelper by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupListeners()
        login()
        loadFactoriesData()
    }

    private fun setupListeners() {
        fab.setOnClickListener {
            AddFactoryDialog().show(notNullFragmentManager, null)
        }
    }

    private fun setupRecycler() {
        with(recycler) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(notNullContext)
            adapter = factoriesAdapter
            addItemDecoration(SpacesItemDecoration(notNullContext.getSize(R.dimen.padding_standard)))
        }
    }

    private fun login() {
        val login = prefHelper.getLogin()
        val password = prefHelper.getPassword()
        val loginObservable = loginRepository.login(login, password)
        compositeDisposable.addAll(
            loginObservable.addLoggingSubscriber(),
            loginObservable.subscribeBy(
                onSuccess = { response ->
                    response.onSuccess { result ->
                        result?.let { prefHelper.saveToken(it.token) }
                    }
                },
                onError = { err -> toast("error loading factories ${err.localizedMessage}") }
            )
        )
    }

    private fun loadFactoriesData() {
        val factoriesObservable = factoriesRepository.getAllFactories()
        compositeDisposable.addAll(
            factoriesObservable.addLoggingSubscriber(),
            factoriesObservable
                .subscribeBy(
                    onSuccess = { response ->
                        response.onSuccess { result ->
                            Timber.d("result: $result")
                            result?.let { factoriesAdapter.swapData(it) }
                        }
                    },
                    onError = { toast("Error loading factoriesData ${it.localizedMessage}") }
                )
        )
    }
}