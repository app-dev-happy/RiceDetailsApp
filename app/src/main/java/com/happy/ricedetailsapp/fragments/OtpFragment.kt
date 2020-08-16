package com.happy.ricedetailsapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.happy.ricedetailsapp.DashboardActivity
import com.happy.ricedetailsapp.R
import com.happy.ricedetailsapp.databinding.FragmentOtpBinding
import java.util.concurrent.TimeUnit


class OtpFragment : Fragment() {
    private var mVerificationId: String? = null
    private var mAuth: FirebaseAuth? = null
   lateinit var layoutActivityDashboardBinding:FragmentOtpBinding
    var mobileNo: String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layoutActivityDashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_otp, container, false)

        //initializing objects
        mAuth = FirebaseAuth.getInstance()
        sendVerificationCode(mobileNo!!)
        return layoutActivityDashboardBinding.root
    }

    fun setData(mobileNo: String) {
        this.mobileNo = mobileNo
    }

    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    private fun sendVerificationCode(mobile: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+$mobile",  //phoneNo that is given by user
            60,  //Timeout Duration
            TimeUnit.SECONDS,  //Unit of Timeout
            TaskExecutors.MAIN_THREAD,  //Work done on main Thread
            mCallbacks
        ) // OnVerificationStateChangedCallbacks
    }


    //the callback to detect the verification status
    private val mCallbacks: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                //Getting the code sent by SMS
                val code = phoneAuthCredential.smsCode

                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code
                if (code != null) {
                    layoutActivityDashboardBinding.editTextCode.setText(code)
                    //verifying the code
                    verifyVerificationCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", e.message)
            }

            //when the code is generated then this method will receive the code.
            override fun onCodeSent(
                s: String,
                forceResendingToken: ForceResendingToken
            ) {
//                super.onCodeSent(s, forceResendingToken);

                //storing the verification id that is sent to the user
                mVerificationId = s
            }
        }

    private fun verifyVerificationCode(code: String) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    //used for signing the user
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity(),
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        //verification successful we will start the profile activity
                        val intent =
                            Intent(requireActivity(), DashboardActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {

                        //verification unsuccessful.. display an error message
                        var message =
                            "Somthing is wrong, we will fix it soon..."
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered..."
                        }
                        Toast.makeText(context, message, Toast.LENGTH_SHORT)
                            .show()
                    }
                })
    }

}