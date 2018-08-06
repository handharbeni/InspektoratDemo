package com.mhandharbeni.inspektoratdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import butterknife.ButterKnife
import butterknife.BindView
import butterknife.OnClick
import com.mhandharbeni.inspektoratdemo.modul.surat_keluar.MainSuratKeluar
import com.mhandharbeni.inspektoratdemo.modul.surat_masuk.MainSuratMasuk
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity(){
    @BindView(R.id.llSuratMasuk) lateinit var llSuratMasuk : LinearLayout
    @BindView(R.id.llSuratKeluar) lateinit var llSuratKeluar : LinearLayout
//    @Nullable @BindView(R.id.llElapor) lateinit var llELapor : LinearLayout
//    @Nullable @BindView(R.id.llEsakip) lateinit var llESakip : LinearLayout
//    @Nullable @BindView(R.id.llTindakLanjut) lateinit var llTindakLanjut : LinearLayout
//    @Nullable @BindView(R.id.llOther) lateinit var llOther : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Realm.init(applicationContext)
        var c = RealmConfiguration.Builder()
        c.name("InspektoratDB")
        c.deleteRealmIfMigrationNeeded()
        Realm.setDefaultConfiguration(c.build())


        setContentView(R.layout.main_layout)
        ButterKnife.bind(this)
    }


    @OnClick(R.id.llSuratMasuk) fun suratMasuk(){
        val intent = MainSuratMasuk.newIntent(applicationContext)
        startActivity(intent)
    }

    @OnClick(R.id.llSuratKeluar) fun suratKeluar(){
        val intent = MainSuratKeluar.newIntent(applicationContext)
        startActivity(intent)
    }

//    @OnClick(R.id.llElapor) fun eLapor(){
//        toast("E-LAPOR Under Maintenance")
//    }
//
//    @OnClick(R.id.llEsakip) fun eSakip(){
//        toast("E-SAKIP Under Maintenance")
//    }
//
//    @OnClick(R.id.llTindakLanjut) fun tindakLanjut(){
//        toast("E-Tindak Lanjut Under Maintenance")
//    }
//
//    @OnClick(R.id.llOther) fun other(){
//        toast("Other")
//    }
}
