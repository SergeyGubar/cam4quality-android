package io.github.cam4quality.ui.main

import android.os.Build
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import io.github.cam4quality.BaseActivity
import io.github.cam4quality.R
import io.github.cam4quality.ui.factory.FactoriesFragment
import io.github.cam4quality.utility.extension.bind
import io.github.cam4quality.utility.extension.inTransaction

class MainActivity : BaseActivity() {

    private val toolbar by bind<Toolbar>(R.id.main_toolbar)
    private val container by bind<FrameLayout>(R.id.main_fragment_container)

    private val navClickListener by lazy {
        NavigationIconClickListener(this, container, AccelerateDecelerateInterpolator())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        initInitialFragment()
    }

    private fun initInitialFragment() {
        supportFragmentManager.inTransaction {
            add(R.id.main_fragment_container, FactoriesFragment.newInstance())
        }
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener(navClickListener)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            container.background = getDrawable(R.drawable.background_shape)
        }
    }

}
