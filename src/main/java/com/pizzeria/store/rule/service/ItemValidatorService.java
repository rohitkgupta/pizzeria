package com.pizzeria.store.rule.service;

import com.pizzeria.store.entity.Item;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.rule.RuleValidator;
import com.pizzeria.store.service.ItemService;
import com.pizzeria.store.service.impl.ItemServiceImpl;
import com.pizzeria.store.utils.PropertyUtils;

import java.util.LinkedList;
import java.util.List;

public class ItemValidatorService {
    private static final ItemValidatorService INSTANCE = new ItemValidatorService();
    private static List<RuleValidator> ruleValidators = null;
    private static RuleValidatorFactory ruleValidatorFactory = new RuleValidatorFactory();
    private ItemService itemService = ItemServiceImpl.getInstance();

    private ItemValidatorService() {
    }

    public static ItemValidatorService getInstance() {
        if (ruleValidators == null) {
            synchronized (INSTANCE) {
                loadRulesFromPropertyFile();
            }
        }
        return INSTANCE;
    }

    public Item validate(Item item) {
        validateMandatoryField(item);
        item = itemService.validateStock(item);
        return validateRules(item);
    }

    private Item validateRules(Item item) {
        for (RuleValidator validator: ruleValidators) {
            item = validator.validate(item);
        }
        return item;
    }

    private void validateMandatoryField(Item item) {
        if (item == null || item.getId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
            throw new InvalidDataException("Invalid order.");
        }
    }

    private static void loadRulesFromPropertyFile() {
        if (ruleValidators == null) {
            ruleValidators = new LinkedList<>();
            String ruleIdentifierList = PropertyUtils.getInstance().getPropertyValue("rules");
            if (ruleIdentifierList != null) {
                for (String ruleIdentifier : ruleIdentifierList.split(",")) {
                    RuleValidator validator = ruleValidatorFactory.getRuleValidator(ruleIdentifier);
                    if (validator != null) {
                        ruleValidators.add(validator);
                    }
                }
            }
        }
    }

}
