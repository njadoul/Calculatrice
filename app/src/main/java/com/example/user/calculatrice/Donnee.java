package com.example.user.calculatrice;

class Donnee {
    private String calcul;
    private String result;


    Donnee(String calcul, String result) {
        setcalcul(calcul);
        setResult(result);

    }

    String getCalcul() {
        return calcul;
    }

    private void setcalcul(String calcul) {
        this.calcul = calcul;
    }

    String getResult() {
        return result;
    }

    private void setResult(String result) {
        this.result = result;
    }
}
