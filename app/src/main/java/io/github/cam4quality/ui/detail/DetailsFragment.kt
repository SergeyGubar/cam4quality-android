package io.github.cam4quality.ui.detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.DetailResponseModel
import io.github.cam4quality.network.repository.DetailsRepository
import io.github.cam4quality.ui.BaseFragment
import io.github.cam4quality.utility.extension.addToContainer
import io.github.cam4quality.utility.extension.defaultSetup
import io.github.cam4quality.utility.extension.lazyBind
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class DetailsFragment : BaseFragment() {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    override val layout: Int = R.layout.fragment_details

    private val adapter = DetailsAdapter(::onLongDetailClick)
    private val repository: DetailsRepository by inject()
    private val recycler by lazyBind<RecyclerView>(R.id.details_recycler)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        loadDetails()
    }

    private fun setupRecycler() {
        recycler.defaultSetup(adapter)
    }

    private fun loadDetails() {
        repository.getAllDetails()
            .subscribeBy(
                onError = { Timber.w("error: ${it.localizedMessage}".also { toast("Error loading details info!") }) },
                onSuccess = { result ->
                    result.fold(
                        { response ->
                            Timber.d("success: loaded [${response.size}] details")
                            adapter.swapData(response)
                        },
                        { Timber.w("error: ${it.localizedMessage}".also { toast("Error loading details info!") }) }
                    )
                }
            ).addToContainer(compositeDisposable)
    }

    private fun onLongDetailClick(detail: DetailResponseModel) {
        repository.removeDetail(detail.id)
            .subscribeBy(
                onError = { Timber.d("error: ${it.localizedMessage}".also { toast("Error removing detail!") }) },
                onSuccess = {
                    Timber.d("success $it")
                    adapter.removeItem(detail.id)
                    toast("Removed ${detail.id}")
                }
            )
            .addToContainer(compositeDisposable)
    }
}