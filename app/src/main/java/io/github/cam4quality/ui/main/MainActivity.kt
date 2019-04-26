package io.github.cam4quality.ui.main

import android.os.Build
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import io.github.cam4quality.BaseActivity
import io.github.cam4quality.R
import io.github.cam4quality.ui.factory.FactoriesFragment
import io.github.cam4quality.utility.extension.bind
import io.github.cam4quality.utility.extension.inTransaction

class MainActivity : BaseActivity() {

    private val nestedScrollView by bind<NestedScrollView>(R.id.main_nested_scroll)
    private val toolbar by bind<Toolbar>(R.id.main_toolbar)

    private val navClickListener by lazy {
        NavigationIconClickListener(this, nestedScrollView, AccelerateDecelerateInterpolator())
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
            nestedScrollView.background = getDrawable(R.drawable.background_shape)
        }
    }

}
