package org.d3if3117.assesment2.tampil_info


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if3117.assesment2.MainActivity
import org.d3if3117.assesment2.R
import org.d3if3117.assesment2.databinding.FragTampilInfoBinding
import org.d3if3117.assesment2.network.ApiStatus


@Suppress("UNREACHABLE_CODE")
class TampilInfoFrag : Fragment() {
    private val viewModel: TampilInfoViewModel by lazy {
        ViewModelProvider(this)[TampilInfoViewModel::class.java]
    }

    private lateinit var binding: FragTampilInfoBinding
    private lateinit var myAdapter: TampilInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragTampilInfoBinding.inflate(layoutInflater, container, false)
        myAdapter = TampilInfoAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner) {
            myAdapter.updateData(it)

            with(binding.recyclerView) {
                addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
                adapter = myAdapter
                setHasFixedSize(true)
            }
        }
        viewModel.getStatus().observe(viewLifecycleOwner) {
            updateProgress(it)
        }

        viewModel.scheduleUpdater(requireActivity().application)
        binding.btnShare.setOnClickListener { bagikanapp() }

    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermission()
                }
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        with(binding.recyclerView) {
//            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
//            adapter = myAdapter
//            setHasFixedSize(true)
//        }
//   }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.btnShare.setOnClickListener { bagikanapp() }
//    }

    fun bagikanapp(){
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, requireActivity().getString(R.string.app_name))
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Ayo Catat Pengeluaranmu dengan Money Manager!")
        requireActivity().startActivity(Intent.createChooser(sharingIntent, "Bagikan Melalui"))
    }
}