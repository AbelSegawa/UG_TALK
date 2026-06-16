# UG_TALK - Android Messaging Application

## Project Overview
UG_TALK is a WhatsApp-like messaging application built with Kotlin for Android. It enables users to authenticate via Supabase, search and connect with other users, and communicate through text, voice messages, and image sharing.

## Application Details
- **Package Name**: com.talk.ug
- **Language**: Kotlin
- **Gradle Version**: Gradle 6.0+
- **Minimum SDK**: API 23 (Android 6.0)
- **Target SDK**: API 34

## Key Features

### Authentication & User Management
- ✅ Email-based authentication via Supabase
- ✅ User registration with phone number and name
- ✅ User profile management (name, bio, profile picture)
- ✅ Online/offline status tracking
- ✅ Last seen timestamp

### Messaging Features
- ✅ Real-time text messaging
- ✅ Voice message recording and playback
- ✅ Image sharing functionality
- ✅ Message read status tracking
- ✅ Typing indicators
- ✅ File sharing support

### User Interface
- ✅ Chat list with unread message indicators
- ✅ Contact/User search (by name or phone number)
- ✅ User profile view
- ✅ Profile editing interface
- ✅ Online status indicators

### Settings & Preferences
- ✅ Push notification settings
- ✅ Theme customization (light/dark mode)
- ✅ Profile information updates
- ✅ Account deletion
- ✅ Audio preferences

## Technology Stack

### Backend & Database
- Supabase (Authentication, Realtime, Storage)
- PostgreSQL (via Supabase)
- Firebase Cloud Messaging (optional)

### Android Libraries
- Jetpack Components (Navigation, Lifecycle, Room)
- Material Design 3
- Kotlin Coroutines
- Hilt (Dependency Injection)
- Retrofit (API calls)
- Glide (Image loading)
- ExoPlayer (Media playback)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/AbelSegawa/UG_TALK.git
cd UG_TALK
```

2. Open in Android Studio

3. Configure Supabase credentials:
   - Edit `app/src/main/java/com/talk/ug/data/remote/SupabaseClient.kt`
   - Replace `SUPABASE_URL` and `SUPABASE_ANON_KEY` with your credentials

4. Build and run:
```bash
./gradlew build
```

## Permissions Required
- INTERNET
- RECORD_AUDIO
- CAMERA
- READ_EXTERNAL_STORAGE
- WRITE_EXTERNAL_STORAGE
- POST_NOTIFICATIONS
- MODIFY_AUDIO_SETTINGS
- READ_CONTACTS

## Database Schema

### Users Table
- id (Primary Key)
- email
- name
- phone
- bio
- profilePicUrl
- isOnline
- lastSeen
- createdAt
- updatedAt

### Messages Table
- id (Primary Key)
- chatId
- senderId
- receiverId
- content
- messageType (TEXT, IMAGE, AUDIO, FILE)
- mediaUrl
- duration (for audio)
- isRead
- timestamp

### Chats Table
- id (Primary Key)
- participantIds
- lastMessage
- lastMessageTime
- unreadCount
- createdAt

## Project Structure

```
UG_TALK/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/talk/ug/
│   │   │   │   ├── data/
│   │   │   │   │   ├── local/
│   │   │   │   │   │   ├── dao/
│   │   │   │   │   │   ├── entity/
│   │   │   │   │   │   ├── AppDatabase.kt
│   │   │   │   │   │   └── Converters.kt
│   │   │   │   │   └── remote/
│   │   │   │   ├── domain/
│   │   │   │   │   ├── model/
│   │   │   │   │   └── repository/
│   │   │   │   ├── ui/
│   │   │   │   │   ├── auth/
│   │   │   │   │   ├── main/
│   │   │   │   │   ├── chat/
│   │   │   │   │   ├── profile/
│   │   │   │   │   └── settings/
│   │   │   │   └── utils/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## Contributing
Contributions are welcome! Please create a feature branch and submit a pull request.

## License
MIT License - feel free to use this project for your own purposes.
