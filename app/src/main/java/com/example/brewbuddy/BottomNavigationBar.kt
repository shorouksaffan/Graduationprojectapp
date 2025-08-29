package com.example.brewbuddy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.collections.forEachIndexed

data class BottomNavItem(
    val icon: ImageVector,
    val label: String,
    val route: String
)

@Composable
fun BottomNavigationBar(
    selectedTab: Int = 0,
    onTabSelected: (Int) -> Unit
) {
    val navItems = listOf(
        BottomNavItem(Icons.Default.Home, "Home", "home"),
        BottomNavItem(Icons.Default.Menu, "Drink Menu", "menu"),
        BottomNavItem(Icons.Default.AddCircle, "Your Order", "order"),
        BottomNavItem(Icons.Default.Favorite, "Favorites", "favorites")
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color(0xFF8B5A3C))
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navItems.forEachIndexed { index, item ->
            BottomNavTab(
                icon = item.icon,
                label = item.label,
                isSelected = selectedTab == index,
                onClick = {
                    onTabSelected(index)
                    // You can add additional click logic here if needed
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun BottomNavTab(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )

        if (isSelected) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(2.dp)
                    .background(Color.White)
            )
        }
    }
}

// Main Screen that demonstrates the clickable navigation
@Composable
fun NavigationBottom(modifier: Modifier) {
    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Content area that changes based on selected tab
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {
                0 -> HomeContent()
                1 -> MenuContent()
                2 -> OrderContent()
                3 -> FavoritesContent()
            }
        }

        // Bottom Navigation
        BottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = { newTab ->
                selectedTab = newTab
                // Add any additional navigation logic here
                println("Selected tab: $newTab")
            }
        )
    }
}

// Content Composables for each tab
@Composable
fun HomeContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Home",
            modifier = Modifier.size(64.dp),
            tint = Color(0xFF8B5A3C)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Welcome to Home!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "This is the home screen content",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun MenuContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            modifier = Modifier.size(64.dp),
            tint = Color(0xFF8B5A3C)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Drink Menu",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Browse our delicious drinks",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun OrderContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "Order",
            modifier = Modifier.size(64.dp),
            tint = Color(0xFF8B5A3C)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your Order",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Review your current order",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun FavoritesContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Favorites",
            modifier = Modifier.size(64.dp),
            tint = Color(0xFF8B5A3C)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Favorites",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Your favorite drinks and items",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true, name = "Main Screen with Navigation")
@Composable
fun MainScreenPreview() {
    NavigationBottom( modifier = Modifier)  // ✅ REMOVE THE PARAMETER
}

@Preview(showBackground = true, name = "Bottom Navigation Bar")
@Composable
fun BottomNavigationBarPreview() {
    var selectedTab by remember { mutableStateOf(1) }

    Column {
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
    }
}