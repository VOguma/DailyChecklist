package com.example.dailychecklist

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Lista de tarefas predefinidas
        val tasks = listOf(
            "Acordar cedo",
            "Fazer exercício físico",
            "Ler 30 minutos",
            "Estudar programação",
            "Organizar a casa"
        )

        // Estados das tarefas (inicialmente todas desmarcadas)
        val taskStates = tasks.map { false }.toMutableList()

        // Configurar o ListView com um adapter personalizado
        val listView: ListView = findViewById(R.id.taskListView)
        val adapter = TaskAdapter(tasks, taskStates)
        listView.adapter = adapter

        // Botão para redefinir a lista
        val resetButton: Button = findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            for (i in taskStates.indices) {
                taskStates[i] = false
            }
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Lista redefinida!", Toast.LENGTH_SHORT).show()
        }
    }

    // Adapter personalizado para lidar com itens com checkbox
    private class TaskAdapter(
        private val tasks: List<String>,
        private val taskStates: MutableList<Boolean>
    ) : ArrayAdapter<String>(parent.context, R.layout.item_task, tasks) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)

            // Referências ao texto e ao checkbox
            val taskText = view.findViewById<CheckBox>(R.id.taskCheckBox)

            // Configurar o texto e o estado do checkbox
            taskText.text = tasks[position]
            taskText.isChecked = taskStates[position]

            // Alterar estado ao clicar no checkbox
            taskText.setOnCheckedChangeListener { _, isChecked ->
                taskStates[position] = isChecked
            }

            return view
        }
    }
}
