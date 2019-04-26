package io.github.cam4quality.ui.main

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import io.github.cam4quality.BaseActivity
import io.github.cam4quality.R
import io.github.cam4quality.ui.factory.FactoriesFragment
import io.github.cam4quality.ui.user.UsersFragment
import io.github.cam4quality.utility.extension.bind
import io.github.cam4quality.utility.extension.inTransaction

class MainActivity : BaseActivity(), View.OnClickListener {

    private val toolbar by bind<Toolbar>(R.id.main_toolbar)
    private val container by bind<FrameLayout>(R.id.main_fragment_container)
    private val usersButton by bind<Button>(R.id.main_users_button)
    private val factoriesButton by bind<Button>(R.id.main_factories_button)

    private val navClickListener by lazy {
        NavigationIconClickListener(this, container, AccelerateDecelerateInterpolator())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        initInitialFragment()
        setupListeners()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_users_button -> showFragment(UsersFragment.newInstance())
            R.id.main_factories_button -> showFragment(FactoriesFragment.newInstance())
        }
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener(navClickListener)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            container.background = getDrawable(R.drawable.background_shape)
        }
    }

    private fun initInitialFragment() {
        supportFragmentManager.inTransaction {
            add(R.id.main_fragment_container, FactoriesFragment.newInstance())
        }
    }

    private fun setupListeners() {
        // At this moment, i started to regret implementing backdrop navigation..
        usersButton.setOnClickListener(this)
        factoriesButton.setOnClickListener(this)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction {
            replace(R.id.main_fragment_container, fragment)
        }
        navClickListener.toggle()
    }
}
