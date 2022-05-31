package com.example.stuffy.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.stuffy.core.domain.model.Filter

import com.example.stuffy.core.domain.model.Product

class CategoryDiffCallback(private val mOldList: ArrayList<Filter>, private val mNewList: List<Filter>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldList.size
    }
    override fun getNewListSize(): Int {
        return mNewList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition].id == mNewList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            mOldList[oldItemPosition].id != mNewList[newItemPosition].id->{
                false
            }
            else->true
        }
    }
}