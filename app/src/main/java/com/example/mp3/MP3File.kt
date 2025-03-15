package com.example.mp3

import android.os.Parcel
import android.os.Parcelable

data class MP3File(
    val title: String?,
    val artist: String?,
    val filePath: String?,
    val duration: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(artist)
        parcel.writeString(filePath)
        parcel.writeInt(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MP3File> {
        override fun createFromParcel(parcel: Parcel): MP3File {
            return MP3File(parcel)
        }

        override fun newArray(size: Int): Array<MP3File?> {
            return arrayOfNulls(size)
        }
    }
}
