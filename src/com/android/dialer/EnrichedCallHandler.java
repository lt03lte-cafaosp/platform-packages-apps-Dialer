/**
 * Copyright (c) 2016, The Linux Foundation. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *     * Neither the name of The Linux Foundation nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.android.dialer;

import android.content.Context;
import android.telephony.SubscriptionManager;

import org.codeaurora.rcscommon.RcsManager;

/**
 * A singleton class which manages the use of RCSManager
 */
public class EnrichedCallHandler {

    /**
     * Interface to be implemented by the search helper
     */
    public interface DialtactsActivityListener {
        void onListFragmentScrollStateChange();
        void onHideDialpadFragment();
        void onShowDialpadFragment();
    }

    private RcsManager mRcsManager;
    private boolean mIsRcsFeatureEnabled = false;
    private static EnrichedCallHandler sEnrichedCallHandler = null;

    // Messages for RCS capability checks
    public static final int MSG_ENABLE_RCS = 100;
    public static final int MSG_DISABLE_RCS = 101;
    public static final int MSG_CHECK_RCS = 102;
    public static final int MSG_ENABLE_RCS_CHECK_PROGRESS = 103;
    public static final int MSG_DISABLE_RCS_CHECK_PROGRESS = 104;
    public static final int RCS_CHECK_TIMEOUT = 32*1000;

    private EnrichedCallHandler(Context context) {
        mRcsManager = RcsManager.getInstance(context);
        mIsRcsFeatureEnabled = mRcsManager.isEnrichCallFeatureEnabled();
        if (mIsRcsFeatureEnabled) {
            mRcsManager.initialize();
        }
    }

    /**
     * Initialize enriched call handler
     * @param context context
     */
    public static void init(Context context) {
        if (sEnrichedCallHandler == null) {
            sEnrichedCallHandler = new EnrichedCallHandler(context);
        }
    }

    /**
     * Returns the enrich call handler instance
     * @return singleton instance
     */
    public static EnrichedCallHandler getInstance() {
        return sEnrichedCallHandler;
    }

    /**
     * Returns the reference to RCS manager
     * @return rcsmanager
     */
    public RcsManager getRcsManager() {
        return mRcsManager;
    }

    /**
     * Returns the status of the enriched call feature
     * @return is feature enabled
     */
    public boolean isRcsFeatureEnabled() {
        return mIsRcsFeatureEnabled;
    }

    /**
     * get the enriched call capability
     * @return the feature capability
     */
    public boolean isEnrichedCallCapable() {
        int subId = SubscriptionManager.getDefaultVoiceSubId();
        return mRcsManager.isEnrichedCallCapable(subId);
    }

    /**
     * get the enriched call capability
     * @return the feature capability
     */
    public boolean isEnrichedCallCapable(int subId) {
        return mRcsManager.isEnrichedCallCapable(subId);
    }

    /**
     * Initialize the RCSManager if the service is not connected
     */
    public void initializeRcsManager() {
        mRcsManager.initialize();
    }
}
