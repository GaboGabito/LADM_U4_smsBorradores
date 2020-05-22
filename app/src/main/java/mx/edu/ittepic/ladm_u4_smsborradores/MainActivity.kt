
package mx.edu.ittepic.ladm_u4_smsborradores

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val siPermiso = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_SMS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_SMS),siPermiso)
        }else{
            leerSMSBorradores()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==siPermiso){
            leerSMSBorradores()
        }
    }

    private fun leerSMSBorradores() {
        var cursor = contentResolver.query(Telephony.Sms.Draft.CONTENT_URI,null,null,null,null)
        var resultado = ""

        if(cursor!!.moveToFirst()){
            var posColumnaMensaje = cursor.getColumnIndex(Telephony.Sms.BODY)
            var posColumnaFecha = cursor.getColumnIndex(Telephony.Sms.DATE)

            do{
                val fechaMensaje = cursor.getString(posColumnaFecha)
                resultado += "MENSAJE: "+cursor.getString(posColumnaMensaje)+
                        "\nFECHA: "+Date(fechaMensaje.toLong())+"\n-------------------------\n"
            }while (cursor.moveToNext())
        }else{
            resultado = "NO HAY MENSAJES EN BANDEJA DE BORRADOR"
        }
        textView.setText(resultado)
    }
}
