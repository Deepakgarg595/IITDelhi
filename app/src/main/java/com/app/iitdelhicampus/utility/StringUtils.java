package com.app.iitdelhicampus.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.SpannableStringBuilder;
import android.text.style.StrikethroughSpan;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Random;

/**
 * Utility class useful when dealing with string objects. This class is a
 * collection of static functions it is not allowed to create instances of this
 * class
 */
public abstract class
StringUtils {

    /**
     * Byte used to pad output.
     */
    protected static final byte PAD_DEFAULT = '='; // Allow static access to default
    private static final String TAG = "StringUtils";
    private static final byte[] DECODE_TABLE = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54,
            55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
            5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
            24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34,
            35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51
    };
    private static String[] colors = {"#FF0000", "#800000", "#808000", "#00FF00", "#008000", "#008080", "#0000FF", "#000080", "#FF00FF", "#800080"};
    protected final byte PAD = PAD_DEFAULT; // inst

    /**
     * @param pStr String object to be tested.
     * @returns true if the given string is null or empty or contains spaces
     * only.
     */
    public static boolean isNullOrEmpty(final String pStr) {
        return pStr == null || pStr.trim().length() == 0 || pStr.trim().equalsIgnoreCase("null") || "".equals(pStr);
    }

    public static boolean isNullOrEmptyOrZero(final String pStr) {
        return pStr == null || pStr.trim().length() == 0 || pStr.trim().equalsIgnoreCase("null") || pStr.trim().equalsIgnoreCase("0");
    }

    /**
     * @param pStr
     * @param pStartIndex or -1 to parse from complete pStr
     * @param pEndIndex
     * @return int value, parsed from pStr or a substring of pStr
     */
    public static int parseInt(String pStr, int pStartIndex, int pEndIndex) {
        if (pStr == null) {
            return 0;
        }
        try {
            if (pStartIndex == -1) {
                return Integer.parseInt(pStr);
            } else {
                return Integer.parseInt(pStr.substring(pStartIndex, pEndIndex));
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public static String capitalize(String pWord) {
        pWord = pWord == null ? "" : pWord;
        if (StringUtils.isNullOrEmpty(pWord)) {
            return pWord;
        }
        String output = Character.toUpperCase(pWord.charAt(0)) + pWord.substring(1);
        return output;
    }

    public static boolean toBool(String value) {
        return value.equals("1");
    }

    public static String getRandomColor() {
        int rnd = new Random().nextInt(colors.length);
        return colors[rnd];
    }

//    public static String getEncData(String txt) {
//        if (StringUtils.isNullOrEmpty(txt)) return "";
//        return Securities.getInstance().encriptedData(txt);
//    }
//
//    public static String getDecData(String txt) {
//        if (StringUtils.isNullOrEmpty(txt)) return "";
//        return Securities.getInstance().decriptedData(txt);
//    }

    public static String generateId() {
        return String.format("%s-%s", System.currentTimeMillis(), random());
    }

    private static int random() {
        return 10000 + new Random().nextInt(9999) + 1;
    }

//    public static String getBase64Data(String rawText) {
//        byte[] data = new byte[0];
//        String base64;
//        try {
//            data = rawText.getBytes(StandardCharsets.UTF_8);
//            base64 = Base64.encodeToString(data, Base64.DEFAULT);
//            if (base64.contains("\n")) {
//                base64.replace("", "\n");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return rawText;
//        }
////        byte[] data = rawText.getBytes(StandardCharsets.UTF_8);
////        String base64 = Base64.encodeToString(data, Base64.DEFAULT_IMAGE);
//        return StringUtils.isNullOrEmpty(base64) ? rawText : base64.trim();
//    }

    public static String getBase64Datanew(String rawText) {
//        return rawText;
        // TODO Dharam commented

        String base64;
        try {
            byte[] data = rawText.getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.DEFAULT);
            if (base64.contains("\n")) {
                base64.replace("", "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return rawText;
        }


//        byte[] data = rawText.getBytes(StandardCharsets.UTF_8);
//        String base64 = Base64.encodeToString(data, Base64.DEFAULT_IMAGE);
        return StringUtils.isNullOrEmpty(base64) ? rawText : base64.trim();
    }

//    public static String getDataFromBase64(String base64data) {
//        byte[] data;
//        String text = null;
//        try {
//            data = Base64.decode(base64data, Base64.DEFAULT);
//            text = new String(data, StandardCharsets.UTF_8);
//            return text;
//        } catch (Exception e) {
//            Log.d(TAG, "decode" + e);
//            return base64data;
//
//        }
//
//    }

    public static byte[] getBytesUtf8(String string) {
        return getBytesUnchecked(string, "UTF_8");
    }

    public static byte[] getBytesUnchecked(String string, String charsetName) {
        if (string == null) {
            return null;
        }
        try {
            return string.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            throw newIllegalStateException(charsetName, e);
        }
    }

    public static boolean isBase64(byte[] arrayOctet) {
        for (int i = 0; i < arrayOctet.length; i++) {
            if (!isBase64(arrayOctet[i]) && !isWhiteSpace(arrayOctet[i])) {
                return false;
            }
        }
        return true;
    }

    private static IllegalStateException newIllegalStateException(String charsetName, UnsupportedEncodingException e) {
        return new IllegalStateException(charsetName + ": " + e);
    }

    public static boolean isBase64(byte octet) {
        return octet == PAD_DEFAULT || (octet >= 0 && octet < DECODE_TABLE.length && DECODE_TABLE[octet] != -1);
    }

    protected static boolean isWhiteSpace(byte byteToCheck) {
        switch (byteToCheck) {
            case ' ':
            case '\n':
            case '\r':
            case '\t':
                return true;
            default:
                return false;
        }
    }

    //public static boolean IsBase64Encoded(String str) {
//    try {
//        String regex = "\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";
//        if (str.matches(regex)) {
//            return true;
//        }
//    } catch (Exception e) {
//        return false;
//    }
//    return false;
//}
    public static boolean hasMoreThanOneDots(String str) {
        int count = 0;
        final int THRESH_HOLD_DOT_COUNT = 1;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                count++;
            }
        }
        if (count > THRESH_HOLD_DOT_COUNT) {
            return true;
        } else
            return false;

    }

    public static String getCurrentAppVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionName;
    }


//    /**
//     * Makes a substring of a string bold.
//     *
//     * @param text       Full text
//     * @param textToBold Text you want to make bold
//     * @return String with bold substring
//     */


//    public static SpannableStringBuilder getStrikeMsg(String message) {
//
//        String pattern1 = " ~";
//        String pattern2 = "~";
//        String text = message.toString();
//
//        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
//        Matcher m = p.matcher(text);
//        String boldMessage = "";
//        while (m.find()) {
////            String boldMessage = String.format("<br>%s</br>", m.group(1));
//            boldMessage = m.group(1);
//
//        }
//
//        return makeSectionOfTextBold(message, boldMessage);
//    }

    private static SpannableStringBuilder makeSectionOfTextBold(String text, String textToBeStrikeThrough) {

        SpannableStringBuilder builder = new SpannableStringBuilder();

        if (textToBeStrikeThrough.length() > 0 && !textToBeStrikeThrough.trim().equals("")) {

            //for counting start/end indexes
            String testText = text.toLowerCase(Locale.US);
            String testTextToBold = textToBeStrikeThrough.toLowerCase(Locale.US);
            int startingIndex = testText.indexOf(testTextToBold);
            int endingIndex = startingIndex + testTextToBold.length();
            //for counting start/end indexes

            if (startingIndex < 0 || endingIndex < 0) {
                return builder.append(text);
            } else if (startingIndex >= 0 && endingIndex >= 0) {
                builder.append(text);
                StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                builder.setSpan(strikethroughSpan, startingIndex, endingIndex, 0);
            }
        } else {
            return builder.append(text);
        }

        return builder;
    }

    public static String getFontStyle(String message) {
        message = message.replace("\n", "<br/>");
        message = String.format(" %s", message);
        if ((message.contains(" *") || message.contains(" _") /*|| message.contains("~") || message.contains("-")*/) && isContainDuplicate(message) && message.length() > 3) {
//            if (message.startsWith(" *") || message.startsWith(" _") /*|| message.startsWith("~") || message.startsWith("-")*/)
            message = message.replace(" *", "&#160;<b>").replace("*", "</b>").replace(" _", "&#160;<i>").replace("_", "</i>")/*.replace(" -", "&#160;<u>").replace("-", "</u>").replace(" ~", "&#160;<span style=\"text-decoration:line-through;\">").replace("~", "</span>")*/;
            return message;
        } else {
            return message;
        }
    }

    private static boolean isContainDuplicate(String msg) {
        boolean flag = msg.startsWith(" **") || msg.startsWith(" __") ? false : true;
        return flag;
    }

    public static String getWebRtcRoomId(String groupId) {
//        14860cxujjc_!ubrcmwifhutncwfjeh_call
        String[] splitGroupId = groupId.split("_");
        if (groupId.contains("call")) {
            //TODO:encrypt domain name
            return String.format("%s%s", splitGroupId[1], ":ciaortc.ciaoim.com");
        } else {
            return null;
        }
    }
}
