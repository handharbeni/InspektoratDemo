package com.mhandharbeni.inspektoratdemo.modul.surat_masuk

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SuratMasukModel(
    @PrimaryKey var id : Long = 0,
    var nomor : String= "",
    var tujuan : String= "",
    var status : String= "",
    var judul_surat : String= "",
    var isi_surat : String= "",
    var tanggal_surat : String= ""
) : RealmObject()