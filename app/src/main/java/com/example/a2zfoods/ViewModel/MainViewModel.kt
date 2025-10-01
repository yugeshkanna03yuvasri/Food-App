package com.example.a2zfoods.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.a2zfoods.Domain.CategoryModel
import com.example.a2zfoods.Domain.FoodModel
import com.example.a2zfoods.Repository.MainRepository

class MainViewModel : ViewModel(){
    private val repository= MainRepository()
    fun loadCategory(): LiveData<MutableList<CategoryModel>>
    {
        return repository.loadCategory()
    }

    fun loadBestFood(): LiveData<MutableList<FoodModel>>
    {
        return repository.loadBestFood()
    }

    fun loadFiltered(id: String): LiveData<MutableList<FoodModel>>{
        return repository.loadFiltered(id)
    }

    fun saveOrder(cartItems: List<FoodModel>, total: Double) {
        repository.saveOrder(cartItems, total)
    }


}