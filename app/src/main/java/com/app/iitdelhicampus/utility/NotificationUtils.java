//package com.app.fullypetvetapp.utility;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.TaskStackBuilder;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.AssetFileDescriptor;
//import android.graphics.Bitmap;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Handler;
//import android.provider.Settings;
//import android.text.TextUtils;
//import android.util.Log;
//
//import androidx.core.app.NotificationCompat;
//import androidx.core.content.ContextCompat;
//
//import com.app.fullypetvetapp.R;
//import com.app.fullypetvetapp.activity.DashBoardActivity;
//import com.app.fullypetvetapp.application.BaseApplication;
//import com.app.fullypetvetapp.constants.AppConstants;
//import com.app.fullypetvetapp.constants.Constants;
//import com.app.fullypetvetapp.preference.AppPreferences;
//import com.app.fullypetvetapp.ui.BaseActivity;
//import com.bumptech.glide.Glide;
//import com.google.gson.Gson;
//import java.io.IOException;
//import java.util.Locale;
//import java.util.Random;
//
//import static com.app.fullypetvetapp.file.FileMeta.DIR.CHAT;
//
//
////import com.risebeamsuntory.activity.CreateEventNewActivity;
//
//
///**
// * Created by pankajsoni on 03/06/16.
// */
//public class NotificationUtils {
//
//    public static final int NOTIFICATION_ID = 25591;
//    private static final String TAG = NotificationUtils.class.getSimpleName();
//    private static final String NOTIFICATION_CHANNEL = "NOTIFICATION_CHANNEL";
//    private static float density;
//    private static NotificationCompat.Builder mBuilder;
//    private static NotificationCompat.BigPictureStyle s;
//    private static Bitmap resources;
//    private static String name = "notiChannel";
//
//    private static int getNotificationIcon() {
//        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ?
//                R.mipmap.app_icon : R.drawable.ic_launcher_background;
//    }
//
//    public static Bitmap getBitmap() {
//        return resources;
//    }
//
//    public static void setBitmap(Bitmap bitmap) {
//        resources = bitmap;
//    }
//
//    //Message from admin
//    public static void sendAdminMessage(Context mContext, String message, String pType, String latestVersion) {
//        Intent intent = null;
//        boolean isCancel = true;
//        int notificationId = 0;
//       /* pType =1 for admin push
//        pType =2 for Minor update
//        pType =3 for Major update*/
//        AppPreferences ciaoPreferences = AppPreferences.getInstance(mContext);
//        ciaoPreferences.setLatestAppVersion(latestVersion);
//        ciaoPreferences.setpType(pType);
////        if (!StringUtils.getCurrentAppVersion(mContext).equalsIgnoreCase(latestVersion) && pType.equalsIgnoreCase("3")) {
////            intent = new Intent(mContext, UpdateActivity.class);
////            intent.putExtra(AppConstants.EXTRA_UPDATE_PTYPE, pType);
////
////        }
//        switch (pType) {
//            case "1":
//                notificationId = 25593;
////                new Intent(mContext,DashBoardActivity.class);
//                break;
//            case "2":
//                notificationId = 25594;
////                TODO: migrate existing Ciao users.
////                intent= new Intent(context, UpdateUsersActivity.class);
////                new Intent(mContext,DashBoardActivity.class);
//                isCancel = true;
//                break;
//            case "3":
//                notificationId = 25595;
////                new Intent(mContext,DashBoardActivity.class);
//                break;
//            default:
//
//        }
//        NotificationCompat.Builder mBuilderAdmin = notificationInitialize(mContext);
//        mBuilderAdmin.setContentText(message);
//        mBuilderAdmin.setAutoCancel(isCancel);
//        mBuilderAdmin.setPriority(NotificationCompat.PRIORITY_HIGH);
//        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
//        // The stack builder object will contain an artificial back stack for
//        // the
//        // started Activity.
//        // This ensures that navigating backward from the Activity leads out of
//        // your application to the Home screen.
////        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        // Adds the back stack for the Intent (but not the Intent itself)
////        stackBuilder.addParentStack(DashBoardActivity.class);
//        // Adds the Intent that starts the Activity to the top of the stack
////        stackBuilder.addNextIntentWithParentStack(intent);
////        PendingIntent contentIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
//        PendingIntent contentIntent =
//                PendingIntent.getActivity(mContext, 0, intent == null ? new Intent(mContext, DashBoardActivity.class) : intent, flags);
//        mBuilderAdmin.setContentIntent(contentIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//
//
////        mNotificationManager.notify(notificationId, mBuilderAdmin.build());
//
//        if (Build.VERSION.SDK_INT >= 26) {
//            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, name, NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription(mContext.getString(R.string.app_name));
//            mNotificationManager.createNotificationChannel(channel);
//        }
//        mNotificationManager.notify(notificationId, mBuilderAdmin.build());
//
//    }
//
//    public static void sendEventNotification(Context mContext, String message, XMPPNotificationListener.NOTIFICATION_TYPE notificationType, String eventId) {
////        NotificationCompat.Builder mBuilderAdmin = notificationInitialize(mContext);
//
//        NotificationCompat.Builder mBuilderAdmin = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL);
//        mBuilderAdmin.setSmallIcon(R.mipmap.app_icon);
//        mBuilderAdmin.setContentTitle(mContext.getString(R.string.app_name));
//        mBuilderAdmin.setStyle(new NotificationCompat.BigTextStyle()
//                .bigText(message));
//        mBuilderAdmin.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        mBuilderAdmin.setAutoCancel(true);
//        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
//        Intent intent = null;
//        boolean isCancel = true;
//        int notificationId = 0;
//        switch (notificationType) {
//            case attendEvent:
//                notificationId = 25596;
//                intent = new Intent(mContext, ViewGridImageActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.attendEvent);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                break;
//            case commentOnEvent:
//                notificationId = 25597;
//                intent = new Intent(mContext, CommentActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.commentOnEvent);
//                intent.putExtra(Constants.Params.EVENT_ID, eventId);
//                break;
//            case likeEvent:
//                notificationId = 25598;
//                intent = new Intent(mContext, ViewGridImageActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.likeEvent);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                break;
//            case follow:
//                notificationId = 25599;
//                intent = new Intent(mContext, DashBoardActivity.class);
//                break;
//            case newEventNotify:
//                notificationId = 255100;
//                intent = new Intent(mContext, ViewGridImageActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.newEventNotify);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                break;
//
//            case newPrivateEventNotify:
//                notificationId = 255101;
//                intent = new Intent(mContext, EventDetailActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.newPrivateEventNotify);
//                break;
//            case adminNotify:
//                notificationId = 255102;
//                final NotificationMessageModel notificationMessageModel = new Gson().fromJson(message.toString(), NotificationMessageModel.class);
//                mBuilderAdmin.setContentText(notificationMessageModel.getMessage());
//                intent = new Intent(mContext, NotificationDetailActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.adminNotify);
//                intent.putExtra(Constants.Params.NAME, message);
//                break;
//
//
//            case vcard_set:
//                notificationId = 255103;
//                break;
//            case cancelEventNotify:
//                notificationId = 255104;
//                intent = new Intent(mContext, EventDetailActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.cancelEventNotify);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                break;
//            case editEventNotify:
//                notificationId = 255105;
//                intent = new Intent(mContext, EventDetailActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.editEventNotify);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                break;
//            case editPrivateEventNotify:
//                notificationId = 255106;
//                intent = new Intent(mContext, EventDetailActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.editPrivateEventNotify);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                break;
//            case requestToAttendEvent:
//                notificationId = 255107;
//                intent = new Intent(mContext, EventDetailActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.requestToAttendEvent);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                break;
//            case requestAcceptedForEvent:
//                notificationId = 255108;
//                intent = new Intent(mContext, EventDetailActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.requestAcceptedForEvent);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                break;
//            case requestDeclinedForEvent:
//                notificationId = 255109;
//                intent = new Intent(mContext, DashBoardActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.requestDeclinedForEvent);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                break;
//            case waitingEventNotify:
//                notificationId = 255110;
//                intent = new Intent(mContext, EventDetailActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.waitingEventNotify);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                break;
//            case emailNotify:
//                notificationId = 255111;
//                intent = new Intent(mContext, UpdateProfileActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.emailNotify);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                break;
//            case silentNotify:
//                notificationId = 255112;
////                intent = new Intent(mContext, CreateEventNewActivity.class);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.silentNotify);
//                break;
//
//
//            case swapNotify:
//                notificationId = 255113;
//                intent = new Intent(mContext, DashBoardActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.swapNotify);
//                intent.putExtra(Constants.Params.NOTIFICATION_FRAGMENT, "notificationFragment");
//                break;
//            case userTaggedPost:
//                notificationId = 255114;
//                intent = new Intent(mContext, ViewGridImageActivity.class);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.userTaggedPost);
//                break;
//
//            case gameRejected:
//            case gameAccepted:
//            case gameRequested:
//            case gameFinished:
//                notificationId = 255115;
//                intent = new Intent(mContext, TicTacToeStartActivity.class);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
////                intent.putExtra(Constants.EXTRA_NOTIFICATION_TYPE, XMPPNotificationListener.NOTIFICATION_TYPE.userTaggedPost);
//                break;
//            case gamePlay:
////                if(AppPreferences.getInstance(mContext).getCurrentGameId().equals(eventId)){
////                  notificationId=
////                }else
//                notificationId = 255115;
//                intent = new Intent(mContext, GameBoardActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra(Constants.EXTRA_EVENT_ID, eventId);
//                break;
//            default:
//                break;
//        }
//        BaseActivity activity = BaseApplication.getInstance().getCurrentActivity();
//        if (activity instanceof UpdateProfileActivity && !AppPreferences.getInstance(mContext).isLoggedIn()) {
//            return;
//        }
//        // The stack builder object will contain an artificial back stack for
//        // the
//        // started Activity.
//        // This ensures that navigating backward from the Activity leads out of
//        // your application to the Home screen.
////        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        // Adds the back stack for the Intent (but not the Intent itself)
////        stackBuilder.addParentStack(DashBoardActivity.class);
//        // Adds the Intent that starts the Activity to the top of the stack
////        stackBuilder.addNextIntentWithParentStack(intent);
////        PendingIntent contentIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
//
//        PendingIntent contentIntent =
//                PendingIntent.getActivity(mContext, 0, intent == null ? new Intent(mContext, DashBoardActivity.class) : intent, flags);
//        mBuilderAdmin.setContentIntent(contentIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
////        mNotificationManager.notify(notificationId, mBuilderAdmin.build());
//
//
//        if (Build.VERSION.SDK_INT >= 26) {
//            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, name, NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription(mContext.getString(R.string.app_name));
//            mNotificationManager.createNotificationChannel(channel);
//        }
//        mNotificationManager.notify(notificationId, mBuilderAdmin.build());
//    }
//
//    //timeline
//    public static void sendNotificationTimeline(Context context, final String message, Class clz, String jid) {
//
//        if (StringUtils.isEmpty(message)) return;
//        String domain = XmppStringUtils.parseDomain(jid);
//        String userId = domain.contains(Nlh.i().muc()) ?
//                XmppStringUtils.parseResource(jid) : XmppStringUtils.parseLocalpart(jid);
//        String groupId = XmppStringUtils.parseLocalpart(jid);
//        userId = TextUtils.isEmpty(userId) ? groupId : userId;
//
//        if (TextUtils.isEmpty(userId)) return;
//
//        Realm realm = Realm.getDefaultInstance();
//
//        Contact contact = RealmContactDBHelper.getContactByUserId(realm, userId);
//        if (contact == null) {
//            realm.close();
//            return;
//        }
//
//        NotificationCompat.Builder builder = notificationInitialize(context);
//        builder.setContentText(message);
//        setNotificationSettingForContact(context, builder, contact);
//        realm.close();
//
//        sendNotification(context, builder, clz, userId, null);
//    }
//
//    private static void sendNotification(Context context, NotificationCompat.Builder mBuilder, Class clz, String userId, SELECT_MODE selectMode) {
//        final Intent intent = new Intent(context, clz);
//
//
//        if (!TextUtils.isEmpty(userId)) intent.putExtra(AppConstants.EXTRA_USER_ID, userId);
//        if (selectMode != null) {
//            intent.putExtra(AppConstants.EXTRA_SELECTION_CHOICE_MODE, selectMode);
//        }
//
//        BaseActivity activity = BaseApplication.getInstance().getCurrentActivity();
//
//        if (activity instanceof ChatWindowActivity
//                && ChatWindowActivity.class.isAssignableFrom(clz)) {
//            String chatWithId = ((ChatWindowActivity) activity).getChatWithId();
//            if (chatWithId.equals(userId) || chatWithId.equals(userId)) return;
//
//
//        }
//
//        if (activity instanceof HoldUserActivity)
//            return;
//
////        if (activity instanceof AcceptRequestActivity) {
////            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
////                    Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
////        }
//        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
//        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
//        // The stack builder object will contain an artificial back stack for
//        // the
//        // started Activity.
//        // This ensures that navigating backward from the Activity leads out of
//        // your application to the Home screen.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        // Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(DashBoardActivity.class);
//        // Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntentWithParentStack(intent);
//        int uniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
//        PendingIntent contentIntent = stackBuilder.getPendingIntent(uniqueId, PendingIntent.FLAG_ONE_SHOT);
////        PendingIntent contentIntent =
////                PendingIntent.getActivity(context, 0, intent, flags);
//        mBuilder.setContentIntent(contentIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
////        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//        try {
//            if (Build.VERSION.SDK_INT >= 26) {
//                NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, name, NotificationManager.IMPORTANCE_DEFAULT);
//                channel.setDescription(BaseApplication.getInstance().getString(R.string.app_name));
//                mNotificationManager.createNotificationChannel(channel);
//            }
//            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void setNotificationSettingForContact(Context context, NotificationCompat.Builder mBuilder, Contact contact) {
//        String notificationAppUri;
//        switch (contact.getSelectMode()) {
//            case GROUP:
//            case GROUP_CALL:
//                notificationAppUri = AppPreferences.getInstance(context).getRingtoneUriGroup();
//                break;
//            case CHAT:
//            default:
//                notificationAppUri = AppPreferences.getInstance(context).getRingtoneUri();
//                break;
//        }
//        Uri notificationUriToPlay;
//
//        String notificationUri = contact.getNotificationSoundPath();
//        if (notificationUri != null) {
//            notificationUriToPlay = Uri.parse(contact.getNotificationSoundPath());
//        } else if (!TextUtils.isEmpty(notificationAppUri)) {
//            notificationUriToPlay = Uri.parse(notificationAppUri);
//        } else {
//            notificationUriToPlay = Settings.System.DEFAULT_NOTIFICATION_URI;
//        }
//        int defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;
//        mBuilder.setDefaults(defaults);
//        mBuilder.setSound(notificationUriToPlay);
//    }
//
//    public static void sendOneToOneNotification(final Context context, final String message, Class clz,
//                                                final String userId, SELECT_MODE selectMode,
//                                                RealmMessage.MSG_TYPE msgType) {
//
//        sendGroupChatNotification(context, message, clz, userId, selectMode, msgType, null);
//    }
//
//    //single chat, group, broadcast, secret chat notification.
//    public static void sendGroupChatNotification(final Context context, final String message, Class clz,
//                                                 final String userId, SELECT_MODE selectMode,
//                                                 final RealmMessage.MSG_TYPE msgType, String groupMemberId) {
//
//        if (MessageTimeCache.getInstance().getLastMessageTime(userId)) return;
//
//        density = context.getResources().getDisplayMetrics().density;
//        Realm realm = Realm.getDefaultInstance();
//        boolean isGroupChat = selectMode == SELECT_MODE.GROUP;
//        final Contact contact = RealmContactDBHelper.getContactByUserId(realm, userId);
//        AppPreferences preferences = AppPreferences.getInstance(context);
//        Contact member = RealmContactDBHelper.getContactByUserId(realm, groupMemberId);
//        if (contact == null) {
//            realm.close();
//            return;
//        }
//        if (contact.isMute())
//            return;
//        mBuilder = notificationInitialize(context);
//        setNotificationSettingForContact(context, mBuilder, contact);
////        TODO: tone for a particular contact disabled (19-12-2016)
////        setNotificationSettingForContact(context, mBuilder, contact);
//
////        File filePath = new File(getProfilePicUrl(contact.getUserId()));
//        final Handler mainHandler = new Handler(context.getMainLooper());
//        final String timeStamp = contact.getAvatarHash();
//        Bitmap bmp;
//
////        Runnable myRunnable = new Runnable() {
////            @Override
////            public void run() {
////                Glide.with(BaseApplication.getInstance().getApplicationContext()).load(getProfilePicUrl(userId)).asBitmap().centerCrop().override((int) (density * 60), (int) (density * 60)).placeholder(R.drawable.default_profile_circle).diskCacheStrategy(DiskCacheStrategy.RESULT).signature(new StringSignature(String.valueOf(timeStamp))).into(new SimpleTarget<Bitmap>() {
////
////                    @Override
////                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
////                        mBuilder.setLargeIcon(resource);
////                        setBitmap(resource);
//////                         s = new NotificationCompat.BigPictureStyle();
//////                            s.bigLargeIcon(resource);
//////                        s.bigPicture(resource);
////                        mBuilder.build();
////                    }
////
////                });
////            } // This is your code
////        };
////        mainHandler.post(myRunnable);
////        mBuilder.setLargeIcon(getBitmap());
////        getProfilePicBitmap(context, contact.getUserId(),
////                R.drawable.default_user_image,contact.getAvatarHash(),mBuilder);
////        mBuilder.setStyle(s);
//
//        if (!preferences.isShowChatMessagePreview() && BaseApplication.getCurrentActivity() == null) {
//
//            mBuilder.setContentTitle("Message from " + contact.getDisplayName());
//            switch (selectMode) {
//                case BROADCAST:
//                    mBuilder.setContentTitle("Broadcast Message");
//                    break;
//                case CHAT:
////                mBuilder.setLargeIcon(bitmap)
////                mBuilder.setLargeIcon(BitmapUtils.getRoundedCircularBitmap(BitmapUtils.getBitmapFromFilepath(filePath.toString()),
////                                (int) density * PROFILE_ICON_SIZE))
//                    mBuilder.setContentTitle("Message from " + contact.getDisplayName());
//                    break;
//                case GROUP:
//                case GROUP_CALL:
////                mBuilder.setLargeIcon(bitmap)
////                mBuilder.setLargeIcon(BitmapUtils.getRoundedCircularBitmap
////                        (BitmapUtils.getBitmapFromFilepath(filePath.toString()),
////                                (int) density * PROFILE_ICON_SIZE))
//                    mBuilder.setContentTitle((String.format("%s %s %s%s%s%s", context.getResources().getString(R.string.message_from), member.getDisplayName(), "@ ", "\"", contact.getDisplayName(), "\"")));
//                    break;
//                case SECRET:
////                setLargeIcon(bitmap)
////                mBuilder.setLargeIcon(BitmapUtils.getRoundedCircularBitmap
////                        (BitmapUtils.getBitmapFromFilepath(filePath.toString()), (int) density * PROFILE_ICON_SIZE))
//                    mBuilder.setContentTitle(contact.getDisplayName())
//                            .setContentText(context.getResources().getString(R.string.you_have_a_new_secret_message));
//                    break;
//                default:
//                    return;
//            }
//
//
//        } else {
//
//            switch (msgType) {
//                case theme:
//                    mBuilder.setContentText(isGroupChat ? String.format(Locale.getDefault(), "%s %s",
//                            member.getDisplayName(), context.getString(R.string._changed_chat_theme))
//                            : context.getString(R.string._changed_chat_theme));
//                    break;
//                case audio:
//                    mBuilder.setContentText(isGroupChat ? String.format(Locale.getDefault(), "%s %s",
//                            member.getDisplayName(), context.getString(R.string.audio))
//                            : context.getString(R.string.audio));
//                    break;
//                case text:
//                    mBuilder.setContentText(isGroupChat ? String.format(Locale.getDefault(), "%s : %s",
//                            member.getDisplayName(), message) : message);
//                    NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//                    bigText.bigText(message);
//                    mBuilder.setStyle(bigText);
//
//                    mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
//
//                    mBuilder.setContentText(isGroupChat ? String.format(Locale.getDefault(), "%s : %s",
//                            member.getDisplayName(), message) : message);
//                    break;
//                case location:
//                    mBuilder.setContentText(isGroupChat ? String.format(Locale.getDefault(), "%s : %s"
//                            , member.getDisplayName(), context.getString(R.string.location))
//                            : context.getString(R.string.location));
//                    break;
//                case file:
//                    mBuilder.setContentText(isGroupChat ? String.format(Locale.getDefault(), "%s : %s"
//                            , member.getDisplayName(), context.getString(R.string.files))
//                            : context.getString(R.string.files));
//                    break;
//                case image:
//                    mBuilder.setContentText(isGroupChat ? String.format(Locale.getDefault(), "%s : %s"
//                            , member.getDisplayName(), context.getString(R.string.image))
//                            : context.getString(R.string.image));
//                    break;
//                case sticker:
//                    mBuilder.setContentText(isGroupChat ? String.format(Locale.getDefault(), "%s : %s",
//                            member.getDisplayName(), context.getString(R.string.stickers))
//                            : context.getString(R.string.stickers));
//                    break;
//                case stickerGif:
//                    mBuilder.setContentText(isGroupChat ? String.format(Locale.getDefault(), "%s : %s",
//                            member.getDisplayName(), context.getString(R.string.stickers_gif))
//                            : context.getString(R.string.stickers_gif));
//                    break;
//                case contact:
//                    mBuilder.setContentText(isGroupChat ? String.format(Locale.getDefault(), "%s : %s",
//                            member.getDisplayName(), context.getString(R.string.contact))
//                            : context.getString(R.string.contact));
//                    break;
//                case pic_change:
//                    mBuilder.setContentText(context.getString(R.string.changed_group_s_icon));
//                    break;
//                case video:
//                    mBuilder.setContentText(isGroupChat ? String.format(Locale.getDefault(), "%s : %s"
//                            , member.getDisplayName(), context.getString(R.string.video))
//                            : context.getString(R.string.video));
//                    break;
//                case subject_change:
//                    mBuilder.setContentText(String.format(Locale.getDefault(), "%s %s",
//                            context.getString(R.string.changed_group_subject_to), contact.getDisplayName()));
//                    break;
//            }
//
//            switch (selectMode) {
//                case BROADCAST:
//                    mBuilder.setContentTitle("Broadcast Message");
//                    break;
//                case CHAT:
////                mBuilder.setLargeIcon(bitmap)
////                mBuilder.setLargeIcon(BitmapUtils.getRoundedCircularBitmap(BitmapUtils.getBitmapFromFilepath(filePath.toString()),
////                                (int) density * PROFILE_ICON_SIZE))
//                    mBuilder.setContentTitle(contact.getDisplayName());
//                    break;
//                case GROUP:
//                case GROUP_CALL:
////                mBuilder.setLargeIcon(bitmap)
////                mBuilder.setLargeIcon(BitmapUtils.getRoundedCircularBitmap
////                        (BitmapUtils.getBitmapFromFilepath(filePath.toString()),
////                                (int) density * PROFILE_ICON_SIZE))
//                    mBuilder.setContentTitle(contact.getDisplayName());
//                    break;
//                case SECRET:
////                setLargeIcon(bitmap)
////                mBuilder.setLargeIcon(BitmapUtils.getRoundedCircularBitmap
////                        (BitmapUtils.getBitmapFromFilepath(filePath.toString()), (int) density * PROFILE_ICON_SIZE))
//                    mBuilder.setContentTitle(contact.getDisplayName())
//                            .setContentText(context.getResources().getString(R.string.you_have_a_new_secret_message));
//                    break;
//                default:
//                    return;
//            }
//        }
//
//        realm.close();
//
//        AppConstants.AppState appState = AppPreferences.getInstance(context).getAppState();
//        switch (appState) {
//            case CONTACT_SYNCED:
//            case GROUP_SYNCED:
//                sendNotification(context, mBuilder, clz, userId, selectMode);
//                break;
//        }
//    }
//
//
//    // group status
//    public static void sendNotificationStatusGroup(Context context, String displayMessage, Class clz, String userId) {
//        Realm realm = Realm.getDefaultInstance();
//        Contact contact = RealmContactDBHelper.getContactByUserId(realm, userId);
//        if (contact == null) {
//            realm.close();
//            return;
//        }
//
//        NotificationCompat.Builder mBuilder = notificationInitialize(context);
////        setNotificationSettingForContact(context,mBuilder);
//        mBuilder.setContentText(displayMessage);
//        mBuilder.setContentTitle(contact.getDisplayName());
//        sendNotification(context, mBuilder, clz, userId, SELECT_MODE.GROUP);
//        realm.close();
//    }
//
//    private static NotificationCompat.Builder notificationInitialize(Context context) {
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
//                        .setSmallIcon(getNotificationIcon())
//                        .setContentTitle(context.getString(R.string.app_name));
////                        .setStyle(new NotificationCompat.BigTextStyle().bigText(displayMessage))
////                        .setContentText(displayMessage);
//
//
////        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(displayMessage));
//        mBuilder.setAutoCancel(true);
//        mBuilder.setColor(ContextCompat.getColor(context, R.color.primary_blue));
//
//
//        String notificationAppUri = AppPreferences.getInstance(context).getRingtoneUriGroup();
//
//        if (!com.chatmodule.krapps.utils.StringUtils.isNullOrEmpty(notificationAppUri)) {
//            mBuilder.setDefaults(Notification.DEFAULT_ALL);
//            mBuilder.setSound(Uri.parse(notificationAppUri));
//        } else {
////            int defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;
////            mBuilder.setDefaults(defaults);
////            int res_sound_id = context.getResources().getIdentifier("sms_tone", "raw", context.getPackageName());
//            mBuilder.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                    + "://" + context.getPackageName() + "/raw/sms_tone"));
////            playBeep(context);
////            mBuilder.setDefaults(Notification.DEFAULT_ALL);
//
//        }
//
//        int vibratePosition = AppPreferences.getInstance(context).getVibration();
//
//        switch (vibratePosition) {
//            case 0:
//                mBuilder.setDefaults(Notification.DEFAULT_LIGHTS);
//                break;
//            case 1:
//                mBuilder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
//                break;
//            case 2:
//                mBuilder.setDefaults(Notification.DEFAULT_LIGHTS);
//                mBuilder.setVibrate(new long[]{0, 700, 100, 700, 100});
//                break;
//            case 3:
//                mBuilder.setDefaults(Notification.DEFAULT_LIGHTS);
//                mBuilder.setVibrate(new long[]{0, 1200, 100, 1200, 100});
//                break;
//        }
//        return mBuilder;
//    }
//
//    private static void playBeep(Context context) {
//        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//
//        MediaPlayer mediaPlayer = new MediaPlayer();
//
//        switch (am.getRingerMode()) {
//            case AudioManager.RINGER_MODE_NORMAL:
//                mediaPlayer.setVolume(1f, 1f);
//                break;
//            default:
//                return;
//        }
//
//        AssetFileDescriptor descriptor;
//        try {
//            descriptor = context.getAssets().openFd("raw/sms_tone.mp3");
//            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
//            descriptor.close();
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            Log.e(TAG, "playBeep: ", e);
//            return;
//        }
//
//        mediaPlayer.setLooping(false);
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                mediaPlayer.release();
//            }
//        });
//        mediaPlayer.start();
//    }
//
//    private static Uri getNotification(Context context) {
//        Uri notification = Uri.parse(AppPreferences.getInstance(context).getRingtoneUriCall());
//        if (notification == null) {
//            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            if (notification == null) {
//                notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//            }
//        }
//        return notification;
//    }
//
//    public static long[] vibrate(Context context) {
//        int vibratePosition = AppPreferences.getInstance(context).getVibrationCall();
//        long[] vibrationCycle = new long[]{0, 0, 0, 0};
//        switch (vibratePosition) {
//            case 0:
//                vibrationCycle = new long[]{0, 0, 0, 0};
//                break;
//            case 1:
//                vibrationCycle = new long[]{0, 1000, 1000};
//                break;
//            case 2:
//                vibrationCycle = new long[]{0, 700, 100, 700, 100};
//                break;
//            case 3:
//                vibrationCycle = new long[]{0, 1200, 100, 1200, 100};
//                break;
//        }
//        return vibrationCycle;
//    }
//
//    /**
//     * Build an incoming call notification.
//     * This notification starts the VectorHomeActivity which is in charge of centralizing the incoming call flow.
//     *
//     * @param context  the context.
//     * @param roomName the room name in which the call is pending.
//     * @param matrixId the matrix id
//     * @param callId   the call id.
//     * @param userId
//     * @return the call notification.
//     */
//    public static Notification buildIncomingCallNotification(Context context, String roomName, String matrixId, String callId, String userId) {
//        final Contact contact = RealmContactDBHelper.getContactByUserId(Realm.getDefaultInstance(), userId);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setWhen(System.currentTimeMillis());
//        builder.setContentTitle(contact.getDisplayName());
//        builder.setContentText(context.getString(R.string.incoming_call));
//        builder.setSmallIcon(getNotificationIcon());
////    builder.setLargeIcon(bitmap);
//        // clear the activity stack to home activity
//        Intent intent = new Intent(context, IncomingCallActivity.class);
//        intent.putExtra(ConversationActivity.EXTRA_MATRIX_ID, matrixId);
//        intent.putExtra(ConversationActivity.EXTRA_CALL_ID, callId);
//        intent.putExtra(AppConstants.START_CONVERSATION_REASON, ConversationFlow.INCOMING_CALL);
//        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
////
////        // Recreate the back stack
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context)
//                .addParentStack(DashBoardActivity.class)
//                .addNextIntent(intent);
//
//
//        // android 4.3 issue
//        // use a generator for the private requestCode.
//        // When using 0, the intent is not created/launched when the user taps on the notification.
//        //
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent((new Random()).nextInt(1000), PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//
//        Notification n = builder.build();
//        n.flags |= Notification.FLAG_SHOW_LIGHTS;
//        n.defaults |= Notification.DEFAULT_LIGHTS;
//
//        return n;
//    }
//
//    /**
//     * Build a pending call notification
//     *
//     * @param context  the context.
//     * @param roomName the room name in which the call is pending.
//     * @param roomId   the room Id
//     * @param matrixId the matrix id
//     * @param callId   the call id.
//     * @param userId
//     * @return the call notification.
//     */
//    public static Notification buildPendingCallNotification(final Context context, String roomName, String roomId, String matrixId, String callId, String userId) {
//
//        final Contact contact = RealmContactDBHelper.getContactByUserId(Realm.getDefaultInstance(), userId);
////        Bitmap mBitmap = BitmapUtils.getTransformedBitmap(FileUtils.getProfilePicBitmap(context, contact.getUserId(),
////                R.drawable.default_user_image, contact.getAvatarHash().toString()));
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
////        FileUtils.getProfilePicBitmap(context, contact.getUserId(),
////                R.drawable.default_user_image, builder);
//        builder.setWhen(System.currentTimeMillis());
//        builder.setContentTitle(contact.getDisplayName());
//        builder.setContentText(context.getString(R.string.call_in_progress));
//        builder.setSmallIcon(getNotificationIcon());
//        try {
//            builder.setLargeIcon(BitmapUtils.getRoundedCircularBitmap(Glide.with(context).load(getProfilePicUrl(userId))
//                    .asBitmap().centerCrop().override((int) (density * 60), (int) (density * 60))
//                    .placeholder(R.drawable.default_profile_circle)
////                .diskCacheStrategy(DiskCacheStrategy.RESULT)
////                .signature(new StringSignature(String.valueOf(timeStamps)))
//                    .into(60, 60).get(), 95));
//        } catch (Exception e) {
//
//        }
//
//        // Build the pending intent for when the notification is clicked
//        Intent roomIntent = new Intent(context, ConversationActivity.class);
//        roomIntent.putExtra(AppConstants.EXTRA_MATRIX_ID, matrixId);
//        roomIntent.putExtra(AppConstants.EXTRA_CALL_ID, callId);
//        roomIntent.putExtra(AppConstants.START_CONVERSATION_REASON, ConversationFlow.OUTGOING_CALL);
////        roomIntent.putExtra(VectorRoomActivity.EXTRA_START_CALL_ID, callId);
//
////        // Recreate the back stack
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context)
//                .addParentStack(DashBoardActivity.class)
//                .addNextIntent(roomIntent);
//
//
//        // android 4.3 issue
//        // use a generator for the private requestCode.
//        // When using 0, the intent is not created/launched when the user taps on the notification.
//        //
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent((new Random()).nextInt(1000), PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//
//        Notification n = builder.build();
//        n.flags |= Notification.FLAG_SHOW_LIGHTS;
//        n.defaults |= Notification.DEFAULT_LIGHTS;
//
//        return n;
//    }
//
//    //    private  void getProfilePicBitmap(final Context context, final String userId, final int defaultImage, String timeStamp, final NotificationCompat.Builder builder) {
////        final String timeStamps = timeStamp != null ? timeStamp : String.valueOf(System.currentTimeMillis());
////        if (!timeStamps.equalsIgnoreCase("0")) {
////            Handler mainHandler = new Handler(context.getMainLooper());
////
////            Runnable myRunnable = new Runnable() {
////                @Override
////                public void run() {
////                    Glide.with(BaseApplication.getInstance().getApplicationContext()).load(getProfilePicUrl(userId)).asBitmap().centerCrop().override((int) (density* 60), (int) (density * 60)).placeholder(defaultImage).diskCacheStrategy(DiskCacheStrategy.RESULT).signature(new StringSignature(String.valueOf(timeStamps))).into(new SimpleTarget<Bitmap>() {
////                        @Override
////                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
////                            builder.setLargeIcon(resource);
////                        }
////
////
////                    });
////                } // This is your code
////            };
////            mainHandler.post(myRunnable);
////
//////            builder.setLargeIcon(BitmapUtils.getRoundedCircularBitmap(BitmapUtils
//////                    .getBitmapFromFilepath(getProfilePicUrl(userId)),(int) (getDensity() * 60)));
////        }
////    }
//    private static String getProfilePicUrl(String userId) {
//        return String.format("%s%s%s%s", /*Nlh.i().dpp(),*/ userId.toLowerCase(), ".", "png");
//    }
//
//
//}