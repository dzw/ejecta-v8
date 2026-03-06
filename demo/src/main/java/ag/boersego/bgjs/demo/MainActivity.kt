package ag.boersego.bgjs.demo

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import ag.boersego.bgjs.V8Engine

class MainActivity : AppCompatActivity() {

    private lateinit var engine: V8Engine
    private var gameView: GameTextureView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val container = findViewById<FrameLayout>(R.id.gameContainer)

        engine = V8Engine()
        engine.addStatusHandler {
            runOnUiThread {
                Log.d(TAG, "V8 engine ready, creating game view")
                gameView = GameTextureView(this, engine)
                container.addView(
                    gameView,
                    FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                )
            }
        }
        engine.start(applicationContext)
    }

    override fun onPause() {
        super.onPause()
        gameView?.pause()
        engine.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView?.unpause()
        engine.unpause()
    }

    override fun onDestroy() {
        gameView?.finish()
        engine.shutdown()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "EjectaDemo"
    }
}
