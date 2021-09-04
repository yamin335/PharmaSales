package com.fantasyapps.darmahealthcare

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavLogger
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import com.roughike.bottombar.BottomBar
import com.fantasyapps.darmahealthcare.fragments.DashBoardFragment
import com.fantasyapps.darmahealthcare.fragments.OrderCreateFragment
import com.fantasyapps.darmahealthcare.fragments.SettingsFragment
import com.fantasyapps.darmahealthcare.fragments.TaskFragment

class MainActivity : AppCompatActivity(), BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    override val numberOfRootFragments: Int = 4

    private val fragNavController: FragNavController = FragNavController(supportFragmentManager, R.id.fragmentContainer)

    lateinit var bottomBar: BottomBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomBar = findViewById(R.id.bottomBar)

        fragNavController.apply {
            transactionListener = this@MainActivity
            rootFragmentListener = this@MainActivity
            createEager = true
            fragNavLogger = object : FragNavLogger {
                override fun error(message: String, throwable: Throwable) {
                    Log.e(TAG, message, throwable)
                }
            }

            fragmentHideStrategy = FragNavController.HIDE

            navigationStrategy = UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    bottomBar.selectTabAtPosition(index)
                }
            })
        }

        val initialTabIndex = intent.getIntExtra("selectionIndex", INDEX_DASHBOARD)
        fragNavController.initialize(initialTabIndex, savedInstanceState)
        val initial = savedInstanceState == null
        if (initial) {
            bottomBar.selectTabAtPosition(initialTabIndex)
        }

//        for (i in 1 until numberOfRootFragments){
//            if (i== 3)
//                supportActionBar?.hide()
//            else
//                supportActionBar?.show()
//        }


        fragNavController.executePendingTransactions()
        bottomBar.setOnTabSelectListener({ tabId ->
            when (tabId) {
                R.id.menu_dashboard -> fragNavController.switchTab(INDEX_DASHBOARD)
                R.id.menu_add_product -> fragNavController.switchTab(INDEX_ADD_PRODUCT)
                R.id.menu_task -> fragNavController.switchTab(INDEX_TASK)
                R.id.menu_settings -> fragNavController.switchTab(INDEX_SETTINGS)

            }
        }, initial)

        bottomBar.setOnTabReselectListener { fragNavController.clearStack() }

    }

    override fun onBackPressed() {
        if (fragNavController.isRootFragment) {
            fragNavController.clearStack()
            //this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right)
            //super.onBackPressed()
            exit()


        } else if (fragNavController.popFragment().not()) {
            //super.onBackPressed()
            exit()

        }


    }

    private fun exit() {
        val builder = AlertDialog.Builder(this@MainActivity)

        // Set the alert dialog title
        builder.setTitle("EXIT")
        builder.setCancelable(false)
        builder.setMessage("Are you sure?")
        builder.setPositiveButton("Yes") { dialog, which ->

            if (OrderCreateFragment.P_list != null) {
                OrderCreateFragment.P_list!!.clear()
            }
            OrderCreateFragment.C_id = ""
            OrderCreateFragment.C_name = ""
            OrderCreateFragment.C_REFERENCE = ""
            OrderCreateFragment.C_discountAmount = ""


            finish()

        }

        builder.setNegativeButton("No") { dialog, which ->
            //Toast.makeText(applicationContext,"You are not agree.",Toast.LENGTH_SHORT).show()
        }

        val b = builder.create()
        b.show()


    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState!!)
    }

    override fun pushFragment(fragment: Fragment) {
        fragNavController.pushFragment(fragment)
    }

    override fun popFragment() {
        fragNavController.popFragment()
    }


    override fun onFragmentTransaction(fragment: Fragment?, transactionType: FragNavController.TransactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())

    }

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        // If we have a backstack, show the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())

        if (index == INDEX_DASHBOARD) {
            supportActionBar?.title = "Sales List"
        } else if (index == INDEX_ADD_PRODUCT) {
            supportActionBar?.title = "New Sale"
        } else if (index == INDEX_TASK) {
            supportActionBar?.title = "Today's Attendance"
        } else if (index == INDEX_SETTINGS) {
            supportActionBar?.title = "Settings"
        }
    }


    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            INDEX_DASHBOARD -> return DashBoardFragment.newInstance(0)
            INDEX_ADD_PRODUCT -> return OrderCreateFragment.newInstance(0)
            INDEX_TASK -> return TaskFragment.newInstance(0)
            INDEX_SETTINGS -> return SettingsFragment.newInstance(0)
        }
        throw IllegalStateException("Need to send an index that we know")
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> fragNavController.popFragment()
        }
        return false
    }

    override fun onStart() {
        super.onStart()
//        val filter = IntentFilter()
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
//        registerReceiver(LocationUpdatesBroadcastReceiver(), filter)
    }

    override fun onDestroy() {
        try {
//            unregisterReceiver(LocationUpdatesBroadcastReceiver())
        } catch (ex: Exception) {
//            ex.printStackTrace()
        }

        super.onDestroy()
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName
        const val INDEX_DASHBOARD = FragNavController.TAB1
        const val INDEX_ADD_PRODUCT = FragNavController.TAB2
        const val INDEX_TASK = FragNavController.TAB3
        const val INDEX_SETTINGS = FragNavController.TAB4

        fun start(context: Context) {
            start(context, INDEX_DASHBOARD)
        }



        fun start(context: Context, index: Int) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("selectionIndex", index)
            context.startActivity(intent)

        }
    }
}