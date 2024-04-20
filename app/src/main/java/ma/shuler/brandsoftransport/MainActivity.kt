package ma.shuler.brandsoftransport

import android.graphics.Color
import android.graphics.Paint.Align
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout.Alignment
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random
import kotlin.random.nextInt


class MainActivity : AppCompatActivity() {

    lateinit var spinner: Spinner

    lateinit var name : TextView
    lateinit var rb1 : RadioButton
    lateinit var rb2 : RadioButton
    lateinit var loadCapacity : TextView
    lateinit var axleCount : TextView

    lateinit var transports: MutableList<Transport>

    fun Spinner.selected(action: (position: Int) -> Unit) {
        this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                action(position)
            }
        }
    }

    fun setTexts() {
        val pos = spinner.selectedItemPosition

        name.text = transports[pos].name

        if (transports[pos].type == "Автомобиль")
            rb1.isChecked = true
        else
            rb2.isChecked = true

        loadCapacity.text = transports[pos].loadCapacity.toString()
        axleCount.text = transports[pos].axleCount.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById<Spinner>(R.id.spinner)

        transports = mutableListOf(
            Transport("BMW", "Автомобиль", 1000, 4),
            Transport("Honda", "Мотоцикл", 0, 2),
            Transport("Audi", "Автомобиль", 750, 4),
            Transport("Kia", "Мотоцикл", 100, 2),
            Transport("Lada", "Автомобиль", 650, 4),
        )

        val adapter = TransportAdapter(this, transports)
        spinner.adapter = adapter

        name = findViewById<TextView>(R.id.textView2)
        rb1 = findViewById<RadioButton>(R.id.radioButton1)
        rb2 = findViewById<RadioButton>(R.id.radioButton2)
        loadCapacity = findViewById<TextView>(R.id.textView5)
        axleCount = findViewById<TextView>(R.id.textView7)

        spinner.selected {
            setTexts()
        }

        val delButton = findViewById<Button>(R.id.button)
        delButton.setOnClickListener {
            showDeleteDialog()
        }

        val addButton = findViewById<Button>(R.id.button2)
        addButton.setOnClickListener {
            showAddDialog()
        }

        val infoButton = findViewById<Button>(R.id.button3)
        infoButton.setOnClickListener {
            infoButtonPressed()
        }

    }

    private fun infoButtonPressed() {
        val rand = Random

        val r = rand.nextInt(255)
        val g = rand.nextInt(255)
        val b = rand.nextInt(255)
        name.setBackgroundColor(Color.rgb(r, g, b))

        name.text = "Позиция из ${spinner.selectedItemPosition + 1} в ${transports.size}"
    }

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Удалить элемент?")
        builder.setMessage("Выберите действие")
        builder.setPositiveButton("Удалить") { dialog, which ->
            if (transports.size > 1) {
                transports.removeAt(spinner.selectedItemPosition)
                setTexts()
            }
        }
        builder.setNegativeButton("Отмена") { dialog, which ->

        }
        builder.setCancelable(true) // Позволяет закрыть диалоговое окно, нажав вне его области
        builder.show()
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_layout, null)

        builder.setView(dialogLayout)
        builder.setPositiveButton("Добавить") { dialog, which ->
            val d1 = dialogLayout.findViewById<TextInputLayout>(R.id.input1)
            val rb1 = dialogLayout.findViewById<RadioButton>(R.id.radioButton11)
            val rb2 = dialogLayout.findViewById<RadioButton>(R.id.radioButton22)
            val d2 = dialogLayout.findViewById<TextInputLayout>(R.id.input2)
            val d3 = dialogLayout.findViewById<TextInputLayout>(R.id.input3)

            var str : String
            if (rb1.isChecked)
                str = "Автомобиль"
            else
                str = "Мотоцикл"

            transports.add(Transport(d1.editText?.text.toString(), str,
                d2.editText?.text.toString().toInt(),  d3.editText?.text.toString().toInt()))
        }
        builder.setNegativeButton("Отмена") { dialog, which ->
        }

        val dialog = builder.create()
        dialog.show()
    }

}