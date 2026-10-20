package cz.utb.meteodenik

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

private const val TAG = "Cyklus"

class ZaznamUlozenReceiver(
    private val onZaznamUlozen: (String) -> Unit
) : BroadcastReceiver() {

    companion object {
        const val ACTION_ZAZNAM_ULOZEN = "cz.utb.meteodenik.ZAZNAM"
        const val EXTRA_MESTO = "mesto" // <-- Добавили недостающую константу!
    }

    override fun onReceive(context: Context, intent: Intent) {
        // Теперь используем константу EXTRA_MESTO для чтения
        val mesto = intent.getStringExtra(EXTRA_MESTO) ?: "Neznámé"
        Log.d(TAG, "Receiver onReceive mesto=$mesto")

        onZaznamUlozen(mesto)
    }
}