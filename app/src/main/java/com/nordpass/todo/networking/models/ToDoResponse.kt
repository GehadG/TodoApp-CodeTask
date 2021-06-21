package com.nordpass.todo.networking.models


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ToDoResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("data") val `data`: List<Data>,
    @SerializedName("meta") val meta: Meta
) : Parcelable {
    @Parcelize
    @Entity(tableName = "todos_table")
    data class Data(
        @SerializedName("completed") val completed: Boolean,
        @SerializedName("created_at") val createdAt: String,
        @PrimaryKey(autoGenerate = false)
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("updated_at") val updatedAt: String,
        @SerializedName("user_id") val userId: Int
    ) : Parcelable
    @Parcelize
    data class Meta(
        @SerializedName("pagination") val pagination: Pagination
    ) : Parcelable {
        @Parcelize
        data class Pagination(
            @SerializedName("limit") val limit: Int,
            @SerializedName("page") val page: Int,
            @SerializedName("pages") val pages: Int,
            @SerializedName("total") val total: Int
        ) : Parcelable
    }
}