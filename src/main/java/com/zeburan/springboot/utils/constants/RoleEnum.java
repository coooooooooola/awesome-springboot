package com.zeburan.springboot.utils.constants;

public enum RoleEnum {
    ADMIN("1", "管理员"),
    TESTER("10", "测试人员"),

    TESTLEADER("11", "测试主管");


    private final String code;

    private final String roleName;

    RoleEnum(String code, String roleName) {
        this.code = code;
        this.roleName = roleName;
    }

    public String getCode() {
        return code;
    }

    public String getRoleName() {
        return roleName;
    }
}
