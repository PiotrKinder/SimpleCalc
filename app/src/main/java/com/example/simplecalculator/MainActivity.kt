package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.mariuszgromada.math.mxparser.Expression
import java.lang.Exception
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    var actual: String =""
    var needMultiply=false
    var allowOperation=false
    var allowComa=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var output: TextView= findViewById(R.id.textOut)
        output.showSoftInputOnFocus = false


    }
    fun addToFinalString(toAdd: String){
        var output: TextView= findViewById(R.id.textOut)
        actual+=toAdd
        output.setText(actual)
        needMultiply=false
    }

    fun zeroAction(view: View){numberCheck("0")}
    fun oneAction(view: View){numberCheck("1")}
    fun twoAction(view: View){numberCheck("2")}
    fun threeAction(view: View){numberCheck("3")}
    fun fourAction(view: View){numberCheck("4")}
    fun fiveAction(view: View){numberCheck("5")}
    fun sixAction(view: View){numberCheck("6")}
    fun sevenAction(view: View){numberCheck("7")}
    fun eightAction(view: View){numberCheck("8")}
    fun nineAction(view: View){numberCheck("9")}
    fun addAction(view: View){signCheck("+")}
    fun subAction(view: View){signCheck("-")}
    fun divAction(view: View){signCheck("÷")}
    fun multiAction(view: View){signCheck("×")}
    fun procentAction(view: View){
        if(!actual.isEmpty()){
            val toTest= actual[actual.length-1]
            if(allowOperation && toTest!='%'){
                addToFinalString("%")
                needMultiply=true
            }
        }
    }
    fun comaAction(view: View){
        if(!actual.isEmpty()){
            if(allowComa)
                signCheck(".")
        }else  addToFinalString("0.")
    }

    fun clearAction(view: View){
        var output: TextView= findViewById(R.id.textOut)
        output.setText("")
        actual=""
    }

    fun brocketOpenAction(view: View){
        var final: String
        if(actual.isEmpty())
            final="("
        else
        if(actual[actual.length-1].isDigit())
            final="×"+"("
        else
            final="("
        addToFinalString(final)
    }

    fun brocketCloseAction(view: View){
        if(actual.isEmpty()){
        }
        else if (actual[actual.length-1].isDigit() || actual[actual.length-1]=='%'){
            addToFinalString(")")
        }

    }

    fun signCheck(sign: String){
        if(!actual.isEmpty()){
            if(allowOperation){
                needMultiply=false
                allowOperation=false
                addToFinalString(sign)
            }
        }
    }

    fun numberCheck(sign: String){
        allowComa=true
        allowOperation=true
        var final: String
        if(needMultiply)
            final="×"+sign
        else
            final=sign
        addToFinalString(final)
    }

    private fun getInputExpression(): String {
        var expression = actual.replace(Regex("÷"), "/")
        expression = expression.replace(Regex("×"), "*")
        return expression
    }

    fun equalAction(view: View){
        var output: TextView= findViewById(R.id.textOut)
        try {
            val expression = getInputExpression()
            val result = Expression(expression).calculate()
            if (result.isNaN()) {
                actual = "Error"
            } else {
                actual= DecimalFormat("0.######").format(result).toString()

            }
        }catch (e : Exception){
            actual = "Error"
        }
        output.text =actual
    }
}