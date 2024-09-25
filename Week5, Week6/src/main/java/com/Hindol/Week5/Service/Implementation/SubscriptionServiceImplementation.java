package com.Hindol.Week5.Service.Implementation;

import com.Hindol.Week5.Entity.Enum.SubscriptionPlan;
import com.Hindol.Week5.Entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImplementation {
    public boolean hasAccessBasedOnPlan(UserEntity userEntity, String plan) {
        SubscriptionPlan subscriptionPlan = userEntity.getSubscriptionPlan();
        return subscriptionPlan.name().equals(plan) || subscriptionPlan.ordinal() >= SubscriptionPlan.valueOf(plan).ordinal();
    }
}
