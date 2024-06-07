package com.pshakhlovich.joining;

import java.math.BigInteger;

public class Main1 {
    public static void main(String[] args) {

        ComplexCalculation complexCalculation = new ComplexCalculation();
        try {
            System.out.println(complexCalculation.calculateResult(
                BigInteger.valueOf(2),
                BigInteger.valueOf(3),
                BigInteger.valueOf(3),
                BigInteger.valueOf(2))
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static class ComplexCalculation {
        public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2)
            throws InterruptedException {
            BigInteger result;
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */

            PowerCalculatingThread thread1 = new PowerCalculatingThread(base1, power1);
            PowerCalculatingThread thread2 = new PowerCalculatingThread(base2, power2);

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            return thread1.getResult().add(thread2.getResult());
        }

        private static class PowerCalculatingThread extends Thread {
            private BigInteger result = BigInteger.ONE;
            private BigInteger base;
            private BigInteger power;

            public PowerCalculatingThread(BigInteger base, BigInteger power) {
                this.base = base;
                this.power = power;
            }

            @Override
            public void run() {

                for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                    result = result.multiply(base);
                }
            }

            public BigInteger getResult() { return result; }
        }
    }
}
