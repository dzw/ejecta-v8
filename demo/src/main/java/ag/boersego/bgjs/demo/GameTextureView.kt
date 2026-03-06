package ag.boersego.bgjs.demo

import android.content.Context
import android.util.Log
import ag.boersego.bgjs.BGJSGLView
import ag.boersego.bgjs.V8Engine
import ag.boersego.bgjs.V8TextureView

class GameTextureView(context: Context, engine: V8Engine) : V8TextureView(context, engine) {

    override fun onGLCreated(jsViewObject: BGJSGLView) {
        Log.d(TAG, "onGLCreated, setting up game script")
        engine.runLocked {
            try {
                val global = engine.getGlobalObject()
                global.setV8Field("__glView", jsViewObject)
                engine.require("./game")
            } catch (e: Exception) {
                Log.e(TAG, "Error setting up game", e)
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
