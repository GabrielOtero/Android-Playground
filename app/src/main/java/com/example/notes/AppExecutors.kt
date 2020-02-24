package com.example.notes

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

object AppExecutors {
    val networkIO: ScheduledExecutorService = Executors.newScheduledThreadPool(3)
}