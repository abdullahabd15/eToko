package com.ai.project.eToko.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ai.project.eToko.R
import com.ai.project.eToko.activity.selling_product.SellingProductActivity
import com.ai.project.libui.AiActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AiActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)
            init()
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    private fun init() {
        cvHistory.setOnClickListener(this)
        cvSell.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.cvHistory -> moveToHistoryActivity()
            R.id.cvSell -> moveToSellProductActivity()
        }
    }

    private fun moveToSellProductActivity() {
        startActivity(Intent(applicationContext, SellingProductActivity::class.java))
    }

    private fun moveToHistoryActivity() {

    }
}
