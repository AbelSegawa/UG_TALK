# Supabase SQL Setup - Step by Step Guide

## Your Supabase Project Details

```
URL: https://nmrxgmnwreatylkhmorc.supabase.co
Publishable Key: sb_publishable_K57BMp09wFfNwcHbSLQGXA_1D8m6-nI
```

---

## ⚡ Quick Start - Copy & Paste SQL Commands

### How to Execute SQL:

1. Go to: https://app.supabase.com
2. Click on your project
3. Left sidebar → **SQL Editor**
4. Click **New Query**
5. Copy and paste the SQL code below
6. Click **Run** button (or Ctrl+Enter)

---

## STEP 1: Create USERS Table

### Copy and Paste This:

```sql
-- ====================================
-- STEP 1: CREATE USERS TABLE
-- ====================================

CREATE TABLE IF NOT EXISTS public.users (
  id UUID PRIMARY KEY DEFAULT auth.uid(),
  email TEXT UNIQUE NOT NULL,
  name TEXT NOT NULL,
  phone TEXT UNIQUE NOT NULL,
  bio TEXT DEFAULT '',
  profile_pic_url TEXT DEFAULT '',
  is_online BOOLEAN DEFAULT false,
  last_seen BIGINT DEFAULT 0,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Add comment to table
COMMENT ON TABLE public.users IS 'Stores user profile information';

-- Add comments to columns
COMMENT ON COLUMN public.users.id IS 'User ID from auth';
COMMENT ON COLUMN public.users.email IS 'User email address';
COMMENT ON COLUMN public.users.name IS 'User display name';
COMMENT ON COLUMN public.users.phone IS 'User phone number (unique)';
COMMENT ON COLUMN public.users.bio IS 'User biography';
COMMENT ON COLUMN public.users.profile_pic_url IS 'URL to profile picture';
COMMENT ON COLUMN public.users.is_online IS 'Online status';
COMMENT ON COLUMN public.users.last_seen IS 'Last seen timestamp in milliseconds';
```

**Expected Result:**
✅ Table "users" created successfully

---

## STEP 2: Enable Row Level Security (RLS) for USERS

### Copy and Paste This:

```sql
-- ====================================
-- STEP 2: ENABLE RLS FOR USERS TABLE
-- ====================================

-- Enable RLS
ALTER TABLE public.users ENABLE ROW LEVEL SECURITY;

-- Policy 1: Anyone can read all users (for discovery)
CREATE POLICY "public_users_select_policy" ON public.users
  FOR SELECT
  USING (true);

-- Policy 2: Users can only update their own profile
CREATE POLICY "users_update_own_policy" ON public.users
  FOR UPDATE
  USING (auth.uid() = id)
  WITH CHECK (auth.uid() = id);

-- Policy 3: Users can only delete their own profile
CREATE POLICY "users_delete_own_policy" ON public.users
  FOR DELETE
  USING (auth.uid() = id);

-- Policy 4: Only auth service can insert users
CREATE POLICY "users_insert_policy" ON public.users
  FOR INSERT
  WITH CHECK (auth.uid() = id);
```

**Expected Result:**
✅ RLS enabled
✅ 4 policies created

---

## STEP 3: Create MESSAGES Table

### Copy and Paste This:

```sql
-- ====================================
-- STEP 3: CREATE MESSAGES TABLE
-- ====================================

CREATE TABLE IF NOT EXISTS public.messages (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  chat_id UUID NOT NULL,
  sender_id UUID NOT NULL,
  receiver_id UUID NOT NULL,
  content TEXT NOT NULL,
  message_type TEXT DEFAULT 'TEXT',
  media_url TEXT DEFAULT '',
  duration BIGINT DEFAULT 0,
  is_read BOOLEAN DEFAULT false,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  
  -- Foreign key constraints
  CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES public.users(id) ON DELETE CASCADE,
  CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES public.users(id) ON DELETE CASCADE,
  CONSTRAINT valid_message_type CHECK (message_type IN ('TEXT', 'IMAGE', 'AUDIO', 'FILE', 'VIDEO'))
);

-- Add comments
COMMENT ON TABLE public.messages IS 'Stores all chat messages';
COMMENT ON COLUMN public.messages.id IS 'Unique message ID';
COMMENT ON COLUMN public.messages.chat_id IS 'Chat ID this message belongs to';
COMMENT ON COLUMN public.messages.sender_id IS 'User ID of sender';
COMMENT ON COLUMN public.messages.receiver_id IS 'User ID of receiver';
COMMENT ON COLUMN public.messages.message_type IS 'Type: TEXT, IMAGE, AUDIO, FILE, VIDEO';
COMMENT ON COLUMN public.messages.duration IS 'Audio duration in milliseconds';
```

