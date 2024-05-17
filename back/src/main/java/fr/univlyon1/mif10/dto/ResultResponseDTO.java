package fr.univlyon1.mif10.dto;

public class ResultResponseDTO {
    private double transport;
    private double logement;
    private double alimentation;
    private double serviceSocietaux;
    private double divers;

    public ResultResponseDTO(double transport, double logement, double alimentation, double serviceSocietaux, double divers) {
        this.transport = transport;
        this.logement = logement;
        this.alimentation = alimentation;
        this.serviceSocietaux = serviceSocietaux;
        this.divers = divers;
    }

    public double getTransport() {
        return transport;
    }

    public void setTransport(double transport) {
        this.transport = transport;
    }

    public double getLogement() {
        return logement;
    }

    public void setLogement(double logement) {
        this.logement = logement;
    }

    public double getAlimentation() {
        return alimentation;
    }

    public void setAlimentation(double alimentation) {
        this.alimentation = alimentation;
    }

    public double getServiceSocietaux() {
        return serviceSocietaux;
    }

    public void setServiceSocietaux(double serviceSocietaux) {
        this.serviceSocietaux = serviceSocietaux;
    }

    public double getDivers() {
        return divers;
    }

    public void setDivers(double divers) {
        this.divers = divers;
    }
}
