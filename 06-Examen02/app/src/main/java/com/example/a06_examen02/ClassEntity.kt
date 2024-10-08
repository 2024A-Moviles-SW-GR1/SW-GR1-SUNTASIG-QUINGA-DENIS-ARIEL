package com.example.a06_examen02

import android.os.Parcel
import android.os.Parcelable

class ClassEntity(
    var id: Int,
    var name: String,
    var semester: Int,
    var groupClass: String,
    var latitude: String,
    var longitude: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        requireNotNull(parcel.readString()) { "Name is null" },
        parcel.readInt(),
        requireNotNull(parcel.readString()) { "GroupClass is null" },
        requireNotNull(parcel.readString()) { "Latitude is null" },
        requireNotNull(parcel.readString()) { "Longitude is null" }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(semester)
        parcel.writeString(groupClass)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ClassEntity> {
        override fun createFromParcel(parcel: Parcel): ClassEntity {
            return ClassEntity(parcel)
        }

        override fun newArray(size: Int): Array<ClassEntity?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "$id - $name"
    }
}