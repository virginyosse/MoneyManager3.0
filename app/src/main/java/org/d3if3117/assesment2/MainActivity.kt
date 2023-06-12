package org.d3if3117.assesment2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.d3if3117.assesment2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val CHANNEL_ID = "updater"
        const val PERMISSION_REQUEST_CODE = 1
    }
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = getString(R.string.channel_desc)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager?
            manager?.createNotificationChannel(channel)
        }

        val navView = binding.bottomNav

        val navController = findNavController(R.id.myNavHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_tambah, R.id.navigation_tampil, R.id.navigation_info
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}