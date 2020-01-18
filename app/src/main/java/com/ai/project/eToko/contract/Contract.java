package com.ai.project.eToko.contract;

import java.util.ArrayList;
import java.util.List;

public abstract class Contract {
    public static List<Contract> contractList = new ArrayList<>();

    public abstract String getTableName();
    public abstract String[] getListColumn();
    public abstract String[] getListPrimaryKey();
    public abstract String[] getListType();

    public static List<Contract> getContractList() {
        if(contractList.size() == 0) {
            contractList.add(new ProductContract());
            contractList.add(new UserContract());
        }
        return contractList;
    }
}
