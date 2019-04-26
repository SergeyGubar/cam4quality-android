package io.github.cam4quality.ui.user

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.UsersRepository
import io.github.cam4quality.ui.BaseFragment
import io.github.cam4quality.ui.SpacesItemDecoration
import io.github.cam4quality.ui.factory.FactoriesAdapter
import io.github.cam4quality.utility.extension.bind
import io.github.cam4quality.utility.extension.getSize
import io.github.cam4quality.utility.extension.notNullContext
import io.github.cam4quality.utility.extension.notNullFragmentManager
import io.reactivex.rxkotlin.subscribeBy
import org.koin.android.ext.android.inject
import timber.log.Timber

class UsersFragment : BaseFragment() {

    companion object {
        fun newInstance() = UsersFragment()
    }

    override val layout: Int = R.layout.fragment_users

    private val usersRepository: UsersRepository by inject()
    private val recycler by bind<RecyclerView>(R.id.users_recycler)
    private val fab by bind<FloatingActionButton>(R.id.users_fab)
    private val usersAdapter = UsersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupRecycler()
        loadUsersData()
    }

    private fun setupListeners() {
        fab.setOnClickListener { AddUserDialogFragment.newInstance(::loadUsersData).show(notNullFragmentManager, null) }
    }

    private fun setupRecycler() {
        with(recycler) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(notNullContext)
            adapter = usersAdapter
            addItemDecoration(SpacesItemDecoration(notNullContext.getSize(R.dimen.padding_standard)))
        }
    }

    private fun loadUsersData() {
        compositeDisposable.add(
            usersRepository.getAllUsers()
                .subscribeBy(
                    onError = { err ->
                        Timber.w("error: ${err.localizedMessage}")
                    },
                    onSuccess = { response ->
                        response.fold(
                            { result ->
                                Timber.d("success: $result")
                                usersAdapter.swapData(result)
                            },
                            { err ->
                                Timber.d("error: $err")
                            }
                        )
                    }
                )
        )

    }
}