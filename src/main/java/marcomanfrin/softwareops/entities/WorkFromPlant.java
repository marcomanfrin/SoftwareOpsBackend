package marcomanfrin.softwareops.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "work_from_plant")
public class WorkFromPlant extends Work {

    @ManyToOne(optional = false)
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    public Plant getPlant() {
        return plant;
    }
    public void setPlant(Plant plant) {
        this.plant = plant;
    }
}
