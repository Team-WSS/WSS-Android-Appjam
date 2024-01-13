package com.teamwss.websoso.ui.novelDetail.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.teamwss.websoso.R
import android.view.LayoutInflater
import android.widget.TextView

class CustomPopupSpinnerAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 보이지 않는 뷰를 생성하여 반환
        return View(context).apply {
            layoutParams = ViewGroup.LayoutParams(0, 0)
            visibility = View.GONE
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.item_custom_popup_drop_down, parent, false)
        val item = getItem(position)
        val textView = view.findViewById<TextView>(R.id.tvPopupSpinnerItem)
        textView.text = item
        return view
    }
}
