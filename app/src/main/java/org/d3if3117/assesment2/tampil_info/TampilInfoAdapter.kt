package org.d3if3117.assesment2.tampil_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.d3if3117.assesment2.databinding.ListFinanceBinding
import org.d3if3117.assesment2.databinding.ListItemBinding

class TampilInfoAdapter : RecyclerView.Adapter<TampilInfoAdapter.ViewHolder>() {

    private val data = mutableListOf<Finance>()
    fun updateData(newData: List<Finance>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }


    class ViewHolder(
        private val binding: ListFinanceBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(finance: Finance) = with(binding) {
            namaTextView.text = finance.judul
            latinTextView.text = finance.contoh
            imageView.setImageResource(finance.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListFinanceBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

}