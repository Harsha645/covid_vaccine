package tm;

public class VaccineDataTM {
    private String dose;
    private String vaccine;
    private String date;
    private String mohArea;

    public VaccineDataTM() {

    }

    public VaccineDataTM(String dose, String vaccine, String date, String mohArea) {
        this.dose = dose;
        this.vaccine = vaccine;
        this.date = date;
        this.mohArea = mohArea;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMohArea() {
        return mohArea;
    }

    public void setMohArea(String mohArea) {
        this.mohArea = mohArea;
    }
}
