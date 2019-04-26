package io.github.cam4quality.ui.factory

import androidx.fragment.app.DialogFragment
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.FactoriesRepository
import io.github.cam4quality.utility.extension.bind
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject


class AddFactoryDialog : DialogFragment() {

    private val toolbar by bind<Toolbar>(R.id.add_factory_toolbar)
    private val factoriesRepository: FactoriesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Dialog_Cam4Quality)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_add_factory, container, false)

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
            it.window?.setWindowAnimations(R.style.Cam4Quality_Slide)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(toolbar) {
            setOnMenuItemClickListener { onOptionsItemSelected(it) }
            setNavigationOnClickListener { dismiss() }
            inflateMenu(R.menu.menu_save_icon)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_action_save -> {
                toast("Saved")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}