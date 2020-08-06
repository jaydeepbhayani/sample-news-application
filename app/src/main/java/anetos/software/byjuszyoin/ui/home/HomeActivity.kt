package anetos.software.byjuszyoin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import anetos.software.byjuszyoin.R
import anetos.software.byjuszyoin.core.BaseActivity
import anetos.software.byjuszyoin.core.InternetCheck
import anetos.software.byjuszyoin.core.dataViewModelProvider
import anetos.software.byjuszyoin.data.model.Articles
import anetos.software.byjuszyoin.ui.common.DataViewModel
import anetos.software.byjuszyoin.ui.common.views.refresh
import anetos.software.byjuszyoin.ui.newsdetail.NewsDetailActivity
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : BaseActivity() {
    val TAG = javaClass.simpleName

    private lateinit var viewModel: DataViewModel

    lateinit var topHeadlinesAdapter: TopHeadlinesAdapter
    private val pageSize: Int = 50
    private var page = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = dataViewModelProvider()

        refreshData()

        progress.visibility = VISIBLE
        viewModel.topHeadlinesData.observe(this, Observer { values ->
            progress.visibility = GONE
            if (values != null) {
                topHeadlinesAdapter.setData(this, values.articles)
                //topHeadlinesAdapter.submitList(values.articles)
            } else {
                if (viewModel.getAllArticles().isEmpty()) {
                    errorLayout.visibility = VISIBLE
                    showSnackBar(container, "\uD83D\uDE28 Something went wrong.", "OK")
                }
            }
        })

        swipeRefreshLayout.refresh()
        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }
        //swipeRefreshLayout.setProgressViewOffset(true, 0, 100)

        viewModel.spinner.observe(this, Observer { value ->
            value?.let { show ->
                with(swipeRefreshLayout) {
                    post { isRefreshing = show }
                }
            }
        })
        setAdapter()
    }

    private fun setAdapter() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        topHeadlinesAdapter = TopHeadlinesAdapter()
        rvHeadlines.layoutManager = layoutManager
        rvHeadlines.adapter = topHeadlinesAdapter

        //animation
        rvHeadlines.adapter?.notifyDataSetChanged()
        rvHeadlines.scheduleLayoutAnimation()

        rvHeadlines.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                //listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })

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
    }

    private fun refreshData() {
        InternetCheck { internet ->
            if (internet) {
                viewModel.refreshTopHeadlinesData("us", pageSize, page)
                errorLayout.visibility = GONE
            } else {
                progress.visibility = GONE
                if (viewModel.getAllArticles().size > 0) {
                    showSnackBar(container, "No network, Loading offline data.", "OK")
                    viewModel.getAllArticles().let { topHeadlinesAdapter.setData(this, it) }
                } else {
                    errorLayout.visibility = VISIBLE
                    //Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show()
                }
            }
            viewModel.spinner.value = false
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + DataViewModel.VISIBLE_THRESHOLD >= totalItemCount) {
            viewModel.refreshTopHeadlinesData("us", 10, page)
            //page++
            //Log.e(TAG, page.toString())
        }
    }

}