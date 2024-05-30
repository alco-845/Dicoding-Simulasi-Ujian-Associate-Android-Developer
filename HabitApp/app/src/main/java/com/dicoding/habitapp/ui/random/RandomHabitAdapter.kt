package com.dicoding.habitapp.ui.random

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {

    private val habitMap = LinkedHashMap<PageType, Habit>()

    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.pager_item, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    override fun getItemCount() = habitMap.size

    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //TODO 14 : Create view and bind data to item view
        private val tvPagerTitle: TextView = itemView.findViewById(R.id.pager_tv_title)
        private val tvPagerData: TextView = itemView.findViewById(R.id.item_tv_page_data)
        private val tvPagerMinute: TextView = itemView.findViewById(R.id.pager_tv_minutes)
        private val btnOpenCountDown: TextView = itemView.findViewById(R.id.btn_open_count_down)
        private val ivPriority: View = itemView.findViewById(R.id.item_priority_level)

        fun bind(pageType: PageType, pageData: Habit) {
            when (pageData.priorityLevel) {
                itemView.resources.getStringArray(R.array.priority_level)[0] -> {
                    ivPriority.background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_priority_high)
                }
                itemView.resources.getStringArray(R.array.priority_level)[1] -> {
                    ivPriority.background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_priority_medium)
                }
                else -> {
                    ivPriority.background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_priority_low)
                }
            }

            tvPagerTitle.text = pageData.title
            tvPagerData.text = pageData.startTime
            tvPagerMinute.text = pageData.minutesFocus.toString()
            btnOpenCountDown.setOnClickListener {
                onClick(pageData)
            }
        }
    }
}
