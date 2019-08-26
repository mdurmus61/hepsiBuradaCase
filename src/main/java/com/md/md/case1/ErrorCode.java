package com.md.md.case1;

public enum ErrorCode {
    OVERLAP("OVERLAP"),
    OUT_OF_AREA("OUT_OF_AREA"),
    WRONG_INPUT("WRONG_INPUT");

    private final String ec;
    ErrorCode(String ec) { this.ec = ec; }
}
