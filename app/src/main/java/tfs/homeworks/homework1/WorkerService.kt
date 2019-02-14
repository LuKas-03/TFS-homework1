package tfs.homeworks.homework1

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import kotlin.random.Random


// TODO: Rename parameters
private const val SECONDS = "tfs.homeworks.homework1.extra.SECONDS"


class WorkerService : IntentService("WorkerService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val seconds = intent.getIntExtra(SECONDS, 0)
            startWork(seconds)
        }
    }

    private fun startWork(seconds: Int) {
        val count = Random.nextInt(1, 6)
        Thread.sleep((seconds * 1000).toLong())
        val intent = Intent(SecondActivity.BROADCAST_ACTION)
        intent.putExtra(SecondActivity.WORK_RESULT, count)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    companion object {
        @JvmStatic
        fun startWaiting(context: Context, time: Int) {
            val intent = Intent(context, WorkerService::class.java).apply {
                putExtra(SECONDS, time)
            }
            context.startService(intent)
        }
    }
}
