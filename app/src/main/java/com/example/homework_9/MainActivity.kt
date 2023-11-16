package com.example.homework_9

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), CategoryAdapter.OnCategoryClickListener {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var binding: ActivityMainBinding

    private val categoryList = listOf<Category>(
        Category("All"),
        Category("\uD83C\uDF89 Party"),
        Category("\uD83C\uDFD5 Camping"),
        Category("\uD83D\uDCA5 Category1"),
        Category("\uD83D\uDD25 Category2"),
        Category("\uD83E\uDD8D Category3"),
    )

    private val itemList = listOf<Item>(
        Item(1, "model_1", "Belt suit blazer", "$120", "\uD83C\uDF89 Party"),
        Item(2, "model_2", "Belt suit blazer", "$110", "\uD83C\uDFD5 Camping"),
        Item(3, "model_3", "Belt suit blazer", "$90", "\uD83D\uDCA5 Category1"),
        Item(4, "model_4", "Belt suit blazer", "$70", "\uD83D\uDD25 Category2"),
        Item(5, "model_1", "Belt suit blazer", "$125", "\uD83E\uDD8D Category3"),
        Item(6, "model_2", "Belt suit blazer", "$85", "\uD83C\uDF89 Party"),
        Item(7, "model_3", "Belt suit blazer", "$135", "\uD83C\uDFD5 Camping"),
        Item(8, "model_4", "Belt suit blazer", "$115", "\uD83D\uDCA5 Category1"),
        Item(9, "model_1", "Belt suit blazer", "$120", "\uD83D\uDD25 Category2"),
        Item(10, "model_2", "Belt suit blazer", "$120", "\uD83E\uDD8D Category3")
    )
    //currently selected category. its not because when you first launch the program nothing is chosen
    private var selectedCategory: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //category adapter
        categoryAdapter = CategoryAdapter(categoryList, this)
        binding.categoryView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryView.adapter = categoryAdapter

        //item adapter
        itemAdapter = ItemAdapter()
        binding.itemView.layoutManager = GridLayoutManager(this, 2)
        binding.itemView.adapter = itemAdapter

        //setting up initial state
        updateItemAdapter()
    }

    //overriding the interface
    override fun onCategoryClick(category: Category) {
        //update the selected category and refresh the item adapter
        selectedCategory = category
        updateItemAdapter()

        //ui manipulations. for selected item in category list, the background changes and the text color
        //when anotehr item is pressed everything is restored
        val selectedPosition = categoryList.indexOf(category)
        for (i in categoryList.indices) {
            val holder =
                binding.categoryView.findViewHolderForAdapterPosition(i) as CategoryAdapter.CategoryViewHolder?
            if (i == selectedPosition) {
                holder?.itemView?.isSelected = true
                holder?.itemView?.setBackgroundResource(R.drawable.category_background_selected)
                val color = ContextCompat.getColor(this, android.R.color.white)
                holder?.binding?.categoryName?.setTextColor(color)
            } else {
                holder?.itemView?.isSelected = false
                holder?.itemView?.setBackgroundResource(R.drawable.category_background)
                val hexColor = "#96A7AF"
                val color = Color.parseColor(hexColor)
                holder?.binding?.categoryName?.setTextColor(color)
            }
        }
    }

    //method to update the item adapter based on the selected category
    private fun updateItemAdapter() {
        val filteredItems = if (selectedCategory == null || selectedCategory!!.name == "All") {
            //showing all categories together (either when "all" is selected or none - inital state)
            itemList
        } else {
            //filtering items based on category
            itemList.filter { it.category == selectedCategory!!.name }
        }
        itemAdapter.setData(filteredItems)
    }
}
