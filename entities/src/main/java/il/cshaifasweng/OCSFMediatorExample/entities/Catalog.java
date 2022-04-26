package il.cshaifasweng.OCSFMediatorExample.entities;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "catalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL)
    private List<Item> items;

    public int getId() {
        return id;
    }
    public void setItems(List<Item> items){ this.items = items;}
    public void addItem(Item item){
        this.items.add(item);
    }

}
