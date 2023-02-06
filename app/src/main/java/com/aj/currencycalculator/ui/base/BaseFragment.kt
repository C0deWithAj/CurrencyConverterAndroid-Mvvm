package com.aj.currencycalculator.ui.base

import androidx.fragment.app.Fragment
import io.github.rupinderjeet.kprogresshud.KProgressHUD

open class BaseFragment : Fragment() {

    private var hud: KProgressHUD? = null

    protected fun showProgressDialog() {
        activity?.let {
            hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading...")
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.2f)
                .show()
        }
    }

    protected fun hideProgressDialog() {
        hud?.dismiss()
    }
}
