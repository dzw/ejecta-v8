package ag.boersego.bgjs.demo

import android.content.Context
import android.util.Log
import ag.boersego.bgjs.BGJSGLView
import ag.boersego.bgjs.JNIV8Function
import ag.boersego.bgjs.V8Engine
import ag.boersego.bgjs.V8TextureView

class GameTextureView(context: Context, engine: V8Engine) : V8TextureView(context, engine) {

    override fun onGLCreated(jsViewObject: BGJSGLView) {
        engine.runLocked {
            try {
                val startGame = engine.require("./game") as? JNIV8Function
                startGame?.callAsV8Function(jsViewObject)
                    ?: Log.e(TAG, "game.js did not export a function")
            } catch (e: Exception) {
                Log.e(TAG, "Error running game.js", e)
            }
        }
    }

    override fun onGLCreateError(ex: Exception) {
        Log.e(TAG, "GL creation failed", ex)
    }

    companion object {
        private const val TAG = "GameTextureView"
    }
}
