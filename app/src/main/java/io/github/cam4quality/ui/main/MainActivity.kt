package io.github.cam4quality.ui.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import io.github.cam4quality.R
import io.github.cam4quality.utility.extension.bind

class MainActivity : AppCompatActivity() {

    private val nestedScrollView by bind<NestedScrollView>(R.id.main_factories_grid)
    private val recycler by bind<RecyclerView>(R.id.main_factories_recycler)
    private val toolbar by bind<Toolbar>(R.id.main_toolbar)

    private val navClickListener by lazy {
        NavigationIconClickListener(this, nestedScrollView, AccelerateDecelerateInterpolator())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.setNavigationOnClickListener(navClickListener)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nestedScrollView.background = getDrawable(R.drawable.background_shape)
        }
    }
}
