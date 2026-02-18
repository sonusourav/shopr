package com.propertyfinder.shopr.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.propertyfinder.shopr.R
import com.propertyfinder.shopr.data.GroceryCategory
import com.propertyfinder.shopr.data.GroceryItem
import com.propertyfinder.shopr.ui.theme.BreadsAmber
import com.propertyfinder.shopr.ui.theme.FruitsRed
import com.propertyfinder.shopr.ui.theme.MeatsPink
import com.propertyfinder.shopr.ui.theme.MilkBlue
import com.propertyfinder.shopr.ui.theme.MilkChipBg
import com.propertyfinder.shopr.ui.theme.VegetablesGreen

@Composable
fun GroceryItemRow(
    item: GroceryItem,
    onToggleComplete: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryColor = itemCategoryColor(item.category)
    val rowBackground = if (item.isCompleted) MilkChipBg else MaterialTheme.colorScheme.surface

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = rowBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isCompleted,
                onCheckedChange = { onToggleComplete() },
                colors = CheckboxDefaults.colors(checkedColor = categoryColor)
            )
            Spacer(modifier = Modifier.width(8.dp))
            ItemContent(name = item.name, category = item.category, categoryColor = categoryColor)
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.cd_edit))
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.cd_delete),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun RowScope.ItemContent(
    name: String,
    category: GroceryCategory,
    categoryColor: Color
) {
    Column(modifier = Modifier.weight(1f)) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(categoryLabelRes(category)),
            style = MaterialTheme.typography.bodySmall,
            color = categoryColor
        )
    }
}

private fun itemCategoryColor(category: GroceryCategory): Color = when (category) {
    GroceryCategory.MILK -> MilkBlue
    GroceryCategory.VEGETABLES -> VegetablesGreen
    GroceryCategory.FRUITS -> FruitsRed
    GroceryCategory.BREADS -> BreadsAmber
    GroceryCategory.MEATS -> MeatsPink
}
