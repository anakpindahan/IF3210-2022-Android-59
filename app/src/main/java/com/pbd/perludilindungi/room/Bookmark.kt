package com.pbd.perludilindungi.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid") val uid: Int,
    @ColumnInfo(name = "faskes_id") val faskesId: Int,
    @ColumnInfo(name = "kode") val kode: String,
    @ColumnInfo(name = "nama") val nama: String,
    @ColumnInfo(name = "provinsi") val provinsi: String,
    @ColumnInfo(name = "kota") val kota: String,
    @ColumnInfo(name = "alamat") val alamat: String,
    @ColumnInfo(name = "latitude") val latitude: String,
    @ColumnInfo(name = "longitude") val longitude: String,
    @ColumnInfo(name = "telp") val telp: String?,
    @ColumnInfo(name = "jenis_faskes") val jenis_faskes: String?,
    @ColumnInfo(name = "kelas_rs") val kelas_rs: String?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "source_data") val sourceData: String?,
) : Parcelable