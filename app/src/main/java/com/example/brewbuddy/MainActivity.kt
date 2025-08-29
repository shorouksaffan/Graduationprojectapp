package com.example.bottomnave

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.brewbuddy.R

class MainActivity : AppCompatActivity() {

    private var selectedTab = 0

    private lateinit var homeContent: View
    private lateinit var menuContent: View
    private lateinit var orderContent: View
    private lateinit var favoritesContent: View

    private lateinit var navHome: View
    private lateinit var navMenu: View
    private lateinit var navOrder: View
    private lateinit var navFavorites: View

    private lateinit var indicatorHome: View
    private lateinit var indicatorMenu: View
    private lateinit var indicatorOrder: View
    private lateinit var indicatorFavorites: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
        setupPageClickListeners()
        selectTab(0)
    }

    private fun initViews() {
        homeContent = findViewById(R.id.home_screen)
        menuContent = findViewById(R.id.menu_screen)
        orderContent = findViewById(R.id.order_screen)
        favoritesContent = findViewById(R.id.favorites_screen)

        navHome = findViewById(R.id.nav_home)
        navMenu = findViewById(R.id.nav_menu)
        navOrder = findViewById(R.id.nav_order)
        navFavorites = findViewById(R.id.nav_favorites)

        indicatorHome = findViewById(R.id.indicator_home)
        indicatorMenu = findViewById(R.id.indicator_menu)
        indicatorOrder = findViewById(R.id.indicator_order)
        indicatorFavorites = findViewById(R.id.indicator_favorites)
    }

    private fun setupClickListeners() {
        navHome.setOnClickListener {
            selectTab(0)
            onTabSelected("Home")
        }
        navMenu.setOnClickListener {
            selectTab(1)
            onTabSelected("Drink Menu")
        }
        navOrder.setOnClickListener {
            selectTab(2)
            onTabSelected("Your Order")
        }
        navFavorites.setOnClickListener {
            selectTab(3)
            onTabSelected("Favorites")
        }
    }

    private fun setupPageClickListeners() {
        setupHomePageClicks()
        setupMenuPageClicks()
        setupOrderPageClicks()
        setupFavoritesPageClicks()
    }

    private fun selectTab(tabIndex: Int) {
        if (selectedTab == tabIndex) return
        selectedTab = tabIndex
        hideAllContent()
        hideAllIndicators()

        when (tabIndex) {
            0 -> {
                homeContent.visibility = View.VISIBLE
                indicatorHome.visibility = View.VISIBLE
            }
            1 -> {
                menuContent.visibility = View.VISIBLE
                indicatorMenu.visibility = View.VISIBLE
            }
            2 -> {
                orderContent.visibility = View.VISIBLE
                indicatorOrder.visibility = View.VISIBLE
            }
            3 -> {
                favoritesContent.visibility = View.VISIBLE
                indicatorFavorites.visibility = View.VISIBLE
            }
        }
    }

    private fun hideAllContent() {
        homeContent.visibility = View.GONE
        menuContent.visibility = View.GONE
        orderContent.visibility = View.GONE
        favoritesContent.visibility = View.GONE
    }

    private fun hideAllIndicators() {
        indicatorHome.visibility = View.GONE
        indicatorMenu.visibility = View.GONE
        indicatorOrder.visibility = View.GONE
        indicatorFavorites.visibility = View.GONE
    }

    private fun onTabSelected(tabName: String) {
        println("Selected tab: $tabName (index: $selectedTab)")
    }

    private fun setupHomePageClicks() {
        val imageHome = findViewById<ImageView>(R.id.image_home)
        val textHome = findViewById<TextView>(R.id.text_home)
        val textHome2 = findViewById<TextView>(R.id.text_home2)

        imageHome?.setOnClickListener {
            println("Home image clicked!")
            showToast("Welcome to our app!")
        }
        textHome?.setOnClickListener {
            println("Home title clicked!")
            showToast("Home title clicked - Show app info!")
        }
        textHome2?.setOnClickListener {
            println("Home description clicked!")
            showToast("Home description clicked - Show help!")
        }
    }

    private fun setupMenuPageClicks() {
        val imageMenu = findViewById<ImageView>(R.id.image_menu)
        val textMenu = findViewById<TextView>(R.id.text_menu)
        val textMenu2 = findViewById<TextView>(R.id.text_menu2)

        imageMenu?.setOnClickListener {
            println("Menu image clicked!")
            showToast("Featured drink of the day!")
        }
        textMenu?.setOnClickListener {
            println("Menu title clicked!")
            showToast("Browse drink categories!")
        }
        textMenu2?.setOnClickListener {
            println("Menu description clicked!")
            showToast("View all delicious drinks!")
        }
    }

    private fun setupOrderPageClicks() {
        val imageOrder = findViewById<ImageView>(R.id.image_order)
        val textOrder = findViewById<TextView>(R.id.text_order)
        val textOrder2 = findViewById<TextView>(R.id.text_order2)

        imageOrder?.setOnClickListener {
            println("Order image clicked!")
            showToast("Add new item to order!")
        }
        textOrder?.setOnClickListener {
            println("Order title clicked!")
            showToast("View order summary!")
        }
        textOrder2?.setOnClickListener {
            println("Order description clicked!")
            showToast("Edit your current order!")
        }
    }

    private fun setupFavoritesPageClicks() {
        val imageFavorites = findViewById<ImageView>(R.id.image_favorites)
        val textFavorites = findViewById<TextView>(R.id.text_favorites)
        val textFavorites2 = findViewById<TextView>(R.id.text_favorites2)

        imageFavorites?.setOnClickListener {
            println("Favorites image clicked!")
            showToast("Your most loved drink!")
        }
        textFavorites?.setOnClickListener {
            println("Favorites title clicked!")
            showToast("Manage your favorites!")
        }
        textFavorites2?.setOnClickListener {
            println("Favorites description clicked!")
            showToast("Add drinks to your favorites!")
        }
    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    fun selectTabProgrammatically(tabIndex: Int) {
        if (tabIndex in 0..3) {
            selectTab(tabIndex)
        }
    }

    fun getCurrentTab(): Int {
        return selectedTab
    }

}