**Expected Result:**
✅ Table "messages" created successfully

---

## STEP 4: Create Indexes for MESSAGES (Better Performance)

### Copy and Paste This:

```sql
-- ====================================
-- STEP 4: CREATE INDEXES FOR MESSAGES
-- ====================================

CREATE INDEX idx_messages_chat_id ON public.messages(chat_id);
CREATE INDEX idx_messages_sender_id ON public.messages(sender_id);
CREATE INDEX idx_messages_receiver_id ON public.messages(receiver_id);
CREATE INDEX idx_messages_created_at ON public.messages(created_at DESC);
CREATE INDEX idx_messages_is_read ON public.messages(is_read);
```

**Expected Result:**
✅ 5 indexes created

---

## STEP 5: Enable RLS for MESSAGES

### Copy and Paste This:

```sql
-- ====================================
-- STEP 5: ENABLE RLS FOR MESSAGES
-- ====================================

-- Enable RLS
ALTER TABLE public.messages ENABLE ROW LEVEL SECURITY;

-- Policy 1: Users can read messages they sent or received
CREATE POLICY "messages_select_own_policy" ON public.messages
  FOR SELECT
  USING (
    auth.uid() = sender_id OR auth.uid() = receiver_id
  );

-- Policy 2: Users can only insert messages they are sending
CREATE POLICY "messages_insert_policy" ON public.messages
  FOR INSERT
  WITH CHECK (
    auth.uid() = sender_id
  );

-- Policy 3: Users can update is_read status on messages they received
CREATE POLICY "messages_update_read_policy" ON public.messages
  FOR UPDATE
  USING (
    auth.uid() = receiver_id
  )
  WITH CHECK (
    auth.uid() = receiver_id
  );

-- Policy 4: Users can delete messages they sent
CREATE POLICY "messages_delete_policy" ON public.messages
  FOR DELETE
  USING (
    auth.uid() = sender_id
  );
```

**Expected Result:**
✅ RLS enabled for messages
✅ 4 policies created

---

## STEP 6: Create CHATS Table

### Copy and Paste This:

```sql
-- ====================================
-- STEP 6: CREATE CHATS TABLE
-- ====================================

CREATE TABLE IF NOT EXISTS public.chats (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  participant_ids UUID[] NOT NULL,
  last_message TEXT DEFAULT '',
  last_message_time BIGINT DEFAULT 0,
  unread_count INT DEFAULT 0,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Add comments
COMMENT ON TABLE public.chats IS 'Stores chat conversations';
COMMENT ON COLUMN public.chats.participant_ids IS 'Array of user IDs in this chat';
COMMENT ON COLUMN public.chats.last_message IS 'Preview of last message';
COMMENT ON COLUMN public.chats.last_message_time IS 'Timestamp of last message';
```

**Expected Result:**
✅ Table "chats" created successfully

---

## STEP 7: Create Indexes for CHATS (Better Performance)

### Copy and Paste This:

```sql
-- ====================================
-- STEP 7: CREATE INDEXES FOR CHATS
-- ====================================

CREATE INDEX idx_chats_participant_ids ON public.chats USING GIN (participant_ids);
CREATE INDEX idx_chats_created_at ON public.chats(created_at DESC);
CREATE INDEX idx_chats_updated_at ON public.chats(updated_at DESC);
```

