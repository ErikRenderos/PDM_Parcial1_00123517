package com.example.basketball.fragments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.basketball.R
import com.example.basketball.database.entities.Match
import com.example.basketball.interfaces.FragmentComunication
import com.example.basketball.models.MatchViewModel
import kotlinx.android.synthetic.main.fragment_new.*
import java.lang.Exception
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var comunicacion: FragmentComunication
    private lateinit var activity: Activity

    private lateinit var viewModel: MatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is Activity){
            activity = context
            if (activity is FragmentComunication){
                comunicacion = activity as FragmentComunication
            }
        }
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onStart() {
        super.onStart()
        btnSave.setOnClickListener {
            if (et_eA.text.isNotEmpty() && et_eB.text.isNotBlank()
                && et_eA.text.isNotEmpty() && et_eA.text.isNotBlank()){
                var partido = Match(0, et_eA.text.toString(), et_eB.text.toString(), 0, 0, Calendar.getInstance().time, false)
                try {
                    viewModel.insertMatch(partido)
                    Toast.makeText(context, "Se ha creado el nuevo partido.", Toast.LENGTH_LONG).show()
                    et_eB.text.clear()
                    et_eA.text.clear()
                }catch(e: Exception){
                    Toast.makeText(context, "Fallo al crear el nuevo partido.", Toast.LENGTH_LONG).show()
                }

            }else{
                Toast.makeText(context, "Por favor revise que se hayan llenado todos los campos.", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}