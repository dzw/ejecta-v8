package ag.boersego.bgjs.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ag.boersego.bgjs.V8Engine
import ag.boersego.bgjs.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var engine: V8Engine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRun.isEnabled = false

        engine = V8Engine()
        engine.addStatusHandler {
            runOnUiThread {
                binding.tvStatus.text = "Status: V8 engine ready"
                binding.btnRun.isEnabled = true
            }
        }
        engine.start(applicationContext)

        binding.btnRun.setOnClickListener {
            val script = binding.etScript.text.toString()
            if (script.isBlank()) return@setOnClickListener

            try {
                val result = engine.runScript(script, "demo.js")
                val output = binding.tvOutput.text.toString()
                val newLine = "> $script\n$result\n\n"
                binding.tvOutput.text = newLine + output
            } catch (e: Exception) {
                Log.e(TAG, "Script error", e)
                val output = binding.tvOutput.text.toString()
                val newLine = "> $script\nError: ${e.message}\n\n"
                binding.tvOutput.text = newLine + output
            }
        }
    }

    companion object {
        private const val TAG = "EjectaDemo"
    }
}
