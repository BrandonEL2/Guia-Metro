package com.example.guiametro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView

class MapaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el diseño XML del fragmento
        return inflater.inflate(R.layout.fragment_mapa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enlazamos el componente y cargamos el mapa aquí adentro
        val imageViewMapa = view.findViewById<SubsamplingScaleImageView>(R.id.imageViewMapa)
        imageViewMapa.setImage(ImageSource.resource(R.drawable.mapa_metro))
    }
}