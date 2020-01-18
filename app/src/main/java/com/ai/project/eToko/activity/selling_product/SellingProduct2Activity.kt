package com.ai.project.eToko.activity.selling_product

import android.os.Bundle
import com.ai.project.eToko.R
import com.ai.project.libui.AiActivity

class SellingProduct2Activity: AiActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_selling_product)
            init()
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    private fun init() {

    }
}