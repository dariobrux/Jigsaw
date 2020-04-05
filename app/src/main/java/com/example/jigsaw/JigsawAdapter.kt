package com.example.jigsaw

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import com.example.jigsaw.JigsawAdapter.CustomViewHolder
//
//class JigsawAdapter(context: Context, itemList: List<Int>?) : RecyclerView.Adapter<CustomViewHolder>() {
//    private val feedItemList: List<FeedItem>?
//    private val mContext: Context
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomViewHolder {
//        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_row, null)
//        return CustomViewHolder(view)
//    }
//
//    override fun onBindViewHolder(customViewHolder: CustomViewHolder, i: Int) {
//        val item: FeedItem = feedItemList!![i]
//
//        //Render image using Picasso library
//        if (!TextUtils.isEmpty(item.getThumbnail())) {
//            Picasso.with(mContext).load(item.getThumbnail())
//                .error(R.drawable.placeholder)
//                .placeholder(R.drawable.placeholder)
//                .into(customViewHolder.imageView)
//        }
//
//        //Setting text view title
//        customViewHolder.textView.text = Html.fromHtml(item.getTitle())
//    }
//
//    override fun getItemCount(): Int {
//        return feedItemList?.size() ?: 0
//    }
//
//    inner class CustomViewHolder(view: View) : ViewHolder(view) {
//        var imageView: ImageView = view.findViewById<View>(R.id.thumbnail) as ImageView
//        var textView: TextView = view.findViewById<View>(R.id.title) as TextView
//
//    }
//
//    init {
//        this.feedItemList = itemList
//        mContext = context
//    }
//}