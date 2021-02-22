package io.github.armcha

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        staticTextButton.setOnClickListener {
            startActivity(Intent(this, StaticTextActivity::class.java))
        }
        recyclerViewButton.setOnClickListener {
            startActivity(Intent(this, RecyclerViewActivity::class.java))
        }
        githubIcon.setOnClickListener {
            browse("https://github.com/armcha/AutoLinkTextViewV2")
        }
        // Use a downloadable font for EmojiCompat
        val fontRequest = FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs)
        val config: EmojiCompat.Config  = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
                .setReplaceAll(true)
                .registerInitCallback(object : EmojiCompat.InitCallback() {
                    override fun onInitialized() {
                    }

                    override fun onFailed(throwable: Throwable?) {
                    }
                })
        EmojiCompat.init(config)
    }
}

fun Context.showDialog(title: String, message: String, url: String? = null) {
    val builder = AlertDialog.Builder(this)
            .setMessage(message)
            .setTitle(title)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
    if (url != null) {
        builder.setNegativeButton("Browse") { dialog, _ -> browse(url);dialog.dismiss() }
    }
    builder.create().show()
}

fun Context.browse(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}
