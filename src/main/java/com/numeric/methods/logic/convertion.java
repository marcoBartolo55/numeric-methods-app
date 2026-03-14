package com.numeric.methods.logic;

public class convertion {
    int decimalNumber;
    int bitsNumber;
    String binaryNumber;
    boolean isNegative;

    public convertion(int decimalNumber, boolean isNegative, int bitsNumber) {
        this.decimalNumber = decimalNumber;
        this.isNegative = isNegative;
        this.bitsNumber = bitsNumber;
    }

    public String decimalToBinary() {
        String binary = Integer.toBinaryString(decimalNumber);

        if (binary.length() > bitsNumber) {
            throw new IllegalArgumentException("El número binario no puede ser representado con el número de bits especificado.");
        }

        while (binary.length() < bitsNumber - 1) {
            binary = "0" + binary;
        }

        String sign = isNegative ? "1" : "0";

    return sign + binary;
    }
}