**Expected Result:**
✅ 3 indexes created

---

## STEP 8: Enable RLS for CHATS

### Copy and Paste This:

```sql
-- ====================================
-- STEP 8: ENABLE RLS FOR CHATS
-- ====================================

-- Enable RLS
ALTER TABLE public.chats ENABLE ROW LEVEL SECURITY;

-- Policy 1: Users can read chats they participate in
CREATE POLICY "chats_select_own_policy" ON public.chats
  FOR SELECT
  USING (
    auth.uid() = ANY(participant_ids)
  );

-- Policy 2: Users can create chats
CREATE POLICY "chats_insert_policy" ON public.chats
  FOR INSERT
  WITH CHECK (
    auth.uid() = ANY(participant_ids)
  );

-- Policy 3: Users can update chats they participate in
CREATE POLICY "chats_update_policy" ON public.chats
  FOR UPDATE
  USING (
    auth.uid() = ANY(participant_ids)
  )
  WITH CHECK (
    auth.uid() = ANY(participant_ids)
  );

-- Policy 4: Users can delete chats they participate in
CREATE POLICY "chats_delete_policy" ON public.chats
  FOR DELETE
  USING (
    auth.uid() = ANY(participant_ids)
  );
```

**Expected Result:**
✅ RLS enabled for chats
✅ 4 policies created

---

## STEP 9: Create STORAGE Buckets

### Copy and Paste This:

```sql
-- ====================================
-- STEP 9: CREATE STORAGE BUCKETS
-- ====================================
-- NOTE: This requires Supabase UI, not SQL
-- Go to: Storage → New Bucket
-- Create 2 buckets:
-- 1. "profile-pictures" - PRIVATE
-- 2. "message-media" - PRIVATE
```

**Manual Steps:**
1. Click **Storage** in left sidebar
2. Click **Create a new bucket**
3. Name: `profile-pictures`
4. Check "Private bucket"
5. Click **Create bucket**
6. Repeat for `message-media` bucket

**Expected Result:**
✅ 2 storage buckets created

---

## STEP 10: Create Storage Policies

### Copy and Paste This:

```sql
-- ====================================
-- STEP 10: STORAGE RLS POLICIES
-- ====================================

-- Profile Pictures Bucket Policies
-- Policy 1: Users can upload their own profile pictures
CREATE POLICY "users_can_upload_profile_pic" ON storage.objects
  FOR INSERT TO authenticated
  WITH CHECK (
    bucket_id = 'profile-pictures' AND
    auth.uid()::text = (string_to_array(name, '/'))[1]
  );

-- Policy 2: Users can read all profile pictures
CREATE POLICY "users_can_read_profile_pics" ON storage.objects
  FOR SELECT
  USING (
    bucket_id = 'profile-pictures'
  );

-- Policy 3: Users can update their own profile pictures
CREATE POLICY "users_can_update_profile_pic" ON storage.objects
  FOR UPDATE TO authenticated
  USING (
    bucket_id = 'profile-pictures' AND
    auth.uid()::text = (string_to_array(name, '/'))[1]
  )
  WITH CHECK (
    bucket_id = 'profile-pictures' AND
    auth.uid()::text = (string_to_array(name, '/'))[1]
  );

-- Policy 4: Users can delete their own profile pictures
CREATE POLICY "users_can_delete_profile_pic" ON storage.objects
  FOR DELETE TO authenticated
  USING (
    bucket_id = 'profile-pictures' AND
    auth.uid()::text = (string_to_array(name, '/'))[1]
  );

-- Message Media Bucket Policies
-- Policy 5: Authenticated users can upload media
CREATE POLICY "users_can_upload_media" ON storage.objects
  FOR INSERT TO authenticated
  WITH CHECK (
    bucket_id = 'message-media'
  );

-- Policy 6: Authenticated users can read media
CREATE POLICY "users_can_read_media" ON storage.objects
  FOR SELECT TO authenticated
  USING (
    bucket_id = 'message-media'
  );

-- Policy 7: Users can delete their own media
CREATE POLICY "users_can_delete_media" ON storage.objects
  FOR DELETE TO authenticated
  USING (
    bucket_id = 'message-media' AND
    auth.uid()::text = (string_to_array(name, '/'))[1]
  );
```

