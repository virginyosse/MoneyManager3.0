package org.d3if3117.assesment2.tampil_data

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if3117.assesment2.data.PengeluaranDB
import org.d3if3117.assesment2.databinding.FragTampilDataBinding
import org.d3if3117.assesment2.main.PengeluaranViewModel
import org.d3if3117.assesment2.main.PengeluaranViewModelFactory

class TampilDataFrag : Fragment() {
    private lateinit var binding: FragTampilDataBinding
    private lateinit var tampilAdapter: TampilDataAdapter

    private val viewModel: PengeluaranViewModel by lazy {
        val db = PengeluaranDB.getInstance(requireContext())
        val factory = PengeluaranViewModelFactory(db.dao)

        ViewModelProvider(this, factory)[PengeluaranViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragTampilDataBinding.inflate(layoutInflater, container, false)

        viewModel.getAllPengeluaran().observe(requireActivity()) {
            tampilAdapter = TampilDataAdapter(it)
            with(binding.recyclerView) {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = tampilAdapter
                setHasFixedSize(true)
            }

            if (it.isNotEmpty()) binding.emptyView.visibility = View.GONE
            else binding.emptyView.visibility = View.VISIBLE
        }


        binding.tombolHapus.setOnClickListener {
            showConfirmationDialog()
        }

        return binding.root
    }
    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Konfirmasi")
        builder.setMessage("Anda yakin ingin menghapus?")

        builder.setPositiveButton("Ya") { _, _ ->
            // Tindakan yang akan dilakukan jika pengguna menekan tombol "Ya"
            // Misalnya, hapus item atau lakukan tindakan lainnya
            viewModel.deleteAllPengeluaran()
        }

        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
            // Tindakan yang akan dilakukan jika pengguna menekan tombol "Tidak" atau menutup dialog
        }

        val dialog = builder.create()
        dialog.show()
    }
}