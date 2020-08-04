package anetos.software.byjuszyoin.ui.newsdetail

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import anetos.software.byjuszyoin.R
import anetos.software.byjuszyoin.data.model.Articles
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news_detail.view.*
import java.util.ArrayList

/**
 * * [NewsDetailAdapter]
 *
 * [PagerAdapter] class for showing news detail.
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */

class NewsDetailAdapter // constructor
    (
    private val activity: Activity,
    private val itemArrayList: ArrayList<Articles>
) : PagerAdapter() {
    //val TAG = javaClass.simpleName
    private var inflater: LayoutInflater? = null
    override fun getCount(): Int {
        return itemArrayList.size
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imgDisplay: ImageView
        val btnClose: ImageView
        inflater = activity
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewLayout = inflater!!.inflate(
            R.layout.item_news_detail, container,
            false
        )
        imgDisplay =
            viewLayout.findViewById(R.id.ivBackground) as ImageView
        btnClose =
            viewLayout.findViewById(R.id.btnBack) as ImageView

        viewLayout.tvTitle.text = itemArrayList.get(position).title
        viewLayout.tvSource.text = itemArrayList.get(position).source?.name
        viewLayout.tvPublishTime.text = itemArrayList.get(position).publishedAt?.split("T")?.get(0)
        viewLayout.tvDescription.text = itemArrayList.get(position).description

        /* val options = BitmapFactory.Options()
         options.inPreferredConfig = Bitmap.Config.ARGB_8888
         val bitmap = BitmapFactory.decodeFile(imagePaths[position], options)
         imgDisplay.setImageBitmap(bitmap)*/

        try {
            Glide.with(activity).load(itemArrayList.get(position).urlToImage)
                /*.error(R.drawable.ic_insert_photo_24)*/
                .into(imgDisplay)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        // close button click event
        btnClose.setOnClickListener { v: View? -> activity.finish() }
        (container as ViewPager).addView(viewLayout)
        return viewLayout
    }

    /*override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        (container as ViewPager).removeView(`object` as RelativeLayout)
    }*/

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}