package com.example.homework2.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.homework2.CURRENT_STATE
import com.example.homework2.EXTRA_MESSAGE
import com.example.homework2.SecondActivity

class SecondActivityContract : ActivityResultContract<String, String>() {
    override fun createIntent(context: Context, input: String): Intent {
        val intent = Intent(context, SecondActivity::class.java)
        intent.putExtra(CURRENT_STATE, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String  = when {
        resultCode != Activity.RESULT_OK -> ""
        else -> intent?.getStringExtra(EXTRA_MESSAGE) ?: ""
    }

}