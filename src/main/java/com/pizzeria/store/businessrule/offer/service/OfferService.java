package com.pizzeria.store.businessrule.offer.service;

import com.pizzeria.store.businessrule.offer.OfferRule;
import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.utils.PropertyUtils;

import java.util.*;

public class OfferService {
    private static final OfferService INSTANCE = new OfferService();
    public static final float DEFAULT_DISCOUNT = 0f;
    private static Map<MenuItem.Type, List<OfferRule>> offerRules = null;
    private static OfferRuleFactory offerRuleFactory = new OfferRuleFactory();

    private OfferService() {
    }

    public static OfferService getInstance() {
        if (offerRules == null) {
            synchronized (INSTANCE) {
                initializeOfferRules();
            }
        }
        return INSTANCE;
    }

    public Float applyOffer(MenuItem item) {
        if (offerRules.containsKey(item.getType())) {
            for (OfferRule offerRule : offerRules.get(item.getType())) {
                Float discount = offerRule.applyOffer(item);
                if (discount != null && discount > DEFAULT_DISCOUNT) {
                    return discount;
                }
            }
        }
        return DEFAULT_DISCOUNT;
    }

    private static void initializeOfferRules() {
        if (offerRules == null) {
            offerRules = new EnumMap<>(MenuItem.Type.class);
            Arrays.stream(MenuItem.Type.values()).forEach(
                    type -> {
                        String ruleIdentifierList = PropertyUtils.getInstance().getPropertyValue(type.toString() + "-offerrules");
                        if (ruleIdentifierList != null) {
                            for (String ruleIdentifier : ruleIdentifierList.split(",")) {
                                offerRuleFactory.getRuleValidator(ruleIdentifier).ifPresent(offerRule -> {
                                    if (!offerRules.containsKey(type)) {
                                        offerRules.put(type, new LinkedList<>());
                                    }
                                    offerRules.get(type).add(offerRule);
                                });
                            }
                        }
                    });
        }
    }

}
