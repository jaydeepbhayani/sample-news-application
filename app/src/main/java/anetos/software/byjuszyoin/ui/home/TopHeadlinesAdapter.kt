package anetos.software.byjuszyoin.ui.home

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import anetos.software.byjuszyoin.R
import anetos.software.byjuszyoin.data.model.Articles
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class TopHeadlinesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TAG = javaClass.simpleName
    var itemArrayList: List<Articles> = ArrayList()
    lateinit var context: Context

    var onItemClick: onItemclickListener? = null

    fun setData(mContext: Context, setList: List<Articles>) {
        itemArrayList = setList
        context = mContext
        notifyDataSetChanged()
        Log.d(TAG, "notified")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_top_headlines, parent, false)
        return SubCategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = itemArrayList.get(position)
        (holder as SubCategoryViewHolder).tvTitle.text = data.title?.split("-")?.get(0)
        holder.tvSource.text = data.source?.name
        holder.tvPublishTime.text = data.publishedAt?.split("T")?.get(0)
        //Picasso.get().load(data.urlToImage).into(holder.ivBackground)
        Glide.with(context)
            .load(data.urlToImage)
            .error(R.drawable.ic_round_report_problem_24)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Log the GlideException here (locally or with a remote logging framework):
                    Log.e(TAG, "Load failed", e)

                    // You can also log the individual causes:
                    for (t in e!!.rootCauses) {
                        Log.e(TAG, "Caused by", t)
                    }
                    // Or, to log all root causes locally, you can use the built in helper method:
                    e.logRootCauses(TAG)

                    return false // Allow calling onLoadFailed on the Target.

                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Log successes here or use DataSource to keep track of cache hits and misses.

                    return false; // Allow calling onResourceReady on the Target.
                }
            })
            .into(holder.ivBackground)

        holder.container.setOnClickListener {
            //onItemClick?.onItemClick(position, data)
            onItemClick?.onItemClick(position, itemArrayList)
        }
    }

    override fun getItemCount(): Int {
        Log.e("size", itemArrayList.size.toString())
        return itemArrayList.size
    }


    private inner class SubCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val container: ConstraintLayout
        val ivBackground: ImageView
        var tvTitle: TextView
        var tvSource: TextView
        var tvPublishTime: TextView

        init {
            container = itemView.findViewById(R.id.container)
            ivBackground = itemView.findViewById(R.id.ivBackground)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvSource = itemView.findViewById(R.id.tvSource)
            tvPublishTime = itemView.findViewById(R.id.tvPublishTime)

        }
    }

    fun getData(): List<Articles> {
        return itemArrayList
    }

    interface onItemclickListener {
        //fun onItemClick(position: Int, data: Articles)
        fun onItemClick(position: Int, itemArrayList: List<Articles>)
    }

    fun setonItemClickListener(onItemclickListener: onItemclickListener) {
        this.onItemClick = onItemclickListener
    }
}