**Expected Result:**
✅ 7 storage policies created

---

## STEP 11: Create Helper Functions

### Copy and Paste This:

```sql
-- ====================================
-- STEP 11: HELPER FUNCTIONS
-- ====================================

-- Function to search users by name or phone
CREATE OR REPLACE FUNCTION search_users(search_query TEXT)
RETURNS TABLE (id UUID, email TEXT, name TEXT, phone TEXT, bio TEXT, profile_pic_url TEXT, is_online BOOLEAN, last_seen BIGINT) AS $$
BEGIN
  RETURN QUERY
  SELECT 
    u.id,
    u.email,
    u.name,
    u.phone,
    u.bio,
    u.profile_pic_url,
    u.is_online,
    u.last_seen
  FROM public.users u
  WHERE 
    u.name ILIKE '%' || search_query || '%' OR
    u.phone ILIKE '%' || search_query || '%'
  ORDER BY u.name ASC;
END;
$$ LANGUAGE plpgsql;

-- Function to get chat messages with user info
CREATE OR REPLACE FUNCTION get_chat_messages(p_chat_id UUID, p_limit INT DEFAULT 50)
RETURNS TABLE (
  message_id UUID,
  sender_id UUID,
  sender_name TEXT,
  receiver_id UUID,
  receiver_name TEXT,
  content TEXT,
  message_type TEXT,
  media_url TEXT,
  is_read BOOLEAN,
  created_at TIMESTAMP WITH TIME ZONE
) AS $$
BEGIN
  RETURN QUERY
  SELECT 
    m.id,
    m.sender_id,
    sender.name,
    m.receiver_id,
    receiver.name,
    m.content,
    m.message_type,
    m.media_url,
    m.is_read,
    m.created_at
  FROM public.messages m
  JOIN public.users sender ON m.sender_id = sender.id
  JOIN public.users receiver ON m.receiver_id = receiver.id
  WHERE m.chat_id = p_chat_id
  ORDER BY m.created_at DESC
  LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;

-- Function to get unread message count
CREATE OR REPLACE FUNCTION get_unread_count(p_user_id UUID)
RETURNS TABLE (unread_count BIGINT) AS $$
BEGIN
  RETURN QUERY
  SELECT COUNT(*)
  FROM public.messages
  WHERE receiver_id = p_user_id AND is_read = false;
END;
$$ LANGUAGE plpgsql;
```

**Expected Result:**
✅ 3 helper functions created

---

## Complete SQL Script (Copy All at Once)

If you want to paste everything at once:

