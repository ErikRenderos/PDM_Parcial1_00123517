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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.basketball.R
import com.example.basketball.interfaces.FragmentComunication
import com.example.basketball.models.MatchViewModel
import kotlinx.android.synthetic.main.fragment_match.*
import kotlinx.android.synthetic.main.fragment_new.*

private const val ARG_PARAM1 = "pos"

class MatchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var viewModel: MatchViewModel

    private lateinit var comunicacion: FragmentComunication
    private lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }

        viewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_match, container, false)
    }

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
        viewModel.getAllMatches.observe(this, Observer {
            var match = it[param1!!]
            viewModel.currentMatch.value = match
            if (match.isOver){
                comunicacion.viewMatches()
            }
        })

        viewModel.currentMatch.observe(this, Observer {
            tv_teamA.text = it.teamA
            tv_teamB.text = it.teamB
            tv_scoreA.text = it.scoreA.toString()
            tv_scoreB.text = it.scoreB.toString()
        })

        btnSave.setOnClickListener{
            viewModel.currentMatch.value!!.teamA = (viewModel.currentMatch.value!!.teamA)+3
        }

        btn_teamA_3.setOnClickListener{
            viewModel.currentMatch.value!!.teamA = (viewModel.currentMatch.value!!.teamA)+3
            updateMatch()

        }

        btn_teamB_3.setOnClickListener {
            viewModel.currentMatch.value!!.teamB = (viewModel.currentMatch.value!!.teamB)+3
            updateMatch()
        }

        btn_teamA_2.setOnClickListener{
            viewModel.currentMatch.value!!.teamA = (viewModel.currentMatch.value!!.teamA)+2
            updateMatch()

        }

        btn_teamB_2.setOnClickListener {
            viewModel.currentMatch.value!!.teamB = (viewModel.currentMatch.value!!.teamB)+2
            updateMatch()
        }

        btn_teamA_1.setOnClickListener{
            viewModel.currentMatch.value!!.teamA = (viewModel.currentMatch.value!!.teamA)+1
            updateMatch()

        }

        btn_teamA_1.setOnClickListener {
            viewModel.currentMatch.value!!.teamB = (viewModel.currentMatch.value!!.teamB)+1
            updateMatch()
        }

        btnEnd.setOnClickListener {
            viewModel.currentMatch.value!!.isOver = true
            viewModel.updateMatch(viewModel.currentMatch.value!!)
            Toast.makeText(context, "El partido ha finalizado.", Toast.LENGTH_LONG).show()
            comunicacion.viewMatches()
        }

        btnReset.setOnClickListener {
            viewModel.currentMatch.value!!.teamB = 0.toString()
            viewModel.currentMatch.value!!.teamA = 0.toString()
            updateMatch()
        }
    }

    fun updateMatch(){
        viewModel.currentMatch.value = viewModel.currentMatch.value
    }

    override fun onPause() {
        viewModel.updateMatch(viewModel.currentMatch.value!!)
        super.onPause()
    }

    override fun onDetach() {
        viewModel.updateMatch(viewModel.currentMatch.value!!)
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MatchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

}