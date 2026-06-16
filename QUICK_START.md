# UG_TALK - Quick Start Guide

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest version)
- Android SDK 23+ (API 23)
- Kotlin 1.8.0+
- Git

### Step 1: Clone the Repository

```bash
git clone https://github.com/AbelSegawa/UG_TALK.git
cd UG_TALK
```

### Step 2: Set Up Supabase

**IMPORTANT:** Complete the Supabase setup FIRST before running the app.

Refer to: `SUPABASE_SQL_SETUP.md`

Quick checklist:
- ✅ All database tables created (users, messages, chats)
- ✅ RLS policies enabled
- ✅ Storage buckets created (profile-pictures, message-media)
- ✅ Email authentication configured

### Step 3: Open Project in Android Studio

1. Open Android Studio
2. Click "File" → "Open"
3. Select the `UG_TALK` folder
4. Wait for Gradle sync to complete

### Step 4: Configure Supabase Credentials

The credentials are already configured in:
`app/src/main/java/com/talk/ug/data/remote/SupabaseClient.kt`

**Already set:**
```kotlin
SUPABASE_URL = "https://nmrxgmnwreatylkhmorc.supabase.co"
SUPABASE_ANON_KEY = "sb_publishable_K57BMp09wFfNwcHbSLQGXA_1D8m6-nI"
```

### Step 5: Build the Project

1. Click "Build" → "Clean Project"
2. Click "Build" → "Make Project"
3. Wait for build to complete

**If you see errors:**
- Check that all dependencies are downloaded
- Go to "File" → "Sync Now"
- Try building again

### Step 6: Run the App

#### Option A: Run on Emulator
1. Click "Tools" → "Device Manager"
2. Create a new virtual device (Pixel 4, API 30+)
3. Start the emulator
4. Click "Run" → "Run app" (or press Shift+F10)

#### Option B: Run on Physical Device
1. Enable USB Debugging on your Android phone
2. Connect phone to computer via USB
3. Click "Run" → "Run app"

### Step 7: Test the App

#### Test User Registration
1. App opens with Splash screen
2. Click "Sign Up"
3. Enter:
   - Name: Test User
   - Email: testuser@example.com
   - Phone: +256701234567
   - Password: Test123!
4. Click "Sign Up"
5. You should be logged in

#### Test User Login
1. Click "Login"
2. Enter the same email and password
3. You should be logged in

#### Test Messaging
1. Go to Contacts tab
2. Create another test user
3. Click on contact to start chat
4. Send a test message
5. Check Supabase database

---

## 📁 Project Structure

```
UG_TALK/
├── app/src/main/
│   ├── java/com/talk/ug/
│   │   ├── data/
│   │   │   ├── local/          # Room Database
│   │   │   │   ├── dao/        # Database Access Objects
│   │   │   │   └── entity/     # Database Models
│   │   │   ├── remote/         # Supabase Configuration
│   │   │   └── repository/     # Data Repositories
│   │   ├── domain/
│   │   │   └── model/          # Domain Models
│   │   ├── ui/
│   │   │   ├── auth/           # Login & SignUp
│   │   │   ├── main/           # Main Activity & Fragments
│   │   │   ├── chat/           # Chat Screen
│   │   │   ├── profile/        # Profile Management
│   │   │   └── settings/       # Settings
│   │   └── utils/              # Utilities
│   ├── AndroidManifest.xml
│   └── res/                    # Layout & Resource Files
├── build.gradle
├── settings.gradle
├── SUPABASE_SQL_SETUP.md       # Database Setup Guide
└── README.md
```

---

## 🔧 Build Variants

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

---

## 🐛 Troubleshooting

### Build Fails: "Gradle sync failed"
**Solution:**
1. Go to "File" → "Sync Now"
2. If still fails, check internet connection
3. Try "File" → "Invalidate Caches / Restart"

### App Crashes on Startup
**Solution:**
1. Check Android Studio Logcat for errors
2. Verify Supabase credentials are correct
3. Ensure app has internet permission in AndroidManifest.xml

