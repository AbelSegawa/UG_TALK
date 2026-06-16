package com.talk.ug.utils

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AudioRecorderManager(private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null
    private var isRecording = false
    private var recordingFile: File? = null
    private var startTime: Long = 0

    fun startRecording(): File? {
        return try {
            recordingFile = createAudioFile()
            
            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }
            
            mediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(recordingFile?.absolutePath)
                prepare()
                start()
                isRecording = true
                startTime = System.currentTimeMillis()
            }
            
            recordingFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun stopRecording(): Long {
        return try {
            if (isRecording && mediaRecorder != null) {
                mediaRecorder?.stop()
                mediaRecorder?.release()
                mediaRecorder = null
                isRecording = false
                System.currentTimeMillis() - startTime
            } else {
                0L
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
    }

    fun cancelRecording() {
        try {
            if (isRecording && mediaRecorder != null) {
                mediaRecorder?.stop()
                mediaRecorder?.release()
                mediaRecorder = null
                isRecording = false
                recordingFile?.delete()
                recordingFile = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isRecordingNow(): Boolean = isRecording

    private fun createAudioFile(): File {
        val audioDir = File(context.cacheDir, "audio_recordings")
        if (!audioDir.exists()) {
            audioDir.mkdirs()
        }
        
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        return File(audioDir, "audio_$timestamp.mp4")
    }
}
