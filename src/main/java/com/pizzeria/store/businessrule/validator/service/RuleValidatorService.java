package com.pizzeria.store.businessrule.validator.service;

import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.businessrule.validator.RuleValidator;
import com.pizzeria.store.service.ItemService;
import com.pizzeria.store.service.impl.ItemServiceImpl;
import com.pizzeria.store.utils.PropertyUtils;

import java.util.*;

public class RuleValidatorService {
    private static final RuleValidatorService INSTANCE = new RuleValidatorService();
    private static Map<MenuItem.Type, List<RuleValidator>> ruleValidators = null;
    private static RuleValidatorFactory ruleValidatorFactory = new RuleValidatorFactory();
    private ItemService itemService = ItemServiceImpl.getInstance();

    private RuleValidatorService() {
    }

    public static RuleValidatorService getInstance() {
        if (ruleValidators == null) {
            synchronized (INSTANCE) {
                initializeRuleValidators();
            }
        }
        return INSTANCE;
    }

    public void validate(MenuItem item) {
        validateMandatoryField(item);
        item = itemService.validateStock(item);
        validateRules(item);
    }

    private void validateRules(MenuItem item) {
        if (ruleValidators.containsKey(item.getType())){
            ruleValidators.get(item.getType()).forEach(validator -> validator.validate(item));
        }
    }

    private void validateMandatoryField(MenuItem item) {
        if (item == null || item.getId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
            throw new InvalidDataException("Invalid order.");
        }
    }

    private static void initializeRuleValidators() {
        if (ruleValidators == null) {
            ruleValidators = new EnumMap<>(MenuItem.Type.class);
            Arrays.stream(MenuItem.Type.values()).forEach(
                    type -> {
                        String ruleIdentifierList = PropertyUtils.getInstance().getPropertyValue(type.toString() + "-rules");
                        if (ruleIdentifierList != null) {
                            for (String ruleIdentifier : ruleIdentifierList.split(",")) {
                                ruleValidatorFactory.getRuleValidator(ruleIdentifier).ifPresent(ruleValidator -> {
                                    if (!ruleValidators.containsKey(type)) {
                                        ruleValidators.put(type, new LinkedList<>());
                                    }
                                    ruleValidators.get(type).add(ruleValidator);
                                });
                            }
                        }
                    });
        }
    }

}
