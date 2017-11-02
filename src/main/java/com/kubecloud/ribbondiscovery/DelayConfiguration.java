package com.kubecloud.ribbondiscovery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * Created by pchaivong on 11/2/2017 AD.
 */

@Configuration
public class DelayConfiguration {

    private long delayed;

    public DelayConfiguration(){}


    public long getDelayed() {
        return delayed;
    }

    public void setDelayed(long delayed) {
        this.delayed = delayed;
    }
}
