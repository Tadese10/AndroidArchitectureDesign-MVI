package com.tadese_teejay.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.tadese_teejay.R
import com.tadese_teejay.ui.main.fragment.post.PostFragment
import com.tadese_teejay.ui.main.fragment.todo.TodoFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Set Toolbar
        setSupportActionBar(toolbar)

        //Handle Drawer toggle
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        displayScreen(R.id.nav_todo)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun displayScreen(id: Int){

        // val fragment =  when (id){

        when (id){
            R.id.nav_todo -> {
                supportFragmentManager.beginTransaction().replace(R.id.relativelayout, TodoFragment()).commit()
            }

           R.id.nav_posts -> {
                supportFragmentManager.beginTransaction().replace(R.id.relativelayout, PostFragment()).commit()
           }
//
//            R.id.nav_albums -> {
//                supportFragmentManager.beginTransaction().replace(R.id.relativelayout, MoviesFragment()).commit()
//            }

            R.id.nav_settings -> {
                Toast.makeText(this, "Clicked Settings", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        displayScreen(item.itemId)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}