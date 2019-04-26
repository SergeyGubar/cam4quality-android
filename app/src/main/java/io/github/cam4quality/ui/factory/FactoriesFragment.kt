package io.github.cam4quality.ui.factory

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.FactoriesRepository
import io.github.cam4quality.ui.BaseFragment
import io.github.cam4quality.ui.SpacesItemDecoration
import io.github.cam4quality.utility.extension.addLoggingSubscriber
import io.github.cam4quality.utility.extension.bind
import io.github.cam4quality.utility.extension.getSize
import io.github.cam4quality.utility.extension.notNullContext
import io.reactivex.rxkotlin.subscribeBy
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        loadFactoriesData()
        setupListeners()
    }

    private fun setupListeners() {
        fab.setOnClickListener {
            AddFactoryDialog().show(fragmentManager!!, "asd")
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
                    }
                )
        )
    }
}