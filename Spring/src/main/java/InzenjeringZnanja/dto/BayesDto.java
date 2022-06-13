package InzenjeringZnanja.dto;

import InzenjeringZnanja.dto.enums.ComputerSymptoms;

public class BayesDto {
    private boolean computerDropped;
    private boolean computerOverclocked;
    private ComputerSymptoms symptom;

    public boolean isComputerOverclocked() {
        return computerOverclocked;
    }

    public void setComputerOverclocked(boolean computerOverclocked) {
        this.computerOverclocked = computerOverclocked;
    }

    public ComputerSymptoms getSymptom() {
        return symptom;
    }

    public void setSymptom(ComputerSymptoms symptom) {
        this.symptom = symptom;
    }

    public boolean isComputerDropped() {
        return computerDropped;
    }

    public void setComputerDropped(boolean computerDropped) {
        this.computerDropped = computerDropped;
    }
}
