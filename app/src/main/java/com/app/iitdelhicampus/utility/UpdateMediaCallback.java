package com.app.iitdelhicampus.utility;

import android.content.Intent;

import java.io.File;

/**
 * Created by pawan on 14/6/17.
 */

public interface UpdateMediaCallback {


    void updateMediaDetails(int requestCode, int resultCode, Intent data, File filePath);

}
