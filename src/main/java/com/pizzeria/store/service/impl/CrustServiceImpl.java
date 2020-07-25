package com.pizzeria.store.service.impl;

import com.pizzeria.store.entity.Crust;
import com.pizzeria.store.service.CrustService;
import com.pizzeria.store.utils.PropertyUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CrustServiceImpl implements CrustService {

    private static final CrustServiceImpl INSTANCE = new CrustServiceImpl();
    private List<Crust> crusts = null;

    private CrustServiceImpl() {
    }

    public static CrustServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Crust> getAvailableCrusts() {
        if (crusts == null) {
            initCrustList();
        }
        return Collections.unmodifiableList(crusts);
    }

    @Override
    public boolean exist(String crust) {
        if (crusts == null) {
            initCrustList();
        }
        for (Crust c : crusts) {
            if (c.getName().equals(crust)) {
                return true;
            }
        }
        return false;
    }

    private void initCrustList() {
        crusts = new LinkedList<>();
        String crustList = PropertyUtils.getInstance().getPropertyValue("crustList");
        if (crustList != null && !crustList.equals("")) {
            for (String crustName :
                    crustList.split(",")) {
                crusts.add(new Crust(crustName));
            }
        }
    }
}
