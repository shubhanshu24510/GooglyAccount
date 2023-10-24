package com.example.compose.rally

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.rally.ui.accounts.AccountsScreen
import com.example.compose.rally.ui.accounts.SingleAccountScreen
import com.example.compose.rally.ui.bills.BillsScreen
import com.example.compose.rally.ui.bills.SingleBillsScreen
import com.example.compose.rally.ui.overview.OverviewScreen

@Composable
 fun RallyNavHost(
    navController: NavHostController
   ) {
    NavHost(navController = navController,
        startDestination = Overview.route) {
        composable(route = Overview.route) {
           OverviewScreen(
               onClickSeeAllAccounts  ={
                   navController.navigateSingleTopTo(Accounts.route)
               },
               onClickSeeAllBills  ={
                   navController.navigateSingleTopTo(Bills.route)
               }
           ) { accountType ->
               navController.navigateToSingleAccount(accountType)
           }
        }
        composable(route = Accounts.route) {
            AccountsScreen(
                onAccountClick = {accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }
        composable(route = Bills.route) {
            BillsScreen(
                onBillsClick = {billType ->
                    navController.navigateToSingleBill(billType)
                }
            )
        }
        composable(
            route=SingleAccount.routeWithArgs,
            arguments =SingleAccount.arguments,
            deepLinks = SingleAccount.deepLinks
        ){navBackStackEntry ->
        val accountType = navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)
        SingleAccountScreen(accountType)

        }
        composable(
            route=SingleBill.routeWithArgs,
            arguments =SingleBill.arguments,
            deepLinks = SingleBill.deepLinks
        ){navBackStackEntry ->
        val billType = navBackStackEntry.arguments?.getString(SingleBill.billTypeArg)
        SingleBillsScreen(billType)

        }
    }
}

fun NavHostController.navigateSingleTopTo(route:String){
    this.navigate(route){
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ){
            saveState =true
        }
        launchSingleTop =true
        restoreState =true
    }
}

private fun NavHostController.navigateToSingleAccount(accountType:String){
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
}
private fun NavHostController.navigateToSingleBill(billType:String){
    this.navigateSingleTopTo("${SingleBill.route}/$billType")
}


