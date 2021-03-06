/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.mediatek.wwtv.setting.parental;

import android.content.Context;
import android.media.tv.TvContentRating;
import android.media.tv.TvContentRatingSystemInfo;
import android.media.tv.TvInputManager;

import android.util.Log;
import com.android.tv.util.TvInputManagerHelper;
import com.android.tv.util.TvInputManagerHelper.TvInputManagerInterface;

import com.mediatek.wwtv.setting.parental.ContentRatingSystem.Rating;
import com.mediatek.wwtv.setting.parental.ContentRatingSystem.SubRating;

import java.util.ArrayList;
import java.util.List;

public class ContentRatingsManager {
    private static final String TAG = "ContentRatingsManager";
    private final List<ContentRatingSystem> mContentRatingSystems = new ArrayList<>();

    private final Context mContext;

    private TvInputManagerInterface mTvInputManager;

    public ContentRatingsManager(Context context) {
        this(context, null);
    }
    
    public ContentRatingsManager(
        Context context, TvInputManagerHelper.TvInputManagerInterface tvInputManager) {
        mContext = context;
        this.mTvInputManager = tvInputManager;
    }

    public void update() {
        mContentRatingSystems.clear();

        ContentRatingsParser parser = new ContentRatingsParser(mContext);
        List<TvContentRatingSystemInfo> infos = null;
        if(mTvInputManager == null) {
          TvInputManager manager =
              (TvInputManager) mContext.getSystemService(Context.TV_INPUT_SERVICE);
          infos = manager.getTvContentRatingSystemList();
        } else {
          infos = mTvInputManager.getTvContentRatingSystemList();
        }
        if (infos != null) {
            for (TvContentRatingSystemInfo info : infos) {
                List<ContentRatingSystem> list = parser.parse(info);
                if (list != null) {
                    mContentRatingSystems.addAll(list);
                }
            }
        } else {
            Log.d(TAG, "update: TvContentRatingSystemInfo = null");
        }
    }

    /**
     * Returns a new list of all content rating systems defined.
     */
    public List<ContentRatingSystem> getContentRatingSystems() {
        return new ArrayList<>(mContentRatingSystems);
    }

    /**
     * Returns the long name of a given content rating including descriptors (sub-ratings) that is
     * displayed to the user. For example, "TV-PG (L, S)".
     */
    public String getDisplayNameForRating(TvContentRating canonicalRating) {
        Rating rating = getRating(canonicalRating);
        if (rating == null) {
            return null;
        }
        List<SubRating> subRatings = getSubRatings(rating, canonicalRating);
        if (!subRatings.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (SubRating subRating : subRatings) {
                builder.append(subRating.getTitle());
                builder.append(", ");
            }
            return rating.getTitle() + " (" + builder.substring(0, builder.length() - 2) + ")";
        }
        return rating.getTitle();
    }

    private Rating getRating(TvContentRating canonicalRating) {
        if (canonicalRating == null || mContentRatingSystems == null) {
            return null;
        }
        for (ContentRatingSystem system : mContentRatingSystems) {
            if (system.getDomain().equals(canonicalRating.getDomain())
                    && system.getName().equals(canonicalRating.getRatingSystem())) {
                for (Rating rating : system.getRatings()) {
                    if (rating.getName().equals(canonicalRating.getMainRating())) {
                        return rating;
                    }
                }
            }
        }
        return null;
    }

    private List<SubRating> getSubRatings(Rating rating, TvContentRating canonicalRating) {
        List<SubRating> subRatings = new ArrayList<>();
        if (rating == null || rating.getSubRatings() == null
                || canonicalRating == null || canonicalRating.getSubRatings() == null) {
            return subRatings;
        }
        for (String subRatingString : canonicalRating.getSubRatings()) {
            for (SubRating subRating : rating.getSubRatings()) {
                if (subRating.getName().equals(subRatingString)) {
                    subRatings.add(subRating);
                    break;
                }
            }
        }
        return subRatings;
    }
}
