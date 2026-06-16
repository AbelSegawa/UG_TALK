package com.talk.ug.data.remote

import io.github.supabase.Supabase
import io.github.supabase.GoTrue
import io.github.supabase.PostgrestClient
import io.github.supabase.RealtimeClient
import io.github.supabase.StorageClient

object SupabaseClient {
    // Supabase Configuration
    private const val SUPABASE_URL = "https://nmrxgmnwreatylkhmorc.supabase.co"
    private const val SUPABASE_ANON_KEY = "sb_publishable_K57BMp09wFfNwcHbSLQGXA_1D8m6-nI"

    // Initialize Supabase client
    val client by lazy {
        Supabase.createClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_ANON_KEY
        )
    }

    // Authentication client
    val auth: GoTrue
        get() = client.auth

    // Database client
    val db: PostgrestClient
        get() = client.postgrest

    // Realtime client
    val realtime: RealtimeClient
        get() = client.realtime

    // Storage client
    val storage: StorageClient
        get() = client.storage
}
