package com.eevajonna.junietestapplication.ui.navigation

/**
 * Enum representing the different screens in the app.
 */
sealed class Screen(val route: String) {
    // Home screen
    object Home : Screen("home")
    
    // Yarn screens
    object YarnList : Screen("yarn_list")
    object YarnDetail : Screen("yarn_detail/{yarnId}") {
        fun createRoute(yarnId: Long) = "yarn_detail/$yarnId"
    }
    object AddYarn : Screen("add_yarn")
    object EditYarn : Screen("edit_yarn/{yarnId}") {
        fun createRoute(yarnId: Long) = "edit_yarn/$yarnId"
    }
    
    // Knitting needles screens
    object NeedlesList : Screen("needles_list")
    object NeedlesDetail : Screen("needles_detail/{needlesId}") {
        fun createRoute(needlesId: Long) = "needles_detail/$needlesId"
    }
    object AddNeedles : Screen("add_needles")
    object EditNeedles : Screen("edit_needles/{needlesId}") {
        fun createRoute(needlesId: Long) = "edit_needles/$needlesId"
    }
}