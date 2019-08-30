package nl.jordanvanbeijnhem.studentportal

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import nl.jordanvanbeijnhem.studentportal.adapter.PortalAdapter
import nl.jordanvanbeijnhem.studentportal.model.Portal

const val PORTAL_REQUEST_CODE = 100

class MainActivity : AppCompatActivity() {

    private val portals = arrayListOf<Portal>()
    private val portalAdapter = PortalAdapter(portals) { portal: Portal -> onPortalClick(portal) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViews()
    }

    private fun initViews() {
        fab.setOnClickListener { startAddActivity() }

        rvPortals.layoutManager = GridLayoutManager(this, 2)
        rvPortals.adapter = portalAdapter
        createItemTouchHelper().attachToRecyclerView(rvPortals)
    }

    private fun startAddActivity() {
        val intent = Intent(this@MainActivity, AddActivity::class.java)
        startActivityForResult(intent, PORTAL_REQUEST_CODE)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                portals.removeAt(position)
                portalAdapter.notifyDataSetChanged()
            }
        }

        return ItemTouchHelper(callback)
    }

    private fun onPortalClick(portal: Portal) {
        CustomTabsIntent.Builder().build().launchUrl(this, Uri.parse(portal.url))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PORTAL_REQUEST_CODE -> {
                    val portal = data!!.getParcelableExtra<Portal>(EXTRA_PORTAL)
                    portals.add(portal)
                    portalAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}
