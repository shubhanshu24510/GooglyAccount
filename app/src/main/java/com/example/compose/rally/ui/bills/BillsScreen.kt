package com.example.compose.rally.ui.bills

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.compose.rally.R
import com.example.compose.rally.data.UserData
import com.example.compose.rally.ui.components.BillRow
import com.example.compose.rally.ui.components.StatementBody

@Composable
fun BillsScreen(
    onBillsClick: (String) -> Unit = {},
) {
    val billsTotel = remember { UserData.bills.map { bills -> bills.amount }.sum() }
    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Bills Screen" },
        items = UserData.bills,
        amounts = { bill -> bill.amount },
        colors = { bill -> bill.color },
        amountsTotal = billsTotel,
        circleLabel = stringResource(R.string.due),
        rows = { bill ->
            BillRow(
                modifier = Modifier.clickable {
                    onBillsClick(bill.name)

                },
                name = bill.name,
                bill.due,
                bill.amount,
                bill.color
            )
        })
}

@Composable
fun SingleBillsScreen(
    billsType:String?=UserData.bills.first().name
){
    val bill = remember(billsType) { UserData.getBill(billsType) }
    StatementBody(items = listOf(bill),
        colors = {bill.color},
        amounts = {bill.amount},
        amountsTotal = bill.amount,
        circleLabel =bill.name ) {row ->
        BillRow(name = row.name,
            due = row.due,
            amount =row.amount ,
            color = row.color)
    }
}