### "Authentication required" Error
**Solution:**
1. Verify user is signed in
2. Check Supabase RLS policies
3. Confirm database tables exist

### App Runs but No Data Shows
**Solution:**
1. Check if layout files exist in `res/layout/`
2. Verify RecyclerView adapters are implemented
3. Check data is being fetched from Supabase

---

## 📦 Dependencies

All dependencies are managed in `app/build.gradle`:

```gradle
// Core Android
androidx.appcompat
androidx.constraintlayout
com.google.android.material:material

// Kotlin
org.jetbrains.kotlin:kotlin-stdlib
kotlinx.coroutines

// Supabase
io.github.supabase:supabase-kt
io.github.supabase:gotrue-kt
io.github.supabase:postgrest-kt

// Database
androidx.room:room-runtime

// Networking
com.squareup.retrofit2:retrofit
com.squareup.okhttp3:okhttp

// DI
com.google.dagger:hilt-android

// Navigation
androidx.navigation:navigation-fragment-ktx

// Image Loading
com.github.bumptech.glide:glide
```

---

## 🎯 Next Steps

### Phase 1: Core Features (Current)
- ✅ Authentication (Sign Up / Login)
- ✅ User Management
- ✅ Database Setup
- 🔄 **Next: Create Layout Files**

### Phase 2: UI & Layout Files
- Create activity_splash.xml
- Create activity_login.xml
- Create activity_signup.xml
- Create activity_main.xml
- Create fragment_chats.xml
- Create fragment_contacts.xml
- Create fragment_profile.xml
- Create item_chat.xml
- Create item_contact.xml
- Create item_message_sent.xml
- Create item_message_received.xml

### Phase 3: Messaging Features
- Implement text messaging
- Add voice recording
- Add image sharing
- Implement real-time updates

### Phase 4: Advanced Features
- Profile picture upload
- Typing indicators
- Message read receipts
- User status (online/offline)
- Chat notifications
- Settings screen

---

## 🔑 Key Classes & Functions

### Authentication
```kotlin
// SignUp
AuthRepository().signUp(email, password, name, phone)

// Login
AuthRepository().login(email, password)

// Logout
AuthRepository().logout()
```

### Messaging
```kotlin
// Send Message
MessageRepository().sendMessage(
    chatId, senderId, receiverId, content
)

// Get Messages
MessageRepository().getMessages(chatId)

// Mark as Read
MessageRepository().markAsRead(messageId)
```

### User Management
```kotlin
// Search Users
UserRepository().searchUsers(query)

// Update Profile
UserRepository().updateUserProfile(
    userId, name, bio, profilePicUrl
)

// Update Status
UserRepository().updateUserStatus(userId, isOnline)
```

---

## 📚 Documentation Links

- [Supabase Documentation](https://supabase.com/docs)
- [Android Jetpack](https://developer.android.com/jetpack)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Hilt Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)

---

## 💡 Tips & Best Practices

1. **Always check Supabase Dashboard** for data verification
2. **Use Logcat** to debug issues in Android Studio
3. **Test authentication** before testing messaging
4. **Use RLS policies** for security
5. **Implement error handling** in all API calls
6. **Cache data locally** with Room Database
7. **Use Coroutines** for async operations

---

## 🤝 Contributing

To contribute to this project:

1. Create a feature branch: `git checkout -b feature/new-feature`
2. Make your changes
3. Commit: `git commit -am 'Add new feature'`
4. Push: `git push origin feature/new-feature`
5. Create a Pull Request

---

## 📝 License

MIT License - See LICENSE file for details

---

## 📞 Support

For issues or questions:
1. Check the troubleshooting section above
2. Review Supabase documentation
3. Check Android Studio Logcat for errors
4. Create an issue on GitHub

---

## 🎉 You're Ready!

Your UG_TALK messaging app is now set up and ready for development. Start by:

1. ✅ Running the app on an emulator
2. ✅ Creating a test user account
3. ✅ Testing the authentication flow
4. ✅ Creating layout files for better UI
5. ✅ Implementing messaging features

Happy coding! 🚀
