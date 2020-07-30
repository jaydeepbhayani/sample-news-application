package anetos.software.byjuszyoin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import anetos.software.byjuszyoin.R
import anetos.software.byjuszyoin.core.BaseActivity
import anetos.software.byjuszyoin.core.InternetCheck
import anetos.software.byjuszyoin.core.dataViewModelProvider
import anetos.software.byjuszyoin.data.model.Articles
import anetos.software.byjuszyoin.ui.common.DataViewModel
import anetos.software.byjuszyoin.ui.newsdetail.NewsDetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : BaseActivity() {
    val TAG = javaClass.simpleName

    private lateinit var viewModel: DataViewModel

    lateinit var topHeadlinesAdapter: TopHeadlinesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = dataViewModelProvider()



        InternetCheck { internet ->
            if (internet) {
                viewModel.refreshTopHeadlinesData("us")
            } else {
                viewModel.getAllArticles()?.let { topHeadlinesAdapter.setData(this, it) }

                Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show()
            }
        }

        progress.visibility = View.VISIBLE
        viewModel.topHeadlinesData.observe(this, Observer { values ->
            progress.visibility = View.GONE
            if (values != null) {
                topHeadlinesAdapter.setData(this, values.articles)
            }
        })

        setAdapter()
    }

    private fun setAdapter() {
        topHeadlinesAdapter = TopHeadlinesAdapter()
        rvHeadlines.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHeadlines.adapter = topHeadlinesAdapter
        rvHeadlines.isNestedScrollingEnabled = false

        //animation
        rvHeadlines.adapter?.notifyDataSetChanged()
        rvHeadlines.scheduleLayoutAnimation()

        topHeadlinesAdapter.setonItemClickListener(object :
            TopHeadlinesAdapter.onItemclickListener {
            override fun onItemClick(position: Int, itemArrayList: List<Articles>) {
                val intent = Intent(
                    this@HomeActivity,
                    NewsDetailActivity::class.java
                )
                intent.putExtra("position", position)
                intent.putParcelableArrayListExtra("news_detail", itemArrayList as ArrayList)
                startActivity(intent)
            }
        })

        //topHeadlinesAdapter.setData(this, categoryItemList)
    }

}