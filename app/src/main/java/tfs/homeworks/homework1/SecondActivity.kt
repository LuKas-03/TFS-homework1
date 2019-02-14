package tfs.homeworks.homework1

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.util.Log


class SecondActivity : AppCompatActivity() {

    private lateinit var messageReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        messageReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == BROADCAST_ACTION) {
                    val result = intent.getIntExtra(WORK_RESULT, 1)
                    finiching(result)
                }
            }
        }
        localBroadcastManager.registerReceiver(messageReceiver, IntentFilter(BROADCAST_ACTION))

        WorkerService.startWaiting(this, 3)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)
    }

    private fun finiching(serviceResult: Int) {
        val data = Intent()
        data.putExtra(WORK_RESULT, serviceResult)
        setResult(Activity.RESULT_OK, data)
        finish()
    }
    companion object {
        public const val WORK_RESULT = "WORK_RESULT"
        public const val BROADCAST_ACTION = "tfs.homeworks.homework1.SERVICE_WORK"
    }
}
