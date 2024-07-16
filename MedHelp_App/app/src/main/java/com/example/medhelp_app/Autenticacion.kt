package com.example.medhelp_app

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class FragmentoAu : Fragment() {
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragmento_login, container, false)

        auth = FirebaseAuth.getInstance()

        val correoCampo = view.findViewById<EditText>(R.id.correo)
        val contrasenaCampo = view.findViewById<EditText>(R.id.contraseña)
        val botonRegistro = view.findViewById<Button>(R.id.register)
        val botonLogin = view.findViewById<Button>(R.id.login)
        val botonGoogle = view.findViewById<Button>(R.id.googleBoton)


        botonRegistro.setOnClickListener {
            val correoR = correoCampo.text.toString().trim()
            val contrasenaR = contrasenaCampo.text.toString().trim()
            if (correoR.isNotEmpty() && contrasenaR.isNotEmpty()) {
                    if(contrasenaR.length >= 8){
                        auth.createUserWithEmailAndPassword(correoR, contrasenaR)
                            .addOnCompleteListener(requireActivity()) { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                    pasarPant()
                                } else {
                                    Toast.makeText(context, "No se ha podido completar su registro", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }else{
                        Toast.makeText(context,"La contrasena debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, "Por favor complete ambos campos", Toast.LENGTH_SHORT).show()
            }
        }

        botonLogin.setOnClickListener {
            val correoL = correoCampo.text.toString().trim()
            val contrasenaL = contrasenaCampo.text.toString().trim()
            if (correoL.isNotEmpty() && contrasenaL.isNotEmpty()) {
                auth.signInWithEmailAndPassword(correoL, contrasenaL)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show()
                            pasarPant()
                        } else {
                            Toast.makeText(context, "No se ha podido completar el inicio de sesion", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(context, "Por favor complete ambos campos", Toast.LENGTH_SHORT).show()
            }
        }
        val googleConfig = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(requireContext(), googleConfig)

        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Manejar el error
                    Log.w(TAG, "Google sign in failed", e)
                }
            }
        }
        botonGoogle.setOnClickListener {
            val signInIntent = googleClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
        return view
    }

    private fun pasarPant() {
        val intent = Intent(activity, Inicio::class.java)
        startActivity(intent)
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = FirebaseAuth.getInstance().currentUser
                    // Redirigir a la actividad Inicio
                    val intent = Intent(requireContext(), Inicio::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // Update UI
                }
            }
    }
}