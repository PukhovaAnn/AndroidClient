package com.pukho.dogappandroid.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * Created by Pukho on 24.03.2017.
 */
class Dog private constructor(var id: Long, var name: String, var pictureLocation: String) : Parcelable {
    override fun toString(): String {
        return "Dog(id=$id, name='$name', pictureLocation='$pictureLocation')"
    }

    constructor(name: String) : this(0, name, "undefined")

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Dog> = object : Parcelable.Creator<Dog> {
            override fun createFromParcel(source: Parcel): Dog = Dog(source)
            override fun newArray(size: Int): Array<Dog?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readLong(), source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeString(name)
        dest?.writeString(pictureLocation)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Dog

        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + pictureLocation.hashCode()
        return result
    }
}