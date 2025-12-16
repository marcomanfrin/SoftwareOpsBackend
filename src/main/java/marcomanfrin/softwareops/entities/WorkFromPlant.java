package marcomanfrin.softwareops.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "work_from_plant")
public class WorkFromPlant extends Work {

    @ManyToOne
    private Plant plant;

    public void setPlant(Plant plant) {
        this.plant = plant;
    }
}
