package org.fansin.ranobereader.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_novel_details.*
import org.fansin.ranobereader.App
import org.fansin.ranobereader.NovelBinder
import org.fansin.ranobereader.R
import org.fansin.ranobereader.domain.model.Novel
import javax.inject.Inject

class NovelDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var novelBinder: NovelBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novel_details)

        App.appComponent.inject(this)

        val novel = intent.getParcelableExtra<Novel>("novel")
        if (novel != null) {
            novelBinder.bind(container, novel)
        }
    }
}
