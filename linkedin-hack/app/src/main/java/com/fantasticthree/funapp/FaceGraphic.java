/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fantasticthree.funapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.fantasticthree.funapp.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.face.Face;

import java.util.ArrayList;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
class FaceGraphic extends GraphicOverlay.Graphic {

    private static final String TAG = "FaceGraphic";

    private static final int NUM_TEXT_LINES = 3;
    private static final float TEXT_PADDING = 60;
    private static final int MAX_TEXT_SIZE = 100;
    private static final float FACE_POSITION_RADIUS = 10.0f;
    private static final float ID_TEXT_SIZE = 40.0f;
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private static final int COLOR_CHOICES[] = {
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.MAGENTA,
            Color.RED,
            Color.WHITE,
            Color.YELLOW
    };
    private static int mCurrentColorIndex = 0;

    private Paint mFacePositionPaint;
    private Paint mIdPaint;
    private Paint mBoxPaint;

    private Context mContext;
    private int mWidth;
    private int mHeight;

    private String mCurrentName;
    private String mCurrentHeadline;

    private volatile Face mFace;
    private int mFaceId;
    private float mFaceHappiness;

    FaceGraphic(Context context, GraphicOverlay overlay, String currentName, String currentHeadline) {
        super(overlay);

        mContext = context;
        mWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        Log.d(TAG, "device width=" + mWidth);
        Log.d(TAG, "device height=" + mHeight);

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

        mCurrentHeadline = currentHeadline;
        mCurrentName = currentName;

        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(selectedColor);

        mIdPaint = new Paint();
        mIdPaint.setColor(selectedColor);
        mIdPaint.setTextSize(ID_TEXT_SIZE);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(selectedColor);
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
    }

    void setId(int id) {
        mFaceId = id;
    }


    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }

        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);
//        canvas.drawCircle(x, y, FACE_POSITION_RADIUS, mFacePositionPaint);
//        canvas.drawText("id: " + mFaceId, x + ID_X_OFFSET, y + ID_Y_OFFSET, mIdPaint);
//        canvas.drawText("happiness: " + String.format("%.2f", face.getIsSmilingProbability()), x - ID_X_OFFSET, y - ID_Y_OFFSET, mIdPaint);
//        canvas.drawText("right eye: " + String.format("%.2f", face.getIsRightEyeOpenProbability()), x + ID_X_OFFSET * 2, y + ID_Y_OFFSET * 2, mIdPaint);
//        canvas.drawText("left eye: " + String.format("%.2f", face.getIsLeftEyeOpenProbability()), x - ID_X_OFFSET*2, y - ID_Y_OFFSET*2, mIdPaint);

        // Draws a bounding box around the face.
        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        canvas.drawRect(left, top, right, bottom, mBoxPaint);

        top -= TEXT_PADDING;
        bottom += TEXT_PADDING;

        float topVerticalSpace = top;
        float bottomVerticalSpace = mHeight - bottom;

        float textSize = Math.min(topVerticalSpace > bottomVerticalSpace ? topVerticalSpace / NUM_TEXT_LINES : bottomVerticalSpace / NUM_TEXT_LINES, MAX_TEXT_SIZE);
        float textYPos = topVerticalSpace > bottomVerticalSpace ? (top - (textSize * NUM_TEXT_LINES)) : bottom;

        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);

        ArrayList<String> data = new ArrayList<>();

        if (mCurrentName != null && !mCurrentName.isEmpty()) {
            data.add(mCurrentName);
        }
        if (mCurrentHeadline != null && !mCurrentHeadline.isEmpty()) {
            data.add(mCurrentHeadline);
        }

        for (int i = 0; i < data.size(); i++) {
            canvas.drawText(data.get(i), x, textYPos - (paint.descent() + paint.ascent()) + (textSize * i), paint);
        }

//        canvas.drawText("test", x, textYPos - (paint.descent() + paint.ascent()), paint);
//        canvas.drawText("yoyoyo", x, textYPos - (paint.descent() + paint.ascent()) + textSize, paint);
//        canvas.drawText("hi these are skills", x, textYPos - (paint.descent() + paint.ascent()) + (textSize * 2), paint);
    }
}
