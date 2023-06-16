package org.d3if3117.assesment2.tampil_info

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3117.assesment2.R
import org.d3if3117.assesment2.databinding.ListFinanceBinding
import org.d3if3117.assesment2.databinding.ListItemBinding
import org.d3if3117.assesment2.network.MoneyApi

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

            Glide.with(imageView.context)
                .load(MoneyApi.getMoneyUrl(finance.image))
                .error(R.drawable.broken_image)
                .into(imageView)


            root.setOnClickListener {
                val message = root.context.getString(R.string.message, finance.judul)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }

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