```sql
-- ====================================
-- COMPLETE UG_TALK DATABASE SETUP
-- ====================================

-- STEP 1-2: CREATE USERS TABLE WITH RLS
CREATE TABLE IF NOT EXISTS public.users (
  id UUID PRIMARY KEY DEFAULT auth.uid(),
  email TEXT UNIQUE NOT NULL,
  name TEXT NOT NULL,
  phone TEXT UNIQUE NOT NULL,
  bio TEXT DEFAULT '',
  profile_pic_url TEXT DEFAULT '',
  is_online BOOLEAN DEFAULT false,
  last_seen BIGINT DEFAULT 0,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

ALTER TABLE public.users ENABLE ROW LEVEL SECURITY;

CREATE POLICY "public_users_select_policy" ON public.users FOR SELECT USING (true);
CREATE POLICY "users_update_own_policy" ON public.users FOR UPDATE USING (auth.uid() = id) WITH CHECK (auth.uid() = id);
CREATE POLICY "users_delete_own_policy" ON public.users FOR DELETE USING (auth.uid() = id);
CREATE POLICY "users_insert_policy" ON public.users FOR INSERT WITH CHECK (auth.uid() = id);

-- STEP 3-5: CREATE MESSAGES TABLE WITH RLS
CREATE TABLE IF NOT EXISTS public.messages (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  chat_id UUID NOT NULL,
  sender_id UUID NOT NULL,
  receiver_id UUID NOT NULL,
  content TEXT NOT NULL,
  message_type TEXT DEFAULT 'TEXT',
  media_url TEXT DEFAULT '',
  duration BIGINT DEFAULT 0,
  is_read BOOLEAN DEFAULT false,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES public.users(id) ON DELETE CASCADE,
  CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES public.users(id) ON DELETE CASCADE,
  CONSTRAINT valid_message_type CHECK (message_type IN ('TEXT', 'IMAGE', 'AUDIO', 'FILE', 'VIDEO'))
);

CREATE INDEX idx_messages_chat_id ON public.messages(chat_id);
CREATE INDEX idx_messages_sender_id ON public.messages(sender_id);
CREATE INDEX idx_messages_receiver_id ON public.messages(receiver_id);
CREATE INDEX idx_messages_created_at ON public.messages(created_at DESC);

ALTER TABLE public.messages ENABLE ROW LEVEL SECURITY;

CREATE POLICY "messages_select_own_policy" ON public.messages FOR SELECT USING (auth.uid() = sender_id OR auth.uid() = receiver_id);
CREATE POLICY "messages_insert_policy" ON public.messages FOR INSERT WITH CHECK (auth.uid() = sender_id);
CREATE POLICY "messages_update_read_policy" ON public.messages FOR UPDATE USING (auth.uid() = receiver_id) WITH CHECK (auth.uid() = receiver_id);
CREATE POLICY "messages_delete_policy" ON public.messages FOR DELETE USING (auth.uid() = sender_id);

-- STEP 6-8: CREATE CHATS TABLE WITH RLS
CREATE TABLE IF NOT EXISTS public.chats (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  participant_ids UUID[] NOT NULL,
  last_message TEXT DEFAULT '',
  last_message_time BIGINT DEFAULT 0,
  unread_count INT DEFAULT 0,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_chats_participant_ids ON public.chats USING GIN (participant_ids);
CREATE INDEX idx_chats_created_at ON public.chats(created_at DESC);

ALTER TABLE public.chats ENABLE ROW LEVEL SECURITY;

CREATE POLICY "chats_select_own_policy" ON public.chats FOR SELECT USING (auth.uid() = ANY(participant_ids));
CREATE POLICY "chats_insert_policy" ON public.chats FOR INSERT WITH CHECK (auth.uid() = ANY(participant_ids));
CREATE POLICY "chats_update_policy" ON public.chats FOR UPDATE USING (auth.uid() = ANY(participant_ids)) WITH CHECK (auth.uid() = ANY(participant_ids));
CREATE POLICY "chats_delete_policy" ON public.chats FOR DELETE USING (auth.uid() = ANY(participant_ids));
```

---

## Verification Checklist

- [ ] Supabase project created
- [ ] Users table created with RLS
- [ ] Messages table created with RLS
- [ ] Chats table created with RLS
- [ ] All indexes created
- [ ] All RLS policies created
- [ ] Storage buckets created (profile-pictures, message-media)
- [ ] Storage policies created
- [ ] Helper functions created
- [ ] Email authentication enabled
- [ ] Redirect URLs configured
- [ ] Credentials added to Android app

---

## Troubleshooting SQL Errors

### Error: "relation already exists"
**Solution:** Use `IF NOT EXISTS` in CREATE TABLE (already included)

### Error: "permission denied"
**Solution:** Make sure you're logged in with project admin account

### Error: "constraint violation"
**Solution:** Check for unique constraints violations (email, phone must be unique)

### Error: "RLS policy violation"
**Solution:** Check RLS policies are correctly set

---

## Next Steps

1. ✅ Run all SQL commands above
2. ✅ Configure storage buckets in UI
3. ✅ Test with sample data
4. ✅ Connect Android app to Supabase
5. ✅ Implement authentication in app
6. ✅ Implement messaging features

You're all set! 🚀
