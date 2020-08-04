package anetos.software.byjuszyoin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.NonNull
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
    //val TAG = javaClass.simpleName

    private lateinit var viewModel: DataViewModel

    lateinit var topHeadlinesAdapter: TopHeadlinesAdapter
    private var pastVisiblesItems = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var loading = true
    var loaded = true
    private var mLoadedItems = 0
    private val pageSize: Int = 10
    private var linearLayoutManager: LinearLayoutManager? = null


    private val articleArrayList: ArrayList<Articles> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = dataViewModelProvider()

        refreshData()

        progress.visibility = VISIBLE
        addDataToList()
        /*viewModel.topHeadlinesData.observe(this, Observer { values ->
            progress.visibility = GONE
            if (values != null) {
                topHeadlinesAdapter.setData(this, values.articles)
            }
        })*/

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
        topHeadlinesAdapter = TopHeadlinesAdapter()
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHeadlines.layoutManager = linearLayoutManager
        rvHeadlines.adapter = topHeadlinesAdapter

        //animation
        rvHeadlines.adapter?.notifyDataSetChanged()
        rvHeadlines.scheduleLayoutAnimation()

        rvHeadlines.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                @NonNull recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = linearLayoutManager!!.childCount
                    totalItemCount = linearLayoutManager!!.itemCount
                    pastVisiblesItems = linearLayoutManager!!.findFirstVisibleItemPosition()

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            //getNextListVideo();
                            addDataToList()
                        }
                    }
                }
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
        topHeadlinesAdapter.setData(this, articleArrayList)
    }

    private fun refreshData() {
        InternetCheck { internet ->
            if (internet) {
                //viewModel.refreshTopHeadlinesData("us")
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

    private fun addDataToList() {
        viewModel.refreshTopHeadlinesData("us", pageSize, mLoadedItems)

        viewModel.topHeadlinesData.observe(this, Observer { values ->
            progress.visibility = GONE
            if (values.articles?.size != 0) {
                values.articles?.let { articleArrayList.addAll(it) }

                /*for (items in values.articles) {
                    articleArrayList.add(items)
                }*/
                values.articles = emptyList()

                topHeadlinesAdapter.notifyDataSetChanged()
                mLoadedItems++
                loading = true
            } else {
                if (values.totalResults == articleArrayList.size - pageSize) {
                    showSnackBar(container, "No more items", "OK")
                }
            }
        })
    }
}