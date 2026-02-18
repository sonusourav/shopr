package com.propertyfinder.shopr.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.propertyfinder.shopr.R
import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.ui.res.categoryLabelRes
import com.propertyfinder.shopr.ui.theme.AddCardBackground

@Composable
fun EditItemCard(
    editName: String,
    editCategory: GroceryCategory,
    onEditNameChange: (String) -> Unit,
    onEditCategoryChange: (GroceryCategory) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AddCardBackground)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = editName,
                onValueChange = onEditNameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.item_name_label)) },
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GroceryCategory.entries.forEach { cat ->
                    FilterChip(
                        selected = editCategory == cat,
                        onClick = { onEditCategoryChange(cat) },
                        label = { Text(stringResource(categoryLabelRes(cat)).take(6)) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancel) {
                    Text(stringResource(R.string.cancel))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onSave) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
}
