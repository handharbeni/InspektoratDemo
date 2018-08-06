package com.mhandharbeni.inspektoratdemo.modul.surat_keluar

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mhandharbeni.inspektoratdemo.R

class SuratKeluarAdapter(private val suratKeluarList:ArrayList<SuratKeluarModel>, val clickListener : (SuratKeluarModel) -> Unit): RecyclerView.Adapter<SuratKeluarAdapter.Holder>() {
    class Holder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        val cvParent = itemView.findViewById<CardView>(R.id.cvParent)!!
        val nomorSurat = itemView.findViewById<TextView>(R.id.txtNomorSurat)!!
        val tanggalSurat = itemView.findViewById<TextView>(R.id.txtTanggalSurat)!!
        val tujuanSurat = itemView.findViewById<TextView>(R.id.txtTujuanSurat)!!
        val judulSurat = itemView.findViewById<TextView>(R.id.txtJudulSurat)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuratKeluarAdapter.Holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_surat_keluar, parent, false)
        return Holder(v)
    }

    override fun getItemCount(): Int {
        return suratKeluarList.size
    }

    override fun onBindViewHolder(holder: SuratKeluarAdapter.Holder, position: Int) {
        holder.nomorSurat.text = suratKeluarList[position].nomor
        holder.tanggalSurat.text = suratKeluarList[position].tanggal_surat
        holder.tujuanSurat.text = suratKeluarList[position].tujuan
        holder.judulSurat.text = suratKeluarList[position].judul_surat
        holder.cvParent.setOnClickListener {
            clickListener(suratKeluarList[position])
        }
    }

}