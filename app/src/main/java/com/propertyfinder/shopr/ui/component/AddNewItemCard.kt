package com.propertyfinder.shopr.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.propertyfinder.shopr.R
import com.propertyfinder.shopr.data.model.GroceryCategory
import com.propertyfinder.shopr.ui.res.categoryEmoji
import com.propertyfinder.shopr.ui.res.categoryLabelRes
import com.propertyfinder.shopr.ui.theme.AddCardBackground
import com.propertyfinder.shopr.ui.theme.BreadsChipBg
import com.propertyfinder.shopr.ui.theme.BreadsChipText
import com.propertyfinder.shopr.ui.theme.ButtonDark
import com.propertyfinder.shopr.ui.theme.FruitsChipBg
import com.propertyfinder.shopr.ui.theme.FruitsChipText
import com.propertyfinder.shopr.ui.theme.GradientEnd
import com.propertyfinder.shopr.ui.theme.GradientStart
import com.propertyfinder.shopr.ui.theme.MilkChipBg
import com.propertyfinder.shopr.ui.theme.MeatsChipBg
import com.propertyfinder.shopr.ui.theme.MeatsChipText
import com.propertyfinder.shopr.ui.theme.SelectedChipBlue
import com.propertyfinder.shopr.ui.theme.VegetablesChipBg
import com.propertyfinder.shopr.ui.theme.VegetablesChipText

@Composable
fun AddNewItemCard(
    itemName: String,
    selectedCategory: GroceryCategory,
    onItemNameChange: (String) -> Unit,
    onCategorySelect: (GroceryCategory) -> Unit,
    primaryButtonLabel: String,
    onPrimaryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AddCardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        AddNewItemCardTitle()
        Column(modifier = Modifier.padding(20.dp)) {
            ItemNameLabel()
            Spacer(modifier = Modifier.height(6.dp))
            ItemNameField(
                value = itemName,
                onValueChange = onItemNameChange
            )
            Spacer(modifier = Modifier.height(14.dp))
            CategoryLabel()
            Spacer(modifier = Modifier.height(8.dp))
            CategoryChipsRow(
                selectedCategory = selectedCategory,
                onCategorySelect = onCategorySelect
            )
            Spacer(modifier = Modifier.height(16.dp))
            AddItemButton(
                enabled = itemName.trim().length > 2,
                buttonLabel = primaryButtonLabel,
                onClick = onPrimaryClick
            )
        }
    }
}

@Composable
private fun AddNewItemCardTitle() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                ),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(vertical = 14.dp)
    ) {
        Text(
            text = stringResource(R.string.add_new_item),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
private fun ItemNameLabel() {
    Text(
        text = stringResource(R.string.item_name),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun ItemNameField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                stringResource(R.string.enter_grocery_item_hint),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GradientStart,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

@Composable
private fun CategoryLabel() {
    Text(
        text = stringResource(R.string.category),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun CategoryChipsRow(
    selectedCategory: GroceryCategory,
    onCategorySelect: (GroceryCategory) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GroceryCategory.entries.forEach { category ->
            CategoryChip(
                category = category,
                isSelected = selectedCategory == category,
                onClick = { onCategorySelect(category) }
            )
        }
    }
}

@Composable
private fun CategoryChip(
    category: GroceryCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val (backgroundBrush, textColor) = when {
        isSelected -> Brush.verticalGradient(
            colors = listOf(SelectedChipBlue, SelectedChipBlue)
        ) to Color.White

        else -> when (category) {
            GroceryCategory.MILK -> Brush.verticalGradient(
                colors = listOf(MilkChipBg, MilkChipBg.copy(alpha = 0.85f))
            ) to SelectedChipBlue

            GroceryCategory.VEGETABLES -> Brush.verticalGradient(
                colors = listOf(VegetablesChipBg, VegetablesChipBg.copy(alpha = 0.85f))
            ) to VegetablesChipText

            GroceryCategory.FRUITS -> Brush.verticalGradient(
                colors = listOf(FruitsChipBg, FruitsChipBg.copy(alpha = 0.85f))
            ) to FruitsChipText

            GroceryCategory.BREADS -> Brush.verticalGradient(
                colors = listOf(BreadsChipBg, BreadsChipBg.copy(alpha = 0.85f))
            ) to BreadsChipText

            GroceryCategory.MEATS -> Brush.verticalGradient(
                colors = listOf(MeatsChipBg, MeatsChipBg.copy(alpha = 0.85f))
            ) to MeatsChipText
        }
    }
    Card(
        modifier = Modifier
            .size(width = 78.dp, height = 74.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp, horizontal = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = categoryEmoji(category),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = stringResource(categoryLabelRes(category)),
                    style = MaterialTheme.typography.labelMedium,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun AddItemButton(
    enabled: Boolean,
    buttonLabel: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SelectedChipBlue,
            disabledContainerColor = ButtonDark,
            contentColor = Color.White,
            disabledContentColor = Color.White.copy(alpha = 0.7f)
        )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(buttonLabel)
    }
}
