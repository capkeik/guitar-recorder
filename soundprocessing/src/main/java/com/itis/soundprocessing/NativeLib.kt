package com.itis.soundprocessing

class NativeLib {

    /**
     * A native method that is implemented by the 'soundprocessing' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'soundprocessing' library on application startup.
        init {
            System.loadLibrary("soundprocessing")
        }
    }
}