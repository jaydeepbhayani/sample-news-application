package anetos.software.byjuszyoin.ui.newsdetail

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import anetos.software.byjuszyoin.R
import anetos.software.byjuszyoin.core.BaseActivity
import anetos.software.byjuszyoin.data.model.Articles

class NewsDetailActivity : BaseActivity() {
    val TAG = javaClass.simpleName

    private lateinit var adapter: NewsDetailAdapter
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        viewPager = findViewById(R.id.pager)

        //utils = new Utils(getApplicationContext());
        val i = intent
        val position = i.getIntExtra("position", 0)

        val itemArrayList = intent.getParcelableArrayListExtra<Articles>("news_detail")

        adapter = NewsDetailAdapter(
            this@NewsDetailActivity,
            itemArrayList
        )
        viewPager.adapter = adapter

        // displaying selected image first
        viewPager.currentItem = position
    }
}