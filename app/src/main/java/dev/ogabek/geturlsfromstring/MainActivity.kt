package dev.ogabek.geturlsfromstring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.webkit.URLUtil
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.qualifiedName

    private lateinit var et_text: EditText
    private lateinit var btn_show_urls: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        et_text = findViewById(R.id.et_text)
        btn_show_urls = findViewById(R.id.btn_show_urls)

        btn_show_urls.setOnClickListener {
            showUrls(et_text.text.toString())
            showHashTeg(et_text.text.toString())
            Toast.makeText(this, "Result in Logcat", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showUrls(text: String) {
        val words = text.split(" ")
        Log.d(TAG, "Result : Urls -> ${getUrls(words)}")
    }

    private fun showHashTeg(text: String) {
        Log.d(TAG, "Result : HashTags -> ${getHashTeg(text)}")
    }

    private fun getUrls(words: List<String>): ArrayList<String> {
        val forReturn = ArrayList<String>()
        for (i in words.indices) {
            if (URLUtil.isValidUrl(words[i])) {
                forReturn.add(words[i])
            }
        }
        return forReturn
    }

    private fun getHashTeg(words: String): ArrayList<String>  {
        val forReturn = ArrayList<String>()
        var word = words
        for (i in word) {
            val firstTag = word.indexOf('#')
            val lastTag = word.lastIndexOf('#')
            val tag = getOnlyHashTeg(firstTag, word)
            if (tag != null) {
                word = word.substring(firstTag + tag.length)
                if (isValidTag(tag)) {
                    forReturn.add(tag)
                }
                if (firstTag == lastTag) {
                    break
                }
            }
        }
        return forReturn
    }

    private fun isValidTag(tag: String): Boolean {
        if (tag.length !in 4..36) {
            return false
        }
        return true
    }

    private fun getOnlyHashTeg(index: Int, text: String): String? {
        if (index >= 0) {
            var tag = text.substring(index)
            for (i in index until text.length) {
                if (text[i] == ' ') {
                    tag = text.substring(index, i)
                    break
                }
            }
            return tag
        }
        return null
    }